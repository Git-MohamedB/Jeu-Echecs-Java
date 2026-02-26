public class horloge {
    // temps total de réflexion en ms
    private long tempsTotalEnMS;    
    // timestamp du début du chrono actuel (en ms), 0 = arrêté
    private long debut;     
    private boolean enCours;

    
     //Construit une horloge initialisée à 0.
    public horloge() {
        this.tempsTotalEnMS = 0;
        this.debut = 0;
        this.enCours = false;
    }
    /**
     * Retourne le temps total de réflexion accumulé (en ms).
     * Si l'horloge est en marche, ajoute aussi le temps écoulé depuis le dernier démarrage.
     */
    public long getTempsTotalEnMs() {
        if (enCours) {
            long maintenant = System.currentTimeMillis();
            return tempsTotalEnMS + (maintenant - debut);
        } else {
            return tempsTotalEnMS;
        }
    }

    /**
     * Retourne le temps de réflexion écoulé depuis que l'horloge a été démarrée,
     * sans inclure le temps déjà accumulé auparavant. Si l'horloge est arrêtée, renvoie 0.
     */
    public long getTempsIntervalCourtEnMs() {
        if (enCours) {
            long maintenant = System.currentTimeMillis();
            return maintenant - debut;
        } else {
            return 0;
        }
    }
    /**
     * Démarre l'horloge. Si elle est déjà en marche, cela n'a pas d'effet.
     * Le temps total n'est pas modifié à ce moment, 
     * on commence à compter à partir de System.currentTimeMillis().
     */
    public void demarrer() {
        if (!enCours) {
            debut = System.currentTimeMillis();
            enCours = true;
        }
    }

    /**
     * Arrête l'horloge. Si elle est déjà arrêtée, cela n'a pas d'effet.
     * Ajoute à tempsTotalEnMS le temps écoulé depuis le dernier démarrage.
     */
    public void arreter() {
        if (enCours) {
            long maintenant = System.currentTimeMillis();
            tempsTotalEnMS += (maintenant - debut);
            debut = 0;
            enCours = false;
        }
    }

    /**
     * Réinitialise complètement l'horloge (met le temps total et intervalle courant à zéro).
     * Si l'horloge était en marche, on l'arrête d'abord.
     */
    public void reinitialiser() {
        if (enCours) {
            arreter();
        }
        tempsTotalEnMS = 0;
        debut = 0;
        enCours = false;
    }

    

    
     // Indique si l'horloge est en marche.
    public boolean EstEnCours() {
        return enCours;
    }

    
     //Pour affichage texte : convertit un milliseconde en chaîne "MM:SS".
    public static String formatMsEnMinSec(long ms) {
        long totalSec = ms / 1000;
        long minutes = totalSec / 60;
        long secondes = totalSec % 60;
        return String.format("%02d:%02d", minutes, secondes);
    }

    
     // Retourne une chaîne lisible indiquant le temps total, exemple "05:23".
     
    @Override
    public String toString() {
        return formatMsEnMinSec(getTempsTotalEnMs());
    }
}
