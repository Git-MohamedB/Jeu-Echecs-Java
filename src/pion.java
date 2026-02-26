public class pion extends piece {
    public pion(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases dest) {
        int rangDepart   = this.position.getRangee();
        int colDepart    = this.position.getColonne();
        int rangDestination = dest.getRangee();
        int colDestination  = dest.getColonne();
        int diffRangee   = rangDestination - rangDepart;
        int diffColonne  = Math.abs(colDestination - colDepart);

        if (estBlanc) {
            // Avance d'une case
            if (diffRangee == 1 && diffColonne == 0) return true;
            // Avance de deux cases depuis rangée 2
            if (rangDepart == 2 && diffRangee == 2 && diffColonne == 0) return true;
            // Prise diagonale
            if (diffRangee == 1 && diffColonne == 1) return true;
        } else {
            // Noir : avance d'une case
            if (diffRangee == -1 && diffColonne == 0) return true;
            // Avance de deux cases depuis rangée 7
            if (rangDepart == 7 && diffRangee == -2 && diffColonne == 0) return true;
            // Prise diagonale
            if (diffRangee == -1 && diffColonne == 1) return true;
        }
        return false;
    }

    @Override
    protected char symbole() {
         // 'P' pour Pion
        return 'P'; 
    }
}
