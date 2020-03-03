package io.dkgj.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Slf4j
public class Sms4Utils {

    public static String apiKey = "563e5f400313bdc840cd031f92158fa4";
    public static String securityKey = "627afe868034d9da2e1ed3ca7e0e86a41636f17a";

    public static JSONObject sendDsSms(String mobile, String code) throws UnsupportedEncodingException {
        String tokenParams = "{" +
                "    \"apiKey\":\"" + apiKey + "\",   \n" +
                "    \"securityKey\":\"" + securityKey + "\"  \n" +
                "}";
        String url = "https://api.dsdatas.com/credit/api/token";
        String smsUrl = "https://api.dsdatas.com/movek/movekSimpleInfo";
        String message = String.format("你的验证码为：%s请不要把验证码泄露给其他人！10分钟内有效", code);
        String smsParams = "{" +
                "  \"timestamp\":" + new Date().getTime() + "," +
                "  \"smsNo\":\"SMS1570459802\"," +
                "  \"mobile\":\"" + mobile + "\", " +
                "  \"message\":\"" + message + "\"" +
                "}";
        try {
            String token = null;
            JSONObject tokenJson = SmsBaseUtils.postBody(null, url, tokenParams);
            if (200 == tokenJson.getInteger("code")) {
                JSONObject data = tokenJson.getJSONObject("data");
                token = data.getString("token");
                log.info(smsParams);
                JSONObject json = SmsBaseUtils.postBody(token, smsUrl, smsParams);
                log.info(JSONObject.toJSONString(json));
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Sms4Utils.sendDsSms("13868140535", "123456");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
