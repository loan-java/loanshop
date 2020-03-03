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
 * @date 2019-06-19 14:35:49
 */
@Data
@TableName("log_aso")
public class LogAsoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String idfa;
	/**
	 * 
	 */
	private String appid;
	/**
	 * 
	 */
	private String ip;
	/**
	 * 
	 */
	private String device;
	/**
	 * 
	 */
	private String os;
	/**
	 * 
	 */
	private String keyword;
	/**
	 * 
	 */
	private String channel;

	private Integer status;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
