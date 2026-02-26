public class roi extends piece {
    public roi(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases destination) {
        int diffRangee = Math.abs(destination.getRangee() - this.position.getRangee());
        int diffColonne = Math.abs(destination.getColonne() - this.position.getColonne());
        return (diffRangee <= 1 && diffColonne <= 1 && (diffRangee + diffColonne > 0));
    }

    @Override
    protected char symbole() {
        // 'R' pour Roi
        return 'R';  
    }
}
