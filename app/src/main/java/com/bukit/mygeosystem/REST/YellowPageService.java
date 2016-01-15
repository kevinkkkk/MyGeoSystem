package com.bukit.mygeosystem.REST;

import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kevin on 12/10/2015.
 */
public interface YellowPageService {

    @GET("/FindBusiness/")
    void findAroundBusiness(@Query("what") String what,
                            @Query("where") String where,
                            @Query("fmt") String fmt,
                            @Query("dist") int dist,
                            @Query("pgLen") int pgLen,
                            @Query("apikey") String key,
                            @Query("UID") int uid,
                            Callback<YellowPagesReturn> cb);
}
