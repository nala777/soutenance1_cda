package fr.cda.projet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.cda.util.*;

// Classe de definition du site de vente
//
public class Site
{
    private ArrayList<Produit> stock;       // Les produits du stock
    private ArrayList<Commande> commandes;  // Les bons de commande

    // Constructeur
    //
    public Site() {
        stock = new ArrayList<Produit>();
        commandes = new ArrayList<Commande>();

        // lecture du fichier data/Produits.txt

        //  pour chaque ligne on cree un Produit que l'on ajoute a stock
        initialiserStock("data/Produits.txt");


        Terminal.sautDeLigne();
        Terminal.sautDeLigne();
        Terminal.sautDeLigne();

        //  pour chaque ligne on cree une commande ou on ajoute une reference
        //  d'un produit a une commande existante.
        initialiserCommandes("data/Commandes.txt");
        // AC AC 
        
    }
    
    // Methode qui retourne sous la forme d'une chaine de caractere
    //  tous les produits du stock
    //
    public String listerTousProduits()
    {
        String res="";
        for(Produit prod : stock)
            res=res+prod.toString()+"\n";

        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  toutes les commandes
    //
    public String listerToutesCommandes()
    {
        String res="\n";
        for(Commande commande : commandes) {
            res = res + commande.toString() + "\n";
        }
//        res=res+"Les commandes sont concatenes dans une chaine";

        return res;
    }

    // Methode qui retourne sous la forme d'une chaine de caractere
    //  une commande
    //
    public String listerCommande(int numero)
    {
        String res="Cette methode n'est pas codee\n";
        res=res+"Numero de commande : "+numero+"\n";
        res=res+"Elle doit retourner le contenu d'une commande\n";
        
        return res;
    }



    // Chargement du fichier de stock
    //
    private void initialiserStock(String nomFichier)
    {
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for(String ligne :lignes)
            {
                System.out.println(ligne);
                String[] champs = ligne.split("[;]",4);
                String reference = champs[0];
                String nom = champs[1];
                double prix = Double.parseDouble(champs[2]);
                int quantite =  Integer.parseInt(champs[3]);
                Produit p = new Produit(reference,
                                        nom,
                                        prix,
                                        quantite
                                        );
                stock.add(p);
            }
    }

    private void initialiserCommandes(String nomFichier){
        String[] lignes = Terminal.lireFichierTexte(nomFichier);
        for(String ligne : lignes){
            ArrayList<String> reference =new ArrayList<>();
            System.out.println(ligne);
            String[] champs = ligne.split("[;]",4);
            int numero = Integer.parseInt(champs[0]);
            String date = champs[1];
            String client = champs[2];
            reference.add(champs[3]);
            Commande c = new Commande(numero,
                    date,
                    client,
                    reference
            );
            commandes.add(c);
        }
    }
}