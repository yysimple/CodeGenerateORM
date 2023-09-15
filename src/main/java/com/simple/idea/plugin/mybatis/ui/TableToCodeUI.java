package com.simple.idea.plugin.mybatis.ui;

import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasNamespace;
import com.intellij.database.model.DataType;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.JBIterable;
import com.simple.idea.plugin.mybatis.action.SelectTablesGenerateAction;
import com.simple.idea.plugin.mybatis.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.mybatis.domain.model.vo.ORMConfigVO;
import com.simple.idea.plugin.mybatis.domain.service.IProjectGenerator;
import com.simple.idea.plugin.mybatis.infrastructure.data.DataSetting;
import com.simple.idea.plugin.mybatis.infrastructure.data.GenerateOptions;
import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandler;
import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandlerInit;
import com.simple.idea.plugin.mybatis.infrastructure.handler.dto.TypeTransferDTO;
import com.simple.idea.plugin.mybatis.infrastructure.handler.sub.NoMatch;
import com.simple.idea.plugin.mybatis.infrastructure.po.Column;
import com.simple.idea.plugin.mybatis.infrastructure.po.Table;
import com.simple.idea.plugin.mybatis.infrastructure.constant.CommonConstants;
import com.simple.idea.plugin.mybatis.infrastructure.utils.DBHelper;
import com.simple.idea.plugin.mybatis.infrastructure.utils.TableUtils;
import com.simple.idea.plugin.mybatis.module.FileChooserComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: UI布局，我们在进行各种设置的时候的一种布局入口
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:45
 **/
public class TableToCodeUI implements Configurable {
    /**
     * 各种UI面板
     */
    private JPanel main;
    private JTextField classpath;
    private JTextField projectName;
    private JTextField poPath;
    private JTextField daoPath;
    private JButton poButton;
    private JButton daoButton;
    private JTextField xmlPath;
    private JButton xmlButton;
    private JTextField controllerPath;
    private JButton controllerButton;
    private JTextField servicePath;
    private JTextField implPath;
    private JButton serviceButton;
    private JButton implButton;
    private JCheckBox mybatisPlusYes;
    private JCheckBox serviceYes;
    private JCheckBox createDirYes;
    private JCheckBox controllerYes;
    private JCheckBox swaggerYes;
    private JTextField authorField;

    /**
     * 我们自己的一些配置信息
     */
    private final ORMConfigVO config;

    /**
     * 配置项
     */
    private final GenerateOptions options;

    /**
     * 这里包含一个项目的基本信息
     */
    private final Project project;

    /**
     * 我们自己代码生成逻辑
     */
    private final IProjectGenerator projectGenerator;

    private final List<DbTable> dbTableList;


    public TableToCodeUI(Project project, List<DbTable> dbTableList, IProjectGenerator projectGenerator) {
        this.project = project;
        // 相当于注入文件生成的bean
        this.projectGenerator = projectGenerator;
        // 这里是拿到配置信息（这里是通过在idea的缓存中拿到的，第一次初始化插件是没有的）
        config = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getORMConfig();
        options = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getOptions();
        // 下面开始就是拿到上次初始化过后的一些值重新赋值
        assert project != null;
        this.projectName.setText(project.getName());
        this.classpath.setText(project.getBasePath());
        this.authorField.setText(config.getAuthor());
        // 回显设置的各种路径
        this.poPath.setText(config.getPoPath());
        this.daoPath.setText(config.getDaoPath());
        this.xmlPath.setText(config.getXmlPath());
        this.controllerPath.setText(config.getControllerPath());
        this.servicePath.setText(config.getServicePath());
        this.implPath.setText(config.getImplPath());

        this.dbTableList = dbTableList;

        // 设置之前的按钮选择状态
        settingButtonStatus();

        // 文件生成目录回显
        chooseFiles();

    }

    public void chooseFiles() {
        // 选择PO生成目录
        this.poButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择PO生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.poPath.setText(virtualFile.getPath());
            }
        });

        // 选择DAO生成目录
        this.daoButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择DAO生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.daoPath.setText(virtualFile.getPath());
            }
        });

        // 选择XMl生成目录
        this.xmlButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择XML生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.xmlPath.setText(virtualFile.getPath());
            }
        });

        // 选择Controller生成目录
        this.controllerButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择Controller生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.controllerPath.setText(virtualFile.getPath());
            }
        });

        // 选择Service生成目录
        this.serviceButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择Service生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.servicePath.setText(virtualFile.getPath());
            }
        });

        // 选择Impl生成目录
        this.implButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择Impl生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                TableToCodeUI.this.implPath.setText(virtualFile.getPath());
            }
        });
    }

    /**
     * 设置按钮的选取状态
     */
    public void settingButtonStatus() {
        mybatisPlusYes.setSelected(CommonConstants.YES.equals(options.getIsPlus()));
        createDirYes.setSelected(CommonConstants.YES.equals(options.getIsCreateDir()));
        serviceYes.setSelected(CommonConstants.YES.equals(options.getIsCreateService()));
        controllerYes.setSelected(CommonConstants.YES.equals(options.getIsCreateController()));
        swaggerYes.setSelected(CommonConstants.YES.equals(options.getIsCreateSwagger()));
    }

    @Override
    public @Nullable JComponent createComponent() {
        return main;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() {
        // 获取配置
        config.setProjectName(this.projectName.getText());
        config.setClasspath(this.classpath.getText());
        // 设置文件路径
        config.setPoPath(this.poPath.getText());
        config.setDaoPath(this.daoPath.getText());
        config.setXmlPath(this.xmlPath.getText());
        config.setControllerPath(this.controllerPath.getText());
        config.setServicePath(this.servicePath.getText());
        config.setImplPath(this.implPath.getText());
        config.setAuthor(this.authorField.getText());

        /**
         * 全局配置项
         */
        options.setIsPlus(getIsPlus());
        options.setIsCreateController(getIsCreateController());
        options.setIsCreateService(getIsCreateService());
        options.setIsCreateDir(getIsCreateDir());
        options.setIsCreateSwagger(getIsCreateSwagger());

        // 组装代码生产上下文
        CodeGenContextVO codeGenContext = new CodeGenContextVO();

        // 这里是去判断是否需要生成前置目录（先将所有的目录生成上下文组装好，留下是否需要生成service等选项之后判断）
        codeGenContext.setModelPackage((CommonConstants.YES.equals(getIsCreateDir())) ? config.getPoPath() + "/domain/" : config.getPoPath() + "/");
        codeGenContext.setDaoPackage((CommonConstants.YES.equals(getIsCreateDir())) ? config.getDaoPath() + "/mapper/" : config.getDaoPath() + "/");
        codeGenContext.setMapperDir((CommonConstants.YES.equals(getIsCreateDir())) ? config.getXmlPath() + "/mapper/" : config.getXmlPath() + "/");
        codeGenContext.setControllerPackage((CommonConstants.YES.equals(getIsCreateDir())) ? config.getControllerPath() + "/controller/" : config.getControllerPath() + "/");
        codeGenContext.setServicePackage((CommonConstants.YES.equals(getIsCreateDir())) ? config.getServicePath() + "/service/" : config.getServicePath() + "/");
        codeGenContext.setImplPackage((CommonConstants.YES.equals(getIsCreateDir())) ? config.getImplPath() + "/service/impl/" : config.getImplPath() + "/");
        codeGenContext.setAuthor(config.getAuthor());
        codeGenContext.setProjectName(config.getProjectName());

        List<Table> tables = buildTable();

        codeGenContext.setTables(tables);

        // 生成代码
        projectGenerator.generation(project, codeGenContext, options, CommonConstants.SELECT_FOR_TABLE);
    }

    private List<Table> buildTable() {
        List<Table> tables = new ArrayList<>();
        dbTableList.forEach(dbTable -> {
            DasNamespace namespace = DasUtil.getNamespace(dbTable);
            String dataSourceName = TableUtils.getDataSourceName(namespace);
            tables.add(new Table(dbTable.getComment(), dbTable.getName(), buildColumns(dbTable), dataSourceName));
        });
        return tables;
    }

    private List<Column> buildColumns(DbTable dbTable) {
        List<Column> javaColumns = new ArrayList<>();
        // 处理所有列
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
        // 简单的记录报错弹窗次数，避免重复报错
        for (DasColumn column : columns) {
            DataType dataType = TableUtils.getDataType(column);
            boolean primary = DasUtil.isPrimary(column);
            Column javaColumn = new Column(column.getComment(), column.getName(),
                    dataType.typeName, dataType.size, dataType.scale, primary);
            javaColumns.add(javaColumn);
        }
        return javaColumns;
    }


    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Config";
    }

    /**
     * 获取当前按钮的状态
     *
     * @return
     */
    public String getIsPlus() {
        return mybatisPlusYes.isSelected() ? CommonConstants.YES : "";
    }

    public String getIsCreateDir() {
        return createDirYes.isSelected() ? CommonConstants.YES : "";
    }

    public String getIsCreateService() {
        return serviceYes.isSelected() ? CommonConstants.YES : "";
    }

    public String getIsCreateController() {
        return controllerYes.isSelected() ? CommonConstants.YES : "";
    }

    public String getIsCreateSwagger() {
        return swaggerYes.isSelected() ? CommonConstants.YES : "";
    }
}
