package com.supersweet.luck.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.wheelview.widget.WheelView;
import com.supersweet.luck.R;
import com.supersweet.luck.wheelview.adapter.SimpleWheelAdapter;
import com.supersweet.luck.widget.AppData;

import java.util.List;

import butterknife.ButterKnife;


public class TimePickerDialog extends DialogFragment {
    private WheelView simpleWheelView;
    private TimePickerDialog.DoneCallback doneCallback;
    private List<WheelData> data;
    private int position;

    public static TimePickerDialog newInstance(DoneCallback doneCallback) {
        Bundle args = new Bundle();
        TimePickerDialog fragment = new TimePickerDialog();
        fragment.setArguments(args);
        fragment.doneCallback = doneCallback;
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       Dialog dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        dialog.setContentView(R.layout.v_picker);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        if (dialog==null){
            return;
        }
        simpleWheelView = dialog.findViewById(R.id.wheel_view);
        simpleWheelView.setWheelAdapter(new SimpleWheelAdapter(getActivity()));
        simpleWheelView.setWheelSize(5);
        simpleWheelView.setWheelData(data);
        simpleWheelView.setLoop(false);
        simpleWheelView.setSelection(0);
        simpleWheelView.setSkin(WheelView.Skin.Holo);
        simpleWheelView.setWheelClickable(false);
        WheelView.WheelViewStyle style1 = new WheelView.WheelViewStyle();
        style1.holoBorderColor = R.color.age_device;
        style1.selectedTextSize = 18;
        style1.textSize = 15;
        simpleWheelView.setStyle(style1);
        simpleWheelView.setOnWheelItemClickListener((position, o) ->
                this.position = position
        );
        simpleWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<WheelData>() {


            @Override
            public void onItemSelected(int position, WheelData wheelData) {
                AppData.WheelData = wheelData.getName().replace("cm", "");
                AppData.WheelPositon = position;
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        dialog.findViewById(R.id.tv_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doneCallback != null) {
                    doneCallback.onDone(TimePickerDialog.this, AppData.WheelData, AppData.WheelPositon);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public TimePickerDialog setdata(List<WheelData> Data) {
        this.data = Data;
        return this;
    }


    public interface DoneCallback {
        void onDone(TimePickerDialog dialog, String data, int position);
    }
}
