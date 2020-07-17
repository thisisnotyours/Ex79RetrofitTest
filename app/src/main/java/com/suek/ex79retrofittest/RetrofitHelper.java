package com.suek.ex79retrofittest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static Retrofit getRetrofitInstance(){    //public static- 객체로 만들지 않고 쓸수있는 메소드
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://suhyun2963.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        return retrofit;

    }
}
