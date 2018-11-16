package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.config.UserConfiguration;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.dto.BaseUserDTO;
import com.winbaoxian.module.security.model.entity.BaseUserEntity;
import com.winbaoxian.module.security.model.entity.UserRoleEntity;
import com.winbaoxian.module.security.model.mapper.UserMapper;
import com.winbaoxian.module.security.repository.UserRepository;
import com.winbaoxian.module.security.repository.UserRoleRepository;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService<D extends BaseUserDTO, E extends BaseUserEntity> {

    @Resource
    private UserRepository<E> userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserConfiguration userConfiguration;

    public D addUser(D dto) {
        if (userRepository.existsByUserNameAndDeletedFalse(dto.getUserName())) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_EXISTS);
        }
        E entity = (E) UserMapper.INSTANCE.toUserEntity(dto, userConfiguration.getUserEntityClass());
        userRepository.save(entity);
        //角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            List<UserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(entity.getId(), dto.getRoleIdList());
            userRoleRepository.save(userRoleEntityList);
        }
        return getUser(entity.getId());
    }

    @Transactional
    public void deleteUser(Long id) {
        E entity = userRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        userRepository.save(entity);
    }

    @Transactional
    public D updateUser(D dto) {
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        E persistent = userRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        //登录名更新时，判断是否重复
        if (StringUtils.isNoneBlank(dto.getUserName()) && userRepository.existsByUserNameAndIdNotAndDeletedFalse(dto.getUserName(), id)) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_USER_EXISTS);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        userRepository.save(persistent);
        //角色
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            userRoleRepository.deleteByUserId(id);
            List<UserRoleEntity> userRoleEntityList = trans2UserRoleEntityList(id, dto.getRoleIdList());
            userRoleRepository.save(userRoleEntityList);
        }
        return getUser(id);
    }

    public D getUser(Long id) {
        D userDTO = (D) UserMapper.INSTANCE.toUserDTO(userRepository.findOne(id), userConfiguration.getUserDTOClass());
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(id);
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public List<D> getUserList() {
        return UserMapper.INSTANCE.toUserDTOList(userRepository.findAllByDeletedFalse(), userConfiguration.getUserDTOClass());
    }

    public PaginationDTO<D> getUserPage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<E> page = userRepository.findAllByDeletedFalse(pageable);
        return (PaginationDTO<D>) PaginationDTO.createNewInstance(page, userConfiguration.getUserDTOClass());
    }

    private List<UserRoleEntity> trans2UserRoleEntityList(Long userId, List<Long> roleIdList) {
        List<UserRoleEntity> userRoleEntityList = new ArrayList<>();
        for (Long roleId : roleIdList) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userId);
            userRoleEntity.setRoleId(roleId);
            userRoleEntityList.add(userRoleEntity);
        }
        return userRoleEntityList;
    }

    private List<Long> trans2RoleIdList(List<UserRoleEntity> userRoleEntityList) {
        if (CollectionUtils.isEmpty(userRoleEntityList)) {
            return null;
        }
        List<Long> roleIdList = new ArrayList<>();
        for (UserRoleEntity entity : userRoleEntityList) {
            roleIdList.add(entity.getRoleId());
        }
        return roleIdList;
    }
}
