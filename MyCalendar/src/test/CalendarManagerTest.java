// CalendarManagerTest.java
import event.Event;
import event.EventFactory;
import event.PersonalMeeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import type.DateEvenement;
import type.Duree;
import type.Location;
import type.Title;

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
    public void testConflit() {
        DateEvenement dateDebut1 = new DateEvenement(2023, 10, 10, 10, 0);
        DateEvenement dateDebut2 = new DateEvenement(2023, 10, 10, 10, 30);
        Duree duree1 = new Duree(30);
        Duree duree2 = new Duree(60);
        Title title1 = new Title("Dentist");
        Title title2 = new Title("Meeting");
        Location lieu = new Location("");
        Event event1 = new EventFactory().creerEvent("RDV_PERSONNEL", title1, "Alice", dateDebut1, duree1, lieu, "", 0);
        Event event2 = new EventFactory().creerEvent("RDV_PERSONNEL", title2, "Bob", dateDebut2, duree2, lieu, "", 0);

        assertTrue(calendarManager.conflit(event1, event2));
    }

}