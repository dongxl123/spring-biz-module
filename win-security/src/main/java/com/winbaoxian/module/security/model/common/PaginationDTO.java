package com.winbaoxian.module.security.model.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 返回前端分页数据
 *
 * @Author DongXL
 * @Create 2016-10-30 11:03
 */
public class PaginationDTO<T> extends Pagination {

    private static final Logger log = LoggerFactory.getLogger(PaginationDTO.class);

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public static <T> PaginationDTO<T> createNewInstance(Pagination pagination, Integer count, List<T> list) {
        PaginationDTO<T> paginationDto = new PaginationDTO<>();
        paginationDto.setPageNum(pagination.getPageNum());
        paginationDto.setPageSize(pagination.getPageSize());
        paginationDto.setTotalRow(count);
        paginationDto.setList(list);
        paginationDto.setOrderProperty(pagination.getOrderProperty());
        paginationDto.setOrderDirection(pagination.getOrderDirection());
        return paginationDto;
    }

    public static <T> PaginationDTO<T> createNewInstance(Page<T> page) {
        PaginationDTO<T> paginationDto = new PaginationDTO<>();
        paginationDto.setPageNum(page.getNumber() + 1);
        paginationDto.setPageSize(page.getSize());
        paginationDto.setTotalRow((int) page.getTotalElements());
        paginationDto.setList(page.getContent());
        if (page.getSort() != null) {
            Iterator<Sort.Order> iterator = page.getSort().iterator();
            while (iterator.hasNext()) {
                Sort.Order order = iterator.next();
                paginationDto.setOrderProperty(order.getProperty());
                paginationDto.setOrderDirection(order.getDirection().name());
                break;
            }
        }
        return paginationDto;
    }

    public static <T, E> PaginationDTO<T> createNewInstance(Page<E> page, Class<T> clazz) {
        PaginationDTO<T> paginationDto = new PaginationDTO<>();
        paginationDto.setPageNum(page.getNumber() + 1);
        paginationDto.setPageSize(page.getSize());
        paginationDto.setTotalRow((int) page.getTotalElements());
        List<T> dataList = new ArrayList<>();
        for (E entity : page.getContent()) {
            T t = null;
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(entity, t);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("BeanUtils.copyProperties error", e);
            }
            dataList.add(t);
        }
        paginationDto.setList(dataList);
        if (page.getSort() != null) {
            Iterator<Sort.Order> iterator = page.getSort().iterator();
            while (iterator.hasNext()) {
                Sort.Order order = iterator.next();
                paginationDto.setOrderProperty(order.getProperty());
                paginationDto.setOrderDirection(order.getDirection().name());
                break;
            }
        }
        return paginationDto;
    }

}
