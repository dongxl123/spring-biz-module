package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.common.Pagination;
import com.winbaoxian.module.security.model.common.PaginationDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import com.winbaoxian.module.security.model.mapper.WinSecurityResourceMapper;
import com.winbaoxian.module.security.repository.WinSecurityResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WinSecurityResourceService {

    @Resource
    private WinSecurityResourceRepository winSecurityResourceRepository;

    @Transactional
    public WinSecurityResourceDTO addResource(WinSecurityResourceDTO dto) {

        WinSecurityResourceEntity entity = WinSecurityResourceMapper.INSTANCE.toResourceEntity(dto);
        winSecurityResourceRepository.save(entity);
        entity.setSeq(entity.getId());
        winSecurityResourceRepository.save(entity);
        return getResource(entity.getId());
    }

    @Transactional
    public void deleteResource(Long id) {
        WinSecurityResourceEntity entity = winSecurityResourceRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityResourceRepository.save(entity);
    }

    @Transactional
    public WinSecurityResourceDTO updateResource(WinSecurityResourceDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = dto.getId();
        WinSecurityResourceEntity persistent = winSecurityResourceRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        winSecurityResourceRepository.save(persistent);
        return getResource(id);
    }

    public WinSecurityResourceDTO getResource(Long id) {
        return WinSecurityResourceMapper.INSTANCE.toResourceDTO(winSecurityResourceRepository.findOne(id));
    }

    public List<WinSecurityResourceDTO> getResourceList() {
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(winSecurityResourceRepository.findAllByDeletedFalseOrderBySeqAsc());
    }

    public PaginationDTO<WinSecurityResourceDTO> getResourcePage(Pagination pagination) {
        Pageable pageable = Pagination.createPageable(pagination);
        Page<WinSecurityResourceEntity> page = winSecurityResourceRepository.findAllByDeletedFalseOrderBySeqAsc(pageable);
        return PaginationDTO.createNewInstance(page, WinSecurityResourceDTO.class);
    }
}
