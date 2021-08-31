package healthylifestyle.database.table.record;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.utils.BloodTypeABO;
import healthylifestyle.utils.Gender;
import healthylifestyle.utils.IJsonSerializable;

@Entity
@Table(name = "Member")
public class MemberProfile implements IUniquidKeyData<String>, IJsonSerializable {

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
	
	@Id
	@Column(name = "[user]", nullable = false)
	private String user;
	
	@Column(name = "mail")
	private String email;
	
	@Column(name = "nickName")
	private String nickName;
	
	@Column(name = "password_SHA256")
	private String password_SHA256;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "bloodtypeABO")
	private String bloodtypeABO;
	
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "photo")
	private byte[] photo;
	
	
	public MemberProfile() {}
	
	public MemberProfile(String user, String password) {
		this();
		this.setUser(user);
		this.setHashedPassword(password);
	}
	
	
	@Override
	public boolean canInsertIntoTable() {
		return !getUniquidKey().isEmpty() && !getHashedPassword().isEmpty();
	}
	
	@Override
	
	public Optional<String> getUniquidKey() {
		return this.getUser();
	}
	
	
	public Optional<String> getUser(){
		return Optional.ofNullable(this.user);
	}
	
	public MemberProfile setUser(String user) {
		this.user = user;
		return this;
	}
	
	
	public Optional<String> getHashedPassword(){
		return Optional.ofNullable(this.password_SHA256);
	}
	
	public MemberProfile setHashedPassword(String pass) {
		this.password_SHA256 = pass;
		return this;
	}

	
	public Optional<String> getNickName() {
		return Optional.ofNullable(this.nickName);
	}

	public MemberProfile setNickName(String name) {
		this.nickName = name;
		return this;
	}
	
	
	public Optional<String> getMail() {
		return Optional.ofNullable(this.email);
	}

	public MemberProfile setMail(String mail) {
		this.email = mail;
		return this;
	}

	
	public Optional<Gender> getGender() {
		
		Gender g = null;
		try {
			g = Gender.valueOf(this.gender);
		}catch(Exception e) {}
		
		return Optional.ofNullable(g);
	}

	public MemberProfile setGender(Gender gender) {
		this.gender = (gender == null ? "null" : gender.name());
		return this;
	}

	
	public Optional<BloodTypeABO> getBloodtypeABO() {
		BloodTypeABO b = null;
		try {
			b = BloodTypeABO.valueOf(this.bloodtypeABO);
		}catch(Exception e) {}
		
		return Optional.ofNullable(b);
	}

	public MemberProfile setBloodtypeABO(BloodTypeABO bloodtypeABO) {
		this.bloodtypeABO = (bloodtypeABO == null ? "null" : bloodtypeABO.name());
		return this;
	}

	
	public Optional<Date> getBirthday() {
		return Optional.ofNullable(this.birthday);
	}

	public MemberProfile setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
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

	public MemberProfile setPhone(String phone) {
		this.phone = phone;
		return this;
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

	
	public Optional<byte[]> getPhoto() {
		return Optional.ofNullable(this.photo);
	}

	public MemberProfile setPhoto(byte[] photo) {
		this.photo = photo;
		return this;
	}

	@Override
	public Object getObjectForJsonSerialize() {
		return new MemberProfileJson(this);
	}

	private static class MemberProfileJson{
		
		private String user;
		
		private String email;
		
		private String nickName;
		
		private String gender;
		
		private String bloodtypeABO;
		
		private Date birthday;
		
		private String phone;
		
		private byte[] photo;

		
		MemberProfileJson(MemberProfile p){
			setUser(p.user);
			setEmail(p.email);
			setNickName(p.nickName);
			setGender(p.gender);
			setBloodtypeABO(p.bloodtypeABO);
			setBirthday(p.birthday);
			setPhone(p.phone);
			setPhoto(p.photo);
		}
		
		
		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
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

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public byte[] getPhoto() {
			return photo;
		}

		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}
		
		
		
	}

	
	
}
