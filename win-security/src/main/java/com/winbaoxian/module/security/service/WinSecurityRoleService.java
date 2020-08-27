package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityRoleDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityRoleEntity;
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
public class WinSecurityRoleService {

    @Resource
    private WinSecurityRoleRepository winSecurityRoleRepository;
    @Resource
    private WinSecurityRoleResourceRepository winSecurityRoleResourceRepository;
    @Autowired(required = false)
    private IRoleAddProcessor iRoleAddProcessor;
    @Autowired(required = false)
    private IRoleUpdateProcessor iRoleUpdateProcessor;

    public WinSecurityRoleDTO addRole(WinSecurityRoleDTO dto) {
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.preProcess(dto);
        }
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.customValidateAfterCommon(dto);
        }
        WinSecurityRoleEntity entity =  WinSecurityRoleMapper.INSTANCE.toRoleEntity(dto);
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.customMappingAfterCommon(dto, entity);
        }
        winSecurityRoleRepository.save(entity);
        //资源
        if (CollectionUtils.isNotEmpty(dto.getResourceIdList())) {
            List<WinSecurityRoleResourceEntity> roleResourceEntityList = trans2RoleResourceEntityList(entity.getId(), dto.getResourceIdList());
            winSecurityRoleResourceRepository.saveAll(roleResourceEntityList);
        }
        entity.setSeq(entity.getId());
        winSecurityRoleRepository.save(entity);
        WinSecurityRoleDTO retDto = getRole(entity.getId());
        if (iRoleAddProcessor != null) {
            iRoleAddProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public void deleteRole(Long id) {
        WinSecurityRoleEntity entity = winSecurityRoleRepository.findOneById(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_ROLE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityRoleRepository.save(entity);
    }

    public WinSecurityRoleDTO updateRole(WinSecurityRoleDTO dto) {
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.preProcess(dto);
        }
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        WinSecurityRoleEntity persistent = winSecurityRoleRepository.findOneById(id);
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
                    winSecurityRoleResourceRepository.saveAll(roleResourceEntityList);
                }
            }
        }
        WinSecurityRoleDTO retDto = getRole(id);
        if (iRoleUpdateProcessor != null) {
            iRoleUpdateProcessor.postProcess(retDto);
        }
        return retDto;
    }

    public WinSecurityRoleDTO getRole(Long id) {
        WinSecurityRoleDTO roleDTO = WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.findOneById(id));
        if (roleDTO == null) {
            return null;
        }
        List<WinSecurityRoleResourceEntity> roleResourceEntityList = winSecurityRoleResourceRepository.findByRoleId(id);
        roleDTO.setResourceIdList(trans2ResourceIdList(roleResourceEntityList));
        return roleDTO;
    }

    public List<WinSecurityRoleDTO> getRoleList(WinSecurityRoleDTO params) {
        Specification<WinSecurityRoleEntity> specification =  QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, WinSecurityRoleEntity.class);
        Sort sort = Sort.by(Sort.Direction.ASC, WinSecurityConstant.SORT_COLUMN_SEQ);
        List<WinSecurityRoleEntity> roleList = winSecurityRoleRepository.findAll(specification, sort);
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(roleList);

    }

    public PaginationDTO<WinSecurityRoleDTO> getRolePage(WinSecurityRoleDTO params, Pagination pagination) {
        Specification<WinSecurityRoleEntity> specification = QuerySpecificationUtils.INSTANCE.getSingleSpecification(params,   WinSecurityRoleEntity.class);
        Pageable pageable = Pagination.createPageable(pagination, WinSecurityConstant.SORT_COLUMN_SEQ, Sort.Direction.ASC.name());
        Page<WinSecurityRoleEntity> page = winSecurityRoleRepository.findAll(specification, pageable);
        return PaginationDTO.createNewInstance(page,  WinSecurityRoleDTO.class);
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

    public List<WinSecurityRoleDTO> getRoleListByUserId(Long userId) {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(winSecurityRoleRepository.getRoleListByUserId(userId));
    }

    public List<WinSecurityRoleDTO> getValidRoleListByUserId(Long userId) {
        return WinSecurityRoleMapper.INSTANCE.toRoleDTOList(winSecurityRoleRepository.getValidRoleListByUserId(userId));
    }

    public WinSecurityRoleDTO getRoleByName(String name) {
        return (WinSecurityRoleDTO) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.findByNameAndDeletedFalse(name));
    }

    public WinSecurityRoleDTO getRoleByUserId(Long userId) {
        return (WinSecurityRoleDTO) WinSecurityRoleMapper.INSTANCE.toRoleDTO(winSecurityRoleRepository.getRoleByUserId(userId));
    }

}
