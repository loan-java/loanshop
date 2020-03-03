package io.dkgj.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Data
@TableName("channel_manage")
public class ChannelManageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String channelurl;
    /**
     *
     */
    @NotBlank(message = "渠道代码不能为空")
    private String channelcode;
    /**
     *
     */
    @NotBlank(message = "渠道名称不能为空")
    private String channelname;
    /**
     *
     */
    private Integer state;
    /**
     *
     */
    @NotBlank(message = "备注不能为空")
    private String remark;
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


    private Date deductdate;

    @TableField(exist=false)
    private String deductDateStr;
    /**
     *
     */
    private String checkregisterurl;

    private Integer deductstatus;

    private Integer deductbase;

    private BigDecimal deductratio;

}
