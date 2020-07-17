package com.suek.ex79retrofittest;


//이름만 있고 기능없는 메소드-> 추상메소드

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitService {

    //1. 단순하게 GET 방식으로 json 문자열을 읽어오는 기능의 추상메소드
    @GET("/Retrofit/board.json")
    Call<BoardItem> getBoardJson();   //스트림기능..들 모든 기능을 할수있도록 call 메소드?

    //2. 경로의 이름을 1번처럼(Retrofit) 고정하지 않고 파라미터로 전달받아 지정 가능! [@Path]
    @GET("/{folder}/board.json")   //폴더이름이 없을때
    Call<BoardItem> getBoardJson2ByPath(@Path("folder") String folder);   //// @GET("Retrofit/{이자리에 전달받을 폴더명이 들어감}")  -->String folder

    //3. GET 방식으로 서버에 데이터전달 [ $Query ]
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest(@Query("name") String name, @Query("msg") String msg);

    //4. GET 방식으로 값을 전달하되 경로파일명도 지정할 수 있음 (2번+3번 함께 쓰는것 가능 = @Query, @Path)
    @GET("/Retrofit/{filename}")     // @GET("Retrofit/{이자리에 전달받을 폴더명이 들어감}")
    Call<BoardItem> getMethodTest2(@Path("filename") String fileName,     //첫번째 파라미터- 경로
                                   @Query("name") String name,           //두번째, 세번째 파라미터- 서버로 보낼데이터 name 과 msg
                                   @Query("msg") String msg);

    //5. GET 방식으로 보낼 값들을 Map Collection(Array-..list, set, map) 으로 한방에 전달하기! [ @QueryMap ]
    @GET("/Retrofit/getTest.php")
    Call<BoardItem> getMethodTest3(@QueryMap Map<String, String> datas);   //첫번째 String= key, 두번째 String= value


    //6. POST 방식으로 데이터 보내기 [ @Body ] - 객체를 전달하면 자동으로 json 문자열로 변환하여 Body 데이터를 넣어 서버로 전송
    @POST("/Retrofit/postTest.php")
    Call<BoardItem> postMethodTest(@Body BoardItem item);


    //7. POST 방식에서 멤버값들을 GET 방식처럼 별도로 보내고 싶을때
    // [ @Field ] -@Query 와 유사한 방법, 단, 이 어노테이션은 반드시!! @FormUrlEncoded 와 함께 사용해야함
    @FormUrlEncoded
    @POST("/Retrofit/postTest2.php")
    Call<BoardItem> postMethodTest2(@Field("name") String name, @Field("msg") String msg);


    //8. 응답받을 데이터가 jsonArray 배열일때...
    @GET("/Retrofit/boardArray.json")
    Call<ArrayList<BoardItem>> getBoardArray();  //배열말고 ArrayList 로 리턴으로 결과를 받을것임..


    //9. baseUrl 을 무시하고 지정된 url 로 연결 [ @Url ]
    @GET
    Call<BoardItem> urlTest(@Url String url);

    //10. 응답결과가 항상 json 이었음. 그 결과를 항상 GSON 을 이용해서 BoardItem 객체로 받았음.
    // 만약, 그냥 String(문자열)으로 받고싶다면..
    @GET("/Retrofit/board.json")
    Call<String> getJsonString();

    }





