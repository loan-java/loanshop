package io.dkgj.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogAppLoginForm {

    @NotBlank(message = "渠道编码不能为空")
    private String channel;
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    @NotBlank(message = "app类型不能为空")
    private String type;
    /**
     *
     */
    private String mac;
}
