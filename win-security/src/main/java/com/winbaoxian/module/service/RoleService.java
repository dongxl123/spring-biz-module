package com.winbaoxian.module.service;

import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.RoleDTO;
import com.winbaoxian.module.model.entity.RoleEntity;
import com.winbaoxian.module.model.entity.RoleResourceEntity;
import com.winbaoxian.module.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.model.exceptions.WinSecurityException;
import com.winbaoxian.module.model.mapper.RoleMapper;
import com.winbaoxian.module.repository.RoleRepository;
import com.winbaoxian.module.repository.RoleResourceRepository;
import com.winbaoxian.module.utils.BeanMergeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleResourceRepository roleResourceRepository;

    public RoleDTO addRole(RoleDTO dto) {
        RoleEntity entity = RoleMapper.INSTANCE.toRoleEntity(dto);
        roleRepository.save(entity);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            List<RoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(entity.getId(), dto.getResourceIdList());
            roleResourceRepository.save(roleResourceEntityList);
        }
        entity.setSeq(entity.getId());
        roleRepository.save(entity);
        return getRole(entity.getId());
    }

    @Transactional
    public void deleteRole(Long id) {
        RoleEntity entity = roleRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        roleRepository.save(entity);
    }

    @Transactional
    public RoleDTO updateRole(Long id, RoleDTO dto) {
        RoleEntity persistent = roleRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        roleRepository.save(persistent);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            roleResourceRepository.deleteByRoleId(id);
            List<RoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(id, dto.getResourceIdList());
            roleResourceRepository.save(roleResourceEntityList);
        }
        return getRole(id);
    }

    public RoleDTO getRole(Long id) {
        RoleDTO roleDTO = RoleMapper.INSTANCE.toRoleDTO(roleRepository.findOne(id));
        List<RoleResourceEntity> roleResourceEntityList = roleResourceRepository.findByRoleId(id);
        roleDTO.setResourceIdList(trans2ResourceIdList(roleResourceEntityList));
        return roleDTO;
    }

    public List<RoleDTO> getRoleList() {
        return RoleMapper.INSTANCE.toRoleDTOList(roleRepository.findAllByDeletedFalseOrderBySeqAsc());

    }

    public PaginationDTO<RoleDTO> getRolePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<RoleEntity> page = roleRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return PaginationDTO.createNewInstance(page, RoleDTO.class);
    }

    private List<RoleResourceEntity> trans2RoleResourceEntityList(Long roleId, List<Long> resourceIdList) {
        List<RoleResourceEntity> roleResourceEntityList = new ArrayList<>();
        for (Long resourceId : resourceIdList) {
            RoleResourceEntity entity = new RoleResourceEntity();
            entity.setRoleId(roleId);
            entity.setResourceId(resourceId);
            roleResourceEntityList.add(entity);
        }
        return roleResourceEntityList;
    }

    private List<Long> trans2ResourceIdList(List<RoleResourceEntity> roleResourceEntityList) {
        if (CollectionUtils.isEmpty(roleResourceEntityList)) {
            return null;
        }
        List<Long> resourceIdList = new ArrayList<>();
        for (RoleResourceEntity entity : roleResourceEntityList) {
            resourceIdList.add(entity.getResourceId());
        }
        return resourceIdList;
    }


}
