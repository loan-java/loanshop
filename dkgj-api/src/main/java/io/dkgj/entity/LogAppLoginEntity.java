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
 * @date 2019-06-12 14:19:52
 */
@Data
@TableName("log_app_login")
public class LogAppLoginEntity implements Serializable {
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
	private Long userId;
	/**
	 *
	 */
	private String type;
	/**
	 *
	 */
	private String mac;
	/**
	 *
	 */
	private Date createdat;
	/**
	 *
	 */
	private Date updatedat;

}
