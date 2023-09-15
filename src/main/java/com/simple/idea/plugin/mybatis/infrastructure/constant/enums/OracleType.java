package com.simple.idea.plugin.mybatis.infrastructure.constant.enums;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: oracle的类型
 *
 * @author: WuChengXing
 * @create: 2023-07-29 13:27
 **/
public enum OracleType {

    NUMBER_BOOLEAN("NUMBER", 1, 1, -1, Boolean.class),
    NUMBER_INTEGER("NUMBER", 2, 10, -1, Integer.class),
    NUMBER_LONG("NUMBER", 2, 10, -1, Integer.class),
    NUMBER_FLOAT("NUMBER", 2, 10, 1, Integer.class),
    NUMBER_DOUBLE("NUMBER", 2, 10, 2, Integer.class),
    VARCHAR2("VARCHAR2", -1, -1, -1, String.class),
    NO_MATCH("NO_MATCH", -1, -1, -1, String.class),

    ;
    private final String typeName;
    private final Integer typeSizeStart;
    private final Integer typeSizeEnd;
    private final Integer scale;
    private final Class javaType;

    OracleType(String typeName, Integer typeSizeStart, Integer typeSizeEnd, Integer scale, Class javaType) {
        this.typeName = typeName;
        this.typeSizeStart = typeSizeStart;
        this.typeSizeEnd = typeSizeEnd;
        this.scale = scale;
        this.javaType = javaType;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getTypeSizeStart() {
        return typeSizeStart;
    }

    public Integer getTypeSizeEnd() {
        return typeSizeEnd;
    }

    public Integer getScale() {
        return scale;
    }

    public Class getJavaType() {
        return javaType;
    }
}
