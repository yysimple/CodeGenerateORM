package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:58
 **/
public class Table {

    private String dataSourceType;
    private final String comment;
    private final String name;
    private final List<Column> columns;

    public Table(String comment, String name, List<Column> columns) {
        this.comment = comment;
        this.name = name;
        this.columns = columns;
    }

    public Table(String comment, String name, List<Column> columns, String dataSourceType) {
        this.comment = comment;
        this.name = name;
        this.columns = columns;
        this.dataSourceType = dataSourceType;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }
}
