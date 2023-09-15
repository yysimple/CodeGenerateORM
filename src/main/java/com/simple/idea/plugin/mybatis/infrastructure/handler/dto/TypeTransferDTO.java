package com.simple.idea.plugin.mybatis.infrastructure.handler.dto;

import com.intellij.database.model.DataType;
import com.simple.idea.plugin.mybatis.infrastructure.po.Column;
import lombok.Builder;
import lombok.Data;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2023-07-29 13:41
 **/
public class TypeTransferDTO {

    private String typeName;
    private Integer typeSize;
    private Integer scale;
    private Integer jdbcType;

    public static TypeTransferDTO build(DataType dataType) {
        TypeTransferDTO typeTransferDTO = new TypeTransferDTO();
        typeTransferDTO.setTypeName(dataType.typeName);
        typeTransferDTO.setTypeSize(dataType.size);
        typeTransferDTO.setScale(dataType.scale);
        typeTransferDTO.setJdbcType(dataType.jdbcType);
        return typeTransferDTO;
    }

    public static TypeTransferDTO build(Column column) {
        TypeTransferDTO typeTransferDTO = new TypeTransferDTO();
        typeTransferDTO.setTypeName(column.getTypeName());
        typeTransferDTO.setTypeSize(column.getTypeSize());
        typeTransferDTO.setScale(column.getScale());
        return typeTransferDTO;
    }



    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(Integer typeSize) {
        this.typeSize = typeSize;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(Integer jdbcType) {
        this.jdbcType = jdbcType;
    }
}
