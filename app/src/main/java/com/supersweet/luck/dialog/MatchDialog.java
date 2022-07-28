package com.supersweet.luck.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.supersweet.luck.R;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.base.BaseDialog;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.utils.SharePreferenceUtils;


/**
 * Created by Administrator on 2016/10/27.
 */
public class MatchDialog extends BaseDialog {
    private final Context mContext;
    private final OtherUserInfoBean Other_head;
    private OnSureClick onSureClick;

    public MatchDialog(Context context,OtherUserInfoBean Other_head) {
        super(context);
        this.mContext = context;
        this.Other_head = Other_head;

    }

    public void setOnSureClick(OnSureClick onSureClick) {
        this.onSureClick = onSureClick;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.chat_match_success;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        TextView tv_send_message = (TextView) view.findViewById(R.id.tv_send_message);
        ImageView match_man = (ImageView) view.findViewById(R.id.match_man);
        ImageView match_woman = (ImageView) view.findViewById(R.id.match_woman);
        LinearLayout rl_match_alpha = (LinearLayout) view.findViewById(R.id.rl_match_alpha);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        Window win = getWindow();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        win.setWindowAnimations(R.style.listDialogWindowAnim);
        win.setAttributes(lp);
        tv_send_message.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        String sex = SharePreferenceUtils.getString(mContext, Constance.SP_SEX, "1");
        String path = SharePreferenceUtils.getString(mContext, Constance.SP_HEADER, "");
        //显示圆形图片
        GlideLocalImageUtils.displaySmallImg(mContext, match_man,sex,path);
        GlideLocalImageUtils.displaySmallImg(mContext, match_woman, Other_head.getSex(),Other_head.getAvatar());
        //startFlick(rl_match_alpha);

    }


    /**
     * 开启View闪烁效果
     **/
    public void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
        switch (v.getId()) {
            case R.id.tv_send_message:
                if (onSureClick != null) {
                    onSureClick.click(this);
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnSureClick {
        void click(Dialog dialog);
    }

}

