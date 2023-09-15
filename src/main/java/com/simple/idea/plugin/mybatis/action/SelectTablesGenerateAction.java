package com.simple.idea.plugin.mybatis.action;

import com.intellij.database.model.*;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbDataSourceImpl;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.ui.JBUI;
import com.simple.idea.plugin.mybatis.domain.service.impl.ProjectGeneratorImpl;
import com.simple.idea.plugin.mybatis.infrastructure.constant.CommonConstants;
import com.simple.idea.plugin.mybatis.infrastructure.constant.CommonDict;
import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandler;
import com.simple.idea.plugin.mybatis.infrastructure.handler.TypeHandlerInit;
import com.simple.idea.plugin.mybatis.infrastructure.handler.dto.TypeTransferDTO;
import com.simple.idea.plugin.mybatis.infrastructure.handler.sub.NoMatch;
import com.simple.idea.plugin.mybatis.infrastructure.utils.JavaType;
import com.simple.idea.plugin.mybatis.infrastructure.utils.StringUtils;
import com.simple.idea.plugin.mybatis.infrastructure.utils.TableUtils;
import com.simple.idea.plugin.mybatis.ui.TableToCodeUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2023-07-28 16:33
 **/
public class SelectTablesGenerateAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 获取当前项目
        Project project = getEventProject(event);
        if (project == null) {
            return;
        }

        //获取选中的PSI元素
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        DbTable selectDbTable = null;
        if (psiElement instanceof DbTable) {
            selectDbTable = (DbTable) psiElement;
        }
        if (selectDbTable == null) {
            return;
        }
        //获取选中的所有表
        PsiElement[] psiElements = event.getData(LangDataKeys.PSI_ELEMENT_ARRAY);
        if (psiElements == null || psiElements.length == 0) {
            return;
        }
        List<DbTable> dbTableList = new ArrayList<>();
        for (PsiElement element : psiElements) {
            if (!(element instanceof DbTable)) {
                continue;
            }
            DbTable dbTable = (DbTable) element;
            dbTableList.add(dbTable);
        }
        if (dbTableList.isEmpty()) {
            return;
        }
        if (!dbTableList.stream().allMatch(dbTable -> typeValidator(project, dbTable))) {
            return;
        }
        ShowSettingsUtil.getInstance().editConfigurable(project, new TableToCodeUI(project, dbTableList, new ProjectGeneratorImpl()));
    }

    /**
     * 类型校验，如果存在未知类型则引导用于去条件类型
     *
     * @param dbTable 原始表对象
     * @return 是否验证通过
     */
    private boolean typeValidator(Project project, DbTable dbTable) {
        // 处理所有列
        JBIterable<? extends DasColumn> columns = DasUtil.getColumns(dbTable);
        DasNamespace namespace = DasUtil.getNamespace(dbTable);
        String dataSourceName = TableUtils.getDataSourceName(namespace);
        // 简单的记录报错弹窗次数，避免重复报错
        for (DasColumn column : columns) {
            DataType dataType = TableUtils.getDataType(column);
            TypeTransferDTO typeTransferDTO = TypeTransferDTO.build(dataType);
            Class transfer = TypeHandlerInit.HANDLER_MAPS.get(CommonConstants.COMMON).transfer(typeTransferDTO);
            if (transfer != NoMatch.class) {
                continue;
            }
            TypeHandler typeHandler = TypeHandlerInit.HANDLER_MAPS.get(dataSourceName);
            if (Objects.nonNull(typeHandler)) {
                Class otherHandler = typeHandler.transfer(typeTransferDTO);
                if (otherHandler != NoMatch.class) {
                    continue;
                }
            }
            String typeName = typeTransferDTO.getTypeName();
            // 没找到类型，提示用户选择输入类型
            new Dialog(project, typeName).showAndGet();
        }
        return true;
    }


    public static class Dialog extends DialogWrapper {

        private final String typeName;

        private JPanel mainPanel;

        private ComboBox<String> comboBox;

        protected Dialog(@Nullable Project project, String typeName) {
            super(project);
            this.typeName = typeName;
            this.initPanel();
        }

        private void initPanel() {
            setTitle("Code Generate ORM");
            String msg = String.format("数据库类型%s，没有找到映射关系，请输入想转换的类型？", typeName);
            JLabel label = new JLabel(msg);
            this.mainPanel = new JPanel(new BorderLayout());
            this.mainPanel.setBorder(JBUI.Borders.empty(5, 10, 7, 10));
            mainPanel.add(label, BorderLayout.NORTH);
            this.comboBox = new ComboBox<>(CommonDict.DEFAULT_JAVA_TYPE_LIST);
            this.comboBox.setEditable(true);
            this.mainPanel.add(this.comboBox, BorderLayout.CENTER);
            init();
        }

        @Override
        protected @Nullable JComponent createCenterPanel() {
            return this.mainPanel;
        }

        @Override
        protected void doOKAction() {
            super.doOKAction();
            String selectedItem = (String) this.comboBox.getSelectedItem();
            if (StringUtils.isEmpty(selectedItem)) {
                return;
            }
            try {
                JavaType.OTHER_MAP.put(typeName, Class.forName(selectedItem));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
