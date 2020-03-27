package com.winbaoxian.module.security.utils;

import com.alibaba.fastjson.JSON;
import com.winbaoxian.module.security.annotation.SearchParam;
import com.winbaoxian.module.security.constant.WinSecurityConstant;
import com.winbaoxian.module.security.model.common.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.path.RootImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum QuerySpecificationUtils {

    INSTANCE;

    private static final String[] LOGIC_DELETE_FIELD_ARRAY = new String[]{"isDeleted", "isDelete", "deleted", "delete", "deleteFlag", "deletedFlag"};

    private Logger logger = LoggerFactory.getLogger(getClass());

    public <T> Specification<T> getSingleSpecification(Object queryParam, Class<T> entityClass) {
        return getSingleSpecification(queryParam, null, entityClass);
    }

    public <T> Specification<T> getSingleSpecification(Object queryParam, Pagination pagination, Class<T> entityClass) {
        if (queryParam == null) {
            return null;
        }
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = null;
            try {
                if (root instanceof RootImpl && !((RootImpl) root).getEntityType().getJavaType().equals(entityClass)) {
                    root = ((RootImpl<T>) root).treatAs(entityClass);
                }
                list = getPredicateList(queryParam, root, criteriaBuilder);
                //排序
                if (pagination != null) {
                    String orderProperty = StringUtils.defaultIfBlank(pagination.getOrderProperty(), WinSecurityConstant.SORT_COLUMN_ID);
                    Sort.Direction orderDirection = StringUtils.isNotBlank(pagination.getOrderDirection()) ? Sort.Direction.fromStringOrNull(pagination.getOrderDirection()) : Sort.Direction.ASC;
                    if (Sort.Direction.DESC.equals(orderDirection)) {
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderProperty)));
                    } else {
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderProperty)));
                    }
                }
            } catch (Exception e) {
                logger.error("QuerySpecificationUtils.getSingleSpecification failed, queryParam:{}", JSON.toJSONString(queryParam), e);
                return null;
            }
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        };
    }

    private <T> List<Predicate> getPredicateList(Object queryParam, Root<T> root, CriteriaBuilder criteriaBuilder) throws IllegalAccessException {
        List<Predicate> list = new ArrayList<>();
        Class<?> paramClz = queryParam.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (paramClz != null) {
            fieldList.addAll(Arrays.asList(paramClz.getDeclaredFields()));
            paramClz = paramClz.getSuperclass();
        }
        for (Field field : fieldList) {
            Predicate predicate = getPredicateBySearchParam(queryParam, field, root, criteriaBuilder);
            if (predicate != null) {
                list.add(predicate);
            }
        }
        return list;
    }

    private <T> Predicate getPredicateBySearchParam(Object queryParam, Field field, Root<T> root, CriteriaBuilder criteriaBuilder) throws IllegalAccessException {
        field.setAccessible(true);
        if (isSpecialField(field)) {
            return null;
        }
        if (isLogicDeleteField(field)) {
            return criteriaBuilder.equal(root.get(field.getName()), false);
        }
        Object value = field.get(queryParam);
        if (value == null) {
            return null;
        }
        if (value instanceof Collection && CollectionUtils.isEmpty((Collection) value)) {
            return null;
        }
        if (value instanceof CharSequence && StringUtils.isBlank((CharSequence) value)) {
            return null;
        }
        Predicate predicate = null;
        SearchParam annotation = field.getAnnotation(SearchParam.class);
        String fieldName = field.getName();
        if (annotation == null) {
            predicate = criteriaBuilder.equal(root.get(fieldName), value);
        } else if (!annotation.ignore()) {
            if (StringUtils.isNotBlank(annotation.name())) {
                fieldName = annotation.name();
            }
            if (SearchParam.COMPARE.eq.equals(annotation.compare())) {
                predicate = criteriaBuilder.equal(root.get(fieldName), value);
            } else if (SearchParam.COMPARE.like.equals(annotation.compare())) {
                predicate = criteriaBuilder.like(root.get(fieldName), value + "%");
            } else if (SearchParam.COMPARE.le.equals(annotation.compare())) {
                predicate = criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), (Comparable) value);
            } else if (SearchParam.COMPARE.ge.equals(annotation.compare())) {
                predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), (Comparable) value);
            } else if (SearchParam.COMPARE.ne.equals(annotation.compare())) {
                predicate = criteriaBuilder.notEqual(root.get(fieldName), value);
            } else if (SearchParam.COMPARE.in.equals(annotation.compare())) {
                predicate = criteriaBuilder.in(root.get(fieldName)).value(value);
            } else {
                predicate = criteriaBuilder.equal(root.get(fieldName), value);
            }
        }
        return predicate;
    }

    private boolean isSpecialField(Field field) {
        if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
            return true;
        }
        return false;
    }

    private boolean isLogicDeleteField(Field field) {
        for (String logicDeleteFieldName : LOGIC_DELETE_FIELD_ARRAY) {
            if (logicDeleteFieldName.toUpperCase().equals(field.getName().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

}
