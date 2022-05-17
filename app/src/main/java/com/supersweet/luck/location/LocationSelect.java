package com.supersweet.luck.location;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.supersweet.luck.R;
import com.supersweet.luck.addressselector.AddressSelector;
import com.supersweet.luck.addressselector.CityInterface;
import com.supersweet.luck.addressselector.OnItemClickListener;
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.ItemAddressReq;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.ui.SPlashActivity;
import com.supersweet.luck.utils.SharePreferenceUtils;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.CommonPopWindow;

import java.util.ArrayList;
import java.util.List;

public  abstract class LocationSelect implements CommonPopWindow.ViewClickListener {



    /**
     * 设置弹出PopWindow
     * @param v
     */
    public void setAddressSelectorPopup(Context context, View v) {
        int screenHeigh = context.getResources().getDisplayMetrics().heightPixels;
          CommonPopWindow.newBuilder()
                .setView(R.layout.pop_address_selector_bottom)
                .setAnimationStyle(R.style.AnimUp)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.7f))
                .setViewOnClickListener(this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(context)
                .showAsBottom(v);
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
        ArrayList<AllCountryBean.Country> allCountry = (ArrayList<AllCountryBean.Country>) SharePreferenceUtils.readObject(MyApplication.getContext(), Constance.ALL_COUNTRY);
        ArrayList<ItemAddressReq> itemCountry = getItemAddressCountry(allCountry);
        addressSelector.setTabAmount(2);
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
                        onCountryListener(code[0], addressSelector);
                        break;
                    case 1:
                        //设置市列表数据
                        sheng[1] = city.getCityName();
                        code[1] = city.getCityCode();
                        onCityListener(sheng[0], sheng[1], code[1], addressSelector);
                        mPopupWindow.dismiss();
                        break;
                    case 2:
                        //设置区列表数据
                        sheng[2] = city.getCityName();
                        code[2] = city.getCityCode();
                        AppData.station = code[2];
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
                        addressSelector.setCities(getItemAddressCity(AppData.allCity));
                        break;
                    case 2:
                        addressSelector.setCities(getItemAddressStation(AppData.allStation));
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
    }

    protected abstract void onCountryListener(String s, AddressSelector addressSelector);

    //protected abstract void onStationListener(String s, AddressSelector addressSelector);

    public abstract  void onCityListener(String s2, String s1, String s, AddressSelector addressSelector) ;

    /*
     * 获取国家的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    private ArrayList<ItemAddressReq> getItemAddressCountry(List<AllCountryBean.Country> addressSelectorList) {
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
     * 获取城市的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    public static  ArrayList<ItemAddressReq> getItemAddressCity(List<CityBean.City> addressSelectorList) {
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
     * 获取station的数据
     *
     * @param addressSelectorList
     */

    @NonNull
    public static ArrayList<ItemAddressReq> getItemAddressStation(List<StationBean.City> addressSelectorList) {
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
