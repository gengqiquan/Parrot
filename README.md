# Parrot
mock webServer respone about okhttp3's request
make your develop and debug convenient when only documents and api has not been released to the test server

[English](https://github.com/gengqiquan/Parrot/blob/master/README_zh.md)

# Start to use
* create a mock class and define some methods that annotationed by  @MOCK
return the result that you want

 ```

public class MockService {
    @MOCK("github_user_info")
    public MockResult auction(Request request) {
        Map<String, String> query = MockRequest.getQuery(request);
        String name = query.get("name");
        return MockResult.create(request, "{\n" +
                "    \"status\": true,\n" +
                "    \"msg\": \"操作成功\",\n" +
                "    \"data\": {\n" +
                "        \"bond\": \"100000\",\n" +
                "        \"auction_plats\": [\n" +
                "            {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"汽车街\",\n" +
                "                \"rule_url\": \"http://www.rule.com\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
    }
}
```
* the value of @MOCK :github_user_info is same as your api path
```

public interface GithubService {
    @GET("github_user_info")
    public Observable<String> getUser();
}
```

* add the MockInterceptor while DEBUG
```
   OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        if (BuildConfig.DEBUG) {//printf logs while  debug
            client = client.newBuilder()
                    .addInterceptor(new MockInterceptor(Parrot.create(MockService.class)))
                    .build();
        }
```

* get your mock result
```
  new Retrofit.Builder()
                .baseUrl("http://www.github.api.com")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(GithubService.class)
                .getUser()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        text.setText(e.toString());
                    }

                    @Override
                    public void onNext(String json) {
                    // the json is  your mock result
                        text.setText(json);
                    }
                });
```
# gradle
```
compile 'com.gengqiquan:parrot:0.0.1'
```
# maven
```
<dependency>
  <groupId>com.gengqiquan</groupId>
  <artifactId>parrot</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```