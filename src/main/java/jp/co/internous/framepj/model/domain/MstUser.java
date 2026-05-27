package jp.co.internous.framepj.model.domain;

import jp.co.internous.framepj.model.form.UserForm;

/**
 * mst_userのドメイン
 * @author インターノウス
 *
 */
public class MstUser {

	private int id;
	private String userName;
	private String password;
	private String familyName;
	private String firstName;
	private String familyNameKana;
	private String firstNameKana;
	private int gender;

	public MstUser() {
	}

	public MstUser(UserForm f) {
		this.userName = f.getUserName();
		this.password = f.getPassword();
		this.familyName = f.getFamilyName();
		this.firstName = f.getFirstName();
		this.familyNameKana = f.getFamilyNameKana();
		this.firstNameKana = f.getFirstNameKana();
		this.gender = f.getGender();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyNameKana() {
		return familyNameKana;
	}

	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}

	public String getFirstNameKana() {
		return firstNameKana;
	}

	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
