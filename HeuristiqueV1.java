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

public class HeuristiqueV1 extends Heuristique {
  
  HeuristiqueV1(final Ramassage monde) {
    super(monde);
  }
	
	/** Estime et retourne le coût restant pour atteindre le but à partir de état.
   *  Attention : pour être admissible, cette fonction heuristique ne doit pas
   *  surestimer le coût restant.
   */
  //@Override
  public double estimerCoutRestant(final Etat etat, final Emplacement but) {

    //utilisation de la distance euclidienne 
  	// À Compléter.
  	// -- Vous devez réflechir à deux façons d'estimer la distance entre l'état courant et l'état but
  	// -- Pensez en termes de cout des déplacements du Van (vers le prochain Colis et vers le point d'Arrivee) 
  	// ainsi que de Ramassge/Depot des Colis
  	// -- Par exemple, pour calculer une distance, vous pouvez utiliser les positions géographiques des emplacements
  	// -- Commencez avec une version plus simple, puis raffinez vos estimations tout en veillant de ne pas surestimer le cout
    double sommeDuree = 0;
    sommeDuree = sommeDuree + heuresticEuclidienne(etat.emplacementVan , but) ;
    return sommeDuree;
  }


  public double heuresticEuclidienne(Emplacement fin,  Emplacement debut){
    double somLocal=0.0;
    double  distanceX=0.0;
    double distanceY =0.0;
      
    distanceX = fin.positionGeographique.getX() - debut.positionGeographique.getX();
    distanceY = fin.positionGeographique.getY() - debut.positionGeographique.getY();
    somLocal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    return somLocal;
  }
}
