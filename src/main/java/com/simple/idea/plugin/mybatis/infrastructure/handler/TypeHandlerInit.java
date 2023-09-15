package com.simple.idea.plugin.mybatis.infrastructure.handler;

import com.simple.idea.plugin.mybatis.infrastructure.constant.CommonConstants;
import com.simple.idea.plugin.mybatis.infrastructure.handler.sub.CommonTypeHandler;
import com.simple.idea.plugin.mybatis.infrastructure.handler.sub.OracleTypeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2023-07-29 14:15
 **/
public class TypeHandlerInit {

    public static Map<String, TypeHandler> HANDLER_MAPS = new HashMap<>(4);

    static {
        HANDLER_MAPS.put(CommonConstants.COMMON, new CommonTypeHandler());
        HANDLER_MAPS.put(CommonConstants.ORACLE, new OracleTypeHandler());
    }
}
