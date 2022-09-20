/*
 * INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * 
 * Hiver 2017 / TP1
 * 
 * Auteur: Diallo Mamadou Aliou(DIAM02079904)
 * Auteur: Foka Samekong Gustave(FOKG21088509)
 * Auteur: NJampou Djamouo Steeven(NJAS76060007)
 */


import java.util.*;

public class AEtoile {

	// Démarche suggérée.
    // -- Commencez avec une heuristique minimaliste (toujours 0) et testez avec un problème TRÈS simple
    // -- Tracez les itérations sur la liste open sur la console avec System.out.println(..).
    // -- Pour chaque itération :
    // ---- Affichez le numéro d'itération.
    // ---- Affichez l'état e sélectionné par l'intération (les e.f affichés devraient croître);
	// -- Vérifiez le bon fonctionnement de la génération des états.
    // -- Vérifiez que e.f soit non-décroissant (>=) tout au long.
    //  Lorsque l'implémentation est terminée et validée, évaluez la pertinence d'un PriorityQueue.
	//  Élaborez des heuristiques plus réalistes


    public static List<String> composerPlan(final Etat etatInitial, final But but, final Heuristique heuristique){
  
		// Déclaration des variables pour les listes des états à examiner (open) et déjà examinés (closed)
        final TreeSet<Etat> open = new TreeSet<>(new AEtoile.FComparator());
		// Une liste auxiliaitre qui reflète fidèlement le contenu de open
        // nécessaire car la recherche dans open doit se faire d'abord par valeur de f et ensuite sur les variables définissant un état 
        // cela ne permet pas de repérer la présence du même état avec une valeur f différente
        final TreeSet<Etat> checkOpen = new TreeSet<>();
        final TreeSet<Etat> closed = new TreeSet<>();
		// la sortie est une liste d'actions, chacune étant une chaîne de caractères
        final LinkedList<String> plan =  new LinkedList<String>(); //variable à retourner
        LinkedList<String> plan2 =  new LinkedList<String>(); //variable à retourner
        
		//  Shéma de l'algorithme A*    
		
        //initialisation des valeurs de distances (exacte et heuristique)
        etatInitial.g = 0;
        etatInitial.h = heuristique.estimerCoutRestant(etatInitial, etatInitial.emplacementsColis[0]);
        etatInitial.f=etatInitial.g+etatInitial.h;
        Emplacement emplacementBut = null;
        //  Suite de l'algorithme A* - A COMPLÉTER
        Etat e =null;
        Collection<Successeur> successeurs=null;
        FComparator fcompar = new FComparator();
        e= etatInitial;
        Etat etatPrecedent = etatInitial;
        for (int i = 0; i < e.emplacementsColis.length+1; i++) {
            boolean colis = false;
            open.add(e);
            checkOpen.add(e);
            if (i < e.emplacementsColis.length){
                emplacementBut = e.emplacementsColis[i];
                colis = true;
            } 
            if (i >= e.emplacementsColis.length) {
                emplacementBut = but.destinationsPatients[i-1];
                colis = false;
            }
            while(!open.isEmpty()){
                e = open.first();  
                if(but.estStatisfait(e)) {
                    System.out.println("sortie");
                    break;
                } 
                closed.add(e);
                successeurs= e.enumererEtatsSuccesseurs();
                for (Successeur successeur : successeurs) {
                    successeur.etat.h = heuristique.estimerCoutRestant(successeur.etat, emplacementBut);
                    successeur.etat.f= successeur.etat.g + successeur.etat.h;
                    if(!closed.contains(successeur.etat)){
                        if(open.contains(successeur.etat)){
                            for (Etat eta  : open) {
                                if (fcompar.compare(successeur.etat, eta)== -1
                                     && successeur.etat.emplacementVan.compareTo(eta.emplacementVan) ==0){
                                    open.add(successeur.etat);
                                    checkOpen.add(successeur.etat);
                                }
                            }   
                        }else if (!open.contains(successeur.etat)){
                            open.add(successeur.etat);
                            checkOpen.add(successeur.etat);
                        }
                    }
                }
                if ((e.emplacementVan).compareTo(emplacementBut) == 0 && colis){
                    plan2 = ajouterRoute(open, plan2, e, etatPrecedent, plan);
                    etatPrecedent = e;
                    plan.add("Ramassage();");
                    break;
                }else if ((e.emplacementVan).compareTo(emplacementBut) == 0 && !colis){
                    plan2 = ajouterRoute(open, plan2, e, etatPrecedent, plan);
                    for (Emplacement lesColis : e.emplacementsColis) {
                        plan.add("Deposer()");
                    }
                    
                    break;
                }else{
                    open.remove(e);
                }
            }
            open.clear(); 
            closed.clear();  
        }
        return plan;
    }



	// Comparatrice pour les états qui se base sur les valeurs de f
    static class FComparator implements Comparator<Etat> {
        @Override
        public int compare(Etat e1, Etat e2) {
            if(e1.f<e2.f)
                return -1;
            if(e1.f>e2.f)
                return +1;

            return e1.compareTo(e2);
        }
    }

    static LinkedList<String> ajouterRoute(final TreeSet<Etat> open, LinkedList<String> plan2, Etat actuel, Etat etatInitial, LinkedList<String> plan){

        while(actuel.compareTo(etatInitial)!=0){
            plan2.add( actuel.actionFromParent + " = Lieu " + actuel.parent.emplacementVan +
             " -> Lieu " + actuel.emplacementVan);
            actuel=actuel.parent;
        }
        while (plan2.size() != 0) {
            plan.add(plan2.getLast());
            plan2.removeLast();
        }
        return plan2;
    }
}
