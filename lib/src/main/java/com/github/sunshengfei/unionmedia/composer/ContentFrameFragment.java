package com.github.sunshengfei.unionmedia.composer;

import android.view.LayoutInflater;

import androidx.viewpager.widget.ViewPager;

import com.annimon.stream.Stream;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import com.github.sunshengfei.unionmedia.IUnionMediaProvider;
import com.github.sunshengfei.unionmedia.R;
import com.github.sunshengfei.unionmedia.baidu.BaiDuProvider;
import com.github.sunshengfei.unionmedia.baidu.ChannelMap;

public class ContentFrameFragment extends BaseFragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private IUnionMediaProvider provider;

    @Override
    protected int getLayout() {
        return R.layout.fragment_union_main;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        viewPager = (ViewPager) $(R.id.contentPages);
        tabLayout = (TabLayout) $(R.id.tabLayout);
        provider = new BaiDuProvider();
        List<ChannelMap> channels = provider.getChannels();
        List<String> titlesList = Stream.of(channels).map(ChannelMap::getChannelName).toList();
        String[] titles = titlesList.toArray(new String[]{});
        List<ContentMediaFragment> fragmentsList = Stream.of(channels).map(item -> ContentMediaFragment.newInstance(item.getChannelId())).toList();
        ContentMediaFragment[] fragments = fragmentsList.toArray(new ContentMediaFragment[]{});
        PagerAdapter pageAdapter = new PagerAdapter(getFragmentManager(), titles, fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(channels.size() % 5);
        tabLayout.setupWithViewPager(viewPager);
    }
}
