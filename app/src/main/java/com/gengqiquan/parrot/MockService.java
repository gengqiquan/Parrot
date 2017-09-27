package com.gengqiquan.parrot;


import com.gengqiquan.MockRequest;
import com.gengqiquan.MockResult;
import com.gengqiquan.annotation.MOCK;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by gengqiquan on 2017/9/27.
 */

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
