# Parrot
模拟服务器响应okhttp3的请求

[English](https://github.com/gengqiquan/Parrot/blob/master/README.md)

# 开始使用
* 创建一个类，然后定义一些注解了@MOCK的方法
在方法内部根据request决定返回对应的数据

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
*  @MOCK的注解值：github_user_info 对应你需要模拟的方法的接口路径
```

public interface GithubService {
    @GET("github_user_info")
    public Observable<String> getUser();
}
```

* 只在DEBUG情况下添加 MockInterceptor，防止忘记移除方法影响生产环境
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

* 获取你模拟的数据结果
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
