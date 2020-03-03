package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-12 19:37:31
 */
@Data
@TableName("log_channel_today_vist")
public class LogChannelTodayVistEntity implements Serializable {
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
	private Integer pv;
	/**
	 *
	 */
	private Integer uv;
	/**
	 *
	 */
	private Date createDate;
	/**
	 *
	 */
	private Date updateDate;

}
