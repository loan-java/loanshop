package io.dkgj.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 12:04:03
 */
@Data
@TableName("history_log_channel")
public class HistoryLogChannelEntity implements Serializable {
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

    @TableField(exist = false)
    private Integer regnumTmp;
    /**
     *
     */
    private Integer regnum;

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

    /**
     *
     */
    @TableField(exist = false)
    private String channelName;

    private Integer appOpenNum;

    private Integer clickdownnum;

    @TableField(exist = false)
    private Integer clickdownnumTmp;

    private Integer uvNum;

    @TableField(exist = false)
    private Integer uvNumTmp;


}
