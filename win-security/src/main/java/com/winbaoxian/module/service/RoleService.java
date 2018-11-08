package com.winbaoxian.module.service;

import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.RoleDTO;
import com.winbaoxian.module.model.entity.RoleEntity;
import com.winbaoxian.module.model.enums.SecurityErrorEnum;
import com.winbaoxian.module.model.exceptions.SecurityException;
import com.winbaoxian.module.model.mapper.RoleMapper;
import com.winbaoxian.module.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    public RoleDTO addRole(RoleDTO dto) {
        RoleEntity entity = RoleMapper.INSTANCE.toRoleEntity(dto);
        return RoleMapper.INSTANCE.toRoleDTO(roleRepository.save(entity));
    }

    @Transactional
    public void deleteRole(Long id) {
        RoleEntity entity = roleRepository.findOne(id);
        if (entity == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_DATA_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        roleRepository.save(entity);
    }

    @Transactional
    public RoleDTO updateRole(Long id, RoleDTO dto) {
        if (!roleRepository.exists(id)) {
            throw new SecurityException(SecurityErrorEnum.COMMON_DATA_NOT_EXISTS);
        }
        RoleEntity entity = RoleMapper.INSTANCE.toRoleEntity(dto);
        entity.setId(id);
        return RoleMapper.INSTANCE.toRoleDTO(roleRepository.save(entity));
    }

    public RoleDTO getRole(Long id) {
        return RoleMapper.INSTANCE.toRoleDTO(roleRepository.findOne(id));

    }

    public List<RoleDTO> getRoleList() {
        return RoleMapper.INSTANCE.toRoleDTOList(roleRepository.findAllByDeletedFalseOrderBySeqAsc());

    }

    public PaginationDTO<RoleDTO> getRolePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<RoleEntity> page = roleRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return PaginationDTO.createNewInstance(page, RoleDTO.class);
    }
}
