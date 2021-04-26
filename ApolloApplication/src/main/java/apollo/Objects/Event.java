package apollo.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public List<PNM> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<PNM> attendees) {
		this.attendees = attendees;
	}
	
	public Event(String n, Date d, String l) {

        this.name = n;
        this.date = d;
        this.location = l;
        this.attendees = new ArrayList<PNM>();

    }

    String name;
    Date date;
    String location;
    List<PNM> attendees = new ArrayList<PNM>();
	

}
