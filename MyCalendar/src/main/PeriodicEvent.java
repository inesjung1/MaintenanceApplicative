import java.time.LocalDateTime;

class PeriodicEvent extends Event {
    private Integer frequenceJours;

    public PeriodicEvent(Title title, Owner proprietaire, LocalDateTime dateDebut, Integer dureeMinutes, Integer frequenceJours) {
        super(title, proprietaire, dateDebut, dureeMinutes);
        this.frequenceJours = frequenceJours;
    }

    @Override
    public String description() {
        return "Événement périodique : " + title.getValue() + " tous les " + frequenceJours + " jours";
    }
}