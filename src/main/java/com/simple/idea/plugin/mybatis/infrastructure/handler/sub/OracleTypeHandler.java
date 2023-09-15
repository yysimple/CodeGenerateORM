package com.simple.idea.plugin.mybatis.infrastructure.handler.sub;

import com.simple.idea.plugin.mybatis.infrastructure.constant.enums.OracleType;
import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandler;
import com.simple.idea.plugin.mybatis.infrastructure.handler.dto.TypeTransferDTO;

import java.util.Arrays;
import java.util.Optional;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: oracle处理
 *
 * @author: WuChengXing
 * @create: 2023-07-29 13:43
 **/
public class OracleTypeHandler implements TypeHandler {

    @Override
    public Class transfer(TypeTransferDTO dto) {
        return getType(dto);
    }

    private Class getType(TypeTransferDTO dto) {
        OracleType match = match(dto);
        if (match.equals(OracleType.NO_MATCH)) {
            return NoMatch.class;
        }
        return match.getJavaType();
    }

    private OracleType match(TypeTransferDTO dto) {
        Optional<OracleType> first = Arrays.stream(OracleType.values())
                .filter(oracleType -> oracleType.getTypeName().equals(dto.getTypeName()))
                .filter(oracleType -> {
                    if (isDouble(dto.getScale())) {
                        return true;
                    } else if (isFloat(dto.getScale())) {
                        return true;
                    } else {
                        return between(oracleType, dto.getTypeSize());
                    }
                }).findFirst();
        return first.orElse(OracleType.NO_MATCH);
    }

    private boolean between(OracleType oracleType, Integer typeSize) {
        if (oracleType.getTypeSizeStart() == -1 || oracleType.getTypeSizeEnd() == -1) {
            return true;
        }
        return typeSize >= oracleType.getTypeSizeStart() && typeSize <= oracleType.getTypeSizeEnd();
    }

    private boolean isDouble(Integer scale) {
        return scale == 2;
    }

    private boolean isFloat(Integer scale) {
        return scale == 1;
    }
}
