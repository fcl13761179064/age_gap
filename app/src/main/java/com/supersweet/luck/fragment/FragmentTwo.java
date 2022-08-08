package com.supersweet.luck.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.supersweet.luck.R;
import com.supersweet.luck.adapter.ChatFragmentAdapter;
import com.supersweet.luck.adapter.MultualMatchAdapter;
import com.supersweet.luck.base.BaseMvpFragment;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.dialog.MonthPayDialog;
import com.supersweet.luck.mvp.present.MultualMatchPresenter;
import com.supersweet.luck.mvp.view.MultualMatchView;
import com.supersweet.luck.ui.BuyCoinPageActivity;
import com.supersweet.luck.ui.ChatActivity;
import com.supersweet.luck.ui.FavoriteDetailActivity;
import com.supersweet.luck.ui.MutualMatchActivity;
import com.supersweet.luck.utils.FastClickUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CustomToast;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Kevin on 2016/11/20.
 * Blog:http://blog.csdn.net/student9128
 * Describe：the first fragment
 */

public class FragmentTwo extends BaseMvpFragment<MultualMatchView, MultualMatchPresenter> implements MultualMatchView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.mutual_match_recycleview)
    RecyclerView mutual_match_recycleview;
    @BindView(R.id.message_recyclerview)
    RecyclerView message_recyclerview;
    @BindView(R.id.more)
    TextView more;

    private ChatFragmentAdapter message_Adapter;
    private MultualMatchAdapter mutual_matchAdapter;
    private MultualMatchBean multualMatchBean;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mutual_match;
    }

    @Override
    protected void initView(View view) {
        refreshLayout.setEnableLoadMore(false);
        mutual_match_recycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mutual_matchAdapter = new MultualMatchAdapter();
        mutual_match_recycleview.setAdapter(mutual_matchAdapter);
        mutual_matchAdapter.bindToRecyclerView(mutual_match_recycleview);
        mutual_matchAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        message_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        message_Adapter = new ChatFragmentAdapter(R.layout.adapter_chat_fragment) {
            @Override
            public void deleteConnection(String connectionUserId, OtherUserInfoBean item) {
                mPresenter.deleteConnection(connectionUserId);
                V2TIMManager.getConversationManager().deleteConversation("c2c_" + connectionUserId, new V2TIMCallback() {

                    @Override
                    public void onError(int code, String desc) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });
                message_Adapter.getData().remove(item);
                notifyDataSetChanged();
            }
        };
        message_recyclerview.setAdapter(message_Adapter);
        message_Adapter.bindToRecyclerView(message_recyclerview);
        message_Adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

    }


    public void onResumeAdapter() {
        //相当于Fragment的onResume，为true时，Fragment已经可见
        if (mPresenter != null) {
            if (message_Adapter != null && message_Adapter.getData().size() > 0) {
                message_Adapter.getData().clear();
            }
            if (mutual_matchAdapter != null && mutual_matchAdapter.getData().size() > 0) {
                mutual_matchAdapter.getData().clear();
            }
            mPresenter.loadFistPage();
        }
    }


    @Override
    protected void initListener() {
        V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
            @Override
            public void onRecvNewMessage(V2TIMMessage msg) {
                super.onRecvNewMessage(msg);
                message_Adapter.getData().clear();
                getConvencation(0);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mutual_matchAdapter.getData().clear();
                    message_Adapter.getData().clear();
                    mPresenter.loadFistPage();

                }
            }
        });

        mutual_matchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    if (FastClickUtils.isDoubleClick()) {
                        return;
                    }
                    List<MultualMatchBean> data = (List<MultualMatchBean>) adapter.getData();
                    multualMatchBean = data.get(position);
                    mPresenter.getMonthPayMatch(multualMatchBean.getUserId() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        message_Adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (FastClickUtils.isDoubleClick()) {
                    return;
                }
                try {
                    OtherUserInfoBean item = message_Adapter.getItem(position);
                    String userName = item.getUserName();
                    String userID = item.getUserId() + "";
                    String faceUrl = item.getAvatar();
                    String nickName = item.getUserName();
                    String sex = item.getSex();
                    AppData.OhterfoBean = item;
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("sendId", userID);
                    intent.putExtra("userImg", faceUrl);
                    intent.putExtra("userName", nickName);
                    intent.putExtra("userSex", sex);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MultualMatchBean> data = mutual_matchAdapter.getData();
                Intent intent = new Intent(getActivity(), MutualMatchActivity.class);
                intent.putExtra("detail_data", (Serializable) data);
                startActivityForResult(intent, 10001);
            }
        });
    }

    @Override
    protected void initData() {
        refreshLayout.autoRefresh();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        onResumeAdapter();
    }


    public void getConvencation(int count) {
        V2TIMManager.getConversationManager().getConversationList(0, 100, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> conversationList = v2TIMConversationResult.getConversationList();
                if (conversationList.size() > 0) {
                    try {
                        mPresenter.getEveryoneHead(conversationList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }


    @Override
    protected MultualMatchPresenter initPresenter() {
        return new MultualMatchPresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }

    @Override
    public void MultualSuccess(List<MultualMatchBean> data) {
        mutual_matchAdapter.setNewData(data);
        loadDataFinish();
    }

    @Override
    public void ConvercationSuccess(int count) {
        getConvencation(count);
    }


    @Override
    public void chatHeadSuccess(List<OtherUserInfoBean> bean, List<V2TIMConversation> message) {
        try {
            message_Adapter.getData().clear();
            for (int x = 0; x < bean.size(); x++) {
                String text = message.get(x).getLastMessage().getTextElem().getText();
                bean.get(x).setMessage(text);
            }
            message_Adapter.addData(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void MultualMatchSuccess(IntenetReposeBean data, int userId) {
        if ("0".equals(data.getCode()) && "success".equalsIgnoreCase(data.getMsg())) {
            multualMatchBean.setMatchFreeFlag(1);
            Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
            intent.putExtra("UserId", userId);
            startActivityForResult(intent, 10001);
        } else {
            if ("Your Are Balance is insufficient.".equalsIgnoreCase(data.getMsg())) {//你的余额不足
                multualMatchBean.setMatchFreeFlag(-1);
                Intent intent = new Intent(getActivity(), BuyCoinPageActivity.class);
                startActivityForResult(intent, 10001);//10002表示跳到付费页面
            } else {
                CustomToast.makeText(getActivity(), data.getMsg(), R.drawable.ic_toast_warming).show();
            }
        }
    }

    @Override
    public void MultualMatchFail(String o) {
        CustomToast.makeText(getActivity(), o, R.drawable.ic_toast_warming).show();
    }


    public void loadDataFinish() {
        if (mutual_matchAdapter.getData().size() == 0 && message_Adapter.getData().size() == 0) {
            message_Adapter.setEmptyView(R.layout.empty_big_page);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            onResumeAdapter();
        }

    }

    @Override
    public void checkIsMonthPay(IntenetReposeBean data) {
        if (data != null) {
            if ("0".equals(data.getCode())) {
                int userId = multualMatchBean.getUserId();
                Intent intent = new Intent(getActivity(), FavoriteDetailActivity.class);
                intent.putExtra("UserId", userId);
                startActivityForResult(intent, 10001);
            } else {
                MonthPayDialog dialog = new MonthPayDialog(getContext());
                dialog.setOnSureClick(new MonthPayDialog.OnSureClick() {

                    @Override
                    public void click(Dialog dialog) {

                    }
                });
                dialog.show();
                dialog.setGravity(Gravity.CENTER);
            }
        }

    }
}
