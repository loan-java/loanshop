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
 * @date 2019-06-06 16:12:44
 */
@Data
@TableName("log_visit_source")
public class LogVisitSourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String mobile;
	/**
	 * 
	 */
	private String channel;
	/**
	 * 
	 */
	private String appName;
	/**
	 * 
	 */
	private String appVersion;
	/**
	 * 
	 */
	private String appCodeName;
	/**
	 * 
	 */
	private String userAgent;
	/**
	 * 
	 */
	private String referrer;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
