public class fou extends piece {
    public fou(boolean estBlanc, cases position) {
        super(estBlanc, position);
    }

    @Override
    public boolean deplacement(cases destination) {
        int diffRangee = Math.abs(destination.getRangee() - this.position.getRangee());
        int diffColonne = Math.abs(destination.getColonne() - this.position.getColonne());
        return (diffRangee == diffColonne && diffRangee > 0);
    }

    @Override
    protected char symbole() {
        // 'F' pour Fou
        return 'F';  
    }
}
