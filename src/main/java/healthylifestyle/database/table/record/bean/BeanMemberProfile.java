package healthylifestyle.database.table.record.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IJavaBean;

@Table(name = "HealthyLifestyle")
public class BeanMemberProfile implements IJavaBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	
	private String name;
	private String mail;
	private String passworld_SHA256;
	
	private String gender;
	private String bloodtypeABO;
	
	private Date birthday;
	
	private String language;
	
	private String phone;
	
	private String socialMediaAccounts;
	
	private Byte[] photo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassworld_SHA256() {
		return passworld_SHA256;
	}

	public void setPassworld_SHA256(String passworld_SHA256) {
		this.passworld_SHA256 = passworld_SHA256;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodtypeABO() {
		return bloodtypeABO;
	}

	public void setBloodtypeABO(String bloodtypeABO) {
		this.bloodtypeABO = bloodtypeABO;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSocialMediaAccounts() {
		return socialMediaAccounts;
	}

	public void setSocialMediaAccounts(String socialMediaAccounts) {
		this.socialMediaAccounts = socialMediaAccounts;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}
	
	
}
