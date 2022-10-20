package fr.cda.projet;

import fr.cda.ihm.Formulaire;
import fr.cda.ihm.FormulaireInt;

import java.util.ArrayList;

public class GUIModif implements FormulaireInt {
    private GUISite formPP;
    private Commande commande;
    private Site site;

    public GUIModif(GUISite formPP,Site site,
                    Commande commande){
        this.formPP = formPP;
        this.site = site;
        this.commande = commande;
        Formulaire form = new Formulaire("Modification Commande", this, 320, 260);

        form.setPosition(20, 10);
        form.addLabel("Modification Commande : ");
        for (int i = 0 ; i<commande.getReferences().size();i++ ) {
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
    @Override
    public void submit(Formulaire form, String nomSubmit) {
        if(nomSubmit.equals("MODIFIER")){
            String res = "";
            ArrayList<Integer> quantiteList = new ArrayList<>();
            for (int i = 0 ; i<commande.getReferences().size();i++ ){
                String quantiteStr = form.getValeurChamp(commande.getReferences().get(i));
                int quantite = Integer.parseInt(quantiteStr);
                quantiteList.add(quantite);
            }

            try {
                site.modifQuantite(commande, quantiteList);
                form.fermer();
            }catch(NumberFormatException e){
                form.fermer();
            }
        }
    }

}
