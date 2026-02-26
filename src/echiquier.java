import java.util.ArrayList;
import java.util.List;

public class echiquier {
    // 8×8, indices [0..7][0..7], où 0 = rangée 1, 7 = rangée 8
    private piece[][] plateau; 

    public echiquier() {
        plateau = new piece[8][8];
        initialiserPosDeDepart();
    }

    /**
     * Initialise les pièces dans la disposition classique au départ.
     * Les blancs en rangée 1-2 (indices internes 0-1), noirs en rangée 7-8 (indices 6-7).
     */
    private void initialiserPosDeDepart() {
        // On crée d'abord les pions
        for (int col = 0; col < 8; col++) {
            // Pions blancs en rangée 2 
            plateau[1][col] = new pion(true, new cases(2, col + 1));
            // Pions noirs en rangée 7 
            plateau[6][col] = new pion(false, new cases(7, col + 1));
        }

        // puis ensuite: Tour, Cavalier, Fou, Reine, Roi, Fou, Cavalier, Tour
        piece[] initBlanc = new piece[] {
            new tour(true,   new cases(1, 1)),
            new cavalier(true, new cases(1, 2)),
            new fou(true,    new cases(1, 3)),
            new reine(true,  new cases(1, 4)),
            new roi(true,    new cases(1, 5)),
            new fou(true,    new cases(1, 6)),
            new cavalier(true, new cases(1, 7)),
            new tour(true,   new cases(1, 8))
        };
        for (int i = 0; i < 8; i++) {
            plateau[0][i] = initBlanc[i];
        }

        // De même pour les noirs, rangée 8-7
        piece[] initNoir = new piece[] {
            new tour(false,   new cases(8, 1)),
            new cavalier(false, new cases(8, 2)),
            new fou(false,    new cases(8, 3)),
            new reine(false,  new cases(8, 4)),
            new roi(false,    new cases(8, 5)),
            new fou(false,    new cases(8, 6)),
            new cavalier(false, new cases(8, 7)),
            new tour(false,   new cases(8, 8))
        };
        for (int i = 0; i < 8; i++) {
            plateau[7][i] = initNoir[i];
        }
        // Les autres cases restent null, c'est-à-dire vides
    }

    
     // Retourne la pièce située sur la case donnée (ou null si vide ou hors limites).
    public piece getPosPiece(cases c) {
        if (!caseDansEchiquier(c)) return null;
        return plateau[c.getRangee() - 1][c.getColonne() - 1];
    }

    /**
     * Retourne la liste de toutes les pièces encore présentes (non saisies), 
     * utile pour vérifier la présence des rois, etc.
     */
    public List<piece> getAllPieces() {
        List<piece> liste = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (plateau[r][c] != null) {
                    liste.add(plateau[r][c]);
                }
            }
        }
        return liste;
    }
    
     // Place ou remplace une pièce sur la case donnée. Si p == null, on vide la case.
    public void setPieceEn(cases c, piece p) {
        if (!caseDansEchiquier(c)) return;
        plateau[c.getRangee() - 1][c.getColonne() - 1] = p;
        if (p != null) {
            p.setPosition(c);
        }
    }

    
     // Vérifie si la case donnée est dans l'échiquier (rangée et colonne entre 1 et 8).
    public boolean caseDansEchiquier(cases c) {
        int r = c.getRangee(), co = c.getColonne();
        return (r >= 1 && r <= 8 && co >= 1 && co <= 8);
    }

    
     // Vérifie si la case spécifiée est vide.
    public boolean estCaseVide(cases c) {
        return getPosPiece(c) == null;
    }

    /**
     * Vérifie si le chemin entre la case de départ et la case de destination est possible pour
     * une pièce glissante (tour, fou, reine). Mais cela ne gère pas la case de destination.
     * @return true si toutes les cases intermédiaires sont null (donc pas de pièces).
     */
    public boolean cheminPossible(cases depart, cases dest) {
        int rangee1 = depart.getRangee(), colonne1 = depart.getColonne();
        int rangee2 = dest.getRangee(), colonne2 = dest.getColonne();
        int diffRangee = Integer.compare(rangee2, rangee1); // -1, 0 ou +1
        int diffcolonne = Integer.compare(colonne2, colonne1); // -1, 0 ou +1

        // Si déplacement non linéaire (ni ligne ni colonne ni diagonal), on considère que c'est pas possible
        if (!(diffRangee == 0 || diffcolonne == 0 || Math.abs(rangee2 - rangee1) == Math.abs(colonne2 - colonne1))) {
            return false;
        }

        int actuelRangee = rangee1 + diffRangee;
        int actuelColonne = colonne1 + diffcolonne;
        while (actuelRangee != rangee2 || actuelColonne != colonne2) {
            if (plateau[actuelRangee - 1][actuelColonne - 1] != null) {
                return false;
            }
            actuelRangee += diffRangee;
            actuelColonne += diffcolonne;
        }
        return true;
    }

    /**
     * Affiche en mode texte l’échiquier dans l’état actuel.
     * Les pièces blanches en majuscule, noires en minuscule, cases vides par ".".
     * On affiche rangée 8 en haut jusqu'à 1 en bas, colonnes 'a'..'h' en bas.
     */
    public void afficher() {
    // 1) Afficher l’en-tête des colonnes
    System.out.print("   ");
    for (char c = 'a'; c <= 'h'; c++) {
        System.out.print("  " + c + " ");
    }
    System.out.println();

    // Pour chaque rangée 8 à 1
    for (int r = 7; r >= 0; r--) {
        // Ligne de séparation horizontale
        System.out.print("   ");
        for (int i = 0; i < 8; i++) {
            System.out.print("+---");
        }
        System.out.println("+");

        // Contenu de la rangée
        System.out.print(" " + (r + 1) + " ");
        for (int c = 0; c < 8; c++) {
            System.out.print("| ");
            piece p = plateau[r][c];
            if (p == null) {
                System.out.print(" ");
            } else {
                System.out.print(p.toString());
            }
            System.out.print(" ");
        }
        System.out.println("| " + (r + 1));
    }

    // Dernière ligne de séparation
    System.out.print("   ");
    for (int i = 0; i < 8; i++) {
        System.out.print("+---");
    }
    System.out.println("+");

    // Ré-afficher l’en-tête des colonnes en bas
    System.out.print("   ");
    for (char c = 'a'; c <= 'h'; c++) {
        System.out.print("  " + c + " ");
    }
    System.out.println("\n");
}


    

    /**
     * Déplace une pièce de la case depart vers la case dest, capture si nécessaire.
     * Cela ne vérifie pas la légalité (chemin libre, forme du déplacement, etc.).
     * @return true si une pièce adverse a été capturée, false sinon
     */
    public boolean bougerPiece(cases depart, cases dest) {
        piece p = getPosPiece(depart);
        if (p == null) return false;
        piece cible = getPosPiece(dest);
        boolean capture = (cible != null);
        // On écrase simplement la référence si capture
        setPieceEn(dest, p);
        setPieceEn(depart, null);
        return capture;
    }
}
