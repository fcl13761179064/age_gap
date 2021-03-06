package com.supersweet.luck.google.listener;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.SkuDetails;

import java.util.List;

/**
 * @author gaopengfei on 2019/08/14.
 */
public abstract class BaseBillingUpdateListener {

    /** BillingClient 初始化完成 */
    public abstract void onBillingClientSetupFinished();

    /** 商品信息查询成功 */
    public abstract void onQuerySkuDetailSuccess(List<SkuDetails> skuDetailsList);

    /** 商品信息查询失败 */
    public abstract void onQuerySkuDetailFailure(int errorCode, String message);

    /** 查询购买历史 */
    public abstract void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list);

    /** 消耗商品完成 */
    public abstract void onConsumeFinished(String token, BillingResult result, String purchaseTokenJson);

    /** 消耗-非消耗型商品 */
    public abstract void onAcknowledgePurchaseResponse(BillingResult result);

    /** 商品购买更新 */
    public abstract void onPurchasesUpdated(List<Purchase> purchases);

    /** 内购取消 */
    public abstract void onPurchasesCancel();

    /** 内购失败 */
    public abstract void onPurchasesFailure(int errorCode, String message);
}
