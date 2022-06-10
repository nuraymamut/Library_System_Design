// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package Diagrama_Clase;


import java.util.Objects;
import java.util.Scanner;

public class Bibliotecar {

    private String nume;
    private String prenume;
    private Permis[] permise;
    private Catalog catalog;

    private static int idImprumut = 0;
    private static final int NR_MAX_PERMISE = 30;
    private int nrPermise;

    public Bibliotecar(String nume, String prenume, Catalog catalog) {
        this.nume = nume;
        this.prenume = prenume;
        this.permise = new Permis[NR_MAX_PERMISE];
        this.catalog = catalog;
        this.nrPermise = 0;
    }

    // Setters
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setPermise(Permis[] permise) {
        this.permise = permise;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    // Getters
    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Permis[] getPermise() {
        return permise;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    // creeaza permis nou
    public void adaugaPermis() {
        Permis p = new Permis();
        this.permise[nrPermise++] = p;
    }

    // returneaza noul permis clientului
    public Permis permisNou() {
        adaugaPermis();
        return permise[nrPermise - 1];
    }

    // returneaza un id pentru noul imprumut finalizat
    public int adaugaIdImpr() {
        idImprumut++;
        return idImprumut;
    }

    // returneaza data returului pentru noul imprumut finalizat/imprumut prelungit
    public String adugareDataRetur() {
        System.out.println("Introduceti data returului (dd/MM/yyyy): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void updateazaStatusCarte(int idCarte) {
        catalog.updateazaCatalog(idCarte);
    }

    public void stergeCarte() {
        catalog.afisare();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti id-ul cartii pe care doriti sa o stergeti: ");
        int id = scanner.nextInt();
        catalog.stergeDinCatalog(id-1);
    }

    //================== Adaugare Carte =========================

    public String carteNeinregistrata() {
        System.out.println("Introduceti titlul: ");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    public int verificareCatalog(String titlu){
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < catalog.getCarti().length; i++) {
            if(catalog.getCarti()[i] == null) {
                System.out.println("Nu s-a putut gasi cartea");
                return -1;
            }
            if (Objects.equals(titlu, catalog.getCarti()[i].getNume())) {
                System.out.println("Titlu existent in catalog: ");
                System.out.println(catalog.getCarti()[i].getNume());
                System.out.println(catalog.getCarti()[i].getAutor());
                System.out.println(catalog.getCarti()[i].getEditura());
                return i;

            }
        }
        return -1;
    }

    public String[] copiereDate(int i) {
        System.out.println("Se copiaza datele");
        String[] date = new String[2];
        date[0] = catalog.getCarti()[i].getAutor();
        date[1] = catalog.getCarti()[i].getEditura();

        return date;
    }

    public String completareDate() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public boolean validareDate(String autor) {
        int valid = 0;
        System.out.println("Validare date...");
        for (char c : autor.toCharArray()) {
            if (!Character.isDigit(c)) {
                valid++;
            }
        }
        return valid == autor.length();
    }

    public boolean verificareDate(String titlu, String autor, String editura) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Date valide");
        System.out.println("Titlu: " + titlu);
        System.out.println("Autor: " + autor);
        System.out.println("Editura: " + editura);
        System.out.println("Doriti sa editati datele? (da/nu)");
        String rasp = scanner.nextLine();

        return Objects.equals(rasp, "da");
    }

    public String[] editareDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Completati datele: ");
        System.out.print("Titlu: ");
        String titlu = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Editura: ");
        String editura = scanner.nextLine();

        return new String[]{titlu, autor, editura};
    }

    public Carte salvareDate(String titlu, String autor, String editura) {
        Carte carte = new Carte(titlu, autor, editura);
        System.out.println("Carte salvata");
        return carte;
    }

    public void actualizareCatalog(Carte carte) {
        System.out.println("Actualizare catalog...");
        catalog.adaugaInCatalog(carte);
        System.out.println("Catalog actualizat");
    }

    public void adaugaCarte() {
        boolean flag = true;
        String autor;
        String editura;
        String titlu = carteNeinregistrata();
        int index = verificareCatalog(titlu);

        if (index != -1) {
            String[] dateCopy = copiereDate(index);
            autor = dateCopy[0];
            editura = dateCopy[1];
        } else {
            System.out.println("Completati datele: ");
            System.out.print("Autor: ");
            autor = completareDate();
            System.out.print("Editura: ");
            editura = completareDate();
            while(flag) {
                if(validareDate(autor)) {
                    if(verificareDate(titlu, autor, editura)) {
                        String[] dateEditate = editareDate();
                        titlu = dateEditate[0];
                        autor = dateEditate[1];
                        editura = dateEditate[2];
                    } else {
                        flag = false;
                    }
                } else {
                    System.out.println("Completati datele: ");
                    System.out.print("Autor: ");
                    autor = completareDate();
                    System.out.print("Editura: ");
                    editura = completareDate();
                }
            }
        }
        Carte carte = salvareDate(titlu, autor, editura);
        actualizareCatalog(carte);
    }

};
