package cn.ludean.net;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author JWT
 */

public interface ApiService {

    @FormUrlEncoded
    @POST
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<ResponseBody> send_Post(@Url String url,@Header("uuid") String uuid, @FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<ResponseBody> saveClockInfo(@Url String url, @Header("uuid") String uuid, @FieldMap Map<String, String> map);

    @POST
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<ResponseBody> getMarker(@Url String url, @Header("uuid") String uuid);


}
