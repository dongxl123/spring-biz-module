package com.winbaoxian.module.security.service;

import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.model.dto.BatchUpdateParamDTO;
import com.winbaoxian.module.security.model.dto.DragAndDropParamDTO;
import com.winbaoxian.module.security.model.dto.WinSecurityResourceDTO;
import com.winbaoxian.module.security.model.entity.WinSecurityBaseRoleEntity;
import com.winbaoxian.module.security.model.entity.WinSecurityResourceEntity;
import com.winbaoxian.module.security.model.enums.WinSecurityErrorEnum;
import com.winbaoxian.module.security.model.enums.WinSecurityStatusEnum;
import com.winbaoxian.module.security.model.exceptions.WinSecurityException;
import com.winbaoxian.module.security.model.mapper.WinSecurityResourceMapper;
import com.winbaoxian.module.security.repository.WinSecurityResourceRepository;
import com.winbaoxian.module.security.repository.WinSecurityRoleRepository;
import com.winbaoxian.module.security.utils.BeanMergeUtils;
import com.winbaoxian.module.security.utils.QuerySpecificationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WinSecurityResourceService {

    @Resource
    private WinSecurityResourceRepository winSecurityResourceRepository;
    @Resource
    private WinSecurityRoleRepository winSecurityRoleRepository;

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
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<WinSecurityBaseRoleEntity> roleEntityList = winSecurityRoleRepository.getValidRoleListByUserId(userId);
        List<Long> roleIdList = null;
        if (CollectionUtils.isNotEmpty(roleEntityList)) {
            roleIdList = roleEntityList.stream().map(o -> o.getId()).collect(Collectors.toList());
        }
        List<Long> finalRoleIdList = roleIdList;
        entityList = entityList.stream().filter(o -> isMatchedRole(o.getBelongRoles(), finalRoleIdList)).collect(Collectors.toList());
        return WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
    }

    private boolean isMatchedRole(String belongRoles, List<Long> roleIdList) {
        if (StringUtils.isBlank(belongRoles)) {
            return true;
        }
        if (CollectionUtils.isEmpty(roleIdList)) {
            return false;
        }
        String[] belongRoleArray = StringUtils.split(belongRoles, ",");
        List<Long> belongRoleIdList = new ArrayList<>();
        for (String belongRoleString : belongRoleArray) {
            try {
                belongRoleIdList.add(Long.parseLong(belongRoleString));
            } catch (Exception e) {
                log.error("Long.parseLong error", e);
            }
        }
        return CollectionUtils.containsAny(belongRoleIdList, roleIdList);
    }

    public List<WinSecurityResourceDTO> getAllValidAccessResourceList() {
        List<WinSecurityResourceEntity> entityList = winSecurityResourceRepository.findAllByStatusAndDeletedFalseOrderBySeqAsc(WinSecurityStatusEnum.ENABLED.getValue());
        List<WinSecurityResourceDTO> resourceList = WinSecurityResourceMapper.INSTANCE.toResourceDTOList(entityList);
        if (CollectionUtils.isEmpty(resourceList)) {
            return null;
        }
        List<WinSecurityResourceDTO> sortedResourceList = getSortedResourceList(resourceList);
        return sortedResourceList.stream().filter(o -> StringUtils.isNotBlank(o.getAjaxUrls())).collect(Collectors.toList());
    }

    private List<WinSecurityResourceDTO> getSortedResourceList(List<WinSecurityResourceDTO> resourceList) {
        for (WinSecurityResourceDTO resource : resourceList) {
            if (resource.getSeq() == null) {
                resource.setSeq(0L);
            }
        }
        resourceList.sort(Comparator.comparingLong(WinSecurityResourceDTO::getSeq));
        Collections.reverse(resourceList);
        List<WinSecurityResourceDTO> toList = getSortedChildResourceList(null, resourceList);
        //顶部补充未处理的数据
        List<WinSecurityResourceDTO> leftList = ListUtils.subtract(resourceList, toList);
        if (CollectionUtils.isNotEmpty(leftList)) {
            toList.addAll(0, leftList);
        }
        //顺序翻转
        Collections.reverse(toList);
        return toList;
    }

    private List<WinSecurityResourceDTO> getSortedChildResourceList(Long pid, List<WinSecurityResourceDTO> resourceList) {
        List<WinSecurityResourceDTO> toList = new ArrayList<>();
        for (WinSecurityResourceDTO resource : resourceList) {
            if (pid == null || pid == 0) {
                if ((resource.getPid() == null || resource.getPid() == 0)) {
                    toList.add(resource);
                }
            } else if (pid.equals(resource.getPid())) {
                toList.add(resource);
            }
        }
        if (CollectionUtils.isEmpty(toList)) {
            return new ArrayList<>();
        }
        //SEQ降序排列, 所有数据
        List<WinSecurityResourceDTO> itrList = new ArrayList<>(toList);
        for (WinSecurityResourceDTO retResource : itrList) {
            List<WinSecurityResourceDTO> sortedChildResourceList = getSortedChildResourceList(retResource.getId(), resourceList);
            if (CollectionUtils.isEmpty(sortedChildResourceList)) {
                continue;
            }
            toList.addAll(toList.indexOf(retResource) + 1, sortedChildResourceList);
        }
        return toList;
    }

    public WinSecurityResourceDTO dragAndDropResource(DragAndDropParamDTO params) {
        if (params == null || params.getId() == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        //顶层元素移动
        if (params.getTargetParentId() == null) {
            params.setTargetParentId(0L);
        }
        Long id = params.getId();
        WinSecurityResourceEntity persistent = winSecurityResourceRepository.findOne(id);
        if (persistent == null) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
        }
        //pid更改，更新自身和下级元素的globalCode
        if (!params.getTargetParentId().equals(persistent.getPid())) {
            //判断同级下的code是否重复
            if (StringUtils.isNotBlank(persistent.getCode()) && winSecurityResourceRepository.existsByCodeAndPidAndIdNotAndDeletedFalse(persistent.getCode(), params.getTargetParentId(), id)) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_EXISTS);
            }
            persistent.setGlobalCode(getGlobalCode(persistent.getCode(), params.getTargetParentId()));
            //更新下级所有的globalCode
            updateGlobalCodeByPid(persistent.getId());
        }
        //计算seq
        persistent.setSeq(reCalculateSequence(id, params.getTargetParentId(), params.getTargetUpId(), params.getTargetDownId()));
        persistent.setPid(params.getTargetParentId());
        winSecurityResourceRepository.save(persistent);
        return getResource(id);
    }

    private Long reCalculateSequence(Long id, Long targetParentId, Long targetUpId, Long targetDownId) {
        WinSecurityResourceEntity thisResource = winSecurityResourceRepository.findOne(id);
        WinSecurityResourceEntity upResource = null;
        WinSecurityResourceEntity downResource = null;
        if (targetUpId != null) {
            upResource = winSecurityResourceRepository.findOne(targetUpId);
            if (upResource == null) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
            }
            if (!targetParentId.equals(upResource.getPid())) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_DATA_NOT_SUITABLE);
            }
        }
        if (targetDownId != null) {
            downResource = winSecurityResourceRepository.findOne(targetDownId);
            if (downResource == null) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_RESOURCE_NOT_EXISTS);
            }
            if (!targetParentId.equals(downResource.getPid())) {
                throw new WinSecurityException(WinSecurityErrorEnum.COMMON_DATA_NOT_SUITABLE);
            }
        }
        Long seq = thisResource.getSeq() == null ? NumberUtils.LONG_ZERO : thisResource.getSeq();
        //兼容前端代码
        List<WinSecurityResourceEntity> resourceEntityList = winSecurityResourceRepository.findByPidAndDeletedFalseOrderBySeqAscIdAsc(targetParentId);
        if (CollectionUtils.isEmpty(resourceEntityList)) {
            return seq;
        }
        if (upResource == null && downResource != null) {
            upResource = chooseUpResource(resourceEntityList, targetDownId, id);
        }
        if (downResource == null && upResource != null) {
            downResource = chooseDownResource(resourceEntityList, targetUpId, id);
        }
        //计算位置
        if (upResource == null && downResource == null) {
            //NoThing to do
        } else if (upResource == null && downResource != null) {
            //in the top
            seq = downResource.getSeq() - 1;
        } else if (upResource != null && downResource == null) {
            //in the bottom
            seq = upResource.getSeq() + 1;
        } else {
            if (upResource.getSeq() > downResource.getSeq()) {
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
                List<WinSecurityResourceEntity> downList = chooseDownResourceList(resourceEntityList, downResource.getId(), id);
                updateDownResourceListSeq(downList, seq);
            }
        }
        return seq;
    }

    private void updateDownResourceListSeq(List<WinSecurityResourceEntity> downList, Long seq) {
        if (CollectionUtils.isNotEmpty(downList)) {
            for (WinSecurityResourceEntity entity : downList) {
                entity.setSeq(++seq);
            }
            winSecurityResourceRepository.save(downList);
        }
    }

    private WinSecurityResourceEntity chooseUpResource(List<WinSecurityResourceEntity> resourceEntityList, Long downId, Long thisId) {
        for (int i = 0; i < resourceEntityList.size(); i++) {
            WinSecurityResourceEntity resourceEntity = resourceEntityList.get(i);
            if (downId.equals(resourceEntity.getId())) {
                int j = i;
                while (j > 0) {
                    WinSecurityResourceEntity preResourceEntity = resourceEntityList.get(j - 1);
                    if (!thisId.equals(preResourceEntity.getId())) {
                        return preResourceEntity;
                    } else {
                        j--;
                    }
                }
                break;
            }
        }
        return null;
    }

    private WinSecurityResourceEntity chooseDownResource(List<WinSecurityResourceEntity> resourceEntityList, Long upId, Long thisId) {
        for (int i = 0; i < resourceEntityList.size(); i++) {
            WinSecurityResourceEntity resourceEntity = resourceEntityList.get(i);
            if (upId.equals(resourceEntity.getId())) {
                int j = i;
                while (j < resourceEntityList.size() - 1) {
                    WinSecurityResourceEntity downResourceEntity = resourceEntityList.get(j + 1);
                    if (!thisId.equals(downResourceEntity.getId())) {
                        return downResourceEntity;
                    } else {
                        j++;
                    }
                }
                break;
            }
        }
        return null;
    }

    private List<WinSecurityResourceEntity> chooseDownResourceList(List<WinSecurityResourceEntity> resourceEntityList, Long downId, Long thisId) {
        for (int i = 0; i < resourceEntityList.size(); i++) {
            WinSecurityResourceEntity resourceEntity = resourceEntityList.get(i);
            if (downId.equals(resourceEntity.getId())) {
                List<WinSecurityResourceEntity> downList = new ArrayList<>();
                for (int j = i; j < resourceEntityList.size(); j++) {
                    WinSecurityResourceEntity downEntity = resourceEntityList.get(j);
                    if (!thisId.equals(downEntity.getId())) {
                        downList.add(downEntity);
                    }
                }
                return downList;
            }
        }

        return null;
    }

    public boolean updateResourceBatch(BatchUpdateParamDTO params) {
        if (params == null || CollectionUtils.isEmpty(params.getIdList())) {
            throw new WinSecurityException(WinSecurityErrorEnum.COMMON_PARAM_NOT_EXISTS);
        }
        //顶层元素移动
        if (params.getTargetParentId() == null) {
            params.setTargetParentId(0L);
        }
        for (Long thisId : params.getIdList()) {
            WinSecurityResourceEntity persistent = winSecurityResourceRepository.findOne(thisId);
            if (persistent == null) {
                continue;
            }
            if (params.getTargetParentId().equals(persistent.getPid())) {
                continue;
            }
            persistent.setGlobalCode(getGlobalCode(persistent.getCode(), params.getTargetParentId()));
            //更新下级所有的globalCode
            updateGlobalCodeByPid(thisId);
            persistent.setPid(params.getTargetParentId());
            winSecurityResourceRepository.save(persistent);
        }
        return true;
    }
}
