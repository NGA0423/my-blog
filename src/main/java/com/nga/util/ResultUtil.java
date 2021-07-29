package com.nga.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtil<T> {
    private int msg;
    private boolean success;
    private T detail;

}
