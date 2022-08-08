package com.supersweet.luck.mvp.model;


import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonArray;
import com.supersweet.luck.application.MyApplication;
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
import com.supersweet.luck.data.net.ApiService;
import com.supersweet.luck.data.net.RetrofitHelper;
import com.google.gson.JsonObject;
import com.supersweet.luck.widget.AppData;
import com.supersweet.luck.widget.DeviceIdUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @描述 网络请求Model
 * @作者 fanchunlei
 * @时间 2020/6/3
 */
public class RequestModel {

    public static final String APP_key = "vrmandroid";
    public static final String APP_SECRET = "92bAH6hNF4Q9RHymVGqYCdn58Zr3FPTU";

    private volatile static RequestModel instance = null;

    private RequestModel() {
    }

    /*  1.存在共享数据
      2.多线程共同操作共享数据。关键字synchronized可以保证在同一时刻，
      只有一个线程可以执行某个方法或某个代码块，同时synchronized可以保证一个线程的变化可见（可见性），即可以代替volatile。*/
    public static RequestModel getInstance() {
        if (instance == null) {
            synchronized (RequestModel.class) {
                if (instance == null) {
                    instance = new RequestModel();
                }
            }
        }
        return instance;
    }

    private ApiService getApiService() {
        return RetrofitHelper.getApiService();
    }

    private ApiService getLocationApiService() {
        return RetrofitHelper.getLocationApiService();
    }

    public Observable<User> login(String account, String password) {
        JsonObject body = new JsonObject();
        body.addProperty("account", account);
        body.addProperty("password", password);
        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().login(new_body);
    }

    public Observable<BaseResult<Boolean>> checkAccount(String user_name, String email) {
        JsonObject body = new JsonObject();
        body.addProperty("userName", user_name);
        body.addProperty("account", user_name);
        body.addProperty("email", email);
        body.addProperty("email", email);
        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().checkAccount(new_body);
    }

    public Observable<MyInfoBean> getMyinfo() {
        return getApiService().getMyInfo();

    }
    public Observable<IntenetReposeBean> ResumePassCoin(String userId) {
        return getApiService().ResumePassCoin(userId);
    }


    public Observable<IntenetReposeBean> register(Intent intent, String about) {
        String userName = intent.getStringExtra("userName");
        String password = intent.getStringExtra("password");
        String age = intent.getStringExtra("age");
        String height = intent.getStringExtra("height");
        String email = intent.getStringExtra("email");
        String sex = intent.getStringExtra("sex");
        String bodyType = intent.getStringExtra("body");
        String hair = intent.getStringExtra("hairColor");
        String userStatus = intent.getStringExtra("userStatus");
        String education = intent.getStringExtra("education");
        String drinking = intent.getStringExtra("drinking");
        String smoking = intent.getStringExtra("smoking");
        String children = intent.getStringExtra("children");
        String ethnicity = intent.getStringExtra("ethnicity");
        String avatar = intent.getStringExtra("avatar");
        JsonObject body = new JsonObject();
        body.addProperty("userName", userName);
        body.addProperty("account", userName);
        body.addProperty("password", password);
        body.addProperty("email", email);
        body.addProperty("deviceId", DeviceIdUtils.getDeviceId(MyApplication.getContext()));
        body.addProperty("sex", sex);
        body.addProperty("age", age);
        body.addProperty("height", height);
        body.addProperty("body", bodyType);
        body.addProperty("hair", hair);
        body.addProperty("userStatus", userStatus);
        body.addProperty("education", education);
        body.addProperty("drinking", drinking);
        body.addProperty("smoking", smoking);
        body.addProperty("children", children);
        body.addProperty("ethnicity", ethnicity);
        body.addProperty("about", about);
        body.addProperty("avatar", avatar);
        body.addProperty("vagueLevel", AppData.vagueLevel);
        body.addProperty("regSource", "2");
        body.addProperty("country", AppData.country);
        body.addProperty("station", AppData.station);
        body.addProperty("city", AppData.city);
        body.addProperty("regSource", 2);
        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().register(new_body);
    }

