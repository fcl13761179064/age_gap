
package com.supersweet.luck.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.addressselector.CityInterface;
import com.supersweet.luck.addressselector.OnItemClickListener;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.base.BaseMvpActivity;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.ItemAddressReq;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.dialog.HighingConsumeCoinDialog;
import com.supersweet.luck.dialog.TimePickerDialog;
import com.supersweet.luck.mvp.present.EditProfilePresenter;
import com.supersweet.luck.mvp.view.EditProfileView;
import com.supersweet.luck.pictureselector.PictureBean;
import com.supersweet.luck.pictureselector.PictureSelector;
import com.supersweet.luck.glide.GlideLocalImageUtils;
import com.supersweet.luck.rxbus.RxBus;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CommonPopWindow;
import com.supersweet.luck.widget.CustomToast;
import com.supersweet.luck.widget.MyDatas;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.supersweet.luck.pictureselector.PictureSelector.SELECT_GUASSIAN;

public class EditProfileActivity extends BaseMvpActivity<EditProfileView, EditProfilePresenter> implements EditProfileView, CommonPopWindow.ViewClickListener {
    @BindView(R.id.iv_head_img)
    ImageView iv_head_img;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.rl_avatar)
    RelativeLayout rl_avatar;
    @BindView(R.id.rl_user_name)
    RelativeLayout rl_user_name;
    @BindView(R.id.rl_gender)
    RelativeLayout rl_gender;
    @BindView(R.id.rl_age)
    RelativeLayout rl_age;
    @BindView(R.id.appBar)
    AppBar appBar;
    @BindView(R.id.rl_current_location)
    RelativeLayout rl_current_location;

    private List<CityBean.City> mCityData;
    private List<StationBean.City> mStation;
    public static final String TAG = "PictureSelector";
    private int EDITMODIFYUSERNAME = 1001;
    private MyInfoBean myinfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_profile;
    }


    @Override
    protected void initView() {
        appBar.setCenterText("Edit profile");
        String station = getIntent().getStringExtra("location_station");
        myinfoBean = (MyInfoBean) getIntent().getSerializableExtra("MyinfoBean");
        if (myinfoBean == null) {
            return;
        }
        String sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        tv_user_name.setText(myinfoBean.getUser().getUserName());
        tv_gender.setText(MyDatas.sextosting(sex));
        tv_age.setText(myinfoBean.getUser().getAge() + "");
        tv_location.setText(station);
        String path = SharePreferenceUtils.getString(EditProfileActivity.this, Constance.SP_HEADER, "");
        GlideLocalImageUtils.displaySmallImg(this, iv_head_img, sex, path);
    }


    @Override
    protected void initListener() {
        rl_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(EditProfileActivity.this, EditModifyUsernameActivity.class);
                startActivityForResult(intent, EDITMODIFYUSERNAME);*/
            }
        });
        rl_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(1);
            }
        });
        rl_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog(2);
            }
        });
        rl_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddressSelectorPopup(view);
            }
        });
        rl_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(EditProfileActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                Log.i(TAG, "是否裁剪: " + pictureBean.isCut());
                Log.i(TAG, "原图地址: " + pictureBean.getPath());
                Log.i(TAG, "图片 Uri: " + pictureBean.getUri());
                if (pictureBean.isCut()) {
                    AppData.avatar = pictureBean.getPath();
                    Intent intent = new Intent(this, GuassianFilterActivity.class);
                    intent.putExtra("guassianImg", pictureBean.getPath());
                    startActivityForResult(intent, SELECT_GUASSIAN);
                } else {
                    AppData.avatar = pictureBean.getPath();
                    Intent intent = new Intent(this, GuassianFilterActivity.class);
                    intent.putExtra("guassianImg", pictureBean.getPath());
                    startActivityForResult(intent, SELECT_GUASSIAN);

                }

            }
        } else if (requestCode == PictureSelector.SELECT_GUASSIAN) {
            Bitmap BproductImg = AppData.BitMap;
            if (BproductImg == null) {
                return;
            }
            String guassianImg = data.getStringExtra("guassian_url");
            mPresenter.uploadHeader(EditProfileActivity.this, guassianImg);
        } else if (requestCode == EDITMODIFYUSERNAME) {
            if (data != null) {
                String userName = (String) data.getExtras().get("userName");
                tv_user_name.setText(userName);
            }
        }
    }


    /**
     * 设置弹出PopWindow
     *
     * @param v
     */
    private void setAddressSelectorPopup(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;

        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_address_selector_bottom)
                .setAnimationStyle(R.style.AnimUp)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.6f))
                .setViewOnClickListener(this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }


    @Override
    protected EditProfilePresenter initPresenter() {
        return new EditProfilePresenter();
    }

    @Override
    public void errorShake(int type, int CycleTimes, String msg) {

    }


    @Override
    public void editMyInfoProgress(String s, int position) {
        if (position == 1) {
            RxBus.getDefault().post("edit_sex", "edit_sex");
        } else if (position == 2) {
            RxBus.getDefault().post(s, "edit_age");
        } else if (position == 3) {
            RxBus.getDefault().post(s, "edit_location");
        }
    }

    @Override
    public void UPHeadSuccess(UpHeadBean s) {
        SharePreferenceUtils.saveString(MyApplication.getContext(), Constance.SP_HEADER, s.getMsg());
        String sex = SharePreferenceUtils.getString(this, Constance.SP_SEX, "1");
        GlideLocalImageUtils.displaySmallImg(this, iv_head_img, sex, s.getMsg());
        mPresenter.editHead(s.getMsg());
    }

    public void dialog(int position) {
        List<WheelData> datas = selectData(position);
        TimePickerDialog.newInstance(new TimePickerDialog.DoneCallback() {
            @Override
            public void onDone(TimePickerDialog dialog, String data, int mPosition) {
                String value = "";
                if (position == 1) {
                    value = MyDatas.stringsextoInt(data);
                    SharePreferenceUtils.saveString(EditProfileActivity.this, Constance.SP_SEX, value);
                    tv_gender.setText(MyDatas.sextosting(value));
                } else if (position == 2) {
                    value = data;
                    tv_age.setText(value);
                }
                mPresenter.editSetting(position, value);
                dialog.dismissAllowingStateLoss();
            }

        }).setdata(datas)
                .show(getSupportFragmentManager(), "time");
    }

    public List<WheelData> selectData(int position) {
        ArrayList<WheelData> datas = null;
        if (position == 1) {
            datas = MyDatas.SexTypes();
        } else if (position == 2) {
            datas = MyDatas.AgeType();
        } else if (position == 3) {
            datas = MyDatas.RelationShipTypes();
        }
        return datas;

    }

    @Override
    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
        switch (mLayoutResId) {
            case R.layout.pop_address_selector_bottom:
                ImageView imageBtn = view.findViewById(R.id.img_guanbi);
                AddressSelector addressSelector = view.findViewById(R.id.address);

                //设置默认选择数据
                dealWithAddressSelector(addressSelector, mPopupWindow);

                imageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow.dismiss();
                    }
                });

                break;
        }
    }

    private void dealWithAddressSelector(AddressSelector addressSelector, final PopupWindow mPopupWindow) {
        final String[] sheng = new String[3];
        final String[] code = new String[3];
        ArrayList<AllCountryBean.Country> allCountry = (ArrayList<AllCountryBean.Country>) SharePreferenceUtils.readObject(EditProfileActivity.this, Constance.ALL_COUNTRY);
        if (allCountry == null) {
            ToastUtils.showLongToast("Data Exception...");
            return;
        }
        ArrayList<ItemAddressReq> itemCountry = getItemAddressCountry(allCountry);
        addressSelector.setTabAmount(3);
        //设置数据
        addressSelector.setCities(itemCountry);
        //设置Tab横线的颜色
        addressSelector.setLineColor(Color.parseColor("#7857EF"));
        //设置Tab文字默认颜色
        addressSelector.setTextEmptyColor(Color.parseColor("#7857EF"));
        //设置列表选中文字颜色
        addressSelector.setListTextSelectedColor(Color.parseColor("#7857EF"));
        //设置Tab文字选中的颜色
        addressSelector.setTextSelectedColor(Color.parseColor("#7857EF"));

        //设置列表的点击事件回调接口
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition, int selecePos) {
                switch (tabPosition) {
                    case 0:
                        //设置省列表数据
                        sheng[0] = city.getCityName();
                        code[0] = city.getCityCode();
                        mPresenter.btncity(code[0], addressSelector);
                        AppData.country = code[0];
                        break;
                    case 1:
                        //设置市列表数据
                        sheng[1] = city.getCityName();
                        code[1] = city.getCityCode();
                        mPresenter.btnStation(code[1], addressSelector);
                        AppData.city = code[1];
                        break;
                    case 2:
                        //设置区列表数据
                        sheng[2] = city.getCityName();
                        code[2] = city.getCityCode();
                        AppData.station = code[2];
                        tv_location.setText(sheng[2]);
                        mPresenter.editSetting(3, code[0] + "," + code[1] + "," + code[2] + "," + sheng[2]);
                        mPopupWindow.dismiss();
                        break;
                }
            }
        });


        //设置顶部tab的点击事件回调
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()) {
                    case 0:
                        addressSelector.setCities(itemCountry);
                        break;
                    case 1:
                        addressSelector.setCities(getItemAddressCity(mCityData));
                        break;
                    case 2:
                        addressSelector.setCities(getItemAddressStation(mStation));
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
    }

    @Override
    public void sheng(CityBean data, AddressSelector addressSelector) {
        this.mCityData = data.getCity();
        addressSelector.setCities(getItemAddressCity(mCityData));
    }


    @Override
    public void updateFuwuqisuccess(MyInfoBean s) {
        RxBus.getDefault().post("", "alter_head");
    }



    @Override
    public void qu(StationBean data, AddressSelector addressSelector) {
        this.mStation = data.getCity();
        addressSelector.setCities(getItemAddressStation(mStation));
    }

    /*
     * 获取省的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressCountry(ArrayList<AllCountryBean.Country> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }


    /*
     * 获取市的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private List<ItemAddressReq> getItemAddressCity(List<CityBean.City> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }


    /*
     * 获取市的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressStation(List<StationBean.City> addressSelectorList) {
        final ArrayList<ItemAddressReq> itemAddressReqs = new ArrayList<>();
        for (int i = 0; i < addressSelectorList.size(); i++) {
            ItemAddressReq itemAddressReq = new ItemAddressReq();
            itemAddressReq.setConfigCode(addressSelectorList.get(i).getConfigCode());
            itemAddressReq.setConfigLevel(addressSelectorList.get(i).getConfigLevel());
            itemAddressReq.setConfigName(addressSelectorList.get(i).getConfigName());
            itemAddressReq.setConfigType(addressSelectorList.get(i).getConfigType());
            itemAddressReq.setConfigValue(addressSelectorList.get(i).getConfigValue());
            itemAddressReq.setId(addressSelectorList.get(i).getId());
            itemAddressReq.setParentId(addressSelectorList.get(i).getParentId());
            itemAddressReq.setYn(addressSelectorList.get(i).getYn());
            itemAddressReq.setOrderBy(addressSelectorList.get(i).getOrderBy());
            itemAddressReqs.add(itemAddressReq);
        }
        return itemAddressReqs;
    }

}
