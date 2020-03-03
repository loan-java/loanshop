package io.dkgj.controller;

import com.alibaba.fastjson.JSONObject;
import io.dkgj.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "首页接口")
public class TestController {

    static String sign = "dd2c67d7d2f2c654";

    @ApiOperation("所有页面埋点")
    @PostMapping("/testAfu")
    public JSONObject testAfu(@RequestParam("params") String params) throws UnsupportedEncodingException {
        System.out.println(params);
        String strData = params;//返回密文
        String strParam = null;//解密报文
        try {
            strParam = EchoCallOrgClient.decrypt(strData, sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("接到加密请求:" + strData);
        System.out.println("请求解密后：" + strParam);
        JSONObject paramsJSON = JSONObject.parseObject(strParam);
        String id_no = paramsJSON.getString("id_no");
        String name = paramsJSON.getString("name");

        return orgReturnEcho(name,id_no);
    }


    /**
     * 机构响应结果
     */
    public JSONObject orgReturnEcho(String name,String idNo){
        try {
            Map loanRecord=new HashMap();
            loanRecord.put("approvalStatus","IN_PROGRESS");//审批结果码
            loanRecord.put("idNo",idNo);//被查询借款人身份证号
            loanRecord.put("loanAmount","1002.00");//借款金额，通过的，取合同金额;未通过或审核中的， 取申请金额
            loanRecord.put("loanDate","20190605");//借款时间，通过的，取合同时间;未通过或审核中的， 取申请时间。 作为数据提供方，平台可识别的时间格式有 2 种: YYYYMM或者YYYYMMDD
            //loanRecord.put("loanStatus","OVERDUE");//还款状态码，指一笔借款合同当前的状态;若历史出 现过逾期，当前还款正常，则还款状态取“正常”
            loanRecord.put("loanType","");//借款类型码，指一笔借款所属的类型
            loanRecord.put("name",name);//被查询借款人姓名
            //loanRecord.put("overdueAmount","2.00");//逾期金额，指一笔借款中，达到还款期限，尚未偿还 的总金额
            //loanRecord.put("overdueM3",1);//历史逾期 M3+次数(不含 M3，包括 M6 及以上)
            //loanRecord.put("overdueM6",1);//历史逾期 M6+次数(不含 M6)
            //loanRecord.put("overdueStatus","M1");//逾期情况，指一笔借款当前逾期的程度
            //loanRecord.put("overdueTotal",1);//历史逾期总次数
            loanRecord.put("periods",1);//期数, , 通过的，取合同期数;未通过或审核中的，取 申请期数，范围 1~120
            Map riskResult=new HashMap();//风险项记录
            riskResult.put("riskDetail","");//风险明细，合作机构提供的借款人的风险类别
            riskResult.put("riskItemType","");//命中项码，如证件号码(当前命中项仅包括证件号码)
            riskResult.put("riskItemValue",idNo);//命中内容，身份证号的具体值
            riskResult.put("riskTime","");//风险最近时间，指风险记录最近一次发现的时间 平台可识别的时间格式有 3 种: YYYY YYYYMM YYYYMMDD

            List<Map> loanRecords=new ArrayList<>();
            loanRecords.add(loanRecord);
            List<Map> riskResults=new ArrayList<>();
            riskResults.add(riskResult);

            JSONObject resultJson=new JSONObject();//
            Boolean success=true; //成功:true; 失败:false
            JSONObject dataJson=new JSONObject(); //响应结果
            dataJson.put("loanRecords",loanRecords);
            dataJson.put("riskResults",riskResults);
            String strData=EchoCallOrgClient.encrypt(dataJson.toJSONString(),sign);
            System.out.println("data数据明文"+dataJson.toJSONString());
            System.out.println("data数据加密"+strData);
            resultJson.put("success",success);
            resultJson.put("data",strData);
            System.out.println("响应结果"+resultJson.toJSONString());
            return resultJson;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
