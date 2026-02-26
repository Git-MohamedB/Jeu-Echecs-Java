public class reine extends piece {
    public reine(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases destination) {
        int diffRangee = Math.abs(destination.getRangee() - this.position.getRangee());
        int diffColonne = Math.abs(destination.getColonne() - this.position.getColonne());
        boolean diagonale = (diffRangee == diffColonne && diffRangee > 0);
        boolean ligne     = (diffRangee == 0 && diffColonne > 0);
        boolean colonne   = (diffColonne == 0 && diffRangee > 0);
        return (diagonale || ligne || colonne);
    }

    @Override
    protected char symbole() {
         // 'D' pour Dame/Reine
        return 'D'; 
    }
}
