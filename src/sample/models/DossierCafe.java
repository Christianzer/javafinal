package sample.models;

public class DossierCafe {
    public int docrecomp;
    public String nomrecomp;
    public String prenomsrecomp;
    public double resultatMoye;
    public String infocandidat;
    public int classement;
    public int resultatvaleur;
    public String resultat;

    public double getResultatMoye() {
        return resultatMoye;
    }

    public void setResultatMoye(double resultatMoye) {
        this.resultatMoye = resultatMoye;
    }

    public String getInfocandidat() {
        return infocandidat;
    }

    public void setInfocandidat(String infocandidat) {
        this.infocandidat = infocandidat;
    }

    public int getDocrecomp() {
        return docrecomp;
    }

    public void setDocrecomp(int docrecomp) {
        this.docrecomp = docrecomp;
    }

    public String getNomrecomp() {
        return nomrecomp;
    }

    public void setNomrecomp(String nomrecomp) {
        this.nomrecomp = nomrecomp;
    }

    public String getPrenomsrecomp() {
        return prenomsrecomp;
    }

    public void setPrenomsrecomp(String prenomsrecomp) {
        this.prenomsrecomp = prenomsrecomp;
    }


    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    public int getResultatvaleur() {
        return resultatvaleur;
    }

    public void setResultatvaleur(int resultatvaleur) {
        this.resultatvaleur = resultatvaleur;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public DossierCafe(int docrecomp, String nomrecomp, String prenomsrecomp, Double resultatMoye, int classement, int resultatvaleur) {
        this.docrecomp = docrecomp;
        this.nomrecomp = nomrecomp;
        this.prenomsrecomp = prenomsrecomp;
        this.classement = classement;
        this.resultatMoye = resultatMoye;
        this.infocandidat = this.nomrecomp+" "+this.prenomsrecomp;
        this.resultatvaleur = resultatvaleur;
        if (this.resultatvaleur == 1){
            this.resultat = "RESULTAT EN ATTENTE";
        }else if (this.resultatvaleur == 2){
            this.resultat = "SUCCES";
        }else {
            this.resultat = "ECHEC";
        }

    }
}

