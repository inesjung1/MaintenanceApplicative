package event;

import type.Owner;
import type.Title;

import java.time.LocalDateTime;

public abstract class Event {
    public Title title;
    public Owner proprietaire;
    public LocalDateTime dateDebut;
    public Integer dureeMinutes;

    public Event(Title title, Owner proprietaire, LocalDateTime dateDebut, Integer dureeMinutes) {
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
    }

    public abstract String description();
}