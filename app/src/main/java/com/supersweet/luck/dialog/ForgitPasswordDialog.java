package com.supersweet.luck.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BaseDialog;
import com.supersweet.luck.utils.DensityUtils;


/**
 * Created by Administrator on 2016/10/27.
 */
public class ForgitPasswordDialog extends BaseDialog {
    private final Context mContext;
    private TextView tv_confire,tv_single_message,tv_password;
    private OnSureClick onSureClick;
    private String message;
    private String mConfirm;
    private String password;

    public ForgitPasswordDialog(Context context, String message,  String password) {
        super(context);
        this.mContext=context;
         this.message= message;
         this.password= password;
    }



    public void setOnSureClick(String Confirm , OnSureClick onSureClick) {
       this.mConfirm= Confirm;
       this.onSureClick = onSureClick;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_forgit_password;
    }
    @Override
    protected void initViews(View view) {
        super.initViews(view);
        tv_confire = (TextView) view.findViewById(R.id.tv_confire);
        tv_single_message = (TextView) view.findViewById(R.id.tv_single_message);
        tv_password = (TextView) view.findViewById(R.id.tv_password);
        tv_confire.setOnClickListener(this);
        tv_single_message.setText(message == null ? "" : message);
        tv_confire.setText(mConfirm == null ? "" : mConfirm);
        tv_password.setText(password == null ? "" : password);
        tv_password.setTextIsSelectable(true);


    }

    public  void setGravity(int gravity){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (DensityUtils.getDisplayWidth(getContext()) * 0.8f);
        lp.gravity= gravity;
        getWindow().setAttributes(lp);

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        dismiss();
        switch (v.getId()){
            case R.id.tv_confire:
                if (onSureClick != null) {
                    onSureClick.click(this);
                }
                break;
            default:
                break;
        }
    }
    public interface OnSureClick {
        void click(Dialog dialog);
    }

}
