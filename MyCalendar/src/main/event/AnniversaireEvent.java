package event;

import type.*;

import java.time.LocalDateTime;

public class AnniversaireEvent extends Event {
    private Location lieu;
    private Participants participants;

    public AnniversaireEvent(Title title, Owner proprietaire, DateEvenement dateDebut, Integer dureeMinutes, Location lieu, Participants participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Anniversaire : " + title.getValue() + " à " + lieu.getValue() + " avec " + participants.getValue();
    }

    public boolean dansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return dateDebut.isAfter(debut) && dateDebut.isBefore(fin);
    }
}

