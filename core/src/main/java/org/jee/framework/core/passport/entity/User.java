package org.jee.framework.core.passport.entity;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
	/* 用户登录�?*/
	private String login;
	/* 用户姓名 */
	private String realName;
	/* 国家 */
	private String country;
	/* �?*/
	private String province;
	/* �?*/
	private String city;
	/* 市以下的地址 */
	private String address;
	/* 邮编 */
	private String zipcode;
	/* 家庭电话 */
	private String homePhone;
	/* 移动电话 */
	private String mobilePhoe;
	/* email */
	private String email;
	/* MD5加密的密�?*/
	private String password;

	/* 忘记密码问题 */
	private String question;
	/* 忘记密码答案 */
	private String answer;
	/* 性别 */
	private String sex;
	/* 身份证号�?*/
	private String personId;
	/* 用户类型 */
	private Integer userType;
	/* 总的购买次数 */
	private Long totalBuy;
	/* 总购买金�?*/
	private Long totalMoney;
	/* 可消费积�?*/
	private Long totalPoint;
	
	/* 冻结积分 */
	private Long freezePoint;
	
	/* 是否经过Email认证 */
	private Integer checked;
	/* �?��登陆时间 */
	private Date lastLogin;
	/* 以前的积�?*/
	private Long oldPoint;
	/* 注册时间 */
	private Date createdAt;
	/* not use */
	private String createdBy;
	/* �?��更新时间 */
	private Date changedAt;
	/* not use */
	private String changedBy;
	/* 会员等级*/
	private Long pLevelId;
	/* 累计积分*/
	private Long addupPoint;
	/* 是否终身蜂后*/
	private Long isLeftMistress;
	
	private Long illegalCount;
	
	private String nickName;
	private Date birthDate;
	private String jobTitle;
	private Integer merry;
	private String educational;
	private String income; 
	private Integer status;
	
	private Date initPointDate;
	
	private Long mailType;
	
	private Long TODAY_NUM;
	private String LAST_JOIN; 
	private Long LAST_FREEZE_POINT;//上一次该用户增加的冻结积分的�?
	private String introduce;

    private Long activePoint;
    private Long isusedExperience;
    
    private Long confirmed;


    private String fans;
    @Id
	@SequenceGenerator(name = "SEQ_P_USER_ID", sequenceName = "SEQ_P_USER_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_P_USER_ID")
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
    
    public Long getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Long confirmed) {
		this.confirmed = confirmed;
	}


	private Integer emailValid;

    public Integer getEmailValid() {
        return emailValid;
    }

    public void setEmailValid(Integer emailValid) {
        this.emailValid = emailValid;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    private Long version;
    
    @Version
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	private String checkedEmail;//邮箱注册已验证邮�?
	
	public String getCheckedEmail() {
		return checkedEmail;
	}

	public void setCheckedEmail(String checkedEmail) {
		this.checkedEmail = checkedEmail;
	}
	
	private String checkedPhone;//手机注册已验证手�?
	
	public String getCheckedPhone() {
		return checkedPhone;
	}

	public void setCheckedPhone(String checkedPhone) {
		this.checkedPhone = checkedPhone;
	}
	
	// Constructors

	/** default constructor */
	public User() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		this.setCreatedAt(date);
	}

	// Property accessors

	

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhoe() {
		return this.mobilePhoe;
	}

	public void setMobilePhoe(String mobilePhoe) {
		this.mobilePhoe = mobilePhoe;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public synchronized Integer getUserType() {
		if(userType == null){
			userType = 0;
		}
		return this.userType;
	}

	public synchronized void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getTotalBuy() {
		return this.totalBuy;
	}

	public void setTotalBuy(Long totalBuy) {
		this.totalBuy = totalBuy;
	}

	public Long getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Long getTotalPoint() {
		if (totalPoint == null) {
			return 0l;
		} else {
    		return this.totalPoint;
		}
	}

	public void setTotalPoint(Long totalPoint) {
		this.totalPoint = totalPoint;
	}

	public Integer getChecked() {
		return this.checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getOldPoint() {
		return this.oldPoint;
	}

	public void setOldPoint(Long oldPoint) {
		this.oldPoint = oldPoint;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getChangedAt() {
		return this.changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public String getChangedBy() {
		return this.changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getMerry() {
		return merry;
	}

	public void setMerry(Integer merry) {
		this.merry = merry;
	}

	public String getEducational() {
		return educational;
	}

	public void setEducational(String educational) {
		this.educational = educational;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	public Date getInitPointDate() {
		return initPointDate;
	}

	public void setInitPointDate(Date initPointDate) {
		this.initPointDate = initPointDate;
	}

	public Long getFreezePoint() {
		if (freezePoint == null) {
			return 0l;
		} else {
    		return freezePoint;
		}
	}

	public void setFreezePoint(Long freezePoint) {
		this.freezePoint = freezePoint;
	}

	public Long getMailType() {
		return mailType;
	}

	public void setMailType(Long mailType) {
		this.mailType = mailType;
	}

	public Long getTODAY_NUM() {
		return TODAY_NUM;
	}

	public void setTODAY_NUM(Long today_num) {
		TODAY_NUM = today_num;
	}

	public String getLAST_JOIN() {
		return LAST_JOIN;
	}

	public void setLAST_JOIN(String last_join) {
		LAST_JOIN = last_join;
	}
	
	@Column(name = "LAST_FREEZE_POINT")
	public synchronized Long getLAST_FREEZE_POINT() {
		return LAST_FREEZE_POINT;
	}

	public synchronized void setLAST_FREEZE_POINT(Long last_freeze_point) {
		LAST_FREEZE_POINT = last_freeze_point;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
    @Column(name = "ACTIVE_POINT")
    public Long getActivePoint() {
        return activePoint;
    }

    public void setActivePoint(Long activePoint) {
        this.activePoint = activePoint;
    }

	public Long getIsusedExperience() {
		return isusedExperience;
	}

	public void setIsusedExperience(Long isusedExperience) {
		this.isusedExperience = isusedExperience;
	}

	public Long getPLevelId() {
		return pLevelId;
	}

	public void setPLevelId(Long levelId) {
		pLevelId = levelId;
	}

	public Long getAddupPoint() {
		return addupPoint;
	}

	public void setAddupPoint(Long addupPoint) {
		this.addupPoint = addupPoint;
	}

	public Long getIsLeftMistress() {
		return isLeftMistress;
	}

	public void setIsLeftMistress(Long isLeftMistress) {
		this.isLeftMistress = isLeftMistress;
	}

	public Long getIllegalCount() {
		return illegalCount;
	}

	public void setIllegalCount(Long illegalCount) {
		this.illegalCount = illegalCount;
	}
	
}