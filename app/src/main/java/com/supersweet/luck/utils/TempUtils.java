package com.supersweet.luck.utils;

import com.supersweet.luck.bean.DeviceListBean;

public class TempUtils {
    /**
     * 判断设备是否为网关
     *
     * @param devicesBean
     * @return
     */
    public static boolean isDeviceGateway(DeviceListBean.DevicesBean devicesBean) {
        if (devicesBean == null) {
            return false;
        }
        return devicesBean.getConnectTypeId() == 1;
    }

    /**
     * 判断是被是否在线
     *
     * @param devicesBean
     * @return
     */
    public static boolean isDeviceOnline(DeviceListBean.DevicesBean devicesBean) {
        if (devicesBean == null) {
            return false;
        }
        return "ONLINE".equals(devicesBean.getDeviceStatus());
    }
}
