package fr.cda.projet;

import fr.cda.ihm.Formulaire;
import fr.cda.ihm.FormulaireInt;

import java.util.ArrayList;
// Classe de l'ihm de modification d'une commande
public class GUIModif implements FormulaireInt {
    private GUISite formPP; //IHM principale
    private Commande commande; // commande
    private Site site; // Site

    /** Constructeur
     *
     * @param formPP
     * @param site
     * @param commande
     */
    
    public GUIModif(GUISite formPP,Site site,
                    Commande commande){
        this.formPP = formPP;
        this.site = site;
        this.commande = commande;
        Formulaire form = new Formulaire("Modification Commande", this, 320, 260);

        form.setPosition(20, 10);
        form.addLabel("Modification Commande : ");
        // Vas itérer sur tout les champs de la commande sur les références ainsi que les quantités liés
        for (int i = 0 ; i<commande.getReferences().size();i++ ) {
            // Si commande non livré les champs seront éditables autrement il ne le seront pas
            if(!commande.getbLivrer()) {
                form.addText(commande.getReferences().get(i), commande.getReferences().get(i), true, commande.getQuantite().get(i).toString());
            }else{
                form.addText(commande.getReferences().get(i), commande.getReferences().get(i), false, commande.getQuantite().get(i).toString());
            }
        }
        form.addLabel("");
        form.addButton("MODIFIER", "Modifier");
        form.setPosition(350, 0);
        form.afficher();
    }


    //    Modifier Quantite de la commande
    /**
     * Méthode appelée lors du clic du boutons VALIDER
     * qui modifiera les quantités de la commande
     * 
     * @param form formulaire qui sera soumis
     * @param nomSubmit le nom du boutton submit
     */
    @Override
    public void submit(Formulaire form, String nomSubmit) {
        if(nomSubmit.equals("MODIFIER")){
            ArrayList<Integer> quantiteList = new ArrayList<>();
            for (int i = 0 ; i<commande.getReferences().size();i++ ){
                String quantiteStr = form.getValeurChamp(commande.getReferences().get(i));
                int quantite = Integer.parseInt(quantiteStr);
                quantiteList.add(quantite);
            }

                site.modifQuantite(commande, quantiteList);
                form.fermer();
        }
    }

}
