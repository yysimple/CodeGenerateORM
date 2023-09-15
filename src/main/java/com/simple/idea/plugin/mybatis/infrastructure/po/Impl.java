package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-31 17:18
 **/
public class Impl extends Base {
    private final Model model;
    private final Service service;
    private final Mapper mapper;

    public Impl(String comment, String name, Model model, Service service, Mapper mapper) {
        super(comment, name);
        this.model = model;
        this.service = service;
        this.mapper = mapper;
    }

    public Model getModel() {
        return model;
    }

    public Service getService() {
        return service;
    }

    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public Set<String> getImports() {
        return setImports(model, service, mapper, null);
    }
}
