package com.mapd.assignmenttracker.network;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServiceGenerator {
    private static ApiService SERVICE;
    private static String BASE_URL = "https://assignment-tracker-d890b.firebaseio.com/";
    private ServiceGenerator(){

    }

    public static ApiService getService(){
        if(SERVICE == null){
            initialize();
        }
        return SERVICE;
    }

    private static void initialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Setting base url of target endpoint
                .addConverterFactory(MoshiConverterFactory.create()) //setting JSON converter
                .build(); //Building Retrofit object
        SERVICE = retrofit.create(ApiService.class);
    }
}
