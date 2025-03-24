package event;

import type.*;

import java.time.LocalDateTime;

public class EventFactory {
    public static Event creerEvent(String type, String title, String proprietaire, DateEvenement dateDebut, int dureeMinutes,
                                   String lieu, String participants, int frequenceJours) {
        return switch (type) {
            case "RDV_PERSONNEL" -> new PersonalMeeting(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes);
            case "REUNION" -> new Reunion(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes, new Location(lieu), new Participants(participants));
            case "PERIODIQUE" -> new PeriodicEvent(new Title(title), new Owner(proprietaire), dateDebut, dureeMinutes, frequenceJours);
            default -> throw new IllegalArgumentException("Type d'événement inconnu : " + type);
        };
    }
}

