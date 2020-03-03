package io.dkgj.controller.vo;

import lombok.Data;

@Data
public class ResponseVO {
    private String msg;
    private String taskId;
    private String token;
    private String apiUser;
    private String apiEnc;
    private String apiName;
    private String success;
    private String type;
}
