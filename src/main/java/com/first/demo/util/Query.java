package com.first.demo.util;

/**
 * 功能描述:
 * 〈前端查询封装工具类〉
 *
 * @param
 * @return :
 * @author : songhuanhao
 * @date : 2019/11/29 9:05
 */
public class Query {

    /**
     * 当前页
     */
    private int offset;

    /**
     * 一页展示的条数
     */
    private int limit;
    /**
     * 查询
     */
    private String search;
    /**
     *
     */
    private String order;
    /**
     *
     */
    private String sort;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
