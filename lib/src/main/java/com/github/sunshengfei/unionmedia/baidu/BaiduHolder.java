package com.github.sunshengfei.unionmedia.baidu;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.androidquery.AQuery;
import com.baidu.mobads.nativecpu.IBasicCPUData;

import com.github.sunshengfei.unionmedia.IStreamHolder;
import com.github.sunshengfei.unionmedia.R;
import com.github.sunshengfei.unionmedia.baidu.ui.NativeCPUView;

public class BaiduHolder extends IStreamHolder<IBasicCPUData> {

    private AQuery aQuery;

    public BaiduHolder(@NonNull ViewGroup parent, AQuery aQuery) {
        super(parent, R.layout.feed_native_listview_item);
        this.aQuery = aQuery;
    }

    @Override
    public void update(IBasicCPUData nrAd) {
        ((ViewGroup) itemView).removeAllViews();
        final NativeCPUView cpuView = new NativeCPUView(getContext());
        if (cpuView.getParent() != null) {
            ((ViewGroup) cpuView.getParent()).removeView(cpuView);
        }
        cpuView.setItemData(nrAd, aQuery);
        ((ViewGroup) itemView).addView(cpuView);
        // 展现时需要调用onImpression上报展现
        nrAd.onImpression(itemView);
    }

}