public abstract class piece {
    protected boolean estBlanc;
    protected cases position;

    public piece(boolean estBlanc, cases position) {
        this.estBlanc = estBlanc;
        this.position = position;
    }

    public boolean isBlanc() {
        return estBlanc;
    }

    public cases getPosition() {
        return position;
    }

    public void setPosition(cases destination) {
        this.position = destination;
    }

    public abstract boolean deplacement(cases destination);
    protected abstract char symbole();

    @Override
    public String toString() {
        char s = symbole();
        if (estBlanc) {
            return "" + s;
        } else {
            return "" + Character.toLowerCase(s);
        }
    }
}
