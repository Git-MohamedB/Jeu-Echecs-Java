import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class partie {
    private echiquier echiquier;
    private joueur joueurBlanc;
    private joueur joueurNoir;
    // true si c'est au tour des blancs
    private boolean tourBlanc;           
     // liste des notations textuelles des coups
    private final List<String> historiqueCoups;
    // pour saisir depuis le terminal
    private final Scanner scan;              

    public partie() {
        scan = new Scanner(System.in);
        historiqueCoups = new ArrayList<>();
        initialiserPartie();
    }

    
    //Initialise la partie : demande les noms, crée les joueurs, l'échiquier, etc.
    private void initialiserPartie() {
        System.out.print("Nom du joueur côté blanc : ");
        String nomBlanc = scan.nextLine().trim();
        System.out.print("Nom du joueur côté noir : ");
        String nomNoir = scan.nextLine().trim();

        joueurBlanc = new joueur(nomBlanc, true);
        joueurNoir  = new joueur(nomNoir, false);
        // les blancs commencent
        tourBlanc  = true; 

        // Crée un nouvel échiquier au départ
        echiquier = new echiquier();

        // Renseigne aux joueurs la liste de leurs pièces
        for (piece p : echiquier.getAllPieces()) {
            if (p.isBlanc()) joueurBlanc.ajoutePiece(p);
            else              joueurNoir.ajoutePiece(p);
        }
    }

    
     // Boucle principale de la partie. Tant que la partie n'est pas terminée, on alterne les tours.
    public void jouer() {
        boolean partiefinie = false;
        while (!partiefinie) {
            //  Affiche l'état
            afficherEtat();

            // Gère les pendules
            if (tourBlanc) {
                joueurBlanc.getHorloge().demarrer();
                joueurNoir.getHorloge().arreter();
            } else {
                joueurNoir.getHorloge().demarrer();
                joueurBlanc.getHorloge().arreter();
            }

            // Saisie du coup
            System.out.print((tourBlanc ? joueurBlanc.getNom() : joueurNoir.getNom())
                    + " (" + (tourBlanc ? "Blanc" : "Noir") + "), entrez votre coup (de cette manière: b2 b4) : ");
            String ligne = scan.nextLine().trim();
            String[] parts = ligne.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Entrée invalide. Syntaxe attendue : <caseDepart> <caseDestination>");
                continue;
            }

            cases début, dest;
            try {
                début = new cases(parts[0]);
                dest   = new cases(parts[1]);
            } catch (IllegalArgumentException ex) {
                System.out.println("Notation de case invalide. Réessayez.");
                continue;
            }

            // Valider le coup
            if (!confirmerCoup(début, dest)) {
                System.out.println("Coup invalide. Réessayez.");
                continue;
            }

            // Enregistrer le coup
            sauvegarderCoup(début, dest);

            // Vérifier fin de partie
            if (verifierFinPartie()) {
                partiefinie = true;
                continuerFinPartie();
                break;
            }

            // On passe au tour suivant
            tourBlanc = !tourBlanc;
        }

        // On arrete les deux horloges
        joueurBlanc.getHorloge().arreter();
        joueurNoir.getHorloge().arreter();
        scan.close();
    }

    
     // Affiche position + historique des coups + pendules.
    private void afficherEtat() {
        echiquier.afficher();

        // Affiche l'historique des coups
        System.out.println("Historique des coups :");
        for (int i = 0; i < historiqueCoups.size(); i++) {
            if (i % 2 == 0) {
                // À chaque coup blanc, on affiche le numéro de la paire
                System.out.print((i/2 + 1) + ". ");
            }
            System.out.print(historiqueCoups.get(i) + " ");
            if (i % 2 == 1) System.out.println();
        }
        if (historiqueCoups.size() % 2 == 1) System.out.println();

        // Affiche les pendules
        System.out.println(joueurBlanc.getNom() + " (Blanc) - Temps total : "
                + horloge.formatMsEnMinSec(joueurBlanc.getHorloge().getTempsTotalEnMs())
                + (tourBlanc ? " *EN JEU* " : ""));
        System.out.println(joueurNoir.getNom() + " (Noir) - Temps total : "
                + horloge.formatMsEnMinSec(joueurNoir.getHorloge().getTempsTotalEnMs())
                + (!tourBlanc ? " *EN JEU* " : ""));
        System.out.println();
    }

    /**
     * Valide un coup par rapport à l'échiquier :
     *  - il y a bien une pièce alliée sur la case depart,
     *  - la déplacement est correcte,
     *  - la destination est dans l’échiquier,
     *  - si destination occupée par allié implique que c'est invalide,
     *  - si pièce glissante (tour, fou, reine) donc le chemin est libre,
     *  - pour pion : avance vertical simple, double depuis rangée initiale, prise diagonale.
     */
    private boolean confirmerCoup(cases depart, cases dest) {
        piece p = echiquier.getPosPiece(depart);
        if (p == null) {
            System.out.println("Il n'y a aucune pièce sur la case " + depart);
            return false;
        }
        // Vérifier la couleur
        if (tourBlanc && !p.isBlanc()) {
            System.out.println("Ce n'est pas une pièce blanche.");
            return false;
        }
        if (!tourBlanc && p.isBlanc()) {
            System.out.println("Ce n'est pas une pièce noire.");
            return false;
        }
        // Vérifier la destination hors échiquier
        if (!echiquier.caseDansEchiquier(dest)) {
            System.out.println("Destination hors échiquier.");
            return false;
        }
        // Forme du déplacement 
        if (!p.deplacement(dest)) {
            System.out.println("Déplacement non conforme à la pièce.");
            return false;
        }

        // Si la destination occupée par allié
        piece cible = echiquier.getPosPiece(dest);
        if (cible != null && cible.isBlanc() == p.isBlanc()) {
            System.out.println("Pièce alliée déjà sur la case " + dest);
            return false;
        }
        String nomClasse = p.getClass().getSimpleName().toLowerCase();

        // Si glissant (tour, fou, reine), vérifier chemin libre
        if (nomClasse.equals("tour") || nomClasse.equals("fou") || nomClasse.equals("reine")) {
            if (!echiquier.cheminPossible(depart, dest)) {
                System.out.println("Le chemin n'est pas libre.");
                return false;
            }
        }

        // Spécifique au pion : avance simple, double, prise diagonale
        if (nomClasse.equals("pion")) {
            int rangeeDepart = depart.getRangee();
            int colonneDepart = depart.getColonne();
            int rangeeDest = dest.getRangee();
            int colonneDest = dest.getColonne();
            int diffRangee = rangeeDest - rangeeDepart;
            int diffColonne = Math.abs(colonneDest - colonneDepart);

            // Avance d'une case (diffcolonne == 0, |diffrangee| == 1) : la case de destination doit être vide
            if (diffColonne == 0 && Math.abs(diffRangee) == 1) {
                if (!echiquier.estCaseVide(dest)) {
                    System.out.println("Le pion ne peut avancer que si la case devant est vide.");
                    return false;
                }
            }
            // Avance de deux cases (diffcolonne == 0, |diffrangee| == 2) : 
            //    On doit être sur rangée de départ (2 pour blanc, 7 pour noir)
            //    et la case intermédiaire et la case de destination doivent être vides
            else if (diffColonne == 0 && Math.abs(diffRangee) == 2) {
                if (p.isBlanc()) {
                    if (rangeeDepart != 2) {
                        System.out.println("Le pion blanc ne peut avancer de deux cases que depuis la rangée 2.");
                        return false;
                    }
                    // Case intermédiaire = rangée 3
                    cases inter = new cases(3, colonneDepart);
                    if (!echiquier.estCaseVide(inter) || !echiquier.estCaseVide(dest)) {
                        System.out.println("Impossible d'avancer de deux cases car la case est bloquée.");
                        return false;
                    }
                } else {
                    if (rangeeDepart != 7) {
                        System.out.println("Le pion noir ne peut avancer de deux cases que depuis la ligne 7.");
                        return false;
                    }
                    // Case intermédiaire = rangée 6
                    cases inter = new cases(6, colonneDepart);
                    if (!echiquier.estCaseVide(inter) || !echiquier.estCaseVide(dest)) {
                        System.out.println("Impossible d'avancer de deux cases car la case est bloquée.");
                        return false;
                    }
                }
            }
            // Prise diagonale (|diffrangee| == 1, |diffcolonne| == 1) : 
            //    Il doit y avoir une pièce adverse sur la destination
            else if (Math.abs(diffRangee) == 1 && diffColonne == 1) {
                if (cible == null || cible.isBlanc() == p.isBlanc()) {
                    System.out.println("Prise diagonale impossible (pas d'adversaire).");
                    return false;
                }
            }
            // Tout autre cas pour un pion est invalide
            else {
                System.out.println("Déplacement non autorisé pour un pion.");
                return false;
            }
        }

        return true;
    }

     // Enregistre le coup dans l’historique, déplace la pièce et gère la capture.
     
    private void sauvegarderCoup(cases depart, cases dest) {
    piece p = echiquier.getPosPiece(depart);
    piece target = echiquier.getPosPiece(dest);
    boolean prise = (target != null);

    // Déplacer la pièce (gère la capture interne)
    echiquier.bougerPiece(depart, dest);

    // Si capture, retirer du joueur adverse
    if (prise) {
        if (target.isBlanc()) {
            joueurBlanc.retirerPiece(target);
        } else {
            joueurNoir.retirerPiece(target);
        }
    }

    // Notation "e2-e4" ou  "e5xf6"
    String coup = depart.toString();
    if (prise) {
        coup = coup + "x";
    } else {
        coup = coup + "-";
    }
    coup = coup + dest.toString();
    historiqueCoups.add(coup);
}

    
     //Vérifie si la partie est terminée : un roi capturé.
    private boolean verifierFinPartie() {
        boolean roiBlancPresent = false;
        boolean roiNoirPresent  = false;
        for (piece p : echiquier.getAllPieces()) {
            String nomClasse = p.getClass().getSimpleName().toLowerCase();
            if (nomClasse.equals("roi")) {
                if (p.isBlanc()) roiBlancPresent = true;
                else             roiNoirPresent  = true;
            }
        }
        return (!roiBlancPresent || !roiNoirPresent);
    }

    
     // Affiche le résultat final : vainqueur ou nul, puis temps des pendules.
     
    private void continuerFinPartie() {
        boolean roiBlancPresent = false;
        boolean roiNoirPresent  = false;
        for (piece p : echiquier.getAllPieces()) {
            String nomClasse = p.getClass().getSimpleName().toLowerCase();
            if (nomClasse.equals("roi")) {
                if (p.isBlanc()) roiBlancPresent = true;
                else             roiNoirPresent  = true;
            }
        }
        System.out.println();
        echiquier.afficher();
        if (roiBlancPresent && !roiNoirPresent) {
            System.out.println("Roi noir capturé ! Vainqueur : " + joueurBlanc.getNom() + " (joueur Blanc).");
        } else if (!roiBlancPresent && roiNoirPresent) {
            System.out.println("Roi blanc capturé ! Vainqueur : " + joueurNoir.getNom() + " (joueur Noir).");
        } else {
            System.out.println("Partie nulle (aucun roi capturé).");
        }
        System.out.println("Résultat final des pendules :");
        System.out.println(joueurBlanc.getNom() + " (joueur Blanc) : "
                + horloge.formatMsEnMinSec(joueurBlanc.getHorloge().getTempsTotalEnMs()));
        System.out.println(joueurNoir.getNom() + " (joueur Noir) : "
                + horloge.formatMsEnMinSec(joueurNoir.getHorloge().getTempsTotalEnMs()));
    }

    
    // Point d'entrée principal si l'on veut lancer depuis le terminal : 'java partie'.
    public static void main(String[] args) {
        partie p = new partie();
        p.jouer();
    }
}
