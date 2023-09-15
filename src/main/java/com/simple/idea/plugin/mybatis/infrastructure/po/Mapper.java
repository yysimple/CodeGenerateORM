package com.simple.idea.plugin.mybatis.infrastructure.po;

import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:02
 **/
public class Mapper extends Base {
    private final Model model;

    public Mapper(String comment, String name, Model model) {
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
