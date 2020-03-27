package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.config.loader.WinSecurityClassLoaderConfiguration;
import com.winbaoxian.module.security.constant.WinSecurityConstant;
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
import com.winbaoxian.module.security.service.extension.IRoleAddProcessor;
import com.winbaoxian.module.security.service.extension.IRoleUpdateProcessor;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.utils.QuerySpecificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WinSecurityRoleService<D extends WinSecurityBaseRoleDTO, E extends WinSecurityBaseRoleEntity> {

    @Resource
    private WinSecurityRoleRepository<E> winSecurityRoleRepository;
    @Resource
    private WinSecurityRoleResourceRepository winSecurityRoleResourceRepository;
    @Resource
    private WinSecurityClassLoaderConfiguration winSecurityClassLoaderConfiguration;
    @Autowired(required = false)
    private IRoleAddProcessor<D, E> iRoleAddProcessor;
    @Autowired(required = false)
    private IRoleUpdateProcessor<D, E> iRoleUpdateProcessor;

    public D addRole(D dto) {
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.preProcess(dto);
        }
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.customValidateAfterCommon(dto);
        }
        E entity = (E) WinSecurityRoleMapper.INSTANCE.toRoleEntity(dto, winSecurityClassLoaderConfiguration.getRoleEntityClass());
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.customMappingAfterCommon(dto, entity);
        }
        winSecurityRoleRepository.save(entity);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(entity.getId(), dto.getResourceIdList());
            winSecurityRoleResourceRepository.save(roleResourceEntityList);
        }
        entity.setSeq(entity.getId());
        winSecurityRoleRepository.save(entity);
        D retDto = getRole(entity.getId());
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public void deleteRole(Long id) {
        E entity = winSecurityRoleRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityRoleRepository.save(entity);
    }

    public D updateRole(D dto) {
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.preProcess(dto);
        }
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        E persistent = winSecurityRoleRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.customValidateAfterCommon(dto);
        }
        //更新数据
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.customMappingAfterCommon(dto, persistent);
        }
        winSecurityRoleRepository.save(persistent);
        //资源
        if (dto.getResourceIdList() != null) {
            if (dto.getResourceIdList().isEmpty()) {
                winSecurityRoleResourceRepository.deleteByRoleId(id);
            } else {
                List<WinSecurityRoleResourceEntity> persistentResourceEntityList = winSecurityRoleResourceRepository.findByRoleId(id);
                Set<Long> persistentResourceIdList = trans2ResourceIdList(persistentResourceEntityList);
                if (!SetUtils.isEqualSet(dto.getResourceIdList(), persistentResourceIdList)) {
                    winSecurityRoleResourceRepository.deleteByRoleId(id);
                    List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(id, dto.getResourceIdList());
                    winSecurityRoleResourceRepository.save(roleResourceEntityList);
                }
            }
        }
        D retDto = getRole(id);
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public D getRole(Long id) {
        D roleDTO = (D) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.findOne(id), winSecurityClassLoaderConfiguration.getRoleDTOClass());
        if (roleDTO == null) {
            return null;
        }
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = winSecurityRoleResourceRepository.findByRoleId(id);
        roleDTO.setResourceIdList(trans2ResourceIdList(roleResourceEntityList));
        return roleDTO;
    }

    public List<D> getRoleList(D params) {
        Specification<E> specification = (Specification<E>) QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, winSecurityClassLoaderConfiguration.getRoleEntityClass());
        Sort sort = new Sort(Sort.Direction.ASC, WinSecurityConstant.SORT_COLUMN_SEQ);
        List<E> roleList = winSecurityRoleRepository.findAll(specification, sort);
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(roleList, winSecurityClassLoaderConfiguration.getRoleDTOClass());

    }

    public PaginationDTO<D> getRolePage(D params, Pagination pagination) {
        Specification<E> specification = (Specification<E>) QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, winSecurityClassLoaderConfiguration.getRoleEntityClass());
        Pageable pageable = Pagination.createPageable(pagination, WinSecurityConstant.SORT_COLUMN_SEQ, Sort.Direction.ASC.name());
        Page<E> page = winSecurityRoleRepository.findAll(specification, pageable);
        return (PaginationDTO<D>) PaginationDTO.createNewInstance(page, winSecurityClassLoaderConfiguration.getRoleDTOClass());
    }

    private List<WinSecurityRoleResourceEntity> trans2RoleResourceEntityList(Long roleId, Set<Long> resourceIdList) {
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = new ArrayList<>();
        for (Long resourceId : resourceIdList) {
            WinSecurityRoleResourceEntity entity = new WinSecurityRoleResourceEntity();
            entity.setRoleId(roleId);
            entity.setResourceId(resourceId);
            roleResourceEntityList.add(entity);
        }
        return roleResourceEntityList;
    }

    private Set<Long> trans2ResourceIdList(List<WinSecurityRoleResourceEntity> roleResourceEntityList) {
        if (CollectionUtils.isEmpty(roleResourceEntityList)) {
            return null;
        }
        Set<Long> resourceIdList = new HashSet<>();
        for (WinSecurityRoleResourceEntity entity : roleResourceEntityList) {
            resourceIdList.add(entity.getResourceId());
        }
        return resourceIdList;
    }

    public List<D> getRoleListByUserId(Long userId) {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(winSecurityRoleRepository.getRoleListByUserId(userId), winSecurityClassLoaderConfiguration.getRoleDTOClass());
    }

    public List<D> getValidRoleListByUserId(Long userId) {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(winSecurityRoleRepository.getValidRoleListByUserId(userId), winSecurityClassLoaderConfiguration.getRoleDTOClass());
    }

    public D getRoleByName(String name) {
        return (D) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.findByNameAndDeletedFalse(name), winSecurityClassLoaderConfiguration.getRoleDTOClass());
    }

    public D getRoleByUserId(Long userId) {
        return (D) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.getRoleByUserId(userId), winSecurityClassLoaderConfiguration.getRoleDTOClass());
    }

}
