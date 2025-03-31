

import event.Event;
import org.junit.jupiter.api.*;
import type.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GestionConsoleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAfficherEvenementsEntreDeuxDates_afficheResultat() {
        CalendarManager calendar = new CalendarManager();

        Title titre = new Title("Test Event");
        DateEvenement date = new DateEvenement(2025, 4, 1, 10, 0);
        Duree duree = new Duree(30);
        Location lieu = new Location("Salle A");

        calendar.ajouterEvent("RDV_PERSONNEL", titre, "Alice", date, duree, lieu, "", 0);

        // Entrée simulée pour les deux dates
        String input = String.join(System.lineSeparator(),
                "2025", "4", "1", "0", "0",   // date de début
                "2025", "4", "2", "0", "0"    // date de fin
        );
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Appel direct à la méthode d’affichage
        GestionConsole.afficherEntreDeuxDates(calendar, scanner);

        String sortie = outContent.toString();

        assertTrue(sortie.contains("Test Event"));
    }
}

