package com.monco.core.query;

/**
 * @Auther: monco
 * @Date: 2019/4/25 21:28
 * @Description: 查询枚举类
 */
public enum MatchType {
    equal,        // filed = value
    //下面四个用于Number类型的比较
    gt,   // filed > value
    ge,   // field >= value
    lt,              // field < value
    le,      // field <= value

    notEqual,            // field != value
    like,   // field like value
    notLike,    // field not like value
    notNull,    // field not like value
    isNull,
    in,
    notIn,
    greaterThan,        // field > value
    greaterThanOrEqualTo,   // field >= value
    lessThan,               // field < value
    lessThanOrEqualTo   // field <= value
}
