package healthylifestyle.database.table.record;

import java.util.Date;
import java.util.Optional;

import healthylifestyle.database.dbinterface.record.IUniquidKeyData;
import healthylifestyle.database.table.record.bean.BeanMemberProfile;
import healthylifestyle.utils.BloodTypeABO;
import healthylifestyle.utils.Gender;

public class MemberProfile implements IUniquidKeyData<Integer> {

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
	
	private final BeanMemberProfile bean;
	
	
	public MemberProfile() {
		this.bean = new BeanMemberProfile();
	}
	
	public MemberProfile(BeanMemberProfile b) {
		this.bean = b == null ? new BeanMemberProfile() : b;
	}
	
	public MemberProfile(String mail, String password) {
		this();
		this.setMail(mail);
		this.setHashedPassword(password);
	}
	
	
	@Override
	public boolean canInsertIntoTable() {
		return this.bean.getMail() != null && this.bean.getPassworld_SHA256() != null;
	}
	
	
	@Override
	public Optional<Integer> getUniquidKey() {
		return Optional.ofNullable(bean.getId());
	}

	public void setId(int i) {
		this.bean.setId(i);
	}
	
	public Optional<String> getHashedPassword(){
		return Optional.ofNullable(bean.getPassworld_SHA256());
	}
	
	public void setHashedPassword(String pass) {
		this.bean.setPassworld_SHA256(pass);
	}

	public Optional<String> getName() {
		return Optional.ofNullable(bean.getName());
	}

	public void setName(String name) {
		this.bean.setName(name);
	}
	
	public Optional<String> getMail() {
		return Optional.ofNullable(bean.getMail());
	}

	public void setMail(String mail) {
		this.bean.setMail(mail);
	}

	public Optional<Gender> getGender() {
		
		Gender g = null;
		try {
			g = Gender.valueOf(bean.getGender());
		}catch(Exception e) {}
		
		return Optional.ofNullable(g);
	}

	public void setGender(Gender gender) {
		this.bean.setGender(gender == null ? "null" : gender.name());
	}

	public Optional<BloodTypeABO> getBloodtypeABO() {
		BloodTypeABO b = null;
		try {
			b = BloodTypeABO.valueOf(bean.getBloodtypeABO());
		}catch(Exception e) {}
		
		return Optional.ofNullable(b);
	}

	public void setBloodtypeABO(BloodTypeABO bloodtypeABO) {
		this.bean.setBloodtypeABO(bloodtypeABO == null ? "null" : bloodtypeABO.name());
	}

	public Optional<Date> getBirthday() {
		return Optional.ofNullable(this.bean.getBirthday());
	}

	public void setBirthday(Date birthday) {
		this.bean.setBirthday(birthday);
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
		return Optional.ofNullable(this.bean.getPhone());
	}

	public void setPhone(String phone) {
		this.bean.setPhone(phone);
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
		return Optional.ofNullable(this.bean.getPhoto());
	}

	public void setPhoto(Byte[] photo) {
		this.bean.setPhoto(photo);
	}



	
}
