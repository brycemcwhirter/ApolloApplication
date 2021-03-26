package apollo.Objects;

import java.util.Date;

public class Event {

    Event(String n, Date d, String l) {

        this.name = n;
        this.date = d;
        this.location = l;

    }

    String name;
    Date date;
    String location;

}
