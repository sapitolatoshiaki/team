package jp.co.internous.framepj.model.domain;

import jp.co.internous.framepj.model.form.DestinationForm;

/**
 * mst_destinationのドメイン
 * @author インターノウス
 *
 */
public class MstDestination {
	
	private int id;
	private int userId;
	private String familyName;
	private String firstName;
	private String telNumber;
	private String address;
	
	public MstDestination(){
	}
	
	public MstDestination(DestinationForm f) {
		this.familyName = f.getFamilyName();
		this.firstName = f.getFirstName();
		this.address = f.getAddress();
		this.telNumber = f.getTelNumber();
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
