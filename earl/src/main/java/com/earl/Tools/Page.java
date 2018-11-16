package com.earl.Tools;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = -1023518292361681996L;
    private int pageNo;
    private int pageSize;
    private long count;
    private List list;
    private String orderBy;

    public Page() {
        this.pageNo = 1;
        this.pageSize = 10;
        this.list = new ArrayList();
        this.orderBy = "";
        this.pageNo = 1;
    }

    public Page(HttpServletRequest request) {
        this.pageNo = 1;
        this.pageSize = 10;
        this.list = new ArrayList();
        this.orderBy = "";
        String no = this.getLastRequestParam(request, "pageNo");

        if (StringUtils.isNumeric(no)) {
            this.setPageNo(Integer.parseInt(no));
        } else {
            this.setPageNo(1);
        }

        String size = this.getLastRequestParam(request, "pageSize");

        if (StringUtils.isNumeric(size)) {
            this.setPageSize(Integer.parseInt(size));
        }

        String orderBy = request.getParameter("orderBy");

        if (StringUtils.isNotBlank(orderBy)) {
            this.setOrderBy(orderBy);
        }

    }

    private String getLastRequestParam(HttpServletRequest request, String paramName) {
        String[] strs = request.getParameterValues(paramName);
        return strs != null ? strs[strs.length - 1] : null;
    }

    public Page(int pageNo, int pageSize) {
        this(pageNo, pageSize, 0L);
    }

    public Page(int pageNo, int pageSize, long count) {
        this(pageNo, pageSize, count, new ArrayList());
    }

    public Page(int pageNo, int pageSize, long count, List<T> list) {
        this.pageNo = 1;
        this.pageSize = 10;
        this.list = new ArrayList();
        this.orderBy = "";
        this.setCount(count);
        this.setPageNo(pageNo);
        this.pageSize = pageSize;
        this.list = list;
    }


    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
        if ((long)this.pageSize >= count) {
            this.pageNo = 1;
        }
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public Page<T> setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public Page<T> setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
        return this;
    }


    public List getList() {
        return this.list;
    }

    public Page<T> setList(List list) {
        this.list = list;
        return this;
    }

    @JSONField(
        serialize = false
    )
    public String getOrderBy() {
        if (this.orderBy == null) {
            return null;
        } else {
            String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
            Pattern sqlPattern = Pattern.compile(reg, 2);
            return sqlPattern.matcher(this.orderBy).find() ? "" : this.orderBy;
        }
    }

    public Page<T> setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

}
