package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.ResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.model.entity.ResourceEntity;
import com.winbaoxian.module.security.model.mapper.ResourceMapper;
import com.winbaoxian.module.security.repository.ResourceRepository;
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
        resourceRepository.save(entity);
        entity.setSeq(entity.getId());
        resourceRepository.save(entity);
        return getResource(entity.getId());
    }

    @Transactional
    public void deleteResource(Long id) {
        ResourceEntity entity = resourceRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        resourceRepository.save(entity);
    }

    @Transactional
    public ResourceDTO updateResource(ResourceDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        ResourceEntity persistent = resourceRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        resourceRepository.save(persistent);
        return getResource(id);
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
