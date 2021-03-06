package com.supersweet.luck.google;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import com.supersweet.luck.application.Constance;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.data.net.ApiService;
import com.supersweet.luck.data.net.RetrofitHelper;
import com.supersweet.luck.google.constant.Constant;
import com.supersweet.luck.google.listener.BaseBillingUpdateListener;
import com.supersweet.luck.google.receiver.BillingPurchasesReceiver;
import com.supersweet.luck.google.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ??????Google Play????????????2.0?????????????????????3?????????????????????????????????
 * ????????????????????????????????????????????????????????????????????????????????????
 * ?????????????????????3????????????
 * 1. ?????????????????????????????????API consumeAsync()
 * 2. ??????????????????????????????????????????API acknowledgePurchase()
 * 3. ????????????????????????API?????????acknowledge()????????????????????????
 *
 * @author gaopengfei on 2019/05/06.
 */
public class BillingManager implements PurchasesUpdatedListener {

    private BillingClient mBillingClient;
    private boolean mIsServiceConnected;
    private  WeakReference<Activity> weakReference;
    private BaseBillingUpdateListener billingUpdatesListener;
    private BillingPurchasesReceiver billingPurchasesReceiver;

    public BillingManager(Activity activity, BaseBillingUpdateListener billingUpdatesListener) {
        this.weakReference = new WeakReference<>(activity);
        this.billingUpdatesListener = billingUpdatesListener;
    }

    public BillingManager(Context activity, BaseBillingUpdateListener billingUpdatesListener) {
        this.weakReference = new WeakReference(activity);
        this.billingUpdatesListener = billingUpdatesListener;
    }

