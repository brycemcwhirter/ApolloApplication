package apollo.Objects;

import java.util.Date;

public class Event {

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Event(String n, Date d, String l) {

        this.name = n;
        this.date = d;
        this.location = l;

    }

    String name;
    Date date;
    String location;

}
