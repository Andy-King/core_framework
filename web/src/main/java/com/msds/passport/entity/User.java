package com.msds.passport.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Entity;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity(dynamicUpdate = true)
@javax.persistence.Entity
@Table(name = "P_USER")
public class  User implements java.io.Serializable {
	
	// Fields
	private static final long serialVersionUID = 4812906331674016367L;
	/* 用户ID */
	private Long id;
	/* 用户名*/
	private String userName;
	/* email */
	private String email;
	/* MD5加密的密*/
	private String password;
	/* 性别 */
	private String sex;
	/* 身份证号*/
	private String personId;
	/* 注册时间 */
	private Date createdAt;
	/* not use */
	private String createdBy;
	/* 更新时间 */
	private Date changedAt;
	/* not use */
	private String changedBy;
	
	private Integer status;
	
	private Long version;
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator = "BeanIdGenerator")  
	//@GenericGenerator(name = "BeanIdGenerator", strategy = "org.jee.framework.core.dao.orm.id.generator.BeanIdGenerator",   
	//	parameters = {@Parameter(name = "datasourceName", value = "dataSourceProxy"), 
	//@Parameter(name = "sequenceName", value = "SEQ_P_USER_ID") })
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
    @Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
}