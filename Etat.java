/*
 * INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * 
 * Hiver 2013 / TP1
 * 
 * Auteur: Diallo Mamadou Aliou(DIAM02079904)
 * Auteur: Foka Samekong Gustave(FOKG21088509)
 * Auteur: NJampou Djamouo Steeven(NJAS76060007)
 */

import java.util.*;


public class Etat implements Comparable<Etat> {

    // Référence sur la situation du Ramassage
    protected Ramassage ramassage;

    // Noyau de la représentation d'un état. Ici, on met tout ce qui rend l'état unique.
    /* Emplacement du van. */
    protected Emplacement emplacementVan;
    /* Array indicant l'emplacement de chaque colis. */
    protected Emplacement emplacementsColis[];
    /* Array indicant l'état de chargement de chaque colis par le van. */
    protected boolean colisRecuperes[];
    /* Etat de chargement du van */
    protected boolean colisCharge = false;

    // Variables pour l'algorithme A*.
    /* État précédent permettant d'atteindre cet état. */
    protected Etat            parent;
    /* Action à partir de parent permettant d'atteindre cet état. */
    protected String          actionFromParent;
    /* f=g+h. */
    protected double          f;
    /* Meilleur coût trouvé pour atteindre cet état à partir de l'état initial. */    
    protected double          g;
    /* Estimation du coût restant pour atteindre le but. */
    protected double          h;

    
    public Etat(final Ramassage ramassage){
        this.ramassage = ramassage;
    }

    /** Fonction retournant les états successeurs à partir de cet état.
     *  Aussi appelé fonction de transition.
     *  Cela permet d'explorer l'espace d'état (le graphe de rechegrche).
     */
    // À compléter.
    //
    //   - Pour chaque action possible :
    //   - Instanciez un objet Successeur s;
    //   - Copiez l'état courant (s.etat = clone()).
    //   - Créez une chaîne de caractère représentant l'action (s.action).
    //   - Affectez le coût de cette action (s.cout). 
    //   - Changez la valeur des variables appropriées dans s.etat.
    //   - Ajoutez s dans la liste successeurs.
    public Collection<Successeur> enumererEtatsSuccesseurs()
    {
        final LinkedList<Successeur> successeurs = new LinkedList<Successeur>();
        Successeur s = null;
        int nbrRoutes = this.emplacementVan.routes.size();

        for (int i = 0; i < nbrRoutes; i++){
            s = new Successeur();
            s.etat = this.clone();
            s.action = typeAction(emplacementVan.routes.get(i).origine, emplacementVan.routes.get(i).destination);
            s.cout = calculCout(emplacementVan.routes.get(i).destination) +g;
            //   --- Modifier la valeur des variables appropriées dans s.etat pour refléter l'effet de l'action (qu'est-ce qui change?) 
            s.etat.g = s.cout;
            s.etat.parent = this;
            s.etat.actionFromParent = s.action;
            s.etat.emplacementVan = emplacementVan.routes.get(i).destination;
            successeurs.add(s);
        }
        return successeurs;
    }

    public String typeAction (Emplacement parent, Emplacement successeur){
        if (parent.positionGeographique.getX() > successeur.positionGeographique.getX())
            return "Nord";
        else if (parent.positionGeographique.getX() < successeur.positionGeographique.getX())
            return "Sud";
        else if (parent.positionGeographique.getY() > successeur.positionGeographique.getY())
            return "Ouest";
        else if (parent.positionGeographique.getY() < successeur.positionGeographique.getY())
            return "Est";
        return "erreur action";
    } 


    /* Crée un nouvel État en copiant le contenu pertinent de l'état actuel */
    @Override
    public Etat  clone()
    {
        final Etat etat2 = new Etat(ramassage);
        etat2.colisCharge = colisCharge;
        etat2.emplacementVan = emplacementVan;
        etat2.emplacementsColis = new Emplacement[emplacementsColis.length];
        for(int i = 0; i< emplacementsColis.length; i++)
            etat2.emplacementsColis[i] = emplacementsColis[i];

        etat2.colisRecuperes = new boolean[colisRecuperes.length];
        for(int i = 0; i< colisRecuperes.length; i++)
            etat2.colisRecuperes[i] = colisRecuperes[i];
        return etat2;
    }


    /* Relation d'ordre nécessaire pour TreeSet checkOpen . */
    @Override
    public int compareTo(final Etat o) {
        int c;
        c = this.emplacementVan.compareTo(o.emplacementVan);
        if(c!=0) return c;

        if(colisCharge == o.colisCharge)
            c=0;
        else if(colisCharge == true)
            return 1;
        else
            return -1;
       
        for(int i = 0; i< emplacementsColis.length; i++){
            c = (colisRecuperes[i]?1:0) - (o.colisRecuperes[i]?1:0);
            if(c!=0) return c;
            if(!colisRecuperes[i]){
                c = emplacementsColis[i].compareTo(o.emplacementsColis[i]);
                if(c!=0) return c;
            }
        }
        return 0;
    }


    @Override
    public String toString(){
        String s = "ETAT: f=" + f + "  g=" + g + "\n";
        s += "  Pos=" + emplacementVan.nom + "";
        for(int i = 0; i< emplacementsColis.length; i++){
            s += "\n  PosColis[i]=";
            s += emplacementsColis[i]==null ? "--" : emplacementsColis[i].nom;
        }
        s += "\n";
        return s;
    }
    

    public int calculCout(Emplacement e)
    {
        int cout = 1;
        //System.out.println(e.type);
        if("-".equals(e.type)) cout += 1;
        if("C".equals(e.type)) cout += 30;
        if("A".equals(e.type)) cout += 30;
        return cout;
    }
    

    public void fairRamassage(){
        for (Map.Entry<String, Emplacement> 
        entry : ramassage.emplacements.entrySet()){
            if (entry.getValue().compareTo(this.emplacementVan)==0){

            }
        }
    }
}
