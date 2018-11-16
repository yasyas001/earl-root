package com.earl.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanqihan
 * @version 2018/1/17
 */
@SuppressWarnings("rawtypes")
public class GenConfig implements Serializable {

    private static final long serialVersionUID = 5805590088497297114L;

    private String url;

    private String username;

    private String password;

    private String tableName;

    private String outputDir;

    private String parent;

    private String moduleName;

    private List advancedColumns = new ArrayList();

    private boolean isSupportDelete;

    private boolean isSupportEdit;

    private List<GenColumn> columnList = new ArrayList<>();

    public List getAdvancedColumns() {
        return advancedColumns;
    }

    public void setAdvancedColumns(List advancedColumns) {
        this.advancedColumns = advancedColumns;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<GenColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<GenColumn> columnList) {
        this.columnList = columnList;
    }

    public boolean isSupportDelete() {
        return isSupportDelete;
    }

    public void setSupportDelete(boolean supportDelete) {
        isSupportDelete = supportDelete;
    }

    public boolean isSupportEdit() {
        return isSupportEdit;
    }

    public void setSupportEdit(boolean supportEdit) {
        isSupportEdit = supportEdit;
    }
}
