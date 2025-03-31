// CalendarManagerTest.java
import event.Event;
import event.EventFactory;
import event.PersonalMeeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import type.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarManagerTest {
    private CalendarManager calendarManager;

    @BeforeEach
    public void setUp() {
        calendarManager = new CalendarManager();
    }

    @Test
    public void testAjouterEvent() {
        DateEvenement dateDebut = new DateEvenement(2023, 10, 10, 10, 0);
        Title title = new Title("Dentist");
        Duree duree = new Duree(30);
        Location lieu = new Location("");

        calendarManager.ajouterEvent("RDV_PERSONNEL", title, "Alice", dateDebut, duree, lieu, "", 0);
        assertEquals(1, calendarManager.eventsDansPeriode(LocalDateTime.of(2023, 10, 10, 0, 0), LocalDateTime.of(2023, 10, 10, 23, 59)).size());
    }

    @Test
    public void testEventsDansPeriode() {
        DateEvenement dateDebut1 = new DateEvenement(2023, 10, 10, 10, 0);
        DateEvenement dateDebut2 = new DateEvenement(2023, 10, 15, 10, 0);
        Duree duree1 = new Duree(30);
        Duree duree2 = new Duree(60);
        Title title1 = new Title("Dentist");
        Title title2 = new Title("Meeting");
        Location lieu = new Location("");
        calendarManager.ajouterEvent("RDV_PERSONNEL", title1, "Alice", dateDebut1, duree1, lieu, "", 0);
        calendarManager.ajouterEvent("RDV_PERSONNEL", title2, "Bob", dateDebut2, duree2, lieu, "", 0);

        LocalDateTime debut = LocalDateTime.of(2023, 10, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2023, 10, 20, 23, 59);
        List<Event> events = calendarManager.eventsDansPeriode(debut, fin);

        assertEquals(2, events.size());
    }

    @Test
    public void testAjoutEvenementAnniversaire() {
        CalendarManager manager = new CalendarManager();
        Utilisateur utilisateur = new Utilisateur("Alice", "mdp");
        DateEvenement date = new DateEvenement(2023, 10, 10, 10, 0);
        Title title = new Title("Anniv Alice");
        Duree duree = new Duree(30);
        Location lieu = new Location("Chez moi");

        manager.ajouterEvent("ANNIVERSAIRE", title, utilisateur.getNom(), date, duree, lieu, "", 0);

        List<Event> evenements = manager.eventsDansPeriode(date.minusDays(1).toLocalDateTime(), date.plusDays(1).toLocalDateTime());

        boolean contient = evenements.stream().anyMatch(e -> e.getTitle().equals("Anniv Alice"));

        assertTrue(contient); // ce test doit échouer car le type "ANNIVERSAIRE" n’est pas encore géré
    }

    @Test
    public void testConflit() {
        DateEvenement dateDebut1 = new DateEvenement(2023, 10, 10, 10, 0); // 10h00 à 10h30
        DateEvenement dateDebut2 = new DateEvenement(2023, 10, 10, 10, 15);
        Duree duree1 = new Duree(30);
        Duree duree2 = new Duree(60);
        Title title1 = new Title("Dentist");
        Title title2 = new Title("Meeting");
        Location lieu = new Location("");
        Event event1 = new EventFactory().creerEvent("RDV_PERSONNEL", title1, "Alice", dateDebut1, duree1, lieu, "", 0);
        Event event2 = new EventFactory().creerEvent("RDV_PERSONNEL", title2, "Bob", dateDebut2, duree2, lieu, "", 0);

        assertTrue(calendarManager.conflit(event1, event2));
    }

    @Test
    public void testSuppressionEvenementParId() {
        CalendarManager calendar = new CalendarManager();

        Title titre = new Title("Test Suppression");
        DateEvenement date = new DateEvenement(2025, 4, 1, 10, 0);
        Duree duree = new Duree(60);
        Location lieu = new Location("Salle X");

        Event event = new EventFactory().creerEvent("RDV_PERSONNEL", titre, "Alice", date, duree, lieu, "", 0);

        calendarManager.getListeEvents().getEvents().add(event);

        String id = event.getId().getValue();

        boolean supprime = calendar.supprimerEventParId(id);

        assertTrue(supprime);
        assertFalse(calendar.getListeEvents().getEvents().contains(event));
    }


}