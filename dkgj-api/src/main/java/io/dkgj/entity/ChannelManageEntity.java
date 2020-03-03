package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 15:16:27
 */
@Data
@TableName("channel_manage")
public class ChannelManageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String channelurl;
	/**
	 * 
	 */
	private String channelcode;
	/**
	 * 
	 */
	private String channelname;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private String remark;
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
	private String checkregisterurl;
	/**
	 * 是否扣量，0不扣量，1扣量
	 */
	private Integer deductstatus;
	/**
	 * 基数
	 */
	private Integer deductbase;
	/**
	 * 比例
	 */
	private BigDecimal deductratio;
	/**
	 * 
	 */
	private Date deductdate;

}
