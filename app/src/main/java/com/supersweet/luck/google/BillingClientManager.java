package com.supersweet.luck.google;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.supersweet.luck.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class BillingClientManager {
    private static final String TAG = BillingClientManager.class.getName();

    private static BillingClient billingClient;
    private static Activity mActivity;

    //注意，每次请求前最好判断是否已经连接
    private static boolean mIsServiceConnected = false;
    private static List<String> sku;

    /**
     * @param activity
     * @param listener 这里自己写了一个回调，处理常见的问题
     *                 <p>
     *                 在调用购买方法后，Google Play 会调用 {@link PurchasesUpdatedListener#onPurchasesUpdated(BillingResult, List)}方法，
     * @param skuList
     */
    public static void init(Activity activity, @NonNull final OnPurchaseCallBack listener, List<String> skuList) {
        sku =skuList;
        billingClient = BillingClient.newBuilder(activity).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
                Log.d(TAG, "onPurchasesUpdated: ");
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                    /**
                     * 购买成功
                     */
                    listener.onPaySuccess(purchases);
                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // Handle an error caused by a user cancelling the purchase flow.
                    //处理由用户取消购买流程引起的错误
                    listener.onUserCancel();
                } else {
                    // Handle any other error codes.
                    listener.responseCode(billingResult.getResponseCode());
                }
            }
        }).enablePendingPurchases().build();
        mActivity = activity;
        connectionService();
    }

    /**
     * 连接服务
     * @param
     */
    public static void connectionService() {
        if (billingClient == null)
            throw new IllegalArgumentException("Please call init(); first!");

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    mIsServiceConnected = true;
                    querySkuDetailsAsync(BillingClient.SkuType.INAPP, new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                                    LogUtil.e("info", "what == 0");
                                }
                            },
                            sku);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                mIsServiceConnected = false;
            }
        });
    }


    /**
     * 要异步查询商品详情，应用会使用您在 Google Play 管理中心内配置商品时定义的商品 ID。
     *
     * @param itemType 针对一次性商品或奖励产品：SkuType.INAPP  ； 订阅：SkuType.SUBS
     * @param skuList  指定产品 ID 字符串列表
     * @param listener
     */
    public static void querySkuDetailsAsync(@BillingClient.SkuType String itemType,
                                            SkuDetailsResponseListener listener, List<String> skuList) {
        if (billingClient == null)
            throw new IllegalArgumentException("querySkuDetailsAsync(); error . Please call init(); first!");

        //判断是否连接
        if (!mIsServiceConnected) {
            connectionService();
        } else {
            SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
                    .setType(itemType)
                    .setSkusList(skuList)//数组
                    .build();
            billingClient.querySkuDetailsAsync(skuDetailsParams, listener);
        }
    }


    /**
     * 启动应用内商品的购买
     *
     * @param skuDetails 这里商品详情，从 querySkuDetailsAsync(); 查询
     * @return 方法会返回 BillingClient.BillingResponse 中列出的几个响应代码之一
     */
    public static BillingResult launchBillingFlow(@NonNull SkuDetails skuDetails) {
        if (mActivity == null || billingClient == null)
            throw new IllegalArgumentException("launchBillingFlow(); error . Please call init(); first!");

        //判断是否连接
        if (!mIsServiceConnected) connectionService(   );

        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();

        return billingClient.launchBillingFlow(mActivity, flowParams);
    }

    /**
     * Android 手机安装的 Google Play 商店应用可能是旧版的，不支持订阅等商品类型。
     * 因此，在应用进入结算流程之前，请调用 isFeatureSupported()
     * 以检查设备是否支持您要销售的商品。如需查看商品类型的列表，请参阅 BillingClient.FeatureType。
     *
     * @param feature
     * @return
     */
    public static boolean isFeatureSupported(@BillingClient.FeatureType String feature) {
        if (billingClient == null)
            throw new IllegalArgumentException("isFeatureSupported(); error . Please call init(); first!");

        //判断是否连接
        if (!mIsServiceConnected) connectionService();

        BillingResult result = billingClient.isFeatureSupported(feature);

        if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
            return true;
        } else {
            Log.e(TAG, "isFeatureSupported: isFeatureSupported = false , errorMsg : " + result.getDebugMessage());
            return false;
        }
    }

    /**
     * 确认、消费一次性商品交易
     * <p>
     * 警告！ 所有购买都需要确认。 未能确认购买将导致购买退款。 对于一次性产品，请确保使用此方法作为隐式确认，
     * 或者您可以通过{@link BillingClient#acknowledgePurchase(AcknowledgePurchaseParams, AcknowledgePurchaseResponseListener)} 明确确认购买。
     * 对于订阅，请使用{@link #acknowledgePurchase）。
     * 有关详细信息，请参阅https://developer.android.com/google/play/billing/billing_library_overview#acknowledge。
     */
    public static void consumeAsync(@NonNull Purchase purchase, ConsumeResponseListener listener) {
        if (billingClient == null)
            throw new IllegalArgumentException("consumeAsync(); error . Please call init(); first!");

        //判断是否连接
        if (!mIsServiceConnected) connectionService();

        //Purchase 对象包含 isAcknowledged() 方法，该方法会指示购买交易是否已得到确认
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED
                && !purchase.isAcknowledged()) {

            ConsumeParams consumeParams = ConsumeParams
                    .newBuilder()
//                    .setDeveloperPayload(purchase.getDeveloperPayload())//指定开发人员有效负载与购买信息一起发回。
                    .setPurchaseToken(purchase.getPurchaseToken())//指定标识要使用的购买的标记。
                    .build();
            billingClient.consumeAsync(consumeParams, listener);
        }

    }

    /**
     * 确认“订阅商品”交易
     * <p>
     * <p>
     * 消费“费消耗品” 还可以使用服务器 API 中新增的 acknowledge() 方法。
     */
    public static void acknowledgePurchase(@NonNull Purchase purchase, AcknowledgePurchaseResponseListener listener) {
        if (billingClient == null)
            throw new IllegalArgumentException("acknowledgePurchase(); error . Please call init(); first!");

        //判断是否连接
        if (!mIsServiceConnected) connectionService();

        //Purchase 对象包含 isAcknowledged() 方法，该方法会指示购买交易是否已得到确认
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED
                && !purchase.isAcknowledged()) {
            AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                    .newBuilder()
//                    .setDeveloperPayload(purchase.getDeveloperPayload())//这个参数不确定是否需要
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build();

            billingClient.acknowledgePurchase(acknowledgePurchaseParams, listener);
        }
    }

    /**
     * 用户通过应用发起的购买交易的相关信息（使用 Google Play 商店应用的缓存）
     *
     * @param type 购买类型（SkuType.INAPP 或 SkuType.SUBS）
     * @return 注意:
     * 在每次启动您的应用时都调用 queryPurchases()，
     * 以便您可以恢复用户自应用上次停止以来发起的任何购买交易。
     * 在 onResume() 方法中调用 queryPurchases()，
     * 因为当您的应用在后台时，用户可能会发起购买交易（例如，在 Google Play 商店应用中兑换促销代码）。
     * <p>
     * queryPurchases() 方法使用 Google Play 商店应用的缓存，而不发起网络请求。
     * <p>
     * 如果您需要查看用户对每个商品 ID 发起的最近一笔购买交易，您可以使用 queryPurchaseHistoryAsync()
     */
    public static List<Purchase> queryPurchases(@BillingClient.SkuType String type) {
        if (billingClient == null)
            throw new IllegalArgumentException("acknowledgePurchase(); error . Please call init(); first!");

        Purchase.PurchasesResult result = billingClient.queryPurchases(type);

        if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {

//            BillingResult billingResult = result.getBillingResult();

            return result.getPurchasesList();
        }

        return null;
    }

    /**
     * 查询最近的购买交易（网络）
     *
     * @param type
     * @param listener 注意: 该方法走网络
     *                 如果使用 queryPurchaseHistoryAsync()，您也可以将其与刷新按钮结合使用，使用户能更新其购买交易列表。
     */
    public static void queryPurchaseHistoryAsync(@BillingClient.SkuType String type, PurchaseHistoryResponseListener listener) {

        if (billingClient == null)
            throw new IllegalArgumentException("acknowledgePurchase(); error . Please call init(); first!");
        final List<PurchaseHistoryRecord> purchaseHistoryRecords = new ArrayList<>();

        billingClient.queryPurchaseHistoryAsync(type, listener);
    }

public interface OnPurchaseCallBack {
    void onPaySuccess(List<Purchase> list);

    void responseCode(int responseCode);

    void onUserCancel();
}
}

