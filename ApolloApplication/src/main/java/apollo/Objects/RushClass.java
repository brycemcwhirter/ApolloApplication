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
<<<<<<< HEAD
=======
	
	public void addMember(PNM pnm) {
		this.members.add(pnm);
	}
	
	public void removePerson(String name) {
		Boolean found = false;
		int index = 0;
		while (!found && index != members.size()) {
			if (name == members.get(index).getName()) {
				found = true;
			}
			index++;
		}
		if (found) {
			members.remove(index);
		}
	}

>>>>>>> 2020ff3a07f4380f047a8a1225ad65b37f5ad8fc
}
