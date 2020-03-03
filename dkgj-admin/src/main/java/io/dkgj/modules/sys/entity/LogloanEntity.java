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
 * @date 2019-03-27 10:12:15
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
    /**
     *
     */
    private Integer uv;

    private Integer todayPv;

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

    @TableField(exist = false)
    private String loanName;

    @TableField(exist = false)
    private String loanPrice;

}
