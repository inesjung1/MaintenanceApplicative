package event;

import event.Event;
import type.DateEvenement;
import type.Owner;
import type.Title;

import java.time.LocalDateTime;

public class PersonalMeeting extends Event {
    public PersonalMeeting(Title title, Owner proprietaire, DateEvenement dateDebut, Integer dureeMinutes) {
        super(title, proprietaire, dateDebut, dureeMinutes);
    }

    @Override
    public String description() {
        return "RDV : " + title.getValue() + " à " + dateDebut.toString();
    }

    public boolean dansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return dateDebut.isAfter(debut) && dateDebut.isBefore(fin);
    }
}


