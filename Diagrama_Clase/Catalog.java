// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package Diagrama_Clase;


public class Catalog {

    private Carte[] carti;

    private int nrCarti;
    private static final int NR_MAX_CARTI = 20;

    public Catalog() {
        this.carti = new Carte[NR_MAX_CARTI];
        this.nrCarti = 0;
    }

    // Setters
    public void setCarti(Carte[] carti) {
        this.carti = carti;
    }

    public void setNrCarti(int nrCarti) {
        this.nrCarti = nrCarti;
    }

    // Getters
    public Carte[] getCarti() {
        return carti;
    }

    public int getNrCarti() {
        return nrCarti;
    }


//==================== Actualizare catalog ======================

    public void adaugaInCatalog(Carte carte) {
        Carte c = new Carte(carte);
        this.carti[nrCarti++] = c;
        c.setIdCarte(nrCarti);
    }

    public void stergeDinCatalog(int index){
        int aux = 0;
        Carte[] c = new Carte[nrCarti];
        for (int i = 0; i < nrCarti; i++) {
            if (i != index) {
                c[aux++] = carti[i];
            }
        }
        carti = c;
        afisare();
    }

    public void updateazaCatalog(int idCarte) {
        carti[idCarte - 1].setStatus(!carti[idCarte - 1].isStatus());
    }

    //==============================================================

    public boolean verificareStatusCarte(int idCarti) {
        return carti[idCarti-1].isStatus();
    }

    public void afisare() {
        for (int i = 0; i < carti.length - 1; i++) {
            if(carti[i] == null) {
                break;
            }
            carti[i].afisare();
        }
    }

};
