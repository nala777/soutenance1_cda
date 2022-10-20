package fr.cda.projet;

import fr.cda.ihm.*;
import fr.cda.util.TerminalException;

// Classe de definition de l'IHM principale du compte
//
public class GUISite implements FormulaireInt
{
    private Site site;  // Le site

    // Constructeur
    //
    public GUISite(Site site)
    {
        this.site = site;

        // Creation du formulaire
        Formulaire form = new Formulaire("Site de vente",this,1100,730);
        
        //  Creation des elements de l'IHM
        //
        form.addLabel("Afficher tous les produits du stock");
        form.addButton("AFF_STOCK","Tous le stock");
        form.addLabel("");
        form.addLabel("Afficher tous les bons de commande");
        form.addButton("AFF_COMMANDES","Toutes les commandes");
        form.addLabel("");
        form.addText("NUM_COMMANDE","Numero de commande",true,"1");
        form.addButton("AFF_COMMANDE","Afficher");
        form.addButton("MODIF_COMMANDE", "Modifier");
        form.addLabel("");
        form.addButton("AFF_LIVRAISONS","Livrer");

        form.addButton("AFF_VENTES","Calculer les ventes");

        form.setPosition(400,0);
        form.addZoneText("RESULTATS","Resultats",
                            false,
                            "",
                            600,700);

        // Affichage du formulaire
        form.afficher();
    }

    // Methode appellee quand on clique dans un bouton
    //
    public void submit(Formulaire form,String nomSubmit)
    {

        // Affichage de tous les produits du stock
        //
        if (nomSubmit.equals("AFF_STOCK")) {
            String res = site.listerTousProduits();
            form.setValeurChamp("RESULTATS",res);
        }

        // Affichage de toutes les commandes
        //
        if (nomSubmit.equals("AFF_COMMANDES")) {
            String res = site.listerToutesCommandes();
            form.setValeurChamp("RESULTATS",res);
        }

        // Affichage d'une commande
        //
        if (nomSubmit.equals("AFF_COMMANDE")) {
            try {
                String numStr = form.getValeurChamp("NUM_COMMANDE");
                int num = Integer.parseInt(numStr);
                String res = site.listerCommande(num);
                form.setValeurChamp("RESULTATS",res);
            }catch(NumberFormatException e){
                String res = "Veuillez saisir un entier";
                form.setValeurChamp("RESULTATS",res);
            }
        }

//        Modifier Livraison

        if (nomSubmit.equals("MODIF_COMMANDE")) {
            try {
                String numStr = form.getValeurChamp("NUM_COMMANDE");
                int num = Integer.parseInt(numStr);
                if (site.modifierCommandes(num) == null) {
                    form.setValeurChamp("RESULTATS", "Commande non trouvé");
                } else if (site.modifierCommandes(num) != null) {
                    GUIModif ihm = new GUIModif(this,this.site, site.modifierCommandes(num));
                }
            }catch(NumberFormatException e){
                String res = "Veuillez saisir un entier";
                form.setValeurChamp("RESULTATS",res);
            }

        }

        // Affichage Livraison Commande
        //
        if(nomSubmit.equals("AFF_LIVRAISONS")) {
            String res = site.livrerCommandes();
            form.setValeurChamp("RESULTATS",res);
        }

        // Calcul des ventes
        //
        if(nomSubmit.equals("AFF_VENTES"))
        {
            String res = site.afficherVentes();
            form.setValeurChamp("RESULTATS",res);
        }
    }

}