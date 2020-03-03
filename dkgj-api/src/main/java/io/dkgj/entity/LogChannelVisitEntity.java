package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-24 21:28:45
 */
@Data
@TableName("log_channel_visit")
public class LogChannelVisitEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private String channel;
    /**
     *
     */
    private String ip;
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

}
