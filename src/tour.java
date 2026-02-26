public class tour extends piece {
    public tour(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases destination) {
        int diffRangee = Math.abs(destination.getRangee() - this.position.getRangee());
        int diffColonne = Math.abs(destination.getColonne() - this.position.getColonne());
        return ((diffRangee == 0 && diffColonne > 0) || (diffColonne == 0 && diffRangee > 0));
    }

    @Override
    protected char symbole() {
        return 'T';
    }
}
