package fr.cda.projet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.cda.util.*;

// Classe de definition du site de vente
//
public class Site {
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    // Constructeur
    //
    public Site() {
        stock = new ArrayList<>();
        commandes = new ArrayList<>();

        // lecture du fichier data/Produits.txt
        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");


        //  pour chaque ligne on cree une commande ou on ajoute une reference
        //  d'un produit a une commande existante.
        initialiserCommandes("data/Commandes.txt");
        // AC AC

    }

    // Chargement du fichier de stock
    private void initialiserStock(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            String[] champs = ligne.split("[;]", 5);
            String reference = champs[0];
            String nom = champs[1];
            double prix = Double.parseDouble(champs[2]);
            int quantite = Integer.parseInt(champs[3]);
            Produit p = new Produit(reference,
                    nom,
                    prix,
                    quantite
            );
            stock.add(p);
        }
    }

    // Chargement du fichier commandes
    private void initialiserCommandes(String nomFichier) {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for (String ligne : lignes) {
            ArrayList<String> reference = new ArrayList<>();
            ArrayList<Integer> quantite = new ArrayList<>();
            String[] champs = ligne.split("[;=]", 5);
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            reference.add(champs[3]);
            quantite.add(Integer.valueOf(champs[4]));

            boolean findClient = false;

//            Parcours du tableau pour voir si un client est déjà dans la liste des commandes

            for (Commande commande : commandes) {

                if (commandes.get(commandes.indexOf(commande)).getClient().equalsIgnoreCase(champs[2])) {
                    findClient = true;
                    commande.getReferences().add(champs[3]);
                    commande.getQuantite().add(Integer.valueOf(champs[4]));
                }
            }

            if (!findClient) {
                Commande c = new Commande(numero,
                        date,
                        client,
                        reference,
                        quantite
                );
                commandes.add(c);
            }
        }
    }
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  tous les produits du stock

    //
    public String listerTousProduits() {
        String res = "";
        for (Produit prod : stock)
            res = res + prod.toString() + "\n";

        return res;
    }
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  toutes les commandes

    //

    public String listerToutesCommandes() {
        String res = "\n";
        for (Commande commande : commandes) {
            if(commande.getbLivrer()){
                res = res + commande.toString() +"\nCommande livrée"+ "\n -----------------------------------------------------------------------------------------\n\n\n";
            }else {
                res = res + commande.toString() +"\nCommande non livrée"+ "\n -----------------------------------------------------------------------------------------\n\n\n";
            }
        }

        return res;
    }
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  une commande

    //

    public String listerCommande(int numero) {
        String res = "La commande n'a pas été trouvé";

        for (Commande commande : commandes) {

            if (commande.getNumero() == numero) {
                res = commande.toString() + "\n" + "\n\n -----------------------------------------------------------------------------------------\n";
            }
        }

        return res;
    }

    public String livrerCommandes() {
        boolean livrer = true;
        boolean quantOk = true;
        int quantDispo = 0;
        String res = "Les commandes non livrées : \n=============================================\n\n";
        for (Commande commande : commandes) {
            if (!commande.getbLivrer()) {
                String manque = "";
                for (int i = 0; i < commande.getReferences().size(); i++) {

                    for (int j = 0; j < stock.size(); j++) {
                        if (commande.getReferences().get(i).equals(stock.get(j).getReference())) {
                            if (commande.getQuantite().get(i) > stock.get(j).getQuantite()) {
                                livrer = false;
                                quantOk = false;
                                quantDispo = stock.get(j).getQuantite();
                            }
                        }
                    }
                    if (quantOk == false) {
                        int quantManquante = commande.getQuantite().get(i) - quantDispo;
                        manque += "\n Il manque " + quantManquante + " " + commande.getReferences().get(i);
                    }
                    quantOk = true;
                }


                if (!livrer) {
                    res += commande.toString() + manque+"\n\n-----------------------------------------------------------------------------------------\n\n";
                } else {
                    decrementeStock(commande);
                    commande.setbLivrer(true);
                }
                ;
            }
        }
        return res;
    }


    public void decrementeStock(Commande c) {
        for (int i = 0; i < c.getReferences().size(); i++) {
            for (int j = 0; j < stock.size(); j++) {
                if (c.getReferences().get(i).equals(stock.get(j).getReference())) {
                    stock.get(j).setQuantite(stock.get(j).getQuantite() - c.getQuantite().get(i));
                }
            }
        }
    }

    public Commande modifierCommandes(int numero) {
        Commande trouve = null;
        for (Commande commande : commandes) {
            if (commande.getNumero() == numero) {
                trouve = commande;
            }
        }
        return trouve;
    }


    public void modifQuantite(Commande c,ArrayList quantite){
        c.setQuantite(quantite);
    }

    public String afficherVentes(){
        double total = 0;
        String res = "Total des ventes livrés : ";
        for(Commande commande : commandes){
            if(commande.getbLivrer()){
                for(int i=0;i<commande.getReferences().size();i++){
                    for (int j = 0;j<stock.size();j++){
                        if(commande.getReferences().get(i).equalsIgnoreCase(stock.get(j).getReference())){
                            total += stock.get(j).getPrix() * commande.getQuantite().get(i);
                        }
                    }
                }
            }
        }
        res+= total + " euros";
        return res;
    }


}