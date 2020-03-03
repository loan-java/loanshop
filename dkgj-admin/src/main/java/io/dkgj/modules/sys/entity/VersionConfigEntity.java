package io.dkgj.modules.sys.entity;

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
 * @date 2019-07-01 17:48:42
 */
@Data
@TableName("tb_version_config")
public class VersionConfigEntity implements Serializable {
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
	 * 1:iod,2:android
	 */
	private Integer type;
	/**
	 * 
	 */
	private String version;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Integer isForce;
	/**
	 * 
	 */
	private String link;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
