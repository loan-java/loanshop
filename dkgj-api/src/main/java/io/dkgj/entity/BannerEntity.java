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
 * @date 2019-04-08 10:59:05
 */
@Data
@TableName("banner")
public class BannerEntity implements Serializable {
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
	private String gourl;
	/**
	 * 
	 */
	private String intro;
	/**
	 * 
	 */
	private Integer seq;
	/**
	 * 
	 */
	private Integer byuid;
	/**
	 * 
	 */
	private Integer pos;
	/**
	 * 
	 */
	private Integer state;

	private Long loanId;

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
