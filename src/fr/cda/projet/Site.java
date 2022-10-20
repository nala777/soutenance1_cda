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

    /** Constructeur
     * 
     */
    public Site() {
        stock = new ArrayList<>();
        commandes = new ArrayList<>();

        // lecture du fichier data/Produits.txt
        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");


        //  pour chaque ligne on cree une commande ou on ajoute une reference
        //  d'un produit a une commande existante.
        initialiserCommandes("data/Commandes.txt");

    }

    // Chargement du fichier de stock
    /**
     * Initialisation de la liste des produits à partir d'un fichier texte
     * 
     * @param nomFichier the name of the file
     */
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

    /**
     * Initialisation de la listes des commandes à partir d'un fichier texte
     * 
     * @param nomFichier
     */
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
    
    /**
     * Méthode pour lister tous les produits
     * 
     * @return A string
     */
    public String listerTousProduits() {
        String res = "";
        for (Produit prod : stock)
            res = res + prod.toString() + "\n";

        return res;
    }

    /**
     * Méthode pour lister toutes les commandes
     * 
     * @return A string
     */
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

    /**
     * Méthode pour l'affichage d'une commande à partir d'un numéro de commande
     * 
     * @param numero the number of the order
     * @return A string
     */
    public String listerCommande(int numero) {
        String res = "La commande n'a pas été trouvé";

        for (Commande commande : commandes) {

            if (commande.getNumero() == numero) {
                res = commande.toString() + "\n" + "\n\n -----------------------------------------------------------------------------------------\n";
            }
        }

        return res;
    }

    /**
     * Cette méthode va permettre l'affichage des commandes non livrées et de voir les 
     * produits manquant dans le stock . Pour les commandes livrées le stock sera décrémenter
     * des produits livrés
     * 
     * 
     * @return A string.
     */
    public String livrerCommandes() {
        boolean livrer = true;
        boolean quantOk = true;
        int quantDispo = 0;
        String res = "Les commandes non livrées : \n=============================================\n\n";
        for (Commande commande : commandes) {
            // Vérifie si la commande n'est pas déjà livrée
            if (!commande.getbLivrer()) {
                String manque = "";
                for (int i = 0; i < commande.getReferences().size(); i++) {

                    for (int j = 0; j < stock.size(); j++) {
                        // Va comparer la référence de la commande avec celle en stock et vérifier que la quantité demandé est inferieur à celle du stock
                        if (commande.getReferences().get(i).equals(stock.get(j).getReference())) {
                            if (commande.getQuantite().get(i) > stock.get(j).getQuantite()) {
                                livrer = false;
                                quantOk = false;
                                quantDispo = stock.get(j).getQuantite();
                            }
                        }
                    }
                    // Si la quantité en stock n'est pas suffisante cela va ajouté au résultat la quantité manquante
                    if (quantOk == false) {
                        int quantManquante = commande.getQuantite().get(i) - quantDispo;
                        manque += "\n Il manque " + quantManquante + " " + commande.getReferences().get(i);
                    }
                    quantOk = true;
                }


                if (!livrer) {
                    res += commande.toString() + manque+"\n\n-----------------------------------------------------------------------------------------\n\n";
                } else {
                    // Dans le cas où la livraison est bonne va décrémenter le stock des quantités livrées et passé le statut de livraisons à true
                    decrementeStock(commande);
                    commande.setbLivrer(true);
                }
                ;
            }
        }
        return res;
    }


    /**
     * Méthode appelé lors de la livraisons pour décrémenter le stock 
     * 
     * @param c Commande
     */
    public void decrementeStock(Commande c) {
        for (int i = 0; i < c.getReferences().size(); i++) {
            for (int j = 0; j < stock.size(); j++) {
                if (c.getReferences().get(i).equals(stock.get(j).getReference())) {
                    stock.get(j).setQuantite(stock.get(j).getQuantite() - c.getQuantite().get(i));
                }
            }
        }
    }

    /**
     * Méthode qui va itéré sur les commande pour voir s'il y a une correspondance avec la commande recherché par son numéro
     * 
     * @param numero
     * @return retourne la commande si trouvé
     */
    public Commande modifierCommandes(int numero) {
        Commande trouve = null;
        for (Commande commande : commandes) {
            if (commande.getNumero() == numero) {
                trouve = commande;
            }
        }
        return trouve;
    }


    /**
     * Méthode pour modifier les quantité de la commande
     * 
     * @param c commande
     * @param quantite arraylist de quantite
     */
    public void modifQuantite(Commande c,ArrayList quantite){
        c.setQuantite(quantite);
    }

    /**
     * Méthode qui va vérifier si une commande à été livrée et afficher les noms des différents produits livrés , la quantité demandée
     * ainsi que son prix unitaire et affichera le total des ventes éffectuées
     * 
     * @return a string.
     */
    public String afficherVentes(){
        double total = 0;
        String res = "";
        for(Commande commande : commandes){
            // Va vérifier si une commande est livré
            if(commande.getbLivrer()){
                res += "COMMANDE "+commande.getNumero()+"\n";
                for(int i=0;i<commande.getReferences().size();i++){

                    for (int j = 0;j<stock.size();j++){
                        // Va chercher dans le stock la référence correspondante à celui de la commande pour avoir le prix unitaire et son nom complet
                        if(commande.getReferences().get(i).equalsIgnoreCase(stock.get(j).getReference())){
                            total += stock.get(j).getPrix() * commande.getQuantite().get(i);
                            res += "                      "+stock.get(j).getNom()+"  / Quantite : "+commande.getQuantite().get(i) + "  / Prix :  "+ stock.get(j).getPrix()+" euros \n";
                        }
                    }
                }
                res+="\n\n";
            }
        }
        res+= "=========================\nSOMME   :   "+total + " euros";
        return res;
    }


}