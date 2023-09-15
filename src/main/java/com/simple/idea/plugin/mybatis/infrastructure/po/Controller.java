package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-31 17:19
 **/
public class Controller extends Base {
    private final Model model;
    private final Service service;
    private final Response response;

    public Controller(String comment, String name, Model model, Service service, Response response) {
        super(comment, name);
        this.model = model;
        this.service = service;
        this.response = response;
    }

    public Model getModel() {
        return model;
    }

    public Service getService() {
        return service;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public Set<String> getImports() {
        return setImports(model, service, null, response);
    }
}
