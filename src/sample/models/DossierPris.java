package sample.models;

public class DossierPris {
    private int dossierId;
    private String nomCand;
    private String prenomsCand;
    private String DateRdv;

    public int getDossierId() {
        return dossierId;
    }

    public void setDossierId(int dossierId) {
        this.dossierId = dossierId;
    }

    public String getNomCand() {
        return nomCand;
    }

    public void setNomCand(String nomCand) {
        this.nomCand = nomCand;
    }

    public String getPrenomsCand() {
        return prenomsCand;
    }

    public void setPrenomsCand(String prenomsCand) {
        this.prenomsCand = prenomsCand;
    }

    public String getDateRdv() {
        return DateRdv;
    }

    public void setDateRdv(String dateRdv) {
        DateRdv = dateRdv;
    }

    public DossierPris(int dossierId, String nomCand, String prenomsCand, String dateRdv) {
        this.dossierId = dossierId;
        this.nomCand = nomCand;
        this.prenomsCand = prenomsCand;
        DateRdv = dateRdv;
    }
}
