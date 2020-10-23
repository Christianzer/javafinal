package sample.models;

public class DossierVisiter {
   public int visiteid;
   public int dossiervisiteid;
   public String candvisiter;
   public String prenvisiter;
   public float resultvisite;
   public int typevisite;
   public String culturevisite;
   public String candidat;

    public String getCandidat() {
        return candvisiter+" "+prenvisiter;
    }

    public void setCandidat(String candidat) {
        this.candidat = candvisiter+" "+prenvisiter;
    }

    public DossierVisiter(int visiteid, int dossiervisiteid, String candvisiter, String prenvisiter, float resultvisite, int typevisite, String culturevisite) {
        this.visiteid = visiteid;
        this.dossiervisiteid = dossiervisiteid;
        this.candvisiter = candvisiter;
        this.prenvisiter = prenvisiter;
        this.resultvisite = resultvisite;
        this.typevisite = typevisite;
        this.culturevisite = culturevisite;
        this.candidat = this.candvisiter+" "+this.prenvisiter;
    }

    public int getVisiteid() {
        return visiteid;
    }

    public void setVisiteid(int visiteid) {
        this.visiteid = visiteid;
    }

    public int getDossiervisiteid() {
        return dossiervisiteid;
    }

    public void setDossiervisiteid(int dossiervisiteid) {
        this.dossiervisiteid = dossiervisiteid;
    }

    public String getCandvisiter() {
        return candvisiter;
    }

    public void setCandvisiter(String candvisiter) {
        this.candvisiter = candvisiter;
    }

    public String getPrenvisiter() {
        return prenvisiter;
    }

    public void setPrenvisiter(String prenvisiter) {
        this.prenvisiter = prenvisiter;
    }

    public float getResultvisite() {
        return resultvisite;
    }

    public void setResultvisite(float resultvisite) {
        this.resultvisite = resultvisite;
    }

    public int getTypevisite() {
        return typevisite;
    }

    public void setTypevisite(int typevisite) {
        this.typevisite = typevisite;
    }

    public String getCulturevisite() {
        return culturevisite;
    }

    public void setCulturevisite(String culturevisite) {
        this.culturevisite = culturevisite;
    }
}
