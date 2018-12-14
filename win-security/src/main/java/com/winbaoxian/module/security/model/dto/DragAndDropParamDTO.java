package com.winbaoxian.module.security.model.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * @author dongxuanliang252
 * @date 2018-12-14 16:17
 */
@Data
public class DragAndDropParamDTO implements Serializable {

    /**
     * 当前元素ID
     */
    private Long id;
    /**
     * 目标位置上面的元素ID
     */
    private Long targetUpId;
    /**
     * 目标位置下面的元素ID
     */
    private Long targetDownId;
    /**
     * 目标位置父元素ID
     */
    private Long targetParentId;

}
