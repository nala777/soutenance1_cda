// Projet 1 CDA
// 
// NOM,Prenom
// NOM,Prenom
//
package fr.cda.projet;

import java.io.*;
import java.util.*;

import fr.cda.util.*;

// Classe principale d'execution du projet
//
public class Projet
{
    public static void main(String a_args[])
    {
        Terminal.ecrireStringln("Execution du projet ");
        System.out.println();

        Site site = new Site();
//        System.out.println(site.listerTousProduits());
//        System.out.println(site.listerToutesCommandes());
//        System.out.println();
//        System.out.println();
//        System.out.println();


//        System.out.println("Entrez le numéro de la commande à chercher : ");
//        int i = Terminal.lireInt();
//
//        System.out.println(site.listerCommande(i));

        GUISite ihm = new GUISite(site);
    }
}
