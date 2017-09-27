package com.gengqiquan.parrot;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public interface GithubService {
    @GET("github_user_info")
    public Observable<String> getUser();
}
