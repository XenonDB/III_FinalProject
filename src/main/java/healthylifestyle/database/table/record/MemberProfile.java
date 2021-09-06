package healthylifestyle.database.table.record;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.Hibernate;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.TableMember;
import healthylifestyle.utils.BloodTypeABO;
import healthylifestyle.utils.Gender;
import healthylifestyle.utils.Language;
import healthylifestyle.utils.TagsAndPatterns;
import healthylifestyle.utils.exception.DeserializeException;
import healthylifestyle.utils.json.IJsonSerializable;

@Entity
@Table(name = TableMember.NAME)
public class MemberProfile implements IUniquidKeyData<String>, IJsonSerializable<MemberProfile.MemberProfileJson>, IHibernateInitializable {
	
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
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "hashedPassword")
	private String hashedPassword;
	
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
	
	
	@Column(name = "height")
	private Float height;
	
	@Column(name = "[weight]")
	private Float weight;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "[location]")
	private String location;	
	
	@ElementCollection
	@CollectionTable(name="AvailableLanguage", joinColumns=@JoinColumn(name="[user]"))
	@Column(name="[language]")
	private Set<String> availableLangs = new HashSet<>();
	
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
		return Optional.ofNullable(this.hashedPassword);
	}
	
	public MemberProfile setHashedPassword(String pass) {
		this.hashedPassword = pass;
		return this;
	}

	
	public Optional<String> getFirstName() {
		return Optional.ofNullable(this.firstName);
	}

	public MemberProfile setFirstName(String name) {
		this.firstName = name;
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
		this.gender = (gender == null ? null : gender.name());
		return this;
	}
	
	public MemberProfile setGender(String gender) {
		this.setGender(gender == null ? null : Gender.valueOf(gender));
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
		this.bloodtypeABO = (bloodtypeABO == null ? null : bloodtypeABO.name());
		return this;
	}
	
	public MemberProfile setBloodtypeABO(String bloodtypeABO) {
		this.setBloodtypeABO(bloodtypeABO == null ? null : BloodTypeABO.valueOf(bloodtypeABO));
		return this;
	}

	public Optional<Date> getBirthday() {
		return Optional.ofNullable(this.birthday);
	}

	public MemberProfile setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}
	
	public MemberProfile setBirthday(String birthday){
		try {
			this.birthday = TagsAndPatterns.DATE_FORMAT.parse(birthday);
			return this;
		} catch (ParseException e) {
			throw new DeserializeException(e);
		}
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

	public float getHeight() {
		return this.height == null ? 0 : this.height.floatValue();
	}

	public MemberProfile setHeight(Float height) {
		this.height = height;
		return this;
	}

	public float getWeight() {
		return this.weight == null ? 0 : this.weight.floatValue();
	}

	public MemberProfile setWeight(Float weight) {
		this.weight = weight;
		return this;
	}

	public Optional<String> getCity() {
		return Optional.ofNullable(city);
	}

	public MemberProfile setCity(String city) {
		this.city = city;
		return this;
	}

	public Optional<String> getLocation() {
		return Optional.ofNullable(location);
	}

	public MemberProfile setLocation(String location) {
		this.location = location;
		return this;
	}

	public Optional<String> getLastName() {
		return Optional.ofNullable(lastName);
	}

	public MemberProfile setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public Set<Language> getAvailableLangs() {
		Set<Language> result = new HashSet<>();
		
		Iterator<String> iter = this.availableLangs.iterator();
		String tmp;
		while(iter.hasNext()) {
			tmp = iter.next();
			try {
				result.add(Language.valueOf(tmp));
			}catch(Exception e) {
				iter.remove();
			}
		}
		
		return result;
	}

	public void setAvailableLangs(Set<String> avaLangs) {
		this.availableLangs = avaLangs == null ? new HashSet<>() : avaLangs;
		
		//檢查其中的內容是不是都符合列舉類別裡的項目
		Iterator<String> iter = this.availableLangs.iterator();
		String tmp;
		while(iter.hasNext()) {
			tmp = iter.next();
			try {
				Language.valueOf(tmp);
			}catch(Exception e) {
				iter.remove();
			}
		}
		
	}
	
	public void addAvailableLang(Language l) {
		if(l == null) return;
		
		this.availableLangs.add(l.name());
	}

	public void removeAvailableLang(Language l) {
		if(l == null) return;
		
		this.availableLangs.remove(l.name());
	}
	
	public void clearAvailableLang() {
		this.availableLangs.clear();
	}
	
	@Override
	public void initialize() {
		IHibernateInitializable.super.initialize();
		Hibernate.initialize(this.availableLangs);
		
		//不管是使用iterator或是remove直接移除特定元素，就算是在交易期間，好像也不會一併更新set的內容。可能當時不在set的交易時間內?
		//this.availableLangs.remove("ja_jpp");
		this.setAvailableLangs(availableLangs);//觸發一次內容檢查用。
	}
	
	@Override
	public Class<MemberProfileJson> getProxyClass() {
		return MemberProfileJson.class;
	}

	@Override
	public void constructWithProxy(MemberProfileJson target) {
		if(target == null) return;
		
		this.setUser(target.getUser()).
		setMail(target.getEmail()).
		setLastName(target.getLastName()).
		setFirstName(target.getFirstName()).
		setGender(target.getGender()).
		setBloodtypeABO(target.getBloodtypeABO()).
		setBirthday(target.getBirthday()).
		setPhone(target.getPhone()).
		setPhoto(target.getPhoto()).
		setHeight(target.getHeight()).
		setWeight(target.getWeight()).
		setCity(target.getCity()).
		setLocation(target.getLocation()).
		setAvailableLangs(target.getAvailableLangs());
		
	}
	
	public static class MemberProfileJson implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2548959694922551187L;

		private String user;
		
		private String email;
		
		private String lastName;
		
		private String firstName;
		
		private String gender;
		
		private String bloodtypeABO;
		
		private String birthday;
		
		private String phone;
		
		private byte[] photo;

		
		private float height;
		
		private float weight;
		
		private String city;
		
		private String location;	
		
		private Set<String> availableLangs;
		
		public MemberProfileJson() {}
		
		public MemberProfileJson(MemberProfile p){
			setUser(p.user);
			setEmail(p.email);
			setLastName(p.lastName);
			setFirstName(p.firstName);
			setGender(p.gender);
			setBloodtypeABO(p.bloodtypeABO);
			setBirthday(p.birthday);
			setPhone(p.phone);
			setPhoto(p.photo);
			setHeight(p.getHeight());
			setWeight(p.getWeight());
			setCity(p.city);
			setLocation(p.location);
			setAvailableLangs(p.availableLangs);
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

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
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

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday == null ? null : TagsAndPatterns.DATE_FORMAT.format(birthday);
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


		public float getHeight() {
			return height;
		}


		public void setHeight(float height) {
			this.height = height;
		}


		public float getWeight() {
			return weight;
		}


		public void setWeight(float weight) {
			this.weight = weight;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}


		public String getLocation() {
			return location;
		}


		public void setLocation(String location) {
			this.location = location;
		}


		public String getLastName() {
			return lastName;
		}


		public void setLastName(String lastName) {
			this.lastName = lastName;
		}


		public Set<String> getAvailableLangs() {
			return availableLangs;
		}


		public void setAvailableLangs(Set<String> list) {
			this.availableLangs = list;
		}
		
	}
	
}
