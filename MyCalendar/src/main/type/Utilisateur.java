package type;

public class Utilisateur {
    private final String nom;
    private final String motDePasse;

    public Utilisateur(String nom, String motDePasse) {
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public boolean verifierMotDePasse(String motDePasseSaisi) {
        return this.motDePasse.equals(motDePasseSaisi);
    }

    @Override
    public String toString() {
        return nom;
    }
}

