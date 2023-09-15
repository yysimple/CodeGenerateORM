package com.simple.idea.plugin.mybatis.infrastructure.po;

import com.google.common.base.CaseFormat;
import com.simple.idea.plugin.mybatis.infrastructure.utils.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:00
 **/
public abstract class Base {
    private int ormType;
    private String comment;
    private String name;
    private String author;
    private String projectName;

    public Base(String comment, String name) {
        this.comment = comment;
        this.name = name;
    }

    public String getPackage() {
        String str = StringUtils.substringAfterLast(name, "java/");
        str = str.substring(0, str.lastIndexOf(getSimpleName()) - 1);
        return str.replaceAll("/", ".");
    }

    protected Set<String> setImports(Model model, Service service, Mapper mapper, Response response) {
        Set<String> baseImports = new HashSet<>();
        if (Objects.nonNull(model)) {
            baseImports.add(model.getPackage() + "." + model.getSimpleName());
        }
        if (Objects.nonNull(service)) {
            baseImports.add(service.getPackage() + "." + service.getSimpleName());
        }
        if (Objects.nonNull(mapper)) {
            baseImports.add(mapper.getPackage() + "." + mapper.getSimpleName());
        }
        if (Objects.nonNull(response)) {
            baseImports.add(response.getPackage() + "." + response.getSimpleName());
        }
        return setImports(model, baseImports);
    }

    protected Set<String> setImports(Model model, Set<String> baseImports) {
        if (Objects.nonNull(model)) {
            List<Field> fields = model.getFields();
            for (Field field : fields) {
                if (field.isId() && field.isImport()) {
                    baseImports.add(field.getTypeName());
                    break;
                }
            }
        }
        return baseImports;
    }


    /**
     * 获取需要倒入的包
     *
     * @return
     */
    public abstract Set<String> getImports();

    public String getComment() {
        return comment;
    }

    public String getSimpleName() {
        return name.lastIndexOf("/") == -1 ? name : StringUtils.substringAfterLast(name, "/");
    }

    public String getVarName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getSimpleName());
    }

    public String getName() {
        return name;
    }

    public void setOrmType(int ormType) {
        this.ormType = ormType;
    }

    public int getOrmType() {
        return ormType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
