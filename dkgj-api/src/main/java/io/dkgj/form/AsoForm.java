package io.dkgj.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AsoForm {

    @NotBlank(message = "appId不能为空！")
    private String appId;

    private String idfa;

    private String ip;

    private String device;

    private String os;

    private String keyword;

    private String channel;

}
