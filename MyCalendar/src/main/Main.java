import event.Event;
import type.Utilisateur;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        Scanner scanner = new Scanner(System.in);
        List<Utilisateur> utilisateurs = new ArrayList<>();
        AtomicReference<Utilisateur> utilisateurRef = new AtomicReference<>(null);

        while (true) {
            GestionConsole.afficherMenuConnexion(scanner, utilisateurs, utilisateurRef);

            Optional.ofNullable(utilisateurRef.get())
                    .ifPresent(u -> GestionConsole.boucleUtilisateur(u, scanner, calendar, () -> main(args)));
        }
    }



}

