package com.monco.core.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class QueryUtils {

    /**
     * 默认排序
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize) {
        return PageRequest.of(pageNum - 1, pageSize);
    }

    /*
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param sortType 排序字段
     * @param direction 排序方向
     * @param properties 排序属性
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String direction, String... properties) {
        Sort sort = null;
        if (properties == null || properties.length == 0) {
            return PageRequest.of(pageNum - 1, pageSize);
        } else if (!StringUtils.isEmpty(direction)) {
            if (Direction.ASC.equals(direction)) {
                sort = new Sort(Direction.ASC, properties);
            } else {
                sort = new Sort(Direction.DESC, properties);
            }
            return PageRequest.of(pageNum - 1, pageSize, sort);
        } else {
            sort = new Sort(Direction.ASC, properties);
            return PageRequest.of(pageNum - 1, pageSize, sort);
        }
    }

    /*
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @param sortType 排序字段
     * @param directions key:排序方向 value:排序的属性
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, Map<String, String[]> directions) {
        Sort sort = null;
        if (directions == null || directions.size() == 0) {
            return PageRequest.of(pageNum - 1, pageSize);
        } else {
            Set<String> keySet = directions.keySet();
            for (String direction : keySet) {
                String[] strings = directions.get(direction);
                Direction directionType = Direction.DESC;
                if (Direction.ASC.equals(direction)) {
                    directionType = Direction.ASC;
                }
                if (sort == null) {
                    sort = new Sort(directionType, strings);
                } else {
                    sort.and(new Sort(directionType, strings));
                }
            }
            return PageRequest.of(pageNum - 1, pageSize, sort);
        }
    }

    /**
     * @param pageNum    当前页
     * @param pageSize   每页条数
     * @param properties 排序字段(默认排序规则)
     * @return
     */
    public static PageRequest buildPageRequest(int pageNum, int pageSize, String... properties) {
        return buildPageRequest(pageNum, pageSize, null, properties);
    }

    @SuppressWarnings("unchecked")
    public static <T> Specification<T> buildPredicate(final List<QueryParam> params) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> andPredicates = new ArrayList<>();
                List<Predicate> orPredicates = new ArrayList<>();
                for (QueryParam queryParam : params) {
                    MatchType matchType = queryParam.getMatchType();
                    Object value = queryParam.getValue();
                    Predicate predicateRecord = cb.conjunction();
                    switch (matchType) {
                        case like:
                            String searchWord = (String) value;
                            if (!searchWord.contains("%")) {
                                searchWord = "%" + searchWord + "%";
                            }
                            predicateRecord = cb.like(root.get(queryParam.getFiled()).as(String.class), searchWord);
                            break;
                        case notLike:
                            String searchWord1 = (String) value;
                            if (!searchWord1.contains("%")) {
                                searchWord1 = "%" + searchWord1 + "%";
                            }
                            predicateRecord = cb.notLike(root.get(queryParam.getFiled()).as(String.class), searchWord1);
                            break;
                        case equal:
                            predicateRecord = cb.equal(root.get(queryParam.getFiled()), value);
                            break;
                        case notEqual:
                            predicateRecord = cb.notEqual(root.get(queryParam.getFiled()), value);
                            break;
                        case notNull:
                            predicateRecord = cb.isNotNull(root.get(queryParam.getFiled()));
                            break;
                        case isNull:
                            predicateRecord = cb.isNull(root.get(queryParam.getFiled()));
                            break;
                        case in:
                            In<Object> in = cb.in(root.get(queryParam.getFiled()));
                            if (value instanceof List) {
                                for (Object val : (List) value) {
                                    in.value(val);
                                }
                            } else if (value instanceof Object[]) {
                                for (Object val : (Object[]) value) {
                                    in.value(val);
                                }
                            }
                            predicateRecord = in;
                            break;
                        case notIn:
                            In<Object> notIn = cb.in(root.get(queryParam.getFiled()));
                            if (value instanceof List) {
                                for (Object val : (List) value) {
                                    notIn.value(val);
                                }
                            } else if (value instanceof Object[]) {
                                for (Object val : (Object[]) value) {
                                    notIn.value(val);
                                }
                            }
                            predicateRecord = cb.not(notIn);
                            break;
                        case lessThanOrEqualTo:
                            if (value instanceof Number) {
                                predicateRecord = cb.le(root.get(queryParam.getFiled()).as(Number.class), (Number) value);
                            } else if (value instanceof String) {
                                predicateRecord = cb.lessThanOrEqualTo(root.get(queryParam.getFiled()).as(String.class), (String) value);
                            } else if (value instanceof Date) {
                                predicateRecord = cb.lessThanOrEqualTo(root.get(queryParam.getFiled()).as(Date.class), (Date) value);
                            }
                            break;
                        case lessThan:
                            if (value instanceof Number) {
                                predicateRecord = cb.lt(root.get(queryParam.getFiled()).as(Number.class), (Number) value);
                            } else if (value instanceof String) {
                                predicateRecord = cb.lessThan(root.get(queryParam.getFiled()).as(String.class), (String) value);
                            } else if (value instanceof Date) {
                                predicateRecord = cb.lessThan(root.get(queryParam.getFiled()).as(Date.class), (Date) value);
                            }
                            break;
                        case greaterThanOrEqualTo:
                            if (value instanceof Number) {
                                predicateRecord = cb.ge(root.get(queryParam.getFiled()).as(Number.class), (Number) value);
                            } else if (value instanceof String) {
                                predicateRecord = cb.greaterThanOrEqualTo(root.get(queryParam.getFiled()).as(String.class), (String) value);
                            } else if (value instanceof Date) {
                                predicateRecord = cb.greaterThanOrEqualTo(root.get(queryParam.getFiled()).as(Date.class), (Date) value);
                            }
                            break;
                        case greaterThan:
                            if (value instanceof Number) {
                                predicateRecord = cb.gt(root.get(queryParam.getFiled()).as(Number.class), (Number) value);
                            } else if (value instanceof String) {
                                predicateRecord = cb.greaterThan(root.get(queryParam.getFiled()).as(String.class), (String) value);
                            } else if (value instanceof Date) {
                                predicateRecord = cb.greaterThan(root.get(queryParam.getFiled()).as(Date.class), (Date) value);
                            }
                            break;
                    }
                    if ("or".equalsIgnoreCase(queryParam.getConnector())) {
                        predicateRecord = cb.or(predicateRecord);
                        orPredicates.add(predicateRecord);
                    } else {
                        andPredicates.add(predicateRecord);
                    }
                }
                Predicate and = cb.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
                Predicate or = cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                if (or.getExpressions().isEmpty()) {
                    return query.where(and).getRestriction();
                } else if (and.getExpressions().isEmpty()) {
                    return query.where(or).getRestriction();
                } else {
                    return query.where(and, or).getRestriction();
                }
            }
        };
    }
}