    public BillingManager(Activity activity, BillingPurchasesReceiver billingPurchasesReceiver) {
        this.weakReference = new WeakReference<>(activity);
        this.billingPurchasesReceiver = billingPurchasesReceiver;
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_BILLING);
        LocalBroadcastManager.getInstance(activity).registerReceiver(billingPurchasesReceiver, intentFilter);
    }

    public BillingManager() {
    }
    /**
     * ??????????????????(??????)
     */
    public void startServiceConnection(final Runnable runnable) {
        mBillingClient = BillingClient
                .newBuilder(weakReference.get().getApplicationContext())
                .enablePendingPurchases()
                .setListener(this)
                .build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    if (billingUpdatesListener != null) {
                        billingUpdatesListener.onBillingClientSetupFinished();
                    }
                    mIsServiceConnected = true;
                    LogUtils.e("Google billing service connect success!");
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                mIsServiceConnected = false;
                LogUtils.e("Google billing service connect fail!");
            }
        });
    }

    /**
     * ????????????????????????
     *
     * @param skuId       ????????????ID
     * @param skuType ???????????? ??????{@link BillingClient.SkuType}
     */
    public void querySkuDetailAsyn(final String skuId, final String skuType) {
        LogUtils.e("querySkuDetailAsyn >>> [" + skuId + ",type:" + skuType + "]");
        executeServiceRequest(new Runnable() {
            @Override
            public void run() {
                List<String> skuList = new ArrayList<>();
                skuList.add(skuId);
                final SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(skuType);
                mBillingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                // Process the result.
                                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                    if (billingUpdatesListener != null) {
                                        billingUpdatesListener.onQuerySkuDetailSuccess(skuDetailsList);
                                    }
                                    if (!skuDetailsList.isEmpty()) {
                                        for (SkuDetails skuDetails : skuDetailsList) {
                                            LogUtils.e("querySkuDetailAsyn success >>> [skuDetails:" + skuDetails.toString() + "]");
                                        }
                                    }
                                } else {
                                    if (billingUpdatesListener != null) {
                                        billingUpdatesListener.onQuerySkuDetailFailure(billingResult.getResponseCode(), billingResult.getDebugMessage());
                                    }
                                }
                            }
                        });
            }
        });
    }

    /**
     * ????????????????????????????????????
     * @param skuType ???????????? {@link BillingClient.SkuType}
     */
    public void queryPurchaseHistoryAsync(final @BillingClient.SkuType String skuType){
        LogUtils.e("queryPurchaseHistoryAsync >>> [" + skuType + "]");
        executeServiceRequest(new Runnable() {
            @Override
            public void run() {
                mBillingClient.queryPurchaseHistoryAsync(skuType,new PurchaseHistoryResponseListener() {
                            @Override
                            public void onPurchaseHistoryResponse(@NonNull BillingResult billingResult, List<PurchaseHistoryRecord> list) {
                                if (billingUpdatesListener != null){
                                    billingUpdatesListener.onPurchaseHistoryResponse(billingResult, list);
                                }
                            }
                        });
            }
        });
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param skuType ???????????? {@link BillingClient.SkuType}
     */
    public void confirmHistoryPurchase(final String skuType) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /* ???????????????????????? */
                Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(skuType);
                if (purchasesResult != null && purchasesResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    List<Purchase> purchasesList = purchasesResult.getPurchasesList();
                    if (purchasesList != null && !purchasesList.isEmpty()) {
                        for (Purchase purchase : purchasesList) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                                if (!purchase.isAcknowledged()) {
                                    if (BillingClient.SkuType.SUBS.equals(skuType)) {
                                        acknowledgePurchase(purchase.getPurchaseToken());
                                        LogUtils.e("acknowledgePurchase success >>> [orderId???" + purchase.getOrderId() + "]");
                                    } else if (BillingClient.SkuType.INAPP.equals(skuType)) {
                                        consumeAsync(purchase);
                                        LogUtils.e("consumeAsync success >>> [orderId???" + purchase.getOrderId() + "]");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
       executeServiceRequest(runnable);
    }

    /**
     * <p>
     * ??????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????{@link BaseBillingUpdateListener#onPurchasesFailure(int, String)}
     * </p>
     *
     * @param skuId       ??????ID
     * @param skuType ????????????
     */
    public void launchBillingFlow(final String skuId, final String skuType) {
        LogUtils.e("launchBillingFlow > querySkuDetailsAsync >>> [" + skuId + ",type:" + skuType + "]");
        executeServiceRequest(new Runnable() {
            @Override
            public void run() {
                List<String> skuList = new ArrayList<>();
                skuList.add(skuId);
                final SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(skuType);
                mBillingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                // Process the result.
                                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                    if (!skuDetailsList.isEmpty()) {
                                        for (SkuDetails skuDetails : skuDetailsList) {
                                            // ????????????
                                            launchBillingFlow(skuDetails);
                                            LogUtils.e("querySkuDetailsAsync success >>> [skuDetails:" + skuDetails.toString() + "]");
                                        }
                                    }
                                } else {
                                    if (billingUpdatesListener != null) {
                                        billingUpdatesListener.onPurchasesFailure(billingResult.getResponseCode(), billingResult.getDebugMessage());
                                    }
                                }
                            }
                        });
            }
        });
    }


    /**
     * ????????????
     *
     * @param skuDetails ????????????
     */
    private void launchBillingFlow(final SkuDetails skuDetails) {
        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build();
                int responseCode = mBillingClient.launchBillingFlow(weakReference.get(), flowParams).getResponseCode();
                LogUtils.e("launchBillingFlow >>> [responseCode:" + responseCode + "]");
            }
        };
        executeServiceRequest(runnable);
    }

    /**
     * ??????????????????????????????????????????
     * @param purchaseTokenJson
     */
    public  void consumeAsync(Purchase purchaseTokenJson) {
        executeServiceRequest(new Runnable() {
            @Override
            public void run() {
                ConsumeParams consumeParams =
                        ConsumeParams.newBuilder()
                                .setPurchaseToken(purchaseTokenJson.getPurchaseToken())
                                .build();
                mBillingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
                    @Override
                    public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String purchaseToken) {
                        if (billingUpdatesListener != null) {
                            billingUpdatesListener.onConsumeFinished(purchaseToken, billingResult,purchaseTokenJson.getOriginalJson());
                        }
                    }
                });
            }
        });
    }

    /**
     * ?????????????????????????????????????????????
     */
    public void acknowledgePurchase(final String purchaseToken) {
        executeServiceRequest(new Runnable() {
            @Override
            public void run() {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchaseToken)
                                .build();
                mBillingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
                    @Override
                    public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                        if (billingUpdatesListener != null) {
                            billingUpdatesListener.onAcknowledgePurchaseResponse(billingResult);
                        }
                    }
                });
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void executeServiceRequest(Runnable runnable) {
        if (mBillingClient != null) {
            runnable.run();
        } else {
            startServiceConnection(runnable);
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            if (billingUpdatesListener != null) {
                billingUpdatesListener.onPurchasesUpdated(purchases);
            }
            LogUtils.e("Payment success >>> [code???"
                    + billingResult.getResponseCode() + ",message???" + billingResult.getDebugMessage() + "]");
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            if (billingUpdatesListener != null) {
                billingUpdatesListener.onPurchasesCancel();
            }
            LogUtils.e("Payment cancel >>> [code???"
                    + billingResult.getResponseCode() + ",message???" + billingResult.getDebugMessage() + "]");
        } else {
            // Handle any other error codes.
            if (billingUpdatesListener != null) {
                billingUpdatesListener.onPurchasesFailure(billingResult.getResponseCode(), billingResult.getDebugMessage());
            }
            LogUtils.e("Payment failure >>> [code???"
                    + billingResult.getResponseCode() + ",message???" + billingResult.getDebugMessage() + "]");
        }
    }

    /**
     * ????????????
     */
    public void destroy() {
        LogUtils.d("Destroying the manager.");
        if (mBillingClient != null && mBillingClient.isReady()) {
            mBillingClient.endConnection();
            mBillingClient = null;
        }
        if (billingPurchasesReceiver != null) {
            LocalBroadcastManager.getInstance(weakReference.get()).unregisterReceiver(billingPurchasesReceiver);
        }
    }

    public void buyCoin(String purchase){
        Retrofit retrofit=new Retrofit.Builder()
                .client(RetrofitHelper.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constance.sProdUrl)
                .build();

     retrofit.create(ApiService.class).getCheckBuyOrder(purchase).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void MonthPay(String purchase){
        Retrofit retrofit=new Retrofit.Builder()
                .client(RetrofitHelper.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constance.sProdUrl)
                .build();

        retrofit.create(ApiService.class).getMonthPayOrder(purchase).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                })
                .subscribe(new Observer<IntenetReposeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IntenetReposeBean s) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}