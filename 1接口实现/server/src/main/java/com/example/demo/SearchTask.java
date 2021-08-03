package com.example.demo;

import net.sf.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
public class SearchTask{

    public static final int OK = 0;
    public static final int ID_ERROR = 1;
    public static final int CONTENT_ERROR = 2;

//    private String a, b;
//    public SearchTask(String a,String b){
//        this.a = a;
//        this.b = b;
//    }

    @Async("myTaskAsyncPool")
    public Future<String> call(String a, String b){
        if(a != null) {
            int id;
            try {
                id = Integer.parseInt(a);
                if (id < 0 || id > 99999) id = -1;
            } catch (Exception e) {
                id = -1;
            }

            if (id == -1) {
                JSONObject RES_ID_ERROR = new JSONObject();
                RES_ID_ERROR.put("error_code",ID_ERROR);
                RES_ID_ERROR.put("error_message","id不合法");
                RES_ID_ERROR.put("reference", "id " + a + " 不合法");
                return new AsyncResult<>(RES_ID_ERROR.toString());
            }
        }

        if(b.length()>32){
            JSONObject RES_CONTENT_ERROR = new JSONObject();
            RES_CONTENT_ERROR.put("error_code",CONTENT_ERROR);
            RES_CONTENT_ERROR.put("error_message","搜索内容不合法");
            RES_CONTENT_ERROR.put("reference","关键字 " + b + " 过长");
            return new AsyncResult<>(RES_CONTENT_ERROR.toString());
        }

        if(b.length() == 0){
            JSONObject RES_CONTENT_ERROR = new JSONObject();
            RES_CONTENT_ERROR.put("error_code",CONTENT_ERROR);
            RES_CONTENT_ERROR.put("error_message","搜索内容不合法");
            RES_CONTENT_ERROR.put("reference","关键字不能为空");
            return new AsyncResult<>(RES_CONTENT_ERROR.toString());
        }

        JSONObject RES_OK = new JSONObject();
        RES_OK.put("error_code",OK);
        RES_OK.put("error_message","");
        if(a == null)
            RES_OK.put("reference","关键字 " + b + " 匿名搜索成功");
        else
            RES_OK.put("reference","关键字 " + b + " 搜索成功");
        return new AsyncResult<>(RES_OK.toString());
    }
}
