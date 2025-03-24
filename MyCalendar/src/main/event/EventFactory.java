package event;

import type.*;

import java.time.LocalDateTime;

public class EventFactory {
    public static Event creerEvent(String type, Title title, String proprietaire, DateEvenement dateDebut, Duree dureeMinutes,
                                   Location lieu, String participants, int frequenceJours) {
        return switch (type) {
            case "RDV_PERSONNEL" -> new PersonalMeeting(title, new Owner(proprietaire), dateDebut, dureeMinutes.enMinutes());
            case "REUNION" -> new Reunion(title, new Owner(proprietaire), dateDebut, dureeMinutes.enMinutes(), lieu, new Participants(participants));
            case "PERIODIQUE" -> new PeriodicEvent(title, new Owner(proprietaire), dateDebut, dureeMinutes.enMinutes(), frequenceJours);
            default -> throw new IllegalArgumentException("Type d'événement inconnu : " + type);
        };
    }
}

