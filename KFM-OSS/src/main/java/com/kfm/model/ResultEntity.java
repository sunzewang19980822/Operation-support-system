package com.kfm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultEntity<T> {
    private int code;
    private String message;
    private T data;

}
