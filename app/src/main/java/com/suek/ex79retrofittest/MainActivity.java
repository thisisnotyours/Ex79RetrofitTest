package com.suek.ex79retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
    }

    public void clickBtn(View view) {
        //네트워크에서 읽어들인 json 을 곧바로 객체로 생성까지... => BoardItem 객체

        //Retrofit2 라이브버리를 이용해서 http 통신작업 시작!!
        //단계
        //1. Retrofit 객체 생성 및 기본설정
        Retrofit.Builder builder= new Retrofit.Builder();   //retrofit 객체를 만들어주는 Builder 건축가 객체
        builder.baseUrl("http://suhyun2963.dothome.co.kr");  //서버의 기본주소
        builder.addConverterFactory(GsonConverterFactory.create());   //retrofit 이 읽어온 json 데이터를 GSON 을 이용해서 파싱(분석)하기 위한 설정
        Retrofit retrofit= builder.build();    //retrofit 객체 하나 만듦

        //2. RetrofitService 인터페이스 설계
        //원하는 기능의 추상메소드를 설계 : getBoardJson() 추상메소드

        //3. RetrofitService 인터페이스를 객체로 생성
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);   //new 안함
        //서비스 객체의 추상메소드의 기능들을 알아서 레트로핏이 만들어냄..

        //4. 서비스객체의 원하는 기능메소드를 실행해서 Call 객체 얻어오기
        Call<BoardItem> call= retrofitService.getBoardJson();   //call : 네트워트 작업을 하는 객체****

        //5. 원하는 기능으로 network 작업을 수행하도록  call 객체를 큐에 삽입
        // enqueue : 사전적의미- 데이터의 아이템을 큐(대기행렬)에 더하다.
        call.enqueue(new Callback<BoardItem>() {   //callback 이 결과를 가지고 돌아옴
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {  //call-> 실행했던 놈 //응답받음  //response- 응답객체(결과를 가지고있음)
                Toast.makeText(MainActivity.this, "응답받음", Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()){
                    //응답객체로부터 GSON 라이브러리에 의해 자동으로 BoardItem 으로
                    //파싱되어있는 json 문자열의 데이터값 body 얻어오기
                    BoardItem item= response.body();   //파싱된 BoardItem 가져오기
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {   //응답못받음
                Toast.makeText(MainActivity.this, "응답실패", Toast.LENGTH_SHORT).show();
            }
        });   //queue : 먼저온게 먼저 수행됨- 네트워크

    }





    public void clickBtn2(View view) {
        //Retrofit 작업
        //1. Retrofit 객체 생성 및 설정
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://suhyun2963.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        //2. RetrofitService 인터페이스에 원하는 기능 추상메소드 설계
        // getBoardJsonByPath()

        //3. RetrofitService 객체를 생성
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //4. 원하는 기능 메소드를 호출하여 네트워크 작업을 하는 객체를 리턴시킴
        Call<BoardItem> call= retrofitService.getBoardJson2ByPath("Retrofit");

        //5. 실제 네트워크 작업 실행
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item= response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }




    public void clickBtn3(View view) {
        //1.
        Retrofit retrofit= RetrofitHelper.getRetrofitInstance();

        //2. getMethodTest()

        //3.
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        //4.
        Call<BoardItem> call= retrofitService.getMethodTest("Robin", "Nice to meet you");  //이게 서버로 GET 방식으로 전송됨
        //5.
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item= response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }





    public void clickBtn4(View view) {
        Retrofit retrofit = RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);   //추상메소드를 객체로 만들어줌
        Call<BoardItem> call = retrofitService.getMethodTest2("getTest.php", "hong", "Good Morning");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if (response.isSuccessful()) {
                    BoardItem item = response.body();
                    tv.setText(item.name + ", " + item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }




    public void clickBtn5(View view) {
        Retrofit retrofit= RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //서버에 전달할 데이터들을 Map Collection 에 저장
        Map<String, String> datas= new HashMap<>();
        datas.put("name","park");
        datas.put("msg", "Good afternoon");

        Call<BoardItem> call= retrofitService.getMethodTest3(datas);
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item= response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }





    public void clickBtn6(View view) {
        RetrofitService retrofitService= RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);

        //보낼 데이터를 가진 객체
        BoardItem boardItem= new BoardItem("Lee","Good evening");
        Call<BoardItem> call= retrofitService.postMethodTest(boardItem);
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item= response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }





    public void clickBtn7(View view) {
        RetrofitService retrofitService= RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);

        Call<BoardItem> call= retrofitService.postMethodTest2("Rosa", "Good night");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                if(response.isSuccessful()){
                    BoardItem item= response.body();
                    tv.setText(item.name+", "+item.msg);
                }
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }





    public void clickBtn8(View view) {
        RetrofitService retrofitService= RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        Call<ArrayList<BoardItem>> call= retrofitService.getBoardArray();
        call.enqueue(new Callback<ArrayList<BoardItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BoardItem>> call, Response<ArrayList<BoardItem>> response) {
                if(response.isSuccessful()){
                    ArrayList<BoardItem> items= response.body();
                    //tv.setText(items.size()+"");   //4
                    for(BoardItem item : items){
                        tv.append("\n"+item.name+", "+item.msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BoardItem>> call, Throwable t) {

            }
        });
    }





    public void clickBtn9(View view) {
        RetrofitService retrofitService= RetrofitHelper.getRetrofitInstance().create(RetrofitService.class);
        Call<BoardItem> call= retrofitService.urlTest("http://suhyun2963.dothome.co.kr/Retrofit/board.json");
        call.enqueue(new Callback<BoardItem>() {
            @Override
            public void onResponse(Call<BoardItem> call, Response<BoardItem> response) {
                BoardItem item= response.body();
                tv.setText(item.name+", "+item.msg);
            }

            @Override
            public void onFailure(Call<BoardItem> call, Throwable t) {

            }
        });
    }




    //파싱을 안쓰고 전달받고 싶을때..? -> Scalar
    public void clickBtn10(View view) {
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://suhyun2963.dothome.co.kr");
        //결과를 String 으로 받으려면 GsonConverter 가 아니라..
        //ScalarsConverter 을 사용해야함..(종류가 다름) -Scalar 값. 수치가 다름 [converter-scalars:2.9.0] 라이브러리 추가
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit= builder.build();

        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        Call<String> call= retrofitService.getJsonString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                tv.setText(s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }





}
