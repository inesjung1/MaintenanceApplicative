// CalendarManagerTest.java
import event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        LocalDateTime dateDebut = LocalDateTime.of(2023, 10, 10, 10, 0);
        calendarManager.ajouterEvent("RDV_PERSONNEL", "Dentist", "Alice", dateDebut, 30, "", "", 0);
        assertEquals(1, calendarManager.events.size());
    }

    @Test
    public void testEventsDansPeriode() {
        LocalDateTime dateDebut1 = LocalDateTime.of(2023, 10, 10, 10, 0);
        LocalDateTime dateDebut2 = LocalDateTime.of(2023, 10, 15, 10, 0);
        calendarManager.ajouterEvent("RDV_PERSONNEL", "Dentist", "Alice", dateDebut1, 30, "", "", 0);
        calendarManager.ajouterEvent("RDV_PERSONNEL", "Meeting", "Bob", dateDebut2, 60, "", "", 0);

        LocalDateTime debut = LocalDateTime.of(2023, 10, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2023, 10, 20, 23, 59);
        List<Event> events = calendarManager.eventsDansPeriode(debut, fin);

        assertEquals(2, events.size());
    }

    @Test
    public void testConflit() {
        LocalDateTime dateDebut1 = LocalDateTime.of(2023, 10, 10, 10, 0);
        LocalDateTime dateDebut2 = LocalDateTime.of(2023, 10, 10, 10, 30);
        Event event1 = new Event("RDV_PERSONNEL", "Dentist", "Alice", dateDebut1, 60, "", "", 0);
        Event event2 = new Event("RDV_PERSONNEL", "Meeting", "Bob", dateDebut2, 60, "", "", 0);

        assertTrue(calendarManager.conflit(event1, event2));
    }

}