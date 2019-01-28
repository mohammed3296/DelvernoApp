package com.codecaique3296.delivrenoapp.network;

import com.codecaique3296.delivrenoapp.models.object.Mechanism;
import com.codecaique3296.delivrenoapp.models.response.ImageUpdateJson;
import com.codecaique3296.delivrenoapp.models.response.LoginResponse;
import com.codecaique3296.delivrenoapp.models.response.PhoneJson;
import com.codecaique3296.delivrenoapp.models.response.PublicResponse;
import com.codecaique3296.delivrenoapp.models.response.ReportsJson;
import com.codecaique3296.delivrenoapp.models.response.RequestsJson;
import com.codecaique3296.delivrenoapp.models.response.ResturantsJson;
import com.codecaique3296.delivrenoapp.models.response.TayarStatusJson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login_system/index.php")
    Call<LoginResponse> getLoginTayar(@Field("login") String btn, @Field("phone1") String email
            , @Field("password") String pass);


    @FormUrlEncoded
    @POST("addoperations/addoperations.php")
    Call<PublicResponse> addProblem(@Field("addProblem") String addProblem,
                                    @Field("content") String content,
                                    @Field("tayar_id") String tayar_id);


    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<Mechanism> getMechanism(@Field("mechanism") String mechanism);

    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<Mechanism> getWe(@Field("we") String we);


    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<PhoneJson> getphones(@Field("phones") String phones);


    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<ReportsJson> getReport(@Field("reports") String reports,
                                @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<ReportsJson> getReportByDate(@Field("reports_by_time") String reports,
                                      @Field("tayar_id") String tayar_id, @Field("report_date") String report_date);


    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<ResturantsJson> getResturants(@Field("resturants") String resturants);

    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<RequestsJson> getRequests(@Field("requests") String requests,
                                   @Field("tayar_id") String tayar_id);


    @FormUrlEncoded
    @POST("deleteoperation/delete.php")
    Call<PublicResponse> deleteRequest(@Field("delete_request") String delete_request,
                                       @Field("request_id") String request_id,
                                       @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> acceptRequest(@Field("accept_request") String accept_request,
                                       @Field("request_id") String request_id,
                                       @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> receiptRequest(@Field("receipt_request") String receipt_request,
                                        @Field("request_id") String request_id,
                                        @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> deleverdRequest(@Field("deleverd_request") String deleverd_request,
                                         @Field("request_id") String request_id,
                                         @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> unacceptRequest(@Field("unaccept_request") String unaccept_request,
                                         @Field("request_id") String request_id,
                                         @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> unreceiptRequest(@Field("unreceipt_request") String unreceipt_request,
                                          @Field("request_id") String request_id,
                                          @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> undeleverdRequest(@Field("undeleverd_request") String undeleverd_request,
                                           @Field("request_id") String request_id,
                                           @Field("tayar_id") String tayar_id);


    @FormUrlEncoded
    @POST("deleteoperation/delete.php")
    Call<PublicResponse> deleteReport(@Field("delete_report") String delete_report,
                                      @Field("report_id") String report_id,
                                      @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> editPhone(@Field("edit_phone") String edit_phone,
                                   @Field("tayar_id") String tayar_id,
                                   @Field("new_phone") String new_phone);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> editpassword(@Field("edit_password") String edit_password,
                                      @Field("tayar_id") String tayar_id,
                                      @Field("new_pass") String new_pass ,
                                      @Field("old_pass") String old_pass);
    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<ImageUpdateJson> editImage(@Field("edit_image") String edit_image,
                                    @Field("tayar_id")   String tayar_id,
                                    @Field("new_image")  String new_image);


    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> setStatusOff(@Field("status_off") String status_off,
                                      @Field("tayar_id") String tayar_id);



    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> setStatusOn(@Field("status_on") String status_on,
                                      @Field("tayar_id") String tayar_id);

    @FormUrlEncoded
    @POST("editoperations/edit.php")
    Call<PublicResponse> editLocation(@Field("edit_location") String edit_location,
                                      @Field("tayar_id") String tayar_id ,
                                      @Field("longtude") String longtude ,
                                      @Field("latitude") String latitude);



    @FormUrlEncoded
    @POST("getoperations/show.php")
    Call<TayarStatusJson> getTayarStatus(@Field("tayar_status") String tayar_status,
                                         @Field("tayar_id") String tayar_id);

}
