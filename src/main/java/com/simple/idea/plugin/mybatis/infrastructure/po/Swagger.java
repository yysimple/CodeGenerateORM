package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-01 20:39
 **/
public class Swagger extends Base {

    private final Model model;

    public Swagger(String comment, String name, Model model) {
        super(comment, name);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public Set<String> getImports() {
        return setImports(model, null, null, null);
    }
}
