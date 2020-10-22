package sample.models;

public class DossierPris {
    public int dossierid;
    public String nomcand;
    public String prenomsCand;
    public String typeculture;
    public String daterdv;

    public int getDossierid() {
        return dossierid;
    }

    public void setDossierid(int dossierid) {
        this.dossierid = dossierid;
    }

    public String getNomcand() {
        return nomcand;
    }

    public void setNomcand(String nomcand) {
        this.nomcand = nomcand;
    }

    public String getPrenomsCand() {
        return prenomsCand;
    }

    public void setPrenomsCand(String prenomsCand) {
        this.prenomsCand = prenomsCand;
    }

    public String getTypeculture() {
        return typeculture;
    }

    public void setTypeculture(String typeculture) {
        this.typeculture = typeculture;
    }

    public String getDaterdv() {
        return daterdv;
    }

    public void setDaterdv(String daterdv) {
        this.daterdv = daterdv;
    }

    public DossierPris(int dossierid, String nomcand, String prenomsCand, String typeculture, String daterdv) {
        this.dossierid = dossierid;
        this.nomcand = nomcand;
        this.prenomsCand = prenomsCand;
        this.typeculture = typeculture;
        this.daterdv = daterdv;
    }
}
