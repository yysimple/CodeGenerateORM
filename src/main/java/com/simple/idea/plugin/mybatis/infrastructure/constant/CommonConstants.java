package com.simple.idea.plugin.mybatis.infrastructure.constant;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-27 13:49
 **/
public final class CommonConstants {
    /**
     * 是否生成plus的模板
     */
    public static final String IS_PLUS = "isPlus";

    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String MYSQL = "MYSQL";
    public static final String ORACLE = "ORACLE";
    public static final String COMMON = "COMMON";
    public static final String Postgresql = "POSTGRESQL";

    /**
     * 用于区分生成代码的入口
     */
    public static final String SELECT_FOR_SEARCH_DB = "0";
    public static final String SELECT_FOR_TABLE = "1";

    public static final String DEFAULT_TABLE_MEANING = "todo Please parse the meaning of the table.";
    public static final String DEFAULT_FILED_MEANING = "todo Please parse the meaning of the field.";
}
