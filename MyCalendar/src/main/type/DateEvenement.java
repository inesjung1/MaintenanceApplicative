package type;

import java.time.LocalDateTime;

public class DateEvenement {
    private final int annee;
    private final int mois;
    private final int jour;
    private final int heure;
    private final int minute;

    public DateEvenement(int annee, int mois, int jour, int heure, int minute) {
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        this.heure = heure;
        this.minute = minute;
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(annee, mois, jour, heure, minute);
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d %02d:%02d", jour, mois, annee, heure, minute);
    }

    public boolean isAfter(LocalDateTime date) {
            return toLocalDateTime().isAfter(date);
    }

    public boolean isBefore(LocalDateTime date) {
            return toLocalDateTime().isBefore(date);
    }

    public DateEvenement plusDays(int jours) {
        LocalDateTime nouvelleDate = this.toLocalDateTime().plusDays(jours);
        return new DateEvenement(
                nouvelleDate.getYear(),
                nouvelleDate.getMonthValue(),
                nouvelleDate.getDayOfMonth(),
                nouvelleDate.getHour(),
                nouvelleDate.getMinute()
        );
    }



}
