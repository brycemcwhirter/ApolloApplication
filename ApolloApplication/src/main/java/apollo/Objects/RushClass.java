package apollo.Objects;

import apollo.Enum.Semester;
import apollo.Enum.Tier;

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
	public void addMember(PNM pnm) {
		this.members.add(pnm);
	}
	
	public void removePerson(String name) {
		int index = findPerson(name);
		if (index != -1) {
			members.remove(index);
		}
	}
	
	public void editTier(String name, Tier t) {
		int index = findPerson(name);
		if (index != -1) {
			members.get(index).setT(t);
		}
	}
	
	public int findPerson(String name) {
		Boolean found = false;
		int index = 0;
		while (!found && index != members.size()) {
			if (name == members.get(index).getName()) {
				found = true;
			} else {
				index++;
			}
		}
		if (!found) {
			index = -1;
		}
		return index;
	}

}
