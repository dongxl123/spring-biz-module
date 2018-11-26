package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.config.WinSecurityUserConfiguration;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityBaseRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleResourceEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityRoleMapper;
import com.winbaoxian.module.security.repository.WinSecurityRoleRepository;
import com.winbaoxian.module.security.repository.WinSecurityRoleResourceRepository;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WinSecurityRoleService<D extends WinSecurityBaseRoleDTO, E extends WinSecurityBaseRoleEntity> {

    @Resource
    private WinSecurityRoleRepository<E> winSecurityRoleRepository;
    @Resource
    private WinSecurityRoleResourceRepository winSecurityRoleResourceRepository;
    @Resource
    private WinSecurityUserConfiguration winSecurityUserConfiguration;

    public D addRole(D dto) {
        E entity = (E) WinSecurityRoleMapper.INSTANCE.toRoleEntity(dto, winSecurityUserConfiguration.getRoleEntityClass());
        winSecurityRoleRepository.save(entity);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(entity.getId(), dto.getResourceIdList());
            winSecurityRoleResourceRepository.save(roleResourceEntityList);
        }
        entity.setSeq(entity.getId());
        winSecurityRoleRepository.save(entity);
        return getRole(entity.getId());
    }

    @Transactional
    public void deleteRole(Long id) {
        E entity = winSecurityRoleRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityRoleRepository.save(entity);
    }

    @Transactional
    public D updateRole(D dto) {
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        E persistent = winSecurityRoleRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        winSecurityRoleRepository.save(persistent);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            winSecurityRoleResourceRepository.deleteByRoleId(id);
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(id, dto.getResourceIdList());
            winSecurityRoleResourceRepository.save(roleResourceEntityList);
        }
        return getRole(id);
    }

    public D getRole(Long id) {
        D roleDTO = (D) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.findOne(id), winSecurityUserConfiguration.getRoleDTOClass());
        if (roleDTO == null) {
            return null;
        }
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = winSecurityRoleResourceRepository.findByRoleId(id);
        roleDTO.setResourceIdList(trans2ResourceIdList(roleResourceEntityList));
        return roleDTO;
    }

    public List<D> getRoleList() {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(winSecurityRoleRepository.findAllByDeletedFalseOrderBySeqAsc(), winSecurityUserConfiguration.getRoleDTOClass());

    }

    public PaginationDTO<D> getRolePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<E> page = winSecurityRoleRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return (PaginationDTO<D>) PaginationDTO.createNewInstance(page, winSecurityUserConfiguration.getRoleDTOClass());
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