package com.mini.registry.loadbalance;

import com.mini.extention.SPI;

import java.util.List;

/**
 * @author carl-xiao
 * @description
 **/
@SPI
public interface LoadBalance<T> {
    T select(List<T> servers, int hashcode);
}
