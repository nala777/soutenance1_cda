package fr.cda.projet;

import java.util.*;

// Classe de definition d'une commande
//
public class Commande
{
    // Les caracteristiques d'une commande
    //
    private int     numero;         // numero de la commande
    private String  date;           // date de la commande. Au format JJ/MM/AAAA
    private String  client;         // nom du client
    private ArrayList<String> references; // les references des produits de la commande


    //TODO vous devez coder le reste (constructeur, methodes ...)

    public Commande(int numero, String date, String client, ArrayList<String> references) {
        this.numero = numero;
        this.date = date;
        this.client = client;
        this.references = references;
    }


    @Override

    public String toString()
    {
        return String.format("%-15s %-50s %3.2f   %3d",numero,date,client,references);
    }
}