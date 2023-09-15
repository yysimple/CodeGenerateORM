package com.simple.idea.plugin.mybatis.infrastructure.handler;

import com.simple.idea.plugin.mybatis.infrastructure.handler.dto.TypeTransferDTO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2023-07-29 13:39
 **/
public interface TypeHandler {

    Class transfer(TypeTransferDTO dto);
}
