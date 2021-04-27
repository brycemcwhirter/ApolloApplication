package apollo.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import apollo.Enum.Tier;

public class PNM {

	String name;
	String hometown;
	String email;
	String major;
	boolean legacy;
	int age;
	String phoneNumber;
	Tier t;
	List<String> vouchList;
	List<Event> eventList;

	static String columnNames[] = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };

	public PNM(String name, String hometown, String email, String major, boolean legacy, int age, String phoneNumber,
			Tier temp) {
		this.name = name;
		this.hometown = hometown;
		this.email = email;
		this.major = major;
		this.legacy = legacy;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.t = temp;
		this.vouchList = new ArrayList<String>();
		this.eventList = new ArrayList<Event>();
	}

	public static String[] getColumnNames() {
		return columnNames;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public boolean isLegacy() {
		return this.legacy;
	}

	public boolean getLegacy() {
		return this.legacy;
	}

	public void setLegacy(boolean legacy) {
		this.legacy = legacy;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Tier getT() {
		return this.t;
	}

	public void setT(Tier t) {
		this.t = t;
	}

	public List<String> getVouchList() {
		return this.vouchList;
	}

	public void setVouchList(List<String> vouchList) {
		this.vouchList = vouchList;
	}

	public List<Event> getEventList() {
		return this.eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof PNM)) {
			return false;
		}
		PNM pNM = (PNM) o;
		return Objects.equals(name, pNM.name) && Objects.equals(hometown, pNM.hometown)
				&& Objects.equals(email, pNM.email) && Objects.equals(major, pNM.major) && legacy == pNM.legacy
				&& age == pNM.age && Objects.equals(phoneNumber, pNM.phoneNumber) && Objects.equals(t, pNM.t)
				&& Objects.equals(vouchList, pNM.vouchList) && Objects.equals(eventList, pNM.eventList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, hometown, email, major, legacy, age, phoneNumber, t, vouchList, eventList);
	}

}
