package event;

import type.DateEvenement;
import type.Owner;
import type.Title;

import java.time.LocalDateTime;

public abstract class Event {
    public Title title;
    public Owner proprietaire;
    public DateEvenement dateDebut;
    public Integer dureeMinutes;

    public Event(Title title, Owner proprietaire, DateEvenement dateDebut, Integer dureeMinutes) {
        this.title = title;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.dureeMinutes = dureeMinutes;
    }

    public abstract String description();

    public abstract boolean dansPeriode(LocalDateTime debut, LocalDateTime fin);

    public Title getTitle() {
        return title;
    }
}