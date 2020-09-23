package com.github.sunshengfei.unionmedia;

import java.io.Serializable;

public class IStreamModel<T> implements Serializable {

    private int type = IStreamType.BAIDU_CPU;
    private T data;


    public IStreamModel() {
    }

    public IStreamModel(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
