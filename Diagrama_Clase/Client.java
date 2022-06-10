// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package Diagrama_Clase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private String nume;
    private String prenume;
    private String telefon;
    private String CNP;
    private Permis permis;
    private Imprumut[] imprumuturi;

    private int nrImprumuturi;
    private static final int NR_MAX_IMPR = 20;

    public Client(String nume, String prenume, String telefon, String CNP) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.CNP = CNP;
        this.imprumuturi = new Imprumut[NR_MAX_IMPR];
        this.nrImprumuturi = 0;
    }

    // Setters
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public void setPermis(Permis permis) {
        this.permis = permis;
    }

    public void setImprumuturi(Imprumut[] imprumuturi) {
        this.imprumuturi = imprumuturi;
    }

    public void setNrImprumuturi(int nrImprumuturi) {
        this.nrImprumuturi = nrImprumuturi;
    }

    // Getters
    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getCNP() {
        return CNP;
    }

    public Permis getPermis() {
        return permis;
    }

    public Imprumut[] getImprumuturi() {
        return imprumuturi;
    }

    void inregistrare(Bibliotecar bibliotecar) {
        if (permis != null) {
            System.out.println("Permis ok");
        } else {
            System.out.println("Creare permis");
            permis = bibliotecar.permisNou();
            System.out.println("Permis:");
            permis.afisare();
        }
    }

    void imprumutCarte(Bibliotecar bibliotecar, int[] idCarti, Catalog catalog) {
        inregistrare(bibliotecar);
        cerereImprumut(idCarti, catalog, bibliotecar);
        afisareImprumuturiActive();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Doriti sa prelungiti un imprumut? (da/nu): ");
        String myString = scanner.nextLine();

        if (Objects.equals(myString, "da")) {
            System.out.println("Introduceti id-ul imprumutului: ");
            int id = scanner.nextInt();
            String data = bibliotecar.adugareDataRetur();
            imprumuturi[id-1].setDataRetur(data);
            System.out.println("Data retur actualizata: " + imprumuturi[id-1].getDataRetur());
            System.out.println("Prelungire imprumut finalizata");
        }
    }

    public void returCarte(Bibliotecar bibliotecar) throws ParseException {
        afisareImprumuturiActive();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti id-ul imprumutului pentru retur: ");
        int id = scanner.nextInt();

        System.out.println("Introduceti data de astazi (dd/MM/yyyy): ");
        String aux = scanner.nextLine(); // trece peste fara sa citeasca
        String dataR = scanner.nextLine();

        Date data1 = new SimpleDateFormat("dd/MM/yyyy").parse(dataR);
        Date data2 = new SimpleDateFormat("dd/MM/yyyy").parse(imprumuturi[id-1].getDataRetur());

        if (data1.compareTo(data2) > 0) {
            System.out.println("Termen de retur depasit");
            permis.setPermisValid(false);
        }

        for (int i = 0; i < imprumuturi[id-1].getIdCarti().length; i++) {
            bibliotecar.updateazaStatusCarte(imprumuturi[id-1].getIdCarti()[i]);
        }

        imprumuturi[id-1].setImprumutActiv(false);
        permis.setNrCartiNereturnate(permis.getNrCartiNereturnate() - imprumuturi[id-1].getIdCarti().length);
        System.out.println("Retur finalizat");
    }

    public void cerereImprumut(int[] idCarti, Catalog catalog, Bibliotecar bibliotecar) {
        Imprumut impr = new Imprumut(idCarti);
        int nrCarti = impr.imprumutNou(permis.getNrCartiNereturnate(), permis.isPermisValid(), catalog, bibliotecar);

        if (nrCarti != 0) {
            this.imprumuturi[nrImprumuturi++] = impr;
            permis.setNrCartiNereturnate(permis.getNrCartiNereturnate() + nrCarti);
            System.out.println("Imprumut finalizat");
        } else {
            System.out.println("Imprumut esuat");
        }
    }

    public void afisareImprumuturiActive() {
        System.out.println("Imprumuturi active:");
        for (int i = 0; i < nrImprumuturi; i++) {
            if (imprumuturi[i].isImprumutActiv()) {
                imprumuturi[i].afisare();
            }
        }
    }
}