    public Observable<BaseResult<List<SeachPeopleBean>>> filterSearch(String city, int currentPage, String gender, String isVerify, String minAge, String maxAge, String start, String height, String bodyType, String hair, String relationship, String education, String ethnicity, String drinking, String smoking, String children, String disatance) {
        JsonObject body = new JsonObject();
        if (disatance.isEmpty()){
            body.addProperty("city", city);
        }else {
            body.addProperty("distance", disatance);
        }
        body.addProperty("currentPage", currentPage);
        body.addProperty("gender", gender);
        body.addProperty("maxAge", maxAge);
        body.addProperty("minAge", minAge);
        body.addProperty("pageSize", 10);
        JsonArray jsonElements1 = new JsonArray();
        JsonArray jsonElements2 = new JsonArray();
        JsonArray jsonElements3 = new JsonArray();
        JsonArray jsonElements4 = new JsonArray();
        JsonArray jsonElements5 = new JsonArray();
        JsonArray jsonElements6 = new JsonArray();
        JsonArray jsonElements7 = new JsonArray();
        JsonArray jsonElements8 = new JsonArray();
        JsonArray jsonElements9 = new JsonArray();
        jsonElements1.add(height);
        jsonElements2.add(bodyType);
        jsonElements3.add(hair);
        jsonElements4.add(relationship);
        jsonElements5.add(education);
        jsonElements6.add(ethnicity);
        jsonElements7.add(drinking);
        jsonElements8.add(smoking);
        jsonElements9.add(children);
        if (!height.isEmpty()) {
            body.add("height", jsonElements1);
        }

        if (!bodyType.isEmpty()) {
            body.add("body", jsonElements2);
        }
        if (!hair.isEmpty()) {
            body.add("hair", jsonElements3);
        }
        if (!relationship.isEmpty()) {
            body.add("userStatus", jsonElements4);
        }
        if (!education.isEmpty()) {
            body.add("education", jsonElements5);
        }
        if (!ethnicity.isEmpty()) {
            body.add("ethnicity", jsonElements6);
        }
        if (!drinking.isEmpty()) {
            body.add("drinking", jsonElements7);
        }
        if (!smoking.isEmpty()) {
            body.add("smoking", jsonElements8);

        }
        if (!children.isEmpty()) {
            body.add("children", jsonElements9);

        }

        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().getSerchCard(new_body);
    }


    public Observable<BaseResult<List<SeachPeopleBean>>> getVerified(String city, int currentPage, String gender, String isVerify, String minAge, String maxAge, String start) {
        JsonObject body = new JsonObject();
        body.addProperty("city", city);
        body.addProperty("currentPage", currentPage);
        body.addProperty("gender", gender);
        body.addProperty("isVerify", "1");
        body.addProperty("maxAge", maxAge);
        body.addProperty("minAge", minAge);
        body.addProperty("pageSize", 20);
        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().getSerchCard(new_body);
    }


