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
@TableName("logroute")
public class LogrouteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String path;
	/**
	 * 
	 */
	private String prepath;
	/**
	 * 
	 */
	private String query;
	/**
	 * 
	 */
	private Integer deviceid;
	/**
	 * 
	 */
	private Integer uid;
	/**
	 * 
	 */
	private Integer seqid;
	/**
	 * 
	 */
	private Integer appid;
	/**
	 * 
	 */
	private Date createdat;
	/**
	 * 
	 */
	private Date deletedat;

}
