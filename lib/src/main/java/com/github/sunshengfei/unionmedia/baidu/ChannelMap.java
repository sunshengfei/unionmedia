package com.github.sunshengfei.unionmedia.baidu;

public class ChannelMap {
    private String channelName;
    private int ChannelId;

    public ChannelMap() {
    }

    public ChannelMap(String channelName, int channelId) {
        this.channelName = channelName;
        ChannelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelId() {
        return ChannelId;
    }

    public void setChannelId(int channelId) {
        ChannelId = channelId;
    }
}
