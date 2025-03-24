package type;

public class Duree {
    private final int minutes;

    public Duree(int minutes) {
        this.minutes = minutes;
    }

    public int enMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return minutes + " min";
    }
}

