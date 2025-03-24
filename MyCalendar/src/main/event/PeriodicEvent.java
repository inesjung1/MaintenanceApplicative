package event;

import event.Event;
import type.DateEvenement;
import type.Owner;
import type.Title;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class PeriodicEvent extends Event {
    private Integer frequenceJours;

    public PeriodicEvent(Title title, Owner proprietaire, DateEvenement dateDebut, Integer dureeMinutes, Integer frequenceJours) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.getValue() + " tous les " + frequenceJours + " jours";
    }

    public boolean dansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return Stream.iterate(dateDebut, d -> d.plusDays(frequenceJours))
                .takeWhile(d -> d.isBefore(fin))
                .anyMatch(d -> !d.isBefore(debut));
    }


    public Integer getFrequenceJours() {
        return frequenceJours;
    }
}