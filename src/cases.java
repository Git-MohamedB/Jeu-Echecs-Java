public class cases {
    // de 1 à 8
    private int rangee;    
    // de 1 à 8
    private int colonne;   

    /**
     * Construit une case à partir d'une rangée et d'une colonne 
     * @param rangee numéro de la rangée (1 = rangée du bas pour les Blancs, 8 = rangée du haut)
     * @param colonne numéro de la colonne (1 = colonne 'a', 8 = colonne 'h')
     * @throws IllegalArgumentException si les valeurs ne sont pas dans [1..8]
     */
    public cases(int rangee, int colonne) {
        if (rangee < 1 || rangee > 8 || colonne < 1 || colonne > 8) {
            throw new IllegalArgumentException("Les indices de case doivent être entre 1 et 8.");
        }
        this.rangee = rangee;
        this.colonne = colonne;
    }

    /**
     * Construit une case à partir d’une notation en chiffre (par exemple "e4").
     * @param coup chaîne de longueur 2, première lettre entre 'a' et 'h', chiffre entre '1' et '8'
     * @throws IllegalArgumentException si la notation n’est pas valide
     */
    public cases(String coup) {
    if (coup == null || coup.length() != 2) {
        throw new IllegalArgumentException("Notation de case invalide : " + coup);
    }
    char lettreColonne = coup.charAt(0);
    char chiffreRangee = coup.charAt(1);

    if (lettreColonne < 'a' || lettreColonne > 'h' || chiffreRangee < '1' || chiffreRangee > '8') {
        throw new IllegalArgumentException("Notation de case invalide : " + coup);
    }
    this.colonne = lettreColonne - 'a' + 1;
    this.rangee   = chiffreRangee - '1' + 1;
    }

    public int getRangee() {
        return rangee;
    }

    public int getColonne() {
        return colonne;
    }

    public void setRangee(int rangee) {
        if (rangee < 1 || rangee > 8) {
            throw new IllegalArgumentException("La rangée doit être entre 1 et 8.");
        }
        this.rangee = rangee;
    }

    public void setColonne(int colonne) {
        if (colonne < 1 || colonne > 8) {
            throw new IllegalArgumentException("La colonne doit être entre 1 et 8.");
        }
        this.colonne = colonne;
    }

    
     // Retourne la représentation de la case, par exemple "a1", "h8", etc.
     
    @Override
    public String toString() {
        char ligneColonne = (char) ('a' + colonne - 1);
        char chiffreRangee = (char) ('1' + rangee - 1);
        return "" + ligneColonne + chiffreRangee;
    }

    
     // Compare deux cases : mêmes rangée et colonne.
     
     @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        cases autre = (cases) obj;
        return this.rangee == autre.rangee && this.colonne == autre.colonne;
    }


}
