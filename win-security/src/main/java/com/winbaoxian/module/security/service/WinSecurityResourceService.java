package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.model.dto.DragAndDropParamDTO;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WinSecurityResourceService {

    @Resource
    private WinSecurityResourceRepository winSecurityResourceRepository;

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
        Specification<WinSecurityResourceEntity> specification = QuerySpecificationUtils.INSTANCE.getSingleSpecification(params, WinSecurityResourceEntity.class);
        Sort sort = new Sort(Sort.Direction.ASC, WinSecurityConstant.SORT_COLUMN_SEQ);
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.findAll(specification, sort);
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
    }

    public List<WinSecurityResourceDTO> getValidResourceListByUserId(Long userId) {
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.getValidResourceListByUserId(userId);
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
    }

    public List<WinSecurityResourceDTO> getAllValidAccessResourceList() {
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.findAllByStatusAndDeletedFalse(WinSecurityStatusEnum.ENABLED.getValue());
        List<WinSecurityResourceDTO> resourceList = WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
        }
        return resourceList.stream().filter(o -> WinSecurityResourceTypeEnum.OTHER.getValue().equals(o.getResourceType()) && StringUtils.isNotBlank(o.getValue())).collect(Collectors.toList());
    }

    public WinSecurityResourceDTO dragAndDropResource(DragAndDropParamDTO params) {
        if (params == null || params.getId() == null || params.getTargetParentId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        Long id = params.getId();
        WinSecurityResourceEntity persistent = winSecurityResourceRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        //pid更改，更新自身和下级元素的globalCode
        if (!params.getTargetParentId().equals(persistent.getPid())) {
            //判断同级下的code是否重复
            if (winSecurityResourceRepository.existsByCodeAndPidAndIdNotAndDeletedFalse(persistent.getCode(), params.getTargetParentId(), id)) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_EXISTS);
            }
            persistent.setGlobalCode(getGlobalCode(persistent.getCode(), params.getTargetParentId()));
            //更新下级所有的globalCode
            updateGlobalCodeByPid(persistent.getId());
        }
        //计算seq
        persistent.setSeq(reCalculateSequence(id, params.getTargetUpId(), params.getTargetDownId()));
        persistent.setPid(params.getTargetParentId());
        winSecurityResourceRepository.save(persistent);
        return getResource(id);
    }

    private Long reCalculateSequence(Long id, Long targetUpId, Long targetDownId) {
        WinSecurityResourceEntity thisResource = winSecurityResourceRepository.findOne(id);
        WinSecurityResourceEntity upResource = null;
        WinSecurityResourceEntity downResource = null;
        if (targetUpId != null) {
            upResource = winSecurityResourceRepository.findOne(targetUpId);
            if (upResource == null) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
            }
        }
        if (targetDownId != null) {
            downResource = winSecurityResourceRepository.findOne(targetDownId);
            if (downResource == null) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
            }
        }
        Long seq = thisResource.getSeq() == null ? NumberUtils.LONG_ZERO : thisResource.getSeq();
        if (upResource == null && downResource == null) {
            //NoThing to do
        } else if (upResource == null && downResource != null) {
            //in the top
            seq = downResource.getSeq() - 1;
        } else if (upResource != null && downResource == null) {
            //in the bottom
            seq = upResource.getSeq() + 1;
        } else {
            if (!upResource.getPid().equals(downResource.getPid()) || upResource.getSeq() > downResource.getSeq()) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_DATA_NOT_SUITABLE);
            }
            //in the middle
            if (seq > upResource.getSeq() && seq < downResource.getSeq()) {
                //suitable seq
            } else if (downResource.getSeq() - upResource.getSeq() > 1) {
                seq = upResource.getSeq() + 1;
            } else {
                seq = upResource.getSeq() + 1;
                //update downResourceList seq
                updateDownResourceListSeq(upResource.getPid(), downResource.getSeq());
            }
        }
        return seq;
    }

    private void updateDownResourceListSeq(Long pid, Long seq) {
        List<WinSecurityResourceEntity> downList = winSecurityResourceRepository.findByPidAndSeqGreaterThanEqualAndDeletedFalse(pid, seq);
        if (CollectionUtils.isNotEmpty(downList)) {
            for (WinSecurityResourceEntity entity : downList) {
                entity.setSeq(entity.getSeq() + 1);
            }
            winSecurityResourceRepository.save(downList);
        }
    }

}
