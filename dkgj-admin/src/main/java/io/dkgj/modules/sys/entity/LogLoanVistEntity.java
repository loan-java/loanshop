package io.dkgj.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 15:21:07
 */
@Data
@TableName("log_loan_vist")
public class LogLoanVistEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long loanId;

    @TableField(exist = false)
    private String loanName;
    /**
     *
     */
    private Long userId;
    @TableField(exist = false)
    private String mobile;
    /**
     *
     */
    private String ip;
    /**
     *
     */
    private String channel;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

}
