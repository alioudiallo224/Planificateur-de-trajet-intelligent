/*
 * INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * 
 * Hiver 2019 / TP1
 * 
 * Auteur: Diallo Mamadou Aliou(DIAM02079904)
 * Auteur: Foka Samekong Gustave(FOKG21088509)
 * Auteur: NJampou Djamouo Steeven(NJAS76060007)
 */

public class But {

    /* Array contenant la destination des colis. Dans le même ordre que dans la classe Ramassage */
    protected Emplacement[]   destinationsPatients;

    /* Retourne vrai si et seulement si le but est satisfait dans l'état passé en paramètre.
     * Le but ici est : tous les Colis ont été amenés à l'Arrivee */
    public boolean estStatisfait(final Etat etat)
    {
        for(int i=0;i<destinationsPatients.length;i++)
            if(etat.emplacementsColis[i] != destinationsPatients[i]){
                return false;
            }
           
        return true;
    }

}
