package event;

import event.Event;
import type.Location;
import type.Owner;
import type.Participants;
import type.Title;

import java.time.LocalDateTime;

public class Reunion extends Event {
    private Location lieu;
    private Participants participants;

    public Reunion(Title title, Owner proprietaire, LocalDateTime dateDebut, Integer dureeMinutes, Location lieu, Participants participants) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + title.getValue() + " à " + lieu.getValue() + " avec " + participants.getValue();
    }
}
