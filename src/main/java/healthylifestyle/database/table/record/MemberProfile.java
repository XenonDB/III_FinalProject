package healthylifestyle.database.table.record;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IJavaBean;
import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.utils.BloodTypeABO;
import healthylifestyle.utils.Gender;

@Entity
@Table(name = "Member")
public class MemberProfile implements IUniquidKeyData<String>, IJavaBean {

	/*
	private Optional<Integer> id = Optional.empty();
	
	private Optional<String> name = Optional.empty();
	private Optional<String> mail = Optional.empty();
	private Optional<String> passworld_SHA256 = Optional.empty();
	
	private Optional<Gender> gender = Optional.empty();
	private Optional<BloodTypeABO> bloodtypeABO = Optional.empty();
	
	private Optional<Date> birthday = Optional.empty();
	
	private final HashSet<Language> language = new HashSet<Language>();
	
	private Optional<String> phone = Optional.empty();
	
	private HashMap<OtherSocialMedia,String> socialMediaAccounts = new HashMap<>();
	
	private Optional<Byte[]> photo = Optional.empty();
	
	*/
	
	@Column(name = "name")
	private String name;
	
	@Id
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "password_SHA256")
	private String password_SHA256;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "bloodtypeABO")
	private String bloodtypeABO;
	
	@Column(name = "birthday")
	private Date birthday;
	/*
	@Column(name = "language")
	private String language;
	*/
	@Column(name = "phone")
	private String phone;
	/*
	@Column(name = "socialMediaAccounts")
	private String socialMediaAccounts;
	*/
	@Column(name = "photo")
	private Byte[] photo;
	
	
	public MemberProfile() {}
	
	public MemberProfile(String mail, String password) {
		this();
		this.setMail(mail);
		this.setHashedPassword(password);
	}
	
	
	@Override
	public boolean canInsertIntoTable() {
		return !getMail().isEmpty() && !getHashedPassword().isEmpty();
	}
	
	
	/**
	 * 等同於getMail()
	 * */
	@Override
	public Optional<String> getUniquidKey() {
		return this.getMail();
	}
	
	/**
	 * 等同於getMail(), getUniquidKey()
	 * */
	public Optional<String> getUser(){
		return this.getUniquidKey();
	}
	
	public Optional<String> getHashedPassword(){
		return Optional.ofNullable(this.password_SHA256);
	}
	
	public void setHashedPassword(String pass) {
		this.password_SHA256 = pass;
	}

	public Optional<String> getName() {
		return Optional.ofNullable(this.name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Optional<String> getMail() {
		return Optional.ofNullable(this.mail);
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Optional<Gender> getGender() {
		
		Gender g = null;
		try {
			g = Gender.valueOf(this.gender);
		}catch(Exception e) {}
		
		return Optional.ofNullable(g);
	}

	public void setGender(Gender gender) {
		this.gender = (gender == null ? "null" : gender.name());
	}

	public Optional<BloodTypeABO> getBloodtypeABO() {
		BloodTypeABO b = null;
		try {
			b = BloodTypeABO.valueOf(this.bloodtypeABO);
		}catch(Exception e) {}
		
		return Optional.ofNullable(b);
	}

	public void setBloodtypeABO(BloodTypeABO bloodtypeABO) {
		this.bloodtypeABO = (bloodtypeABO == null ? "null" : bloodtypeABO.name());
	}

	public Optional<Date> getBirthday() {
		return Optional.ofNullable(this.birthday);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	/*
	 * TODO:未實作，等之後有時間再處理
	@SuppressWarnings("unchecked")
	public HashSet<Language> getLanguage() {
		return (HashSet<Language>) language.clone();
	}

	public boolean addLanguage(Language language) {
		return this.language.add(language);
	}

	public boolean removeLanguage(Language language) {
		return this.language.remove(language);
	}
	*/

	public Optional<String> getPhone() {
		return Optional.ofNullable(this.phone);
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/*
	@SuppressWarnings("unchecked")
	public HashMap<OtherSocialMedia,String> getSocialMediaAccounts() {
		return (HashMap<OtherSocialMedia, String>) socialMediaAccounts.clone();
	}

	public void setSocialMediaAccounts(OtherSocialMedia mediaType, String account) {
		this.socialMediaAccounts.put(mediaType, account);
	}
	*/

	public Optional<Byte[]> getPhoto() {
		return Optional.ofNullable(this.photo);
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}



	
}
