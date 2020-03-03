package io.dkgj.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

public class SmsDkmUtils {
    /**
     * 钱袋子短信
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendQdzSms(String mobile, String chName, String code) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.xunbd.com/sms/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String accesskey = "3bpWYpQr4TS4WepQ"; //用户开发key
        String accessSecret = "Dzq2Vb5ESAlP8rKMMxZxJViDFDaf7LJs"; //用户开发秘钥

        //String content = String.format("%s##%s", code, "5分钟");

        if (StringUtils.isBlank(chName)) {
            chName = "贷款喵";
        }

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "【" + chName + "】"),
                new NameValuePair("tplId", "410"),
                new NameValuePair("phone", mobile),
                new NameValuePair("msg", code)
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        JSONObject responseJSON = JSONObject.parseObject(postMethod.getResponseBodyAsString());
        return responseJSON;
    }


    public static void main(String[] args) {
        try {
            SmsDkmUtils.sendQdzSms("13868140535", "秒下分期", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
