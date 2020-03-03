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
 * @date 2019-04-01 12:08:41
 */
@Data
@TableName("app_switch")
public class AppSwitchEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 应用包名
	 */
	private String appPackageName;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 是否开启0：关闭，1：开启
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

}
