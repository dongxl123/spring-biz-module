package com.winbaoxian.module.security.model.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 前端入参分页对象
 *
 * @Author DongXL
 * @Create 2016-10-30 10:59
 */
public class Pagination implements Serializable {

    private static final Integer DEFAULT_PAGE_SIZE = 50;
    private Integer pageNum = 1;
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    private Integer totalRow = 0;
    private Integer totalPage = 0;
    private String orderProperty;
    private String orderDirection;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        if (this.getPageNum() == null || this.getPageNum() <= 0 || this.getPageSize() == null) {
            return 0;
        }
        return (this.getPageNum() - 1) * this.getPageSize();
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getTotalPage() {
        if (this.getPageSize() == null || this.getTotalRow() == null || this.getPageSize() == 0 || this.getTotalRow() == 0) {
            return 0;
        }
        int pgsize = this.getPageSize();
        int ttrow = this.getTotalRow();
        int ttpage = ttrow / pgsize;
        if (ttrow % pgsize != 0) {
            ttpage++;
        }
        return ttpage;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public static Pageable createPageable(Pagination pagination, String defaultOrderProperty, String defaultOrderDirection) {
        String orderProperty = StringUtils.defaultIfBlank(pagination.getOrderProperty(), defaultOrderProperty);
        String orderDirection = StringUtils.defaultIfBlank(pagination.getOrderDirection(), defaultOrderDirection);
        Sort sort = new Sort(Sort.Direction.fromStringOrNull(orderDirection), orderProperty);
        return new PageRequest(pagination.getPageNum() - 1, pagination.getPageSize(), sort);
    }

    public static Pageable createPageable(Pagination pagination) {
        return new PageRequest(pagination.getPageNum() - 1, pagination.getPageSize());
    }

}
