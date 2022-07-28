import java.util.Random;

/**
 * Nome classe: MorraCinese
 * Questa classe è stata scritta come supporto al file MorraCinese.javache si trova nella stessa cartella, e contiene
 * il funzionamento logico della morra cinese.
 * Invece, questa classe contiene il codice per l'interfaccia grafica della morra cinese, estendendo la superclasse
 * JFrame e creando una nuovo instanza anonima MorraCineseGUI all'interno del main.
 *
 * @author Bisinella Adam
 */
public class MorraCinese {

    /**
     * Variabili di eseplare: array di mosse, mossa utente e mossa random; inoltre anche morre perse, vinte e
     * pareggiate.
     */
    final private String[] MOSSE = {"sasso", "carta", "forbice"};
    private int mossa;
    private int rand;

    private int perse;
    private int vinte;
    private int pareggi;

    /**
     * Costruttore vuoto, senza parametri. Imposta le variabili in della morra negative e quelle dei risultati
     * a 0. mossa e rand sono negative perché all'esecuzione del programma facciamo un controllo che ci dice
     * se le abbiamo impostate correttamente.
     */
    public MorraCinese() {
        mossa = -1;
        rand = -1;

        perse = 0;
        vinte = 0;
        pareggi = 0;
    }
    /**
     * Imposta la mossa dell'utente.
     * @param mossa mossa utente
     */
    public void setMossa(int mossa) {
        this.mossa = mossa;
    }
    /**
     * Imposta la mossa del computer, random.
     */
    public void setRand() {
        Random gen = new Random();
        rand = gen.nextInt(3);
    }
    /**
     * Confronta la mossa e rand, dicendo quale dei due ha vinto.
     * @return risultato della morra cinese.
     */
    public String esitoMorra() {
        String pareggioText = "pareggio";
        String vittoriaText = "vittoria";
        String sconfittaText = "sconfitta";
        if (mossa >= 0 && mossa < 3 && rand >= 0 && rand < 3) {
            if (mossa == rand) {
                pareggi++;
                return pareggioText;
            } else {
                if (mossa == 0) {
                    if (rand == 1) {
                        perse++;
                        return sconfittaText;
                    } else {
                        vinte++;
                        return vittoriaText;
                    }
                } else if (mossa == 1) {
                    if (rand == 0) {
                        vinte++;
                        return vittoriaText;
                    } else {
                        perse++;
                        return sconfittaText;
                    }
                } else {
                    if (rand == 0) {
                        perse++;
                        return sconfittaText;
                    } else {
                        vinte++;
                        return vittoriaText;
                    }
                }
            }
        }
        return "Benvenuto";
    }

    /**
     * Azzera tutti i valori. Gli imposta come quelli iniziali del costruttore senza parametri simulando una morra
     * completamente nuova.
     */
    public void azzera() {
        mossa = -1;
        rand = -1;

        perse = 0;
        vinte = 0;
        pareggi = 0;
    }

    /**
     * Ottiene la mossa utente in stringa.
     * @return mossa utente.
     */
    public String getMossa() {
        return MOSSE[mossa];
    }

    /**
     * Ottiene la mossa del computer, random in stringa.
     * @return mossa computer, random.
     */
    public String getRand() {
        return MOSSE[rand];
    }

    /**
     * Imposta le partite vinte nella morra cinese.
     * @param vinte morra vinte.
     */
    public void setVinte(int vinte) {
        this.vinte = vinte;
    }

    /**
     * Ottiene le partite vinte attualmente nella morra cinese.
     * @return partite vinte.
     */
    public int getVinte() {
        return vinte;
    }

    /**
     * Ottiene la percentuale delle partite vinte attualmente nella morra cinese.
     * @return percentuale partite vinte.
     */
    public int getPerVinte() {
        double tot = vinte + perse + pareggi;
        return (int) ((vinte / tot) * 100);
    }

    /**
     * Imposta le partite perse nella morra cinese.
     * @param vinte morra perse.
     */
    public void setPerse(int perse) {
        this.perse = perse;
    }

    /**
     * Ottiene le partite perse attualmente nella morra cinese.
     * @return partite perse.
     */
    public int getPerse() {
        return perse;
    }

    /**
     * Ottiene la percentuale delle partite perse attualmente nella morra cinese.
     * @return percentuale partite perse.
     */
    public int getPerPerse() {
        double tot = vinte + perse + pareggi;
        return (int) ((perse / tot) * 100);
    }

    /**
     * Imposta le partite pareggiate nella morra cinese.
     * @param vinte morra pareggiate.
     */
    public void setPareggi(int pareggi) {
        this.pareggi = pareggi;
    }

    /**
     * Ottiene le partite pareggiate attualmente nella morra cinese.
     * @return partite pareggiate.
     */
    public int getPareggi() {
        return pareggi;
    }

    /**
     * Ottiene la percentuale delle partite pareggiate attualmente nella morra cinese.
     * @return percentuale partite pareggiate.
     */
    public int getPerPareggi() {
        double tot = vinte + perse + pareggi;
        return (int) ((pareggi / tot) * 100);
    }

    /**
     * Ottiene le partite totali attualmente giocate nella morra cinese.
     * @return partite giocate.
     */
    public int getTotali() {
        return vinte + perse + pareggi;
    }
}
