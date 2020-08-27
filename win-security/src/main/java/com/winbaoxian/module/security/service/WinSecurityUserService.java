package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityUserDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityUserEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityUserRoleEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityUserMapper;
import com.winbaoxian.module.security.repository.WinSecurityUserRepository;
import com.winbaoxian.module.security.repository.WinSecurityUserRoleRepository;
import com.winbaoxian.module.security.service.extension.IUserAddProcessor;
import com.winbaoxian.module.security.service.extension.IUserPageProcessor;
import com.winbaoxian.module.security.service.extension.IUserUpdateProcessor;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.utils.QuerySpecificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WinSecurityUserService {

    @Resource
    private WinSecurityUserRepository winSecurityUserRepository;
    @Resource
    private WinSecurityUserRoleRepository winSecurityUserRoleRepository;
    @Resource
    private WinSecurityAccessService winSecurityAccessService;
    @Autowired(required = false)
    private IUserAddProcessor iUserAddProcessor;
    @Autowired(required = false)
    private IUserUpdateProcessor iUserUpdateProcessor;
    @Autowired(required = false)
    private IUserPageProcessor iUserPageProcessor;

    public WinSecurityUserDTO addUser(WinSecurityUserDTO dto) {
        if (iUserAddProcessor != null) {
            iUserAddProcessor.preProcess(dto);
        }
        if (winSecurityUserRepository.existsByUserNameAndDeletedFalse(dto.getUserName())) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_EXISTS);
        }
        if (iUserAddProcessor != null) {
            iUserAddProcessor.customValidateAfterCommon(dto);
        }
        WinSecurityUserEntity entity = WinSecurityUserMapper.INSTANCE.toUserEntity(dto);
        if (iUserAddProcessor != null) {
            iUserAddProcessor.customMappingAfterCommon(dto, entity);
        }
        winSecurityUserRepository.save(entity);
        //角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            List<WinSecurityUserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(entity.getId(), dto.getRoleIdList());
            winSecurityUserRoleRepository.saveAll(userRoleEntityList);
        }
        WinSecurityUserDTO retDto = getUser(entity.getId());
        if (iUserAddProcessor != null) {
            iUserAddProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public void deleteUser(Long id) {
        WinSecurityUserEntity entity = winSecurityUserRepository.findOneById(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityUserRepository.save(entity);
    }

    public WinSecurityUserDTO updateUser(WinSecurityUserDTO dto) {
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.preProcess(dto);
        }
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        WinSecurityUserEntity persistent = winSecurityUserRepository.findOneById(id);
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
            List<WinSecurityUserRoleEntity> persistentRoleEntityList = winSecurityUserRoleRepository.findByUserId(id);
            Set<Long> persistentRoleIdList = trans2RoleIdList(persistentRoleEntityList);
            if (!SetUtils.isEqualSet(dto.getRoleIdList(), persistentRoleIdList)) {
                winSecurityUserRoleRepository.deleteByUserId(id);
                List<WinSecurityUserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(id, dto.getRoleIdList());
                winSecurityUserRoleRepository.saveAll(userRoleEntityList);
            }
        }
        WinSecurityUserDTO retDto = getUser(id);
        if (iUserUpdateProcessor != null) {
            iUserUpdateProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public WinSecurityUserDTO getUser(Long id) {
        WinSecurityUserDTO userDTO = WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOneById(id));
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(id);
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public List<WinSecurityUserDTO> getUserList(WinSecurityUserDTO params) {
        if (winSecurityAccessService.isAuthenticated()) {
            com.winbaoxian.module.security.model.dto.WinSecurityUserDTO userDTO = winSecurityAccessService.getLoginUserInfo();
            if (userDTO != null && BooleanUtils.isNotTrue(userDTO.getSuperAdminFlag())) {
                params.setSuperAdminFlag(false);
            }
        }
        Specification<WinSecurityUserEntity> specification = QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, WinSecurityUserEntity.class);
        List<WinSecurityUserEntity> userList = winSecurityUserRepository.findAll(specification);
        List<WinSecurityUserDTO> userDTOList = WinSecurityUserMapper.INSTANCE.toUserDTOList(userList);
        return userDTOList;
    }

    public PaginationDTO<WinSecurityUserDTO> getUserPage(WinSecurityUserDTO params, Pagination pagination) {
        if (iUserPageProcessor != null) {
            iUserPageProcessor.preProcess(params);
        }
        if (winSecurityAccessService.isAuthenticated()) {
            WinSecurityUserDTO userDTO = winSecurityAccessService.getLoginUserInfo();
            if (userDTO != null && BooleanUtils.isNotTrue(userDTO.getSuperAdminFlag())) {
                params.setSuperAdminFlag(false);
            }
        }
        if (iUserPageProcessor != null) {
            iUserPageProcessor.customValidateAfterCommon(params);
        }
        Specification<WinSecurityUserEntity> specification = QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, pagination, WinSecurityUserEntity.class);
        Pageable pageable = Pagination.createPageable(pagination);
        Page<WinSecurityUserEntity> page = winSecurityUserRepository.findAll(specification, pageable);
        PaginationDTO<WinSecurityUserDTO> paginationDTO = PaginationDTO.createNewInstance(page, WinSecurityUserDTO.class);
        if (CollectionUtils.isEmpty(paginationDTO.getList())) {
            return paginationDTO;
        }
        List<Long> userIdList = paginationDTO.getList().stream().map(o -> o.getId()).collect(Collectors.toList());
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserIdIn(userIdList);
        Map<Long, List<WinSecurityUserRoleEntity>> userRoleEntityMap = userRoleEntityList.stream().collect(Collectors.groupingBy(o -> o.getUserId()));
        paginationDTO.getList().stream().forEach(o -> {
                    if (userRoleEntityMap.containsKey(o.getId())) {
                        o.setRoleIdList(trans2RoleIdList(userRoleEntityMap.get(o.getId())));
                    }
                }
        );
        return paginationDTO;
    }

    private List<WinSecurityUserRoleEntity> trans2UserRoleEntityList(Long userId, Set<Long> roleIdList) {
        List<WinSecurityUserRoleEntity> userRoleEntityList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            WinSecurityUserRoleEntity userRoleEntity = new WinSecurityUserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            userRoleEntityList.add(userRoleEntity);
        }
        return userRoleEntityList;
    }

    private Set<Long> trans2RoleIdList(List<WinSecurityUserRoleEntity> userRoleEntityList) {
        if (CollectionUtils.isEmpty(userRoleEntityList)) {
            return null;
        }
        return userRoleEntityList.stream().map(o -> o.getRoleId()).collect(Collectors.toSet());
    }

    public WinSecurityUserDTO getUserByUserName(String userName) {
        WinSecurityUserDTO userDTO =  WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOneByUserNameAndDeletedFalse(userName));
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(userDTO.getId());
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public WinSecurityUserDTO getUserByMobile(String mobile) {
        WinSecurityUserDTO userDTO =  WinSecurityUserMapper.INSTANCE.toUserDTO(winSecurityUserRepository.findOneByMobileAndDeletedFalse(mobile));
        if (userDTO == null) {
            return null;
        }
        List<WinSecurityUserRoleEntity> userRoleEntityList = winSecurityUserRoleRepository.findByUserId(userDTO.getId());
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public List<WinSecurityUserDTO> getUserList(Specification<WinSecurityUserEntity> specification) {
        List<WinSecurityUserEntity> userList = winSecurityUserRepository.findAll(specification);
        List<WinSecurityUserDTO> userDTOList = WinSecurityUserMapper.INSTANCE.toUserDTOList(userList);
        return userDTOList;
    }

}