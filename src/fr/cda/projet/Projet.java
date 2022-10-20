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
    public static void main(String args[])
    {
        Terminal.ecrireStringln("Execution du projet ");
        System.out.println();

        Site site = new Site();

        GUISite ihm = new GUISite(site);
    }
}
