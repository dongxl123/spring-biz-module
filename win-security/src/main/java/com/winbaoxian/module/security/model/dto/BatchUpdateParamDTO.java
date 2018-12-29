package com.winbaoxian.module.security.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongxuanliang252
 * @date 2018-12-29 17:42
 */
@Data
public class BatchUpdateParamDTO  implements Serializable {

    /**
     *  待移动元素ID列表
     */
    private List<Long> idList;

    /**
     * 目标位置父元素ID
     */
    private Long targetParentId;

}

