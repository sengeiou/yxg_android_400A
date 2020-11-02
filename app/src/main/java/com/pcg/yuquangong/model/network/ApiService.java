package com.pcg.yuquangong.model.network;

import com.pcg.yuquangong.model.network.body.AddCureRecordBody;
import com.pcg.yuquangong.model.network.body.AddMemberBody;
import com.pcg.yuquangong.model.network.body.AddSurveryBody;
import com.pcg.yuquangong.model.network.body.LoginBody;
import com.pcg.yuquangong.model.network.body.ModifyProfileInfoBody;
import com.pcg.yuquangong.model.network.body.ModifyPswBody;
import com.pcg.yuquangong.model.network.body.SettingFeedbackBody;
import com.pcg.yuquangong.model.network.body.UploadAlarmBody;
import com.pcg.yuquangong.model.network.entity.AppUpdateInfoEntity;
import com.pcg.yuquangong.model.network.entity.BaseEntity;
import com.pcg.yuquangong.model.network.entity.CheckAppVersionEntity;
import com.pcg.yuquangong.model.network.entity.CureRecordItemEntity;
import com.pcg.yuquangong.model.network.entity.LoginEntity;
import com.pcg.yuquangong.model.network.entity.MainBannerEntity;
import com.pcg.yuquangong.model.network.entity.MemberListItemEntity;
import com.pcg.yuquangong.model.network.entity.SettingProfileInfoEntity;
import com.pcg.yuquangong.model.network.entity.SettingAboutEntity;
import com.pcg.yuquangong.model.network.entity.SettingMyDeviceEntity;
import com.pcg.yuquangong.model.network.entity.WatchRecordItemEntity;
import com.pcg.yuquangong.model.network.entity.ZCodeItemEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gupengcheng on 2018/12/30.
 */

public interface ApiService {

    // 登录相关

    /**
     * 登录
     *
     * @param body
     * @return
     */
    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("login")
    Observable<BaseEntity<LoginEntity>> login(@Body LoginBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("code")
    Observable<BaseEntity<String>> getCaptchaCode(@Query("mobile") String mobile, @Query("zcode") String zcode);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @PUT("password")
    Observable<BaseEntity<String>> modifyPsw(@Header("Authorization") String token, @Body ModifyPswBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @DELETE("logout")
    Observable<BaseEntity<String>> logout(@Header("Authorization") String token);

    // 首页相关

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("banners")
    Observable<BaseEntity<List<MainBannerEntity>>> getBannerInfo(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("version")
    Observable<BaseEntity<CheckAppVersionEntity>> checkAppVersion(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("alarm")
    Observable<BaseEntity<String>> uploadAlarm(@Body UploadAlarmBody body);

    // 设置相关

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("about")
    Observable<BaseEntity<SettingAboutEntity>> getSettingAboutUs(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("devices")
    Observable<BaseEntity<List<SettingMyDeviceEntity>>> getSettingMyDevices(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("advises")
    Observable<BaseEntity<String>> sendSettingFeedback(@Header("Authorization") String token, @Body SettingFeedbackBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("profile")
    Observable<BaseEntity<SettingProfileInfoEntity>> getProfileInfo(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @PUT("profile")
    Observable<BaseEntity<SettingProfileInfoEntity>> modifyProfileInfo(@Header("Authorization") String token, @Body ModifyProfileInfoBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("version")
    Observable<BaseEntity<AppUpdateInfoEntity>> getAppUpdateInfo(@Header("Authorization") String token);

    // 成员相关

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("members")
    Observable<BaseEntity<String>> addMember(@Header("Authorization") String token, @Body AddMemberBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @PUT("members/{id}")
    Observable<BaseEntity<String>> modifyMember(@Header("Authorization") String token, @Path("id") int id, @Body AddMemberBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("members")
    Observable<BaseEntity<List<MemberListItemEntity>>> getAllMembers(@Header("Authorization") String token, @Query("word") String searchWord);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @DELETE("members/{id}")
    Observable<BaseEntity<String>> deleteMember(@Header("Authorization") String token, @Path("id") int id);

    // 治疗相关

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("cures")
    Observable<BaseEntity<List<CureRecordItemEntity>>> getAllCureList(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("members/{id}/cures")
    Observable<BaseEntity<List<CureRecordItemEntity>>> getDirectMemberCureList(@Header("Authorization") String token, @Path("id") int memberId);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("members/{id}/cures")
    Observable<BaseEntity<String>> addCureRecord(@Header("Authorization") String token, @Path("id") int memberId, @Body AddCureRecordBody body);

    // 测量相关

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("surveys")
    Observable<BaseEntity<List<WatchRecordItemEntity>>> getAllSurveryList(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("members/{id}/surveys")
    Observable<BaseEntity<List<WatchRecordItemEntity>>> getDirectMemberSurveryList(@Header("Authorization") String token, @Path("id") int memberId);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @POST("members/{id}/surveys")
    Observable<BaseEntity<String>> addSurveryRecord(@Header("Authorization") String token, @Path("id") int memberId, @Body AddSurveryBody body);

    @Headers({
            "Content-Type: application/json",
            "cache-control: no-cache",
            "Accept: */*"
    })
    @GET("zcodes")
    Observable<BaseEntity<List<ZCodeItemEntity>>> getZCode();

}
