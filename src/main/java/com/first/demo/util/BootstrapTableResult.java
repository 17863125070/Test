package com.first.demo.util;

import java.util.List;

/**
 * bootstrap-Table返回工具类
 * @author sir.song
 */
public class BootstrapTableResult {

    /**
     * 总数据量
     */
    private long total;
    /**
     * 返回list
     */
    private List<?> rows;

    BootstrapTableResult(){}

    public BootstrapTableResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
