package com.github.sunshengfei.unionmedia.composer;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.baidu.mobads.nativecpu.IBasicCPUData;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import com.github.sunshengfei.unionmedia.Bridge;
import com.github.sunshengfei.unionmedia.IStreamModel;
import com.github.sunshengfei.unionmedia.IStreamType;
import com.github.sunshengfei.unionmedia.IUnionMediaProvider;
import com.github.sunshengfei.unionmedia.R;
import com.github.sunshengfei.unionmedia.baidu.BaiDuProvider;
import com.github.sunshengfei.unionmedia.baidu.ui.MediaAdapter;
import com.github.sunshengfei.unionmedia.tools.RegexHelper;

public class ContentMediaFragment extends BaseFragment implements IUnionMediaProvider.UnionMediaDataProvider {

    private RecyclerView rv_data_view;
    private MediaAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private int page;
    private IUnionMediaProvider provider;
    private static final String CHANNEL = "CHANNEL";
    private int currentChannel;


    private boolean hasMore = true;
    private List<IStreamModel<Object>> dataset;

    private ContentMediaFragment() {
    }

    public static ContentMediaFragment newInstance(int channelId) {
        Bundle args = new Bundle();
        args.putInt(CHANNEL, channelId);
        ContentMediaFragment fragment = new ContentMediaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_union_list;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        rv_data_view = (RecyclerView) $(R.id.rv_data_view);
        refreshLayout = (SmartRefreshLayout) $(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        dataset = new ArrayList<>();
        adapter = new MediaAdapter(mContext, dataset);
        rv_data_view.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        this.page = 1;
        if (getArguments() != null) {
            currentChannel = getArguments().getInt(CHANNEL, BaiDuProvider.defaultChannel);
        } else {
            currentChannel = BaiDuProvider.defaultChannel;
        }
        provider = new BaiDuProvider();
        provider.init(mContext, Bridge.AppId, this);
        super.initEvent();
        adapter.setOnItemClickListener((holder, item) -> {
            if (item.getType() == IStreamType.BAIDU_CPU) {
                IBasicCPUData iBasicCPUData = (IBasicCPUData) item.getData();
                iBasicCPUData.handleClick(holder.itemView);
            }
        });
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            boolean isBlocked = loadData();
            refreshlayout.finishRefresh(2000);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            loadData();
            refreshlayout.finishLoadMore(1000);//传入false表示加载失败
        });
        refreshLayout.autoRefresh();
    }


    private boolean loadData() {
        provider.loadAd(currentChannel, page);
        return false;
    }

    @Override
    public void onData(List<IBasicCPUData> list) {
        if (RegexHelper.isEmpty(list)) {
            hasMore = false;
            refreshLayout.setEnableLoadMore(false);
            return;
        }
        hasMore = true;
        refreshLayout.setEnableLoadMore(true);
        List<IStreamModel<Object>> finalList = Stream.of(list).map(item -> new IStreamModel<Object>(IStreamType.BAIDU_CPU, item)).toList();
        if (page == 1) {
            dataset.clear();
        }
        dataset.addAll(finalList);
        adapter.notifyDataSetChanged();
        page++;
    }
}
