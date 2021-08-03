package com.example.demo.controller;

import com.example.demo.SearchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class ServerController {
    private ThreadPoolExecutor threadPoolExecutor;
    private BlockingDeque<Runnable> blockingDeque;

    public ServerController(){
        blockingDeque = new LinkedBlockingDeque<Runnable>(100);
        threadPoolExecutor = new ThreadPoolExecutor(8,15,60, TimeUnit.SECONDS,blockingDeque);
    }

//    @RequestMapping(value = "/http",method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//    public String get(@RequestParam(required = false) String a, @RequestParam String b) {
//        SearchTask task = new SearchTask(a,b);
//        Future<String> future = threadPoolExecutor.submit(task);
//        String response;
//        try{
//            response = future.get();
//        }catch(Exception e){
//            response = null;
//        }
//        return response;
//    }

    @Autowired
    private SearchTask searchTask;

    @RequestMapping(value = "/http",method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String get1(@RequestParam(required = false) String a, @RequestParam String b) {
        Future<String> future = searchTask.call(a,b);
        String res;
        try{
            res = future.get();
        }catch(Exception e){
            res = "";
        }
        return res;
    }

}
