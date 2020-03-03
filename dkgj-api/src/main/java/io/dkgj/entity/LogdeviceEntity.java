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
 * @date 2019-04-08 14:47:08
 */
@Data
@TableName("logdevice")
public class LogdeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String uuid;
	/**
	 * 
	 */
	private String deviceToken;
	/**
	 * 
	 */
	private String bundleId;
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
