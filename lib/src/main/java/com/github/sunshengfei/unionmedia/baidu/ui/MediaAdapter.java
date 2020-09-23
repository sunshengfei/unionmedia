package com.github.sunshengfei.unionmedia.baidu.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import java.util.ArrayList;
import java.util.List;

import com.github.sunshengfei.unionmedia.EmptyHolder;
import com.github.sunshengfei.unionmedia.IStreamHolder;
import com.github.sunshengfei.unionmedia.IStreamModel;
import com.github.sunshengfei.unionmedia.IStreamType;
import com.github.sunshengfei.unionmedia.baidu.BaiduHolder;

public class MediaAdapter extends RecyclerView.Adapter<IStreamHolder> {
    private Context context;
    LayoutInflater inflater;
    AQuery aq;

    private List<IStreamModel<Object>> nrAdList = new ArrayList<>();

    public MediaAdapter(Context context) {
        this(context, null);
    }

    public MediaAdapter(Context context, List<IStreamModel<Object>> mData) {
        super();
        this.context = context;
        if (mData != null) {
            nrAdList = mData;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        aq = new AQuery(context);
    }

    public void notifyData(List<IStreamModel<Object>> nrAdList) {
        if (nrAdList != null) {
            this.nrAdList = nrAdList;
        } else {
            this.nrAdList.clear();
        }
        notifyDataSetChanged();
    }

    public IStreamModel<Object> getItem(int position) {
        return nrAdList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        IStreamModel<Object> item = getItem(position);
        if (item == null) {
            return IStreamType.BAIDU_NONE;
        }
        return item.getType();
    }

    @NonNull
    @Override
    public IStreamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == IStreamType.BAIDU_CPU) {
            return new BaiduHolder(parent, aq);
        }
        return new EmptyHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull IStreamHolder holder, int position) {
//        int type = getItemViewType(position);
        IStreamModel<Object> item = getItem(position);
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(holder, item);
        });
        holder.update(item.getData());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return nrAdList.size();
    }

    private void bindData2View(View view, String data, int dataType) {
        if (TextUtils.isEmpty(data)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            if (dataType == 1) {
                aq.id(view).text(data);
            } else if (dataType == 2) {
                // 通过callback的方式渲染ImageView，避免AQuery直接渲染后将View.GONE的控件显示出来
                aq.id(view).image(data, false, true, 0, 0,
                        new BitmapAjaxCallback() {
                            @Override
                            protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (iv.getVisibility() == View.VISIBLE) {
                                    iv.setImageBitmap(bm);
                                }
                            }
                        });
            }
        }
    }

    public List<IStreamModel<Object>> getDataSet() {
        return nrAdList;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(RecyclerView.ViewHolder holder, IStreamModel<Object> item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}