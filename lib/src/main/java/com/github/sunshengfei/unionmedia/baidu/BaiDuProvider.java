package com.github.sunshengfei.unionmedia.baidu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.nativecpu.CPUAdRequest;
import com.baidu.mobads.nativecpu.CpuLpFontSize;
import com.baidu.mobads.nativecpu.IBasicCPUData;
import com.baidu.mobads.nativecpu.NativeCPUManager;
import com.github.sunshengfei.unionmedia.Bridge;
import com.github.sunshengfei.unionmedia.IUnionMediaProvider;
import com.github.sunshengfei.unionmedia.tools.RegexHelper;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.Secure.getString;

public class BaiDuProvider implements IUnionMediaProvider {

    private NativeCPUManager mCpuManager;


    public static int defaultChannel = 1022;

    public static List<ChannelMap> userMaps;

    public List<ChannelMap> getChannels() {
        if (userMaps != null && userMaps.size() > 0) return userMaps;
        List<ChannelMap> maps = new ArrayList<>();
        maps.add(new ChannelMap("推荐", 1022));
        maps.add(new ChannelMap("视频", 1057));
        maps.add(new ChannelMap("娱乐", 1001));
        maps.add(new ChannelMap("热讯", 1081));
        maps.add(new ChannelMap("小品", 1062));//-视频
        maps.add(new ChannelMap("生活", 1066));//-视频
        maps.add(new ChannelMap("搞笑", 1059));//-视频
        maps.add(new ChannelMap("本地", 1080));
        maps.add(new ChannelMap("热点", 1021));
        maps.add(new ChannelMap("科技", 1013));
        maps.add(new ChannelMap("健康", 1043));
        maps.add(new ChannelMap("军事", 1012));
        maps.add(new ChannelMap("母婴", 1042));
        return maps;
    }


    public void init(Context context, String appId, UnionMediaDataProvider listener) {
        if (RegexHelper.isEmpty(appId)) throw new IllegalArgumentException("appId should be set");
        mCpuManager = new NativeCPUManager(context, appId, new NativeCPUManager.CPUAdListener() {
            @Override
            public void onAdLoaded(List<IBasicCPUData> list) {
                if (listener != null) {
                    listener.onData(list);
                }
            }

            @Override
            public void onAdError(String s, int i) {

            }

            @Override
            public void onNoAd(String s, int i) {

            }

            @Override
            public void onAdClick() {

            }

            @Override
            public void onVideoDownloadSuccess() {

            }

            @Override
            public void onVideoDownloadFailed() {

            }

            @Override
            public void onAdStatusChanged(String s) {

            }
        });
    }

    public void loadAd(int channel, int pageIndex) {
        if (mCpuManager != null) {
            CPUAdRequest.Builder builder = new CPUAdRequest.Builder(); // 设置下载行为的弹框策略，默认 4G 下弹框提示下载
            builder.setDownloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ONLY_MOBILE);
            // 少部分机型出现无法获取手机设备信息问题，媒体可以通过设置 CustomUserId 来代替。
//        builder.setCustomUserId(getAndroidId(context));
            if (RegexHelper.isEmpty(Bridge.DeviceID))
                builder.setCustomUserId("123456789abcdefg");
            else builder.setCustomUserId(Bridge.DeviceID);
            //将配置好的请求参数传入 NativeCPUManager
            mCpuManager.setRequestParameter(builder.build());
            mCpuManager.setLpFontSize(CpuLpFontSize.REGULAR);
            mCpuManager.setLpDarkMode(false);
            // 设置请求的超时时间，单位毫秒，不设置时默认为 5 秒
            mCpuManager.setRequestTimeoutMillis(10 * 1000);
            mCpuManager.loadAd(pageIndex, channel, true);
        }

    }

    public void release() {
        mCpuManager = null;
    }


    @SuppressLint("HardwareIds")
    public static String getAndroidId(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String androidId = getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId == null ? "" : androidId;
        } catch (Exception e) {
            return "";
        }
    }

}
