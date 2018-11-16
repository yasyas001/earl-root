package com.earl.util;

import java.io.Serializable;

/**
 * @author wanqihan
 * @version 2018/1/17
 */
public class GenColumn implements Serializable{

    private static final long serialVersionUID = 2744940304733831633L;

    private String name;

    private String key;

    private boolean isSearch;

    private boolean isEdit;

    private boolean isTable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isTable() {
        return isTable;
    }

    public void setTable(boolean table) {
        isTable = table;
    }
}
