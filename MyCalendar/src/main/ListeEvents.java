import event.Event;
import event.PeriodicEvent;
import event.PersonalMeeting;
import event.Reunion;
import type.Location;
import type.Owner;
import type.Participants;
import type.Title;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListeEvents {
    private List<Event> events;

    public ListeEvents() {
        this.events = new ArrayList<>();
    }

    public void ajouterEvent(Event e) {
        events.add(e);
    }

    public void afficherEvenements() {
        events.forEach(event -> System.out.println("[ ID : " + event.getId() + " ] - " + event.description()));
    }

    public List<Event> getEvents() {
        return events;
    }
} 