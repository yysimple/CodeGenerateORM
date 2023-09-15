package com.simple.idea.plugin.mybatis.infrastructure.utils;

import com.intellij.database.Dbms;
import com.intellij.database.dataSource.DatabaseDriver;
import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.database.model.*;
import com.intellij.database.psi.DbDataSource;
import com.intellij.util.containers.JBIterable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 兼容idea的数据源
 *
 * @author: WuChengXing
 * @create: 2023-07-29 12:27
 **/
public class TableUtils {

    public static DataType getDataType(DasColumn dasColumn) {
        try {
            // 兼容2022.3.3及以上版本
            Method getDasTypeMethod = dasColumn.getClass().getMethod("getDasType");
            Object dasType = getDasTypeMethod.invoke(dasColumn);
            Method toDataTypeMethod = dasType.getClass().getMethod("toDataType");
            return (DataType) toDataTypeMethod.invoke(dasType);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // 兼容2022.3.3以下版本
            try {
                Method getDataTypeMethod = dasColumn.getClass().getMethod("getDataType");
                return (DataType) getDataTypeMethod.invoke(dasColumn);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static String getDataSourceName(DasNamespace namespace) {
        DbDataSource dasParent = (DbDataSource) namespace.getDasParent();
        Dbms dbms = dasParent.getDbms();
        String name = dbms.getName();
        return name;
    }
}
