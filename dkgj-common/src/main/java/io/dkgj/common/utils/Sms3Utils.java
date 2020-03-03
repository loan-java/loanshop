package io.dkgj.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

@Slf4j
public class Sms3Utils {
    /**
     * 钱袋子短信
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendQdzSms(String mobile, String chName, String code) throws Exception {
        log.info(String.format("发送秒花分期短信，参数：{%s,%s,%s}",mobile,chName,code));
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.xunbd.com/sms/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String accesskey = "JWkuhjn78bYZ0HIx"; //用户开发key
        String accessSecret = "ktNNCIn2bBO5UzFT7n3B3aaUc2FRSUaE"; //用户开发秘钥

        //String content = String.format("%s##%s", code, "5分钟");

        if (StringUtils.isBlank(chName)) {
            chName = "秒花分期";
        }

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "【" + chName + "】"),
                new NameValuePair("tplId", "393"),
                new NameValuePair("phone", mobile),
                new NameValuePair("msg", code)
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        log.info("statusCode: " + statusCode + ", body: " + postMethod.getResponseBodyAsString());
        JSONObject responseJSON = JSONObject.parseObject(postMethod.getResponseBodyAsString());
        return responseJSON;
    }
}
