package com.simple.idea.plugin.mybatis.infrastructure.po;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:01
 **/
public class Column {
    private final String comment;
    private final String name;
    private String typeName;
    private Integer typeSize;
    private Integer scale;
    private int type;
    private boolean id;

    public Column(String comment, String name, int type) {
        this.comment = comment;
        this.name = name;
        this.type = type;
    }

    public Column(String comment, String name, String typeName, Integer typeSize, Integer scale, boolean id) {
        this.comment = comment;
        this.name = name;
        this.typeName = typeName;
        this.typeSize = typeSize;
        this.scale = scale;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getTypeSize() {
        return typeSize;
    }

    public Integer getScale() {
        return scale;
    }
}
