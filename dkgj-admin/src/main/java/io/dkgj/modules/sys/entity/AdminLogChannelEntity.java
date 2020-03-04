package io.dkgj.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Data
@TableName("log_channel")
public class AdminLogChannelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String channel;
    /**
     *
     */
    private Integer clicknum;

    @TableField(exist = false)
    private Integer clicknumTmp;
    /**
     *
     */
    private Integer regnum;

    @TableField(exist = false)
    private Integer regnumTmp;
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

    @TableField(exist = false)
    private String channelName;

    private Integer appOpenNum;
    @TableField(exist = false)
    private Integer appOpenNumTmp;

    private Integer clickdownnum;
    @TableField(exist = false)
    private Integer clickdownnumTmp;

    private Integer uvNum;
    @TableField(exist = false)
    private Integer uvNumTmp;

    @TableField(exist = false)
    private Integer loanUvNum = 0;

    @TableField(exist = false)
    private String remark;

    @TableField(exist = false)
    private String todayUvNum;
    @TableField(exist = false)
    private String todayPvNum;
    @TableField(exist = false)
    private Integer channelRegNum;

    /**
     * 注册人均点击数=产品UV/注册数
     */
    @TableField(exist = false)
    private BigDecimal zcrjdjs;

    /**
     * UV注册率 = 注册数/UV率
     */
    @TableField(exist = false)
    private String uvzcl;


}
