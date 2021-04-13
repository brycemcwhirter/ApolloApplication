package apollo.Objects;

import apollo.Enum.Semester;

import java.util.List;

public class RushClass {
	int year;
    Semester s;
    List<PNM> members;

    public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Semester getS() {
		return s;
	}
	public void setS(Semester s) {
		this.s = s;
	}
	public List<PNM> getMembers() {
		return members;
	}
	public void setMembers(List<PNM> members) {
		this.members = members;
	}

}
