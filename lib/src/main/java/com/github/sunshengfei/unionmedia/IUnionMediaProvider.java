package com.github.sunshengfei.unionmedia;

import android.content.Context;

import com.baidu.mobads.nativecpu.IBasicCPUData;

import java.util.List;

import com.github.sunshengfei.unionmedia.baidu.ChannelMap;

public interface IUnionMediaProvider {

    public List<ChannelMap> getChannels();

    public void init(Context context, String appId, UnionMediaDataProvider listener);

    public void loadAd(int channel, int pageIndex);

    public interface UnionMediaDataProvider {
        void onData(List<IBasicCPUData> list);
    }

}
