package sample.models;

public class DossierInscription {
    private Integer dossier;
    private String nom;
    private String prenoms;
    private String typecult;
    private String etat;

    public DossierInscription(Integer dossier, String nom, String prenoms, String typecult, String etat) {
        this.dossier = dossier;
        this.nom = nom;
        this.prenoms = prenoms;
        this.typecult = typecult;
        this.etat = etat;
    }

    public Integer getDossier() {
        return dossier;
    }

    public void setDossier(Integer dossier) {
        this.dossier = dossier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getTypecult() {
        return typecult;
    }

    public void setTypecult(String typecult) {
        this.typecult = typecult;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
