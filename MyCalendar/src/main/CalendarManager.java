import event.*;
import type.DateEvenement;
import type.Duree;
import type.Location;
import type.Title;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {
    private ListeEvents listeEvents;

    public CalendarManager() {
        this.listeEvents = new ListeEvents();
    }

    public void ajouterEvent(String type, Title title, String proprietaire, DateEvenement dateDebut, Duree dureeMinutes,
                             Location lieu, String participants, int frequenceJours) {
        Event e = EventFactory.creerEvent(type, title, proprietaire, dateDebut, dureeMinutes, lieu, participants, frequenceJours);
        listeEvents.ajouterEvent(e);
    }

    public void afficherEvenements() {
        listeEvents.afficherEvenements();
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        return listeEvents.getEvents().stream()
                .filter(e -> e.dansPeriode(debut, fin))
                .collect(Collectors.toList());
    }

    public boolean conflit(Event e1, Event e2) {
        LocalDateTime start1 = e1.dateDebut.toLocalDateTime();
        LocalDateTime end1 = start1.plusMinutes(e1.dureeMinutes);
        LocalDateTime start2 = e2.dateDebut.toLocalDateTime();
        LocalDateTime end2 = start2.plusMinutes(e2.dureeMinutes);

        return !(e1 instanceof PeriodicEvent || e2 instanceof PeriodicEvent)
                && start1.isBefore(end2)
                && end1.isAfter(start2);
    }


}