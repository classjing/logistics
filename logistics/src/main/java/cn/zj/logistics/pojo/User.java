package cn.zj.logistics.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable {
	private static final long serialVersionUID = 4694933517774428079L;

	private Long userId;

    private String username;

    private String realname;

    transient private String password;

    private String salt;

    private Integer status;
    
    //多表联合查询数据
    private String rolename;
    
    private String permissionIds;//1,5,8,10,12
    
    
    
    
    
    public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;

    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", realname=" + realname + ", password=" + password
				+ ", salt=" + salt + ", status=" + status + ", rolename=" + rolename + ", permissionIds="
				+ permissionIds + ", createDate=" + createDate + ", roleId=" + roleId + "]";
	}

    
}