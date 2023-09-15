package com.simple.idea.plugin.mybatis.infrastructure.utils;

import com.simple.idea.plugin.mybatis.infrastructure.constant.CommonDict;
import com.simple.idea.plugin.mybatis.infrastructure.handler.sub.NoMatch;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:08
 **/
public class JavaType {
    private static final LinkedHashMap<JDBCType, Class> COMMON_MAP = new LinkedHashMap();
    public static final LinkedHashMap<String, Class> OTHER_MAP = new LinkedHashMap();

    static {
        //字符串类型
        COMMON_MAP.put(JDBCType.VARCHAR, String.class);
        COMMON_MAP.put(JDBCType.LONGVARCHAR, String.class);
        COMMON_MAP.put(JDBCType.CHAR, String.class);
        //整数类型
        COMMON_MAP.put(JDBCType.INTEGER, Integer.class);
        COMMON_MAP.put(JDBCType.BIGINT, Long.class);
        COMMON_MAP.put(JDBCType.SMALLINT, Integer.class);
        COMMON_MAP.put(JDBCType.TINYINT, Integer.class);
        //浮点类型
        COMMON_MAP.put(JDBCType.FLOAT, Float.class);
        COMMON_MAP.put(JDBCType.DOUBLE, Double.class);
        COMMON_MAP.put(JDBCType.DECIMAL, BigDecimal.class);
        //其他类型
        COMMON_MAP.put(JDBCType.BOOLEAN, Boolean.class);
        COMMON_MAP.put(JDBCType.DATE, Date.class);
        COMMON_MAP.put(JDBCType.TIME, Date.class);
        COMMON_MAP.put(JDBCType.TIMESTAMP, Date.class);
        COMMON_MAP.put(JDBCType.BIT, boolean.class);

        //字符串类型
        OTHER_MAP.put(JDBCType.VARCHAR.getName().toLowerCase(), String.class);
        OTHER_MAP.put(JDBCType.LONGVARCHAR.getName().toLowerCase(), String.class);
        OTHER_MAP.put(JDBCType.CHAR.getName().toLowerCase(), String.class);

        OTHER_MAP.put(JDBCType.INTEGER.getName().toLowerCase(), Integer.class);
        OTHER_MAP.put(JDBCType.BIGINT.getName().toLowerCase(), Long.class);
        OTHER_MAP.put(JDBCType.SMALLINT.getName().toLowerCase(), Integer.class);
        OTHER_MAP.put(JDBCType.TINYINT.getName().toLowerCase(), Integer.class);

        OTHER_MAP.put(JDBCType.FLOAT.getName().toLowerCase(), Float.class);
        OTHER_MAP.put(JDBCType.DOUBLE.getName().toLowerCase(), Double.class);
        OTHER_MAP.put(JDBCType.DECIMAL.getName().toLowerCase(), BigDecimal.class);

        OTHER_MAP.put(JDBCType.BOOLEAN.getName().toLowerCase(), Boolean.class);
        OTHER_MAP.put(JDBCType.DATE.getName().toLowerCase(), Date.class);
        OTHER_MAP.put(JDBCType.TIME.getName().toLowerCase(), Date.class);
        OTHER_MAP.put(JDBCType.TIMESTAMP.getName().toLowerCase(), Date.class);
        OTHER_MAP.put(JDBCType.BIT.getName().toLowerCase(), boolean.class);
        OTHER_MAP.put(CommonDict.EXTEND_DB_TYPE_INT, Integer.class);
        OTHER_MAP.put(CommonDict.EXTEND_DB_TYPE_DATETIME, Date.class);
    }

    public static Class convertType(int sqlType) {
        return COMMON_MAP.getOrDefault(JDBCType.valueOf(sqlType), Object.class);
    }

    public static Class convertType(String typeName) {
        return OTHER_MAP.getOrDefault(typeName, NoMatch.class);
    }

}
