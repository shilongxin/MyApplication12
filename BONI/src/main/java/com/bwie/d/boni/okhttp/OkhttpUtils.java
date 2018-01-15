package com.bwie.d.boni.okhttp;

import android.util.Log;

import com.bwie.d.boni.jiekou.MyAdaptercallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by muhanxi on 17/11/10.
 *
 *
 * Okhttp 单例 范型的封装
 */

public class OkhttpUtils {






    private static OkhttpUtils okhttpUtils = null ;

    private OkhttpUtils(){

    }

    public static OkhttpUtils getInstance(){

        if(okhttpUtils == null){
            okhttpUtils = new OkhttpUtils();
            client = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    .connectTimeout(20,TimeUnit.SECONDS)
                    //添加拦截器
                    .addInterceptor(new HttpInterceptor())
                    .build();
        }
        return okhttpUtils ;

    }


    private static OkHttpClient client ;


    /**
     * 发起异步请求
     * @param params
     * @param url
     * @param callBack
     */
    public void asy(Map<String,String> params,String url,AbstractUiCallBack callBack){
        Request request = null ;

        if(params != null){
            // post 请求
            FormBody.Builder builder = new FormBody.Builder() ;
            for(Map.Entry<String,String> entry : params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
            FormBody body =  builder.build();
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        } else {
            // get 请求
            request = new Request.Builder()
                    .url(url)
                    .build();
        }



        client.newCall(request).enqueue(callBack);

    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    public static void postFile(Map<String,String> map, String url, File file,AbstractUiCallBack callBack){

        String [] array =  file.getAbsolutePath().split("\\/");

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for(Map.Entry<String,String> entry : map.entrySet()){
            builder.addFormDataPart(entry.getKey(),entry.getValue());
        }
        builder.addFormDataPart("imageFileName",array[array.length-1]);


        if(file.exists() && file.length() > 0){
            builder.addFormDataPart("image",array[array.length-1], RequestBody.create(MEDIA_TYPE_PNG,file));
        }
        MultipartBody body =  builder.build() ;

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callBack);


    }




    public void getLrc(String songid, final MyAdaptercallback myAdaptercallback){


        Request request = new Request.Builder()
                .url("http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.song.lry&songid="+songid)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {



            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Log.i("-------",string);
                myAdaptercallback.success(string);
            }
        });

    }


    public void getSong(String songid, final MyAdaptercallback myAdaptercallback){


        Request request = new Request.Builder()
                .url("http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.song.play&songid="+songid)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {



            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Log.i("-------",string);
                myAdaptercallback.success(string);
            }
        });

    }






}
