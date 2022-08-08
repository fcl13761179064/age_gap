package com.supersweet.luck.data.net;

import com.supersweet.luck.bean.AllCountryBean;
import com.supersweet.luck.bean.BaseResult;
import com.supersweet.luck.bean.BlockMemberbean;
import com.supersweet.luck.bean.CityBean;
import com.supersweet.luck.bean.CompanyBean;
import com.supersweet.luck.bean.FavoritesBean;
import com.supersweet.luck.bean.IntenetReposeBean;
import com.supersweet.luck.bean.LocationBean;
import com.supersweet.luck.bean.MultualMatchBean;
import com.supersweet.luck.bean.MyInfoBean;
import com.supersweet.luck.bean.OtherUserInfoBean;
import com.supersweet.luck.bean.PayCordBean;
import com.supersweet.luck.bean.PhotoBean;
import com.supersweet.luck.bean.SeachPeopleBean;
import com.supersweet.luck.bean.SelfAlbumBean;
import com.supersweet.luck.bean.ChatInfoBean;
import com.supersweet.luck.bean.StationBean;
import com.supersweet.luck.bean.UpHeadBean;
import com.supersweet.luck.bean.UpImgBean;
import com.supersweet.luck.bean.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @描述 Retrofit 需要的
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface ApiService {


    @POST("api/register")
    Observable<IntenetReposeBean> register(@Body RequestBody body);

    @POST("api/checkAccount")
    Observable<BaseResult<Boolean>> checkAccount(@Body RequestBody body);

    @POST("api/login")
    Observable<User> login(@Body RequestBody body);


    @POST("api/coin/purchaseCoinByGoogle")
    Observable<IntenetReposeBean> getCheckBuyOrder(@Query("payResult") String userId);

    @POST("api/month/purchaseGoogleMonth")
    Observable<IntenetReposeBean> getMonthPayOrder(@Query("payResult") String payresult);


    @POST("api/search")
    Observable<BaseResult<List<SeachPeopleBean>>> getSerchCard(@Body RequestBody body);

    @GET("api/userInfo")
    Observable<MyInfoBean> getMyInfo();

    @POST("api/coin/useResumeCoin")
    Observable<IntenetReposeBean> ResumePassCoin(@Query("targetUserId") String userId);

    @Multipart
    @POST("api/file/avatar/upload")
    Observable<UpHeadBean> uploadHeader(@Part MultipartBody.Part part);

    @Multipart
    @POST("api/user/uploadAlbum")
    Observable<BaseResult<UpImgBean>> uploadAlbum(@Part MultipartBody.Part part);//上传相册

    @POST("api/user/addConnection")
    Observable<IntenetReposeBean> like(@Query("connectionUserId") int code);//喜欢

    @POST("api/user/deleteConnection")
    Observable<IntenetReposeBean> unlike(@Query("connectionUserId") int code);//不喜欢

    @GET("api/dict/country")
    Observable<AllCountryBean> getAllCountry();

    @GET("api/dict/city")
    Observable<CityBean> getCity(@Query("country") String code);

    @GET("json/?key=Oq1ODFJjUdT8ott&lang=en")
    Observable<LocationBean> getCurrentLocation();

    @GET("api/dict/station")
    Observable<StationBean> getStation(@Query("city") String code);

    @POST("api/user/editProfile")
    Observable<MyInfoBean> editMyinfo(@Body RequestBody body);


    @GET("api/user/getSelfAlbum")
    Observable<BaseResult<List<SelfAlbumBean>>> getSelfAlbum();

    @GET("api/user/interestedMe")
    Observable<BaseResult<FavoritesBean>> getLoveMeList(@Query("currentPage") int page, @Query("pageSize") int size);

    @GET("api/user/userFavorites")
    Observable<BaseResult<FavoritesBean>> getFavoritesList(@Query("currentPage") int page, @Query("pageSize") int size);

    //对我感兴趣的包月
    @GET("api/month/useInMe")
    Observable<IntenetReposeBean> month_insertInMe(@Query("msgUserId") String userId);

    //互相喜欢的包月
    @GET("api/month/useMatch")
    Observable<IntenetReposeBean> monthMathMothPay(@Query("msgUserId") String userId);

    //聊天包月
    @GET("api/month/useMsg")
    Observable<IntenetReposeBean> getSendInfoMonthPay(@Query("msgUserId") String userId);

    //搜索包月
    @POST("api/month/useAdvanceSerach")
    Observable<IntenetReposeBean> getUseAdvanceSerach();

    @POST("api/user/getEachOtherUser")
    Observable<BaseResult<List<MultualMatchBean>>> getMultualMatch(@Body RequestBody body);

    @Multipart
    @POST("api/user/verify/upload")
    Observable<UpHeadBean> verfyUpdataPhoto(@Part MultipartBody.Part part);

    @GET("api/company")
    Observable<CompanyBean> getCompanyInfo();

    @POST("api/logout")
    Observable<IntenetReposeBean> logout();

    @POST("api/user/disableAccount")
    Observable<IntenetReposeBean> delectAccount(@Query("password") String password, @Query(" leaveReason") String leaveReson);

    @POST("api/user/feedback")
    Observable<Object> submitFeedbook(@Query("reason") String reason, @Query("detail") String detail, @Query("imgUrl") String imgUrl);

    @POST("api/user/blockUser")
    Observable<Object> blockUser(@Query("targetUserId") int targetUserId);

    @POST("api/user/report")
    Observable<Object> reportUser(@Query("targetUserId") int targetUserId, @Query("reason") String reason, @Query("detail") String detail, @Query("imgUrl") String imgUrl);

    @GET("api/user/getBlockUsers")
    Observable<BaseResult<BlockMemberbean>> getBlockUserNum(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);


    @POST("api/updatePWD")
    Observable<IntenetReposeBean> UpdataAccountPassword(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    @POST("api/user/editProfile")
    Observable<IntenetReposeBean> AlterPassword(@Body RequestBody body);

    @POST("api/user/unBlockUser")
    Observable<Object> unBlockUser(@Query("targetUserId") int targetUserId);

    @POST("api/user/deleteConnection")
    Observable<Object> deleteConnection(@Query("connectionUserId") String connectionUserId);

    @POST("api/findPWD")
    Observable<IntenetReposeBean> forgitpassword(@Body RequestBody body);

    @GET("api/user/browseUserAlbum")
    Observable<BaseResult<List<PhotoBean>>> scanPhotos(@Query("userId") String userId);

    @GET("api/user/{id}")
    Observable<BaseResult<ChatInfoBean>> getEveryOneHead(@Path("id") String userId);

    @GET("api/user/{id}")
    Observable<BaseResult<OtherUserInfoBean>> getOtherUserInfo(@Path("id") String userId);

    @POST("api/coin/useMsgCoin")
    //发送消息消耗币
    Observable<IntenetReposeBean> SendInfoConsumeCoin(@Query("msgUserId") String userId);

    @POST("api/coin/useInMeCoin")
    //查看别人interested in me
    Observable<IntenetReposeBean> LookOtherInterest(@Query("msgUserId") int userId);

    @POST("api/coin/useMatchCoin")
    //查看别人mutual  match
    Observable<IntenetReposeBean> LookMutualMatch(@Query("msgUserId") int userId);

    @POST("api/coin/useGetHighLightCoin")
    //购买高亮接口币
    Observable<IntenetReposeBean> BuyHighLightCoin();
/*
    @POST("api/coin/purchaseCoin")//购买币验证且充值接口，ios用的
    Observable<IntenetReposeBean> BuyRechargeCoin(@Body RequestBody body);*/

    @GET("api/coin/getUseCoinDetail")
    Observable<BaseResult<PayCordBean>> getPayRecord(@Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    @GET("api/coin/deleteCoinDetail")
    Observable<Object> deletePayReCord(@Query("id") int Id);


    @GET("api/user/updateLocation")
    Observable<IntenetReposeBean> updateLocation(@Query("longitude") double longitude, @Query("latitude") double latitude);

    @GET("api/coin/useVideoCoin")
    Observable<IntenetReposeBean> VedioConsume(@Query("msgUserId") String longitude, @Query("callId") String latitude);
}
