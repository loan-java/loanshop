package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("loan")
public class LoanEntity implements Serializable {
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
	private Integer state;
	/**
	 * 
	 */
	private String tags;
	/**
	 * 
	 */
	private Integer maxloan;

	private Integer minloan;
	/**
	 * 
	 */
	private String raterange;
	/**
	 * 
	 */
	private String periodrange;
	/**
	 * 
	 */
	private String applyurl;
	/**
	 * 
	 */
	private Integer applynum;
	/**
	 * 
	 */
	private String badge;
	/**
	 * 
	 */
	private String applyurl2;
	/**
	 * 
	 */
	private String intro;
	/**
	 * 
	 */
	private String applyinfo;
	/**
	 * 
	 */
	private String logo;
	/**
	 * 
	 */
	private Integer byuid;
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
	/**
	 * 
	 */
	private Integer weights;

	private Integer type;

	@TableField(exist = false)
	private String maxuv;

}
