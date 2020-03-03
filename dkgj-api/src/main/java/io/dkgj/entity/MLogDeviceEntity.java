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
 * @date 2019-04-08 15:09:22
 */
@Data
@TableName("m_log_device")
public class MLogDeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer appid;
	/**
	 * 
	 */
	private String uuid;
	/**
	 * 
	 */
	private String ua;
	/**
	 * 
	 */
	private Date createdat;
	/**
	 * 
	 */
	private Date deletedat;

}
