package event;

import event.Event;
import type.Owner;
import type.Title;

import java.time.LocalDateTime;

public class PersonalMeeting extends Event {
    public PersonalMeeting(Title title, Owner proprietaire, LocalDateTime dateDebut, Integer dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }

    @Override
    public String description() {
        return "RDV : " + title.getValue() + " à " + dateDebut.toString();
    }
}


