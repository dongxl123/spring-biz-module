package com.winbaoxian.module.service;

import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.UserDTO;
import com.winbaoxian.module.model.entity.UserEntity;
import com.winbaoxian.module.model.entity.UserRoleEntity;
import com.winbaoxian.module.model.enums.SecurityErrorEnum;
import com.winbaoxian.module.model.exceptions.SecurityException;
import com.winbaoxian.module.model.mapper.UserMapper;
import com.winbaoxian.module.repository.UserRepository;
import com.winbaoxian.module.repository.UserRoleRepository;
import com.winbaoxian.module.utils.BeanMergeUtils;
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
public class UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;

    public UserDTO addUser(UserDTO dto) {
        if (userRepository.existsByUserNameAndDeletedFalse(dto.getUserName())) {
            throw new SecurityException(SecurityErrorEnum.COMMON_USER_EXISTS);
        }
        UserEntity entity = UserMapper.INSTANCE.toUserEntity(dto);
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
        UserEntity entity = userRepository.findOne(id);
        if (entity == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        userRepository.save(entity);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO dto) {
        UserEntity persistent = userRepository.findOne(id);
        if (persistent == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_USER_NOT_EXISTS);
        }
        //登录名更新时，判断是否重复
        if (StringUtils.isNoneBlank(dto.getUserName()) && userRepository.existsByUserNameAndDeletedFalse(dto.getUserName())) {
            throw new SecurityException(SecurityErrorEnum.COMMON_USER_EXISTS);
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

    public UserDTO getUser(Long id) {
        UserDTO userDTO = UserMapper.INSTANCE.toUserDTO(userRepository.findOne(id));
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findByUserId(id);
        userDTO.setRoleIdList(trans2RoleIdList(userRoleEntityList));
        return userDTO;
    }

    public List<UserDTO> getUserList() {
        return UserMapper.INSTANCE.toUserDTOList(userRepository.findAllByDeletedFalse());

    }

    public PaginationDTO<UserDTO> getUserPage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<UserEntity> page = userRepository.findAllByDeletedFalse(pageable);
        return PaginationDTO.createNewInstance(page, UserDTO.class);
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
