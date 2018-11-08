package com.winbaoxian.module.service;

import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.UserDTO;
import com.winbaoxian.module.model.entity.UserEntity;
import com.winbaoxian.module.model.enums.SecurityErrorEnum;
import com.winbaoxian.module.model.exceptions.SecurityException;
import com.winbaoxian.module.model.mapper.UserMapper;
import com.winbaoxian.module.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public UserDTO addUser(UserDTO dto) {
        UserEntity entity = UserMapper.INSTANCE.toUserEntity(dto);
        return UserMapper.INSTANCE.toUserDTO(userRepository.save(entity));
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity entity = userRepository.findOne(id);
        if (entity == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_DATA_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        userRepository.save(entity);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO dto) {
        if (!userRepository.exists(id)) {
            throw new SecurityException(SecurityErrorEnum.COMMON_DATA_NOT_EXISTS);
        }
        UserEntity entity = UserMapper.INSTANCE.toUserEntity(dto);
        entity.setId(id);
        return UserMapper.INSTANCE.toUserDTO(userRepository.save(entity));
    }

    public UserDTO getUser(Long id) {
        return UserMapper.INSTANCE.toUserDTO(userRepository.findOne(id));

    }

    public List<UserDTO> getUserList() {
        return UserMapper.INSTANCE.toUserDTOList(userRepository.findAllByDeletedFalse());

    }

    public PaginationDTO<UserDTO> getUserPage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<UserEntity> page = userRepository.findAllByDeletedFalse(pageable);
        return PaginationDTO.createNewInstance(page, UserDTO.class);
    }
}
