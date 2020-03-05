package io.dkgj.common.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class SmsUtils {

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

        String accesskey = "ie2oWdECWgToAsiA"; //用户开发key
        String accessSecret = "IbNiMuhloHgPAScVSF060kNfWtfyI6ro"; //用户开发秘钥

        //String content = String.format("%s##%s", code, "5分钟");

        if (StringUtils.isBlank(chName)) {
            chName = "秒下分期";
        }

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "【" + chName + "】"),
                new NameValuePair("tplId", "448"),
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

    /**
     * 小蜜蜂短信
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendBeeSms(String mobile, String chName, String code) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.xunbd.com/sms/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String accesskey = "5UQg4vYZj5aaB2XB"; //用户开发key
        String accessSecret = "FejOOwKb4yZJoqKDCScVjm4wo8wRmlLL"; //用户开发秘钥

        //String content = String.format("%s##%s", code, "5分钟");

        if (StringUtils.isBlank(chName)) {
            chName = "米豆信用";
        }

        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "【" + chName + "】"),
                new NameValuePair("tplId", "612"),
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



    /**
     * 米豆信用短信(信云短信)
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendMdxXinYunSms(String mobile, String chName, String code) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("https://api.zhuanxinyun.com/api/v2/sendSms.json");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String accesskey = "yJ2w0yVV6VdoM7JfhowTVpZL9JpEhEtG"; //用户开发key
        String accessSecret = "0f38e87ddb4540b14f1e3e70d703e292"; //用户开发秘钥

        if (StringUtils.isBlank(chName)) {
            chName = "米豆信用";
        }

        NameValuePair[] data = {
                new NameValuePair("appKey", accesskey),
                new NameValuePair("appSecret", accessSecret),
                new NameValuePair("content", "【" + chName + "】"+"你的验证码为："+code+"请不要把验证码泄露给其他人！ 10分钟内有效"),
                new NameValuePair("phones", mobile),
                new NameValuePair("batchNum", "kjzhou01"+new SimpleDateFormat("YYYYMMdd").format(new Date()) + (int)(Math.random()*1000000))
        };
        postMethod.setRequestBody(data);

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: "
                + postMethod.getResponseBodyAsString());
        JSONObject responseJSON = JSONObject.parseObject(postMethod.getResponseBodyAsString());
        return responseJSON;
    }

    /**
     * 讯必达短信
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendXunbdSms(String accesskey, String accessSecret, String chName, String mobile, String code) throws Exception {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod("http://api.xunbd.com/sms/single_send");
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());


        NameValuePair[] data = {
                new NameValuePair("accesskey", accesskey),
                new NameValuePair("secret", accessSecret),
                new NameValuePair("sign", "【" + chName + "】"),
                new NameValuePair("tplId", "670"),
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

    /**
     * MO信通平台
     * @param username
     * @param pwd
     * @param chName
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendMOSms(String username, String pwd, String chName, String mobile, String code) throws Exception {
        String url = "https://api.uoleem.com.cn/sms/httpBatchSend/";// 应用地址
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setContentCharset("UTF-8");
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String content = "【"+chName+"】验证码：" + code + "。如不是本人操作，请忽略此信息。" ;

        postMethod.setParameter("username", username);
        postMethod.setParameter("pwd", pwd);
        postMethod.setParameter("content", content);
        postMethod.setParameter("mobile", mobile);

        int statusCode = httpClient.executeMethod(postMethod);
        System.out.println("statusCode: " + statusCode + ", body: " + postMethod.getResponseBodyAsString());
        JSONObject responseJSON = JSONObject.parseObject(postMethod.getResponseBodyAsString());
        return responseJSON;
    }


    public static void main(String[] args) throws Exception {
        SmsUtils.sendMOSms("httz001","4dae6489da13cc5285058c9124346ef0", "易财钱包", "15868417851", "123123");
    }
}
