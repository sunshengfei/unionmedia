package com.github.sunshengfei.unionmedia;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class EmptyHolder extends IStreamHolder {

    public EmptyHolder(@NonNull View itemView) {
        super(itemView);
    }

    public EmptyHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.empty_holder_item);
    }

    @Override
    public void update(Object o) {

    }
}
