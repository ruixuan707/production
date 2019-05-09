package com.monco.core.query;

public class QueryParam {

    /**
     * 查询属性
     */
    private String filed;

    /**
     * 查询值(包装类型，如日期则Date,如Double,Integer, )
     */
    private Object value;

    /**
     * 查询连接符
     */
    private String connector = "and";

    /**
     * 操作枚举符
     */
    private MatchType matchType;

    public QueryParam() {
    }

    public QueryParam(String filed, MatchType matchType, Object value,
                      String connector) {
        super();
        this.filed = filed;
        this.matchType = matchType;
        this.value = value;
        this.connector = connector;
    }

    public QueryParam(String filed, MatchType matchType, Object value) {
        super();
        this.filed = filed;
        this.matchType = matchType;
        this.value = value;
    }

    public String getFiled() {
        return filed;
    }

    public QueryParam setFiled(String filed) {
        this.filed = filed;
        return this;
    }


    public Object getValue() {
        return value;
    }

    public QueryParam setValue(Object value) {
        this.value = value;
        return this;
    }

    public String getConnector() {
        return connector;
    }

    public QueryParam setConnector(String connector) {
        this.connector = connector;
        return this;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public QueryParam setMatchType(MatchType matchType) {
        this.matchType = matchType;
        return this;
    }
}
