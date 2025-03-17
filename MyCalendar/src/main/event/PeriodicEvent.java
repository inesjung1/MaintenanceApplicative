package event;

import event.Event;
import type.Owner;
import type.Title;

import java.time.LocalDateTime;

public class PeriodicEvent extends Event {
    private Integer frequenceJours;

    public PeriodicEvent(Title title, Owner proprietaire, LocalDateTime dateDebut, Integer dureeMinutes, Integer frequenceJours) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.getValue() + " tous les " + frequenceJours + " jours";
    }

    public boolean dansPeriode(LocalDateTime debut, LocalDateTime fin) {
        LocalDateTime occurrence = dateDebut;
        while (occurrence.isBefore(fin)) {
            if (!occurrence.isBefore(debut)) {
                return true;
            }
            occurrence = occurrence.plusDays(frequenceJours);
        }
        return false;
    }

    public Integer getFrequenceJours() {
        return frequenceJours;
    }
}