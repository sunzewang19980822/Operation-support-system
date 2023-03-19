package com.kfm.model;

import lombok.Data;

@Data
public class QueryInfo {
    private String query;
    // 当前的页数
    private int pagenum;
    // 当前每页显示多少条数据
    private int pagesize;
}
