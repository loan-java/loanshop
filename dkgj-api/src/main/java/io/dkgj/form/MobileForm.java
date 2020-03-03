package io.dkgj.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "注册表单")
public class MobileForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    private String vcode;

    private String channel;

    private Integer os;

}
