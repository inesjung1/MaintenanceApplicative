package event;

import event.Event;
import type.*;

import java.time.LocalDateTime;

public class Reunion extends Event {
    private Location lieu;
    private Participants participants;

    public Reunion(Title title, Owner proprietaire, DateEvenement dateDebut, Integer dureeMinutes, Location lieu, Participants participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title.getValue() + " à " + lieu.getValue() + " avec " + participants.getValue();
    }

    public String getLieu() {
        return lieu.getValue();
    }

    public String getParticipants() {
        return participants.getValue();
    }

    public boolean dansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return dateDebut.isAfter(debut) && dateDebut.isBefore(fin);
    }
}
