import event.Event;
import type.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestionConsole {

    public static DateEvenement lireDateEvenement(Scanner scanner) {
        int annee = Integer.parseInt(lireChamp("Année (AAAA) :", scanner));
        int mois = Integer.parseInt(lireChamp("Mois (1-12) :", scanner));
        int jour = Integer.parseInt(lireChamp("Jour (1-31) :", scanner));
        int heure = Integer.parseInt(lireChamp("Heure début (0-23) :", scanner));
        int minute = Integer.parseInt(lireChamp("Minute début (0-59) :", scanner));

        return new DateEvenement(annee, mois, jour, heure, minute);
    }


    public static String lireChamp(String message, Scanner scanner) {
        System.out.print(message + " ");
        return scanner.nextLine();
    }



    public static void afficherMenuConnexion(Scanner scanner, List<Utilisateur> utilisateurs, AtomicReference<Utilisateur> utilisateurRef) {
        System.out.println("  _____         _                   _                __  __");
        System.out.println(" / ____|       | |                 | |              |  \\/  |");
        System.out.println("| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
        System.out.println("| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
        System.out.println("| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
        System.out.println(" \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
        System.out.println("                                                                                   __/ |");
        System.out.println("                                                                                  |___/");

        System.out.println("1 - Se connecter");
        System.out.println("2 - Créer un compte");
        System.out.print("Choix : ");

        Map<String, Runnable> actions = new HashMap<>();

        actions.put("1", () -> {
            String nom = lireChamp("Nom d'utilisateur : ", scanner);

            Map<String, String> utilisateursFixes = Map.of(
                    "Roger", "Chat",
                    "Pierre", "KiRouhl"
            );

            // Essayons les fixes
            Optional<Utilisateur> fixe = Optional.ofNullable(utilisateursFixes.get(nom))
                    .map(mdpCorrect -> {
                        String mdpSaisi = lireChamp("Mot de passe: ", scanner);
                        return mdpCorrect.equals(mdpSaisi) ? new Utilisateur(nom, mdpSaisi) : null;
                    });

            // Sinon, cherchons dans la liste dynamique
            Optional<Utilisateur> dynamique = fixe.isPresent() ? fixe : utilisateurs.stream()
                    .map(u -> lireChamp("Mot de passe: ", scanner))
                    .flatMap(mdpSaisi -> utilisateurs.stream()
                            .filter(u -> u.getNom().equals(nom) && u.verifierMotDePasse(mdpSaisi)))
                    .findFirst();

            // Met à jour la référence si trouvé
            fixe.or(() -> dynamique).ifPresent(utilisateurRef::set);
        });


        actions.put("2", () -> {
            String nom = lireChamp("Nom d'utilisateur: ", scanner);
            String mdp = lireChamp("Mot de passe: ", scanner);
            String repete = lireChamp("Répéter mot de passe: ", scanner);

            Optional.of(mdp)
                    .filter(m -> m.equals(repete))
                    .map(m -> new Utilisateur(nom, m))
                    .ifPresentOrElse(u -> {
                        utilisateurs.add(u);
                        utilisateurRef.set(u);
                    }, () -> System.out.println("Les mots de passe ne correspondent pas..."));
        });

        actions.getOrDefault(scanner.nextLine(), () -> {
            System.out.println("Option invalide.");
        }).run();
    }


    private static void afficherListe (List<Event> evenements) {
        Optional.of(evenements)
                .filter(liste -> !liste.isEmpty())
                .ifPresentOrElse(
                        liste -> {
                            System.out.println("Événements trouvés : ");
                            liste.forEach(e -> System.out.println("- " + e.description()));
                        },
                        () -> System.out.println("Aucun événement trouvé pour cette période.")
                );
    }


    public static void boucleUtilisateur(Utilisateur utilisateur, Scanner scanner, CalendarManager calendar, Runnable retourConnexion) {
        System.out.println("\nBonjour, " + utilisateur);
        System.out.println("=== Menu Gestionnaire d'Événements ===");
        System.out.println("1 - Voir les événements");
        System.out.println("2 - Ajouter un rendez-vous perso");
        System.out.println("3 - Ajouter une réunion");
        System.out.println("4 - Ajouter un évènement périodique");
        System.out.println("5 - Se déconnecter");

        String choix = lireChamp("Votre choix : ", scanner);

        Map<String, Runnable> actions = new HashMap<>();

        actions.put("1", () -> afficherEvenements(calendar, scanner));
        actions.put("2", () -> ajouterRdvPersonnel(calendar, scanner, utilisateur));
        actions.put("3", () -> ajouterReunion(calendar, scanner, utilisateur));
        actions.put("4", () -> ajouterPeriodique(calendar, scanner, utilisateur));
        actions.put("5", () -> Optional.ofNullable(lireChamp("Déconnexion ! Voulez-vous continuer ? (oui / non)", scanner))
                .filter(rep -> rep.trim().equalsIgnoreCase("oui"))
                .ifPresentOrElse(
                        rep -> retourConnexion.run(),
                        () -> System.exit(0)
                ));

        Optional.ofNullable(actions.get(choix))
                .orElse(() -> System.out.println("Choix invalide"))
                .run();

        // récursion tant qu'on ne choisit pas 5
        Optional.of(choix)
                .filter(c -> !c.equals("5"))
                .ifPresent(c -> boucleUtilisateur(utilisateur, scanner, calendar, retourConnexion));
    }




    public static void afficherEvenements(CalendarManager calendar, Scanner scanner) {
        System.out.println("\n=== Menu de visualisation d'Événements ===");
        System.out.println("1 - Afficher TOUS les événements");
        System.out.println("2 - Afficher les événements d'un MOIS précis");
        System.out.println("3 - Afficher les événements d'une SEMAINE précise");
        System.out.println("4 - Afficher les événements d'un JOUR précis");
        System.out.println("5 - Retour");

        String choix = lireChamp("Votre choix : ", scanner);

        Map<String, Runnable> affichages = new HashMap<>();

        affichages.put("1", calendar::afficherEvenements);
        affichages.put("2", () -> {
            int annee = Integer.parseInt(lireChamp("Année (AAAA) : ", scanner));
            int mois = Integer.parseInt(lireChamp("Mois (1-12) : ", scanner));
            LocalDateTime debut = LocalDateTime.of(annee, mois, 1, 0, 0);
            LocalDateTime fin = debut.plusMonths(1).minusSeconds(1);
            afficherListe(calendar.eventsDansPeriode(debut, fin));
        });
        affichages.put("3", () -> {
            int annee = Integer.parseInt(lireChamp("Année (AAAA) : ", scanner));
            int semaine = Integer.parseInt(lireChamp("Semaine (1-52) : ", scanner));
            LocalDateTime debut = LocalDateTime.now()
                    .withYear(annee)
                    .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                    .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                    .withHour(0).withMinute(0);
            LocalDateTime fin = debut.plusDays(7).minusSeconds(1);
            afficherListe(calendar.eventsDansPeriode(debut, fin));
        });
        affichages.put("4", () -> {
            int annee = Integer.parseInt(lireChamp("Année (AAAA) : ", scanner));
            int mois = Integer.parseInt(lireChamp("Mois (1-12) : ", scanner));
            int jour = Integer.parseInt(lireChamp("Jour (1-31) : ", scanner));
            LocalDateTime debut = LocalDateTime.of(annee, mois, jour, 0, 0);
            LocalDateTime fin = debut.plusDays(1).minusSeconds(1);
            afficherListe(calendar.eventsDansPeriode(debut, fin));
        });

        affichages.getOrDefault(choix, () -> {}).run();
    }

    public static void ajouterRdvPersonnel(CalendarManager calendar, Scanner scanner, Utilisateur utilisateur) {
        Title titre = new Title(lireChamp("Titre de l'événement :", scanner));
        DateEvenement date = lireDateEvenement(scanner);
        Duree duree = new Duree(Integer.parseInt(lireChamp("Durée (en minutes) :", scanner)));
        Location lieu = new Location("");

        calendar.ajouterEvent("RDV_PERSONNEL", titre, utilisateur.getNom(),
                date, duree, lieu, "", 0);

        System.out.println("Événement ajouté.");
    }


    public static void ajouterReunion(CalendarManager calendar, Scanner scanner, Utilisateur utilisateur) {
        Title titre = new Title(lireChamp("Titre de l'événement :", scanner));
        DateEvenement date = lireDateEvenement(scanner);
        Duree duree = new Duree(Integer.parseInt(lireChamp("Durée (en minutes) :", scanner)));
        Location lieu = new Location(lireChamp("Lieu :", scanner));

        System.out.println("Ajouter un participant ? (oui / non)");

        List<String> participants = Stream
                .iterate(scanner.nextLine(), reponse -> reponse.equalsIgnoreCase("oui"), reponse -> {
                    System.out.print("Ajouter un autre participant ? (oui / non) : ");
                    return scanner.nextLine();
                })
                .map(reponse -> lireChamp("Nom du participant :", scanner))
                .collect(Collectors.toCollection(ArrayList::new));

        participants.add(0, utilisateur.getNom());

        calendar.ajouterEvent("REUNION", titre, utilisateur.getNom(),
                date, duree, lieu, String.join(", ", participants), 0);

        System.out.println("Événement ajouté.");
    }

    public static void ajouterPeriodique(CalendarManager calendar, Scanner scanner, Utilisateur utilisateur) {
        Title titre = new Title(lireChamp("Titre de l'événement :", scanner));
        DateEvenement date = lireDateEvenement(scanner);
        int frequence = Integer.parseInt(lireChamp("Fréquence (en jours) :", scanner));
        Duree dureeMin = new Duree(0);
        Location lieu = new Location("");

        calendar.ajouterEvent("PERIODIQUE", titre, utilisateur.getNom(),
                date, dureeMin, lieu, "", frequence);

        System.out.println("Événement ajouté.");
    }
}
