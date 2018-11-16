package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityRoleDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityRoleMapper;
import com.winbaoxian.module.security.repository.WinSecurityRoleResourceRepository;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleResourceEntity;
import com.winbaoxian.module.security.repository.WinSecurityRoleRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WinSecurityRoleService {

    @Resource
    private WinSecurityRoleRepository roleRepository;
    @Resource
    private WinSecurityRoleResourceRepository roleResourceRepository;

    public WinSecurityRoleDTO addRole(WinSecurityRoleDTO dto) {
        WinSecurityRoleEntity entity = WinSecurityRoleMapper.INSTANCE.toRoleEntity(dto);
        roleRepository.save(entity);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(entity.getId(), dto.getResourceIdList());
            roleResourceRepository.save(roleResourceEntityList);
        }
        entity.setSeq(entity.getId());
        roleRepository.save(entity);
        return getRole(entity.getId());
    }

    @Transactional
    public void deleteRole(Long id) {
        WinSecurityRoleEntity entity = roleRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        roleRepository.save(entity);
    }

    @Transactional
    public WinSecurityRoleDTO updateRole(WinSecurityRoleDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        WinSecurityRoleEntity persistent = roleRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        roleRepository.save(persistent);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            roleResourceRepository.deleteByRoleId(id);
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(id, dto.getResourceIdList());
            roleResourceRepository.save(roleResourceEntityList);
        }
        return getRole(id);
    }

    public WinSecurityRoleDTO getRole(Long id) {
        WinSecurityRoleDTO roleDTO = WinSecurityRoleMapper.INSTANCE.toRoleDTO(roleRepository.findOne(id));
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = roleResourceRepository.findByRoleId(id);
        roleDTO.setResourceIdList(trans2ResourceIdList(roleResourceEntityList));
        return roleDTO;
    }

    public List<WinSecurityRoleDTO> getRoleList() {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(roleRepository.findAllByDeletedFalseOrderBySeqAsc());

    }

    public PaginationDTO<WinSecurityRoleDTO> getRolePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<WinSecurityRoleEntity> page = roleRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return PaginationDTO.createNewInstance(page, WinSecurityRoleDTO.class);
    }

    private List<WinSecurityRoleResourceEntity> trans2RoleResourceEntityList(Long roleId, List<Long> resourceIdList) {
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = new ArrayList<>();
        for (Long resourceId : resourceIdList) {
            WinSecurityRoleResourceEntity entity = new WinSecurityRoleResourceEntity();
            entity.setRoleId(roleId);
            entity.setResourceId(resourceId);
            roleResourceEntityList.add(entity);
        }
        return roleResourceEntityList;
    }

    private List<Long> trans2ResourceIdList(List<WinSecurityRoleResourceEntity> roleResourceEntityList) {
        if (CollectionUtils.isEmpty(roleResourceEntityList)) {
            return null;
        }
        List<Long> resourceIdList = new ArrayList<>();
        for (WinSecurityRoleResourceEntity entity : roleResourceEntityList) {
            resourceIdList.add(entity.getResourceId());
        }
        return resourceIdList;
    }


}
