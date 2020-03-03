package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Data
@TableName("log_channel")
public class LogChannelEntity implements Serializable {
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

    private Integer clickdownnum;

    private Integer uvNum;

}
