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
 * @date 2019-06-05 19:04:34
 */
@Data
@TableName("popup")
public class PopupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 0:IOS,1:android,2:h5
	 */
	private Integer os;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String imgPath;
	/**
	 * 0:在线，1：离线
	 */
	private Integer state;
	/**
	 * 渠道编码
	 */
	private String channel;
	/**
	 * 产品ID
	 */
	private Long loanId;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
