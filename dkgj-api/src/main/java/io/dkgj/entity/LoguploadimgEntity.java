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
@TableName("loguploadimg")
public class LoguploadimgEntity implements Serializable {
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
	private String imgurl;
	/**
	 * 
	 */
	private String dest;
	/**
	 * 
	 */
	private String originalname;
	/**
	 * 
	 */
	private Integer width;
	/**
	 * 
	 */
	private Integer height;
	/**
	 * 
	 */
	private Integer size;
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
