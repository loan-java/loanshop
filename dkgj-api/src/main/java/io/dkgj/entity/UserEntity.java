package io.dkgj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 16:45:11
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String mobile;
    /**
     *
     */
    private String vcode;
    /**
     *
     */
    private Long vtime;
    /**
     *
     */
    private String psw;
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String avatarUrl;
    /**
     *
     */
    private String wxId;
    /**
     *
     */
    private Integer state;

    private String ip;

    private String lastLoginIp;

    private Date lastLoginTime;

    private Integer lastLoginOs;
    /**
     *
     */
    private Date createdAt;
    /**
     *
     */
    private Date updatedat;
    /**
     *
     */
    private Date deletedat;

}