    public Observable<BaseResult<List<SeachPeopleBean>>> SearchUser(String city, int currentPage, String gender, String minAge, String maxAge, String start) {
        JsonObject body = new JsonObject();
        body.addProperty("city", city);
        body.addProperty("currentPage", currentPage);
        body.addProperty("gender", gender);
        body.addProperty("maxAge", maxAge);
        body.addProperty("minAge", minAge);
        body.addProperty("pageSize", 10);
        RequestBody new_body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), body.toString());
        return getApiService().getSerchCard(new_body);
    }

    /**
     * 上传头像
     *
     * @return
     */
    public Observable<UpHeadBean> uploadHeader(String uri) {
        MultipartBody.Part body = null;
        try {
            File file = new File(uri);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            String substring = uri.substring(uri.length() - 4, uri.length());
            //方法 一
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());
            String extends_name = System.currentTimeMillis() + "_" + date + substring;
            body = MultipartBody.Part.createFormData("file", extends_name, requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getApiService().uploadHeader(body);
    }

    /**
     * 上传认证头像
     *
     * @return
     */
    public Observable<UpHeadBean> verfyUpdataPhoto(String uri) {
        MultipartBody.Part body = null;
        try {
            File file = new File(uri);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            String substring = uri.substring(uri.length() - 4, uri.length());
            //方法 一
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());
            String extends_name = System.currentTimeMillis() + "_" + date + substring;
            body = MultipartBody.Part.createFormData("file", extends_name, requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getApiService().verfyUpdataPhoto(body);
    }

    /**
     * 上传相册
     *
     * @return
     */
    public Observable<BaseResult<UpImgBean>> uploadAlbum(Context context, String uri) {
        File file = null;
        MultipartBody.Part body = null;
        try {
            file = initCompressorIOFile(context, uri);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            String substring = uri.substring(uri.length() - 4, uri.length());
            //方法 一
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String date = df.format(new Date());
            String extends_name = System.currentTimeMillis() + "_" + date + substring;
            body = MultipartBody.Part.createFormData("file", extends_name, requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getApiService().uploadAlbum(body);
    }

    /**
     * 使用Compressor IO模式压缩返回File
     */
    public File initCompressorIOFile(Context context, String path) {
        File file = null;
       /* try {
            file = new Compressor(context).compressToFile(new File(path));

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return file;
    }

    /**
     * 喜欢
     *
     * @return
     */
    public Observable<IntenetReposeBean> like(int userId) {
        JsonObject jsonObject = new JsonObject();
        return getApiService().like(userId);
    }

    /**
     * 不喜欢
     *
     * @return
     */
    public Observable<IntenetReposeBean> unlike(int userId) {
        return getApiService().unlike(userId);
    }

    //获取所有国家接口
    public Observable<AllCountryBean> getAllCountry() {
        return getApiService().getAllCountry();
    }


    //获取所有省份接口
    public Observable<CityBean> getCity(String code) {
        return getApiService().getCity(code);
    }

    //获取当前位置
    public Observable<LocationBean> getCurrentLocation() {
        return getLocationApiService().getCurrentLocation();
    }


    //获取城市接口
    public Observable<StationBean> getStation(String qu) {
        return getApiService().getStation(qu);
    }


    public Observable<MyInfoBean> editMyinfo(String abount) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("about", abount);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().editMyinfo(body111);
    }


    public Observable<MyInfoBean> editProfile(int position, String myinfo) {
        JsonObject jsonObject = new JsonObject();
        if (position == 0) {
            jsonObject.addProperty("height", myinfo);
        } else if (position == 1) {
            jsonObject.addProperty("body", myinfo);
        } else if (position == 2) {
            jsonObject.addProperty("hair", myinfo);
        } else if (position == 3) {
            jsonObject.addProperty("userStatus", myinfo);
        } else if (position == 4) {
            jsonObject.addProperty("education", myinfo);
        } else if (position == 5) {
            jsonObject.addProperty("ethnicity", myinfo);
        } else if (position == 6) {
            jsonObject.addProperty("drinking", myinfo);
        } else if (position == 7) {
            jsonObject.addProperty("smoking", myinfo);
        } else if (position == 8) {
            jsonObject.addProperty("children", myinfo);
        }

        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().editMyinfo(body111);
    }


    public Observable<MyInfoBean> editSetting(int position, String myinfo) {
        JsonObject jsonObject = new JsonObject();
        if (position == 0) {
            jsonObject.addProperty("userName", myinfo);
        } else if (position == 1) {
            jsonObject.addProperty("sex", myinfo);
        } else if (position == 2) {
            jsonObject.addProperty("age", myinfo);
        } else if (position == 3) {
            String[] split = myinfo.split(",");
            jsonObject.addProperty("country", split[0]);
            jsonObject.addProperty("city", split[1]);
            jsonObject.addProperty("station", split[2]);
        }

        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().editMyinfo(body111);
    }

    public Observable<MyInfoBean> updateHead(String head_img) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("avatar", head_img);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().editMyinfo(body111);
    }


    public Observable<BaseResult<List<SelfAlbumBean>>> getSelfAlbum() {
        return getApiService().getSelfAlbum();

    }

    public Observable<BaseResult<FavoritesBean>> getLoveMeList(int page, int count) {
        return getApiService().getLoveMeList(page, count);
    }

    public Observable<BaseResult<FavoritesBean>> getFavoritesList(int page, int count) {
        return getApiService().getFavoritesList(page, count);
    }


    public Observable<IntenetReposeBean> getMonthInsertInMe(String userId) {
        return getApiService().month_insertInMe(userId);
    }

    public Observable<IntenetReposeBean> getMonthPayMatch(String userId) {
        return getApiService().monthMathMothPay(userId);
    }

    public Observable<IntenetReposeBean> getSendInfoMonthPay(String userId) {
        return getApiService().getSendInfoMonthPay(userId);
    }

  public Observable<IntenetReposeBean> getUseAdvanceSerach() {
        return getApiService().getUseAdvanceSerach();
    }

    public Observable<BaseResult<List<MultualMatchBean>>> getMultualMatch(int pageNum, int maxNum) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("currentPage", pageNum);
        jsonObject.addProperty("pageSize", maxNum);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().getMultualMatch(body111);
    }


    public Observable<CompanyBean> getCompanyInfo() {
        return getApiService().getCompanyInfo();
    }


    public Observable<IntenetReposeBean> logout() {
        return getApiService().logout();
    }

    public Observable<IntenetReposeBean> delectAccount(String password, String leaveReason) {
        return getApiService().delectAccount(password, password);
    }

    public Observable<Object> submitFeedbook(String reason, String detail, String imgUrl) {
        return getApiService().submitFeedbook(reason, detail, imgUrl);
    }

    public Observable<Object> ReportUser(int targetUserId, String reason, String detail, String imgUrl) {
        return getApiService().reportUser(targetUserId, reason, detail, imgUrl);
    }

    public Observable<Object> blockUser(int targetUserId) {
        return getApiService().blockUser(targetUserId);
    }

    public Observable<BaseResult<BlockMemberbean>> getBlockUserNum(int currentPage, int maxNum) {
        return getApiService().getBlockUserNum(currentPage, maxNum);
    }

    public Observable<BaseResult<PayCordBean>> getPayRecord(int currentPage, int maxNum) {
        return getApiService().getPayRecord(currentPage, maxNum);
    }

    public Observable<MyInfoBean> HideProfile(int isShow) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isShow", isShow);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().editMyinfo(body111);
    }

    public Observable<IntenetReposeBean> UpdataAccountPassword(String oldPassword, String newPassword) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("oldPassword", oldPassword);
        jsonObject.addProperty("newPassword", newPassword);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().UpdataAccountPassword(oldPassword, newPassword);
    }


    public Observable<IntenetReposeBean> UpdataYouXiang(String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().AlterPassword(body111);
    }

    public Observable<Object> unBlockUser(int targetUserId) {
        return getApiService().unBlockUser(targetUserId);

    }

    public Observable<Object> deleteConnection(String connectionUserId) {
        return getApiService().deleteConnection(connectionUserId);

    }

    public Observable<IntenetReposeBean> forgitpassword(String username,String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", username);
        jsonObject.addProperty("email", email);
        RequestBody body111 = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=UTF-8"), jsonObject.toString());
        return getApiService().forgitpassword(body111);
    }

    public Observable<BaseResult<List<PhotoBean>>> scanPhotos(String userId) {
        return getApiService().scanPhotos(userId);
    }

    public Observable<BaseResult<OtherUserInfoBean>> getOtherUserInfo(String userID) {
        return getApiService().getOtherUserInfo(userID);
    }


    public Observable<IntenetReposeBean> SendInfoConsumeCoin(String userID) {
        return getApiService().SendInfoConsumeCoin(userID);
    }

    public Observable<IntenetReposeBean> lookOtherInterest(int userID) {
        return getApiService().LookOtherInterest(userID);
    }

    public Observable<IntenetReposeBean> lookMutualMatch(int userID) {
        return getApiService().LookMutualMatch(userID);
    }

    public Observable<IntenetReposeBean> BuyHighLightCoin() {
        return getApiService().BuyHighLightCoin();
    }


    public Observable<IntenetReposeBean> getCheckBuyOrder(String payResult) {
        return getApiService().getCheckBuyOrder(payResult);
    }


    public Observable<Object> deletePayReCord(int Id) {
        return getApiService().deletePayReCord(Id);

    }

    public Observable<IntenetReposeBean> updateLocation(double latitude, double altitude) {
        return getApiService().updateLocation(latitude, altitude);

    }

    public Observable<IntenetReposeBean> VedioConSume(String msgUserId, String callId) {
        return getApiService().VedioConsume(msgUserId, callId);

    }
}
