package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityResourceTypeEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityResourceMapper;
import com.winbaoxian.module.security.repository.WinSecurityResourceRepository;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.utils.QuerySpecificationUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinSecurityResourceService {

    @Resource
    private WinSecurityResourceRepository winSecurityResourceRepository;

    @Transactional
    public WinSecurityResourceDTO addResource(WinSecurityResourceDTO dto) {
        if (dto.getPid() == null) {
            dto.setPid(0L);
        }
        if (StringUtils.isNotBlank(dto.getCode())) {
            if (winSecurityResourceRepository.existsByCodeAndPidAndDeletedFalse(dto.getCode(), dto.getPid())) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_EXISTS);
            }
            dto.setGlobalCode(getGlobalCode(dto.getCode(), dto.getPid()));
        }
        WinSecurityResourceEntity entity = WinSecurityResourceMapper.INSTANCE.toResourceEntity(dto);
        winSecurityResourceRepository.save(entity);
        entity.setSeq(entity.getId());
        winSecurityResourceRepository.save(entity);
        return getResource(entity.getId());
    }

    private String getGlobalCode(String thisCode, Long pid) {
        String globalCode = thisCode;
        Long thisPid = pid;
        while (thisPid != null && thisPid > 0) {
            WinSecurityResourceEntity pEntity = winSecurityResourceRepository.findOne(thisPid);
            if (pEntity != null) {
                if (StringUtils.isNotBlank(pEntity.getCode())) {
                    globalCode = String.format("%s.%s", pEntity.getCode(), globalCode);
                } else {
                    globalCode = String.format("%s.%s", pEntity.getId(), globalCode);
                }
                thisPid = pEntity.getPid();
            } else {
                break;
            }
        }
        return globalCode;
    }

    @Transactional
    public void deleteResource(Long id) {
        WinSecurityResourceEntity entity = winSecurityResourceRepository.findOne(id);
        if (entity == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        entity.setDeleted(Boolean.TRUE);
        winSecurityResourceRepository.save(entity);
        //删除下级所有数据
        deleteResourceByPid(id);
    }

    private void deleteResourceByPid(Long thisId) {
        List<WinSecurityResourceEntity> nextList = winSecurityResourceRepository.findByPidAndDeletedFalse(thisId);
        if (CollectionUtils.isNotEmpty(nextList)) {
            for (WinSecurityResourceEntity entity : nextList) {
                entity.setDeleted(true);
                deleteResourceByPid(entity.getId());
            }
            winSecurityResourceRepository.save(nextList);
        }
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
        String preCode = persistent.getCode();
        BeanMergeUtils.INSTANCE.copyProperties(dto, persistent);
        if (StringUtils.isNotBlank(persistent.getCode()) && !persistent.getCode().equals(preCode)) {
            if (winSecurityResourceRepository.existsByCodeAndPidAndIdNotAndDeletedFalse(persistent.getCode(), persistent.getPid(), persistent.getId())) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_EXISTS);
            }
            persistent.setGlobalCode(getGlobalCode(persistent.getCode(), persistent.getPid()));
            //更新下级所有的globalCode
            updateGlobalCodeByPid(persistent.getId());
        }
        winSecurityResourceRepository.save(persistent);
        return getResource(id);
    }

    private void updateGlobalCodeByPid(Long thisId) {
        List<WinSecurityResourceEntity> nextList = winSecurityResourceRepository.findByPidAndDeletedFalse(thisId);
        if (CollectionUtils.isNotEmpty(nextList)) {
            for (WinSecurityResourceEntity entity : nextList) {
                if (StringUtils.isNotBlank(entity.getCode())) {
                    entity.setGlobalCode(getGlobalCode(entity.getCode(), entity.getPid()));
                }
                updateGlobalCodeByPid(entity.getId());
            }
            winSecurityResourceRepository.save(nextList);
        }
    }

    public WinSecurityResourceDTO getResource(Long id) {
        return WinSecurityResourceMapper.INSTANCE.toResourceDTO(winSecurityResourceRepository.findOne(id));
    }

    public List<WinSecurityResourceDTO> getResourceList(WinSecurityResourceDTO params) {
        Specification<WinSecurityResourceEntity> specification = QuerySpecificationUtils.INSTANCE.getSingleSpecification(params);
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.findAll(specification);
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
    }

    public List<WinSecurityResourceDTO> getValidResourceListByUserId(Long userId) {
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.getValidResourceListByUserId(userId);
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
    }

    public List<WinSecurityResourceDTO> getAllValidResourceList() {
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.findAllByStatusAndDeletedFalse(WinSecurityStatusEnum.ENABLED.getValue());
        List<WinSecurityResourceDTO> resourceList = WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
        }
        return resourceList.stream().filter(o -> WinSecurityResourceTypeEnum.BUTTON.getValue().equals(o.getResourceType()) && StringUtils.isNotBlank(o.getValue())).collect(Collectors.toList());
    }

}
