package com.github.sunshengfei.unionmedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class IStreamHolder<T> extends RecyclerView.ViewHolder {


    public IStreamHolder(View itemView) {
        super(itemView);
    }

    public IStreamHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    protected <V extends View> V $(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void update(T t);

}
