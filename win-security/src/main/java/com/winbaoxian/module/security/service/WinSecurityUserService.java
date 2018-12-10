package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.config.loader.WinSecurityClassLoaderConfiguration;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseUserEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityUserRoleEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityUserMapper;
import com.winbaoxian.module.security.repository.WinSecurityUserRepository;
import com.winbaoxian.module.security.repository.WinSecurityUserRoleRepository;
import com.winbaoxian.module.security.service.extension.IUserAddProcessor;
import com.winbaoxian.module.security.service.extension.IUserUpdateProcessor;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.utils.QuerySpecificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinSecurityUserService<D extends WinSecurityBaseUserDTO, E extends WinSecurityBaseUserEntity> {

    @Resource
    private WinSecurityUserRepository<E> winSecurityUserRepository;
    @Resource
    private WinSecurityUserRoleRepository winSecurityUserRoleRepository;
    @Resource
    private WinSecurityClassLoaderConfiguration winSecurityClassLoaderConfiguration;
    @Autowired(required = false)
    private IUserAddProcessor<D, E> iUserAddProcessor;
    @Autowired(required = false)
    private IUserUpdateProcessor<D, E> iUserUpdateProcessor;

    public D addUser(D dto) {
        if (iUserAddProcessor != null) {
            iUserAddProcessor.preProcess(dto);
        }
        if (winSecurityUserRepository.existsByUserNameAndDeletedFalse(dto.getUserName())) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_EXISTS);
        }
        if (iUserAddProcessor != null) {
            iUserAddProcessor.customValidateAfterCommon(dto);
        }
        E entity = (E) WinSecurityUserMapper.INSTANCE.toUserEntity(dto, winSecurityClassLoaderConfiguration.getUserEntityClass());
        if (iUserAddProcessor != null) {
            iUserAddProcessor.customMappingAfterCommon(dto, entity);
        }
        winSecurityUserRepository.save(entity);
        //角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            List<WinSecurityUserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(entity.getId(), dto.getRoleIdList());
            winSecurityUserRoleRepository.save(userRoleEntityList);
        }
        D retDto = getUser(entity.getId());
        if (iUserAddProcessor != null) {
            iUserAddProcessor.postProcess(retDto);
        }
        return retDto;
    }

    @Transactional
    public void deleteUser(Long id) {
        E entity = winSecurityUserRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityUserRepository.save(entity);
    }

    @Transactional
    public D updateUser(D dto) {
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.preProcess(dto);
        }
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        E persistent = winSecurityUserRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        //登录名更新时，判断是否重复
        if (StringUtils.isNoneBlank(dto.getUserName()) && winSecurityUserRepository.existsByUserNameAndIdNotAndDeletedFalse(dto.getUserName(), id)) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_EXISTS);
        }
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.customValidateAfterCommon(dto);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.customMappingAfterCommon(dto, persistent);
        }
        winSecurityUserRepository.save(persistent);
        //角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            winSecurityUserRoleRepository.deleteByUserId(id);
            List<WinSecurityUserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(id, dto.getRoleIdList());
            winSecurityUserRoleRepository.save(userRoleEntityList);
        }
        D retDto = getUser(id);
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public D getUser(Long id) {
        D userDTO = (D) WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOne(id), winSecurityClassLoaderConfiguration.getUserDTOClass());
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(id);
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public List<D> getUserList(D params) {
        Specification<E> specification = (Specification<E>) QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, winSecurityClassLoaderConfiguration.getUserEntityClass());
        List<E> userList = winSecurityUserRepository.findAll(specification);
        return WinSecurityUserMapper.INSTANCE.toUserDTOList(userList, winSecurityClassLoaderConfiguration.getUserDTOClass());
    }

    public PaginationDTO<D> getUserPage(D params, Pagination pagination) {
        Specification<E> specification = (Specification<E>) QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, winSecurityClassLoaderConfiguration.getUserEntityClass());
        Pageable pageable = Pagination.createPageable(pagination);
        Page<E> page = winSecurityUserRepository.findAll(specification, pageable);
        return (PaginationDTO<D>) PaginationDTO.createNewInstance(page, winSecurityClassLoaderConfiguration.getUserDTOClass());
    }

    private List<WinSecurityUserRoleEntity> trans2UserRoleEntityList(Long userId, List<Long> roleIdList) {
        List<WinSecurityUserRoleEntity> userRoleEntityList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            WinSecurityUserRoleEntity userRoleEntity = new WinSecurityUserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            userRoleEntityList.add(userRoleEntity);
        }
        return userRoleEntityList;
    }

    private List<Long> trans2RoleIdList(List<WinSecurityUserRoleEntity> userRoleEntityList) {
        if (CollectionUtils.isEmpty(userRoleEntityList)) {
            return null;
        }
        return userRoleEntityList.stream().map(o -> o.getRoleId()).collect(Collectors.toList());
    }

    public D getUserByUserName(String userName) {
        D userDTO = (D) WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOneByUserNameAndDeletedFalse(userName), winSecurityClassLoaderConfiguration.getUserDTOClass());
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(userDTO.getId());
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public D getUserByMobile(String mobile) {
        D userDTO = (D) WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOneByMobileAndDeletedFalse(mobile), winSecurityClassLoaderConfiguration.getUserDTOClass());
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(userDTO.getId());
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

}