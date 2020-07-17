package com.suek.ex79retrofittest;




//json 이 보내준 멤버변수 =>  {"name":"sam", "msg":"Hello Retrofit"}

import com.google.gson.annotations.SerializedName;

public class BoardItem {
    //읽어들인 변환할 json 의 키값들과 같은 이름의 멤버변수
    String name;
    String msg;

    //만약에 json 의 키값과 다른 변수명을 사용하고 싶다면
    @SerializedName("msg")
    String message;


    public BoardItem() {
    }


    public BoardItem(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }


}


