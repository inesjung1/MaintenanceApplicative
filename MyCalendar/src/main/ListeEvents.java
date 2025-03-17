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

    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                             String lieu, String participants, int frequenceJours) {
        Event e = switch (type) {
            case "RDV_PERSONNEL" -> new PersonalMeeting(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes);
            case "REUNION" -> new Reunion(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes, new Location(lieu), new Participants(participants));
            case "PERIODIQUE" -> new PeriodicEvent(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes, frequenceJours);
            default -> throw new IllegalArgumentException("Type d'événement inconnu : " + type);
        };
        events.add(e);
    }

    public void afficherEvenements() {
        events.forEach(event -> System.out.println(event.description()));
    }
} 