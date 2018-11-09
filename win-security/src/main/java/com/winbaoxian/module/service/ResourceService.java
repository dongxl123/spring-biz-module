package com.winbaoxian.module.service;

import com.winbaoxian.module.model.common.Pagination;
import com.winbaoxian.module.model.common.PaginationDTO;
import com.winbaoxian.module.model.dto.ResourceDTO;
import com.winbaoxian.module.model.entity.ResourceEntity;
import com.winbaoxian.module.model.enums.SecurityErrorEnum;
import com.winbaoxian.module.model.exceptions.SecurityException;
import com.winbaoxian.module.model.mapper.ResourceMapper;
import com.winbaoxian.module.repository.ResourceRepository;
import com.winbaoxian.module.utils.BeanMergeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Transactional
    public ResourceDTO addResource(ResourceDTO dto) {
        ResourceEntity entity = ResourceMapper.INSTANCE.toResourceEntity(dto);
        return ResourceMapper.INSTANCE.toResourceDTO(resourceRepository.save(entity));
    }

    @Transactional
    public void deleteResource(Long id) {
        ResourceEntity entity = resourceRepository.findOne(id);
        if (entity == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        resourceRepository.save(entity);
    }

    @Transactional
    public ResourceDTO updateResource(Long id, ResourceDTO dto) {
        ResourceEntity persistent = resourceRepository.findOne(id);
        if (persistent == null) {
            throw new SecurityException(SecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        return ResourceMapper.INSTANCE.toResourceDTO(resourceRepository.save(persistent));
    }

    public ResourceDTO getResource(Long id) {
        return ResourceMapper.INSTANCE.toResourceDTO(resourceRepository.findOne(id));
    }

    public List<ResourceDTO> getResourceList() {
        return ResourceMapper.INSTANCE.toResourceDTOList(resourceRepository.findAllByDeletedFalseOrderBySeqAsc());
    }

    public PaginationDTO<ResourceDTO> getResourcePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<ResourceEntity> page = resourceRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return PaginationDTO.createNewInstance(page, ResourceDTO.class);
    }
}
