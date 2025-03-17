import event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {
    private ListeEvents listeEvents;

    public CalendarManager() {
        this.listeEvents = new ListeEvents();
    }

    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
                             String lieu, String participants, int frequenceJours) {
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
        return !(e1 instanceof PeriodicEvent || e2 instanceof PeriodicEvent) &&
                e1.dateDebut.isBefore(e2.dateDebut.plusMinutes(e2.dureeMinutes)) &&
                e1.dateDebut.plusMinutes(e1.dureeMinutes).isAfter(e2.dateDebut);
    }

}