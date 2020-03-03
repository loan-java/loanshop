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
@TableName("logloan")
public class LogloanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer relid;
	/**
	 * 
	 */
	private String refer;
	/**
	 * 
	 */
	private Integer pv;
	private Integer todayPv;
	/**
	 * 
	 */
	private Integer uv;

	private Integer todayUv;
	/**
	 * 
	 */
	private String note;
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
