package com.simple.idea.plugin.mybatis.infrastructure.handler.sub;

import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandler;
import com.simple.idea.plugin.mybatis.infrastructure.handler.dto.TypeTransferDTO;
import com.simple.idea.plugin.mybatis.infrastructure.utils.JavaType;

import java.util.Objects;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 公用handler处理
 *
 * @author: WuChengXing
 * @create: 2023-07-29 14:07
 **/
public class CommonTypeHandler implements TypeHandler {

    @Override
    public Class transfer(TypeTransferDTO dto) {
        Class aClass = JavaType.convertType(dto.getTypeName());
        return Objects.nonNull(aClass) ? aClass : NoMatch.class;
    }
}
