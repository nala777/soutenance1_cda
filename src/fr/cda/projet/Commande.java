package fr.cda.projet;

import java.util.*;


/**
 * Classe de définition d'une commande.
 */
public class Commande
{
    // Les caracteristiques d'une commande
    //
    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private ArrayList<String> references; // les references des produits de la commande
    private ArrayList<Integer> quantite; // quantité commandé
    private boolean bLivrer; //commande livré


    /** Constructeur
     *
     * @param numero
     * @param date
     * @param client
     * @param references
     * @param quantite
     */
    public Commande(int numero, String date, String client, ArrayList<String> references, ArrayList<Integer> quantite) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
        this.quantite = quantite;
        this.bLivrer = false;

    }


    /**
     * Override de la méthode toString pour l'affichage d'une commande
     */
    @Override
    public String toString() {
        return  "Commande : " + numero +
                "\n           Date : " + date +
                "\n           Client : " + client +
                "\n           References : \n" +
                affichRefQuant() ;

    }

    /**
     * Affichage des références et quantités
     * 
     * @return 
     */
    public String affichRefQuant() {
        String res = "";
        for(int i = 0;i<references.size();i++){
           res+= "               "+references.get(i) + " Quantité : " + quantite.get(i) + "\n";
        }
        return res;
    }


    /** Getters et setters
     *
     * @return
     */

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    public ArrayList<Integer> getQuantite() {
        return quantite;
    }

    public void setQuantite(ArrayList<Integer> quantite) {
        this.quantite = quantite;
    }

    public boolean getbLivrer() {
        return bLivrer;
    }

    public void setbLivrer(boolean bLivrer) {
        this.bLivrer = bLivrer;
    }
}