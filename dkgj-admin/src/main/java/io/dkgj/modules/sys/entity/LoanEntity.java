package io.dkgj.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-25 15:45:22
 */
@Data
@TableName("loan")
public class LoanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    @NotBlank(message = "名称不能为空")
    private String title;
    /**
     *
     */
    private Integer state;
    /**
     *
     */
    private Integer type;

    @NotBlank(message = "角标不能为空")
    private String badge;

    @NotBlank(message = "贷款类型不能为空")
    private String tags;

    @NotBlank(message = "最低可贷金额不能为空")
    private String minloan;
    /**
     *
     */
    @NotBlank(message = "最高可贷金额不能为空")
    private String maxloan;
    /**
     *
     */
    @NotBlank(message = "费率不能为空")
    private String raterange;
    /**
     *
     */
    private String periodrange;
    /**
     *
     */
    @NotBlank(message = "申请链接不能为空")
    private String applyurl;
    /**
     *
     */


    /**
     *
     */
    @NotBlank(message = "权重不能为空")
    private String weights;

    @NotBlank(message = "申请人数不能为空")
    private String applynum;

    /**
     *
     */

    private String applyurl2;
    /**
     *
     */
    @NotBlank(message = "简介不能为空")
    private String intro;
    /**
     *
     */
    @NotBlank(message = "申请条件不能为空")
    private String applyinfo;
    /**
     *
     */
    @NotBlank(message = "图标不能为空")
    private String logo;
    /**
     *
     */
    private Integer byuid;
    /**
     *
     */
    private Date createdat;
    /**
     *
     */
    private Date updatedat;
    /**
     *
     */
    private Date deletedat;

    private String managelink;

    private String manageaccount;

    private String managepassword;

    private String market;

    private String maxuv;
}
