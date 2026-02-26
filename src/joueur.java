import java.util.ArrayList;
import java.util.List;

public class joueur {
    private String nom;
     // true si ce joueur joue les blancs, false si noir
    private boolean estBlanc;      
    // l’horloge de ce joueur
    private final horloge horloge; 
    // liste de références aux pièces que possède (ou contrôlées) ce joueur       
    private List<piece> pieces;     

    /**
     * Construit un joueur.
     * @param nom nom du joueur
     * @param estBlanc true si joue les pièces blanches
     */
    public joueur(String nom, boolean estBlanc) {
        this.nom = nom;
        this.estBlanc = estBlanc;
        this.horloge = new horloge();
        this.pieces = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public horloge getHorloge() {
        return horloge;
    }

    public List<piece> getPieces() {
        return pieces;
    }

    public boolean estBlanc() {
        return estBlanc;
    }
     //Ajoute une pièce à la liste des pièces contrôlées par le joueur.
    public void ajoutePiece(piece p) {
        pieces.add(p);
    }

    
     // Retire une pièce (ex. lorsqu'elle est capturée).
    public void retirerPiece(piece p) {
        pieces.remove(p);
    }

    @Override
    public String toString() {
        return nom + (estBlanc ? " (Blanc)" : " (Noir)");
    }
}
