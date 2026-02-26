public class cavalier extends piece {
    public cavalier(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases destination) {
        int diffRangee = Math.abs(destination.getRangee() - this.position.getRangee());
        int diffColonne = Math.abs(destination.getColonne() - this.position.getColonne());
        return ((diffRangee == 2 && diffColonne == 1) || (diffRangee == 1 && diffColonne == 2));
    }

    @Override
    protected char symbole() {
        // 'C' pour Cavalier
        return 'C';  
    }
}
