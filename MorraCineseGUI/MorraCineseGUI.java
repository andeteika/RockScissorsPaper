import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;

/**
 * Nome classe: MorraCineseGUI Questa classe è stata scritta come supporto al
 * file MorraCinese.java: che si trova nella stessa cartella, e contiene il
 * funzionamento logico della morra cinese. Invece, questa classe contiene il
 * codice per l'interfaccia grafica della morra cinese, estendendo la
 * superclasse JFrame e creando una nuovo instanza anonima MorraCineseGUI
 * all'interno del main.
 *
 * @author Bisinella Adam
 */
public class MorraCineseGUI extends JFrame {

    /**
     * L'oggetto di tipo MorraCinese rappresenta il funzionamento logico della morra
     * cinese.
     */
    private final MorraCinese morra;

    /**
     * Componenti dell'interfaccia grafica dichiarate come variabili di esemplare.
     */
    private final JButton[] mosse;
    private final JLabel esito;
    private final JLabel[] dati;
    private final ImageIcon[] icone;

    /**
     * Font utilizzati.
     */
    final Font fontTitolo = new Font(Font.DIALOG, Font.BOLD, 35);
    final Font fontMossa = new Font(Font.DIALOG, Font.BOLD, 22);
    final Font fontCapo = new Font(Font.DIALOG, Font.BOLD, 18);
    final Font fontDefault = new Font(Font.DIALOG, Font.BOLD, 15);
    final Font fontProduttore = new Font(Font.DIALOG, Font.ITALIC, 15);

    /**
     * Questa è una classe interna di MorraCineseGUI che funziona come listener
     * unico per i tre buttoni che rapresentano le mosse. Se viene premuto il
     * bottone del sasso, viene impostata nella classe MorraCinese la mossa
     * dell'utente a 0. Se viene premuto il bottone della carta, viene impostata
     * nella classe MorraCinese la mossa dell'utente a 1. Se viene premuto il
     * bottone della forbice, viene impostato nella classe MorraCinese la mosse
     * dell'utente a 2.
     */
    class Calcola implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == mosse[0])
                morra.setMossa(0);
            else if (event.getSource() == mosse[1])
                morra.setMossa(1);
            else if (event.getSource() == mosse[2])
                morra.setMossa(2);

            morra.setRand();
            esito.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
            String esitoMorra = morra.esitoMorra();
            esito.setText(esitoMorra);

            JLabel testo = new JLabel();
            testo.setFont(fontDefault);

            if (esitoMorra.equals("vittoria")) {
                testo = new JLabel("<html>Congratulazioni! Hai vinto. <br>" + "Il computer ha tirato " + morra.getRand() + ".</html>");
                testo.setFont(fontDefault);
                JOptionPane.showMessageDialog(new JFrame(), testo, "Esito morra", JOptionPane.INFORMATION_MESSAGE,
                        icone[3]);
            } else if (esitoMorra.equals("sconfitta")) {
                testo = new JLabel("<html>Che peccato! Hai perso. <br>" + " Il computer ha tirato " + morra.getRand() + ".</html>");
                testo.setFont(fontDefault);
                JOptionPane.showMessageDialog(new JFrame(), testo, "Esito morra", JOptionPane.INFORMATION_MESSAGE,
                        icone[4]);
            } else {
                testo = new JLabel("<html>Accidenti! Hai pareggiato. <br>" + " Il computer ha tirato " + morra.getRand() + ".</html>");
                testo.setFont(fontDefault);
                JOptionPane.showMessageDialog(new JFrame(), testo, "Esito morra", JOptionPane.INFORMATION_MESSAGE,
                        icone[5]);
            }

            aggiornaDati();
        }
    }

    /**
     * Questa è una classe interna di MorraCineseGUI che funziona come listener che
     * permette il reset, l'azzeramento dei dati di gioco attuali.
     */
    class Azzera implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            morra.azzera();
            aggiornaDati();
        }
    }

    /**
     * Questa è una classe interna di MorraCineseGUI che funziona come Listener che
     * permette si salvare un file con i dati della partita attuale.
     */
    class SalvaListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /*
             * JFileChooser è l'argomento avanto 1.12 trattato a pagina 580. Pemette di
             * scegliere selezionare il nome e la destinazione del file da creare.
             */
            JFileChooser chooser = new JFileChooser();
            /*
             * Si usa l'istruzione condizionale if fuori dal costrutto try e catch, siccome
             * l'azione di salvataggio può essere annulata. In questo caso non deve essere
             * lanciata un'eccezione.
             */
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                PrintWriter out;
                try {
                    out = new PrintWriter(selectedFile);
                    out.println("PARTITA A MORRA CINESE");
                    out.println("Mosse vinte: " + morra.getVinte() + ", percentuale: " + morra.getPerVinte() + "%");
                    out.println("Mosse perse: " + morra.getPerse() + ", percentuale: " + morra.getPerPerse() + "%");
                    out.println("Mosse pareggiate: " + morra.getPareggi() + ", percentuale: " + morra.getPerPareggi()
                            + "%");
                    out.println("Mosse totali: " + morra.getTotali());
                    out.close();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "\"" + selectedFile.getName() + "\" salvato correttamente.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "\"" + selectedFile.getName() + "\" NON salvato correttamente.");
                }
            }
        }
    }

    /**
     * Questa è una classe interna di MorraCineseGUI che funziona come Listener che
     * permette si aprire un file con i dati salvati di una partita.
     */
    class ApriListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                Scanner in;
                try {
                    in = new Scanner(selectedFile);
                    /*
                     * useDelimiter() è un argomento trattato a pagina 583 del libro di testo.
                     * Permette di crare un range di caratteri. Lo scanner vedrà solo le parole
                     * (hesNext()) che rispettano il range usato come limitatore.
                     */
                    in.useDelimiter("[^0-9]+");
                    int i = 0;
                    /*
                     * Nel file di una partita salvata sono numeri (delimiter) solo i dati della
                     * partita e rispetterano un determinato ordine. Il primo, secondo, quarto
                     * saranno rispettivamente vinte, perse, pareggi. Ignoriamo le percentuali
                     * perché possiamo ricavarle dagli eseti appena ricavati.
                     */
                    while (in.hasNext()) {
                        int giocate = Integer.parseInt(in.next());
                        if (i == 0)
                            morra.setVinte(giocate);
                        else if (i == 2)
                            morra.setPerse(giocate);
                        else if (i == 4)
                            morra.setPareggi(giocate);
                        i++;
                    }
                    /*
                     * Invochiamo la funzione per aggiornare i JLabel delle statistiche dopo aver
                     * impostato i dati della partita salvata sulla morra attuale tramite i set().
                     */
                    aggiornaDati();
                    /**
                     * Chiusdiamo lo Scanner
                     */
                    in.close();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "\"" + selectedFile.getName() + "\" aperto correttamente.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "\"" + selectedFile.getName() + "\" NON aperto correttamente.");
                }
            }
        }
    }

    /**
     * Costruttore della classe MorraCineseGUI. Avendo esteso la classe JFrame,
     * consideriamo il seguente costruttore come il costruttore del frame.
     */
    public MorraCineseGUI() {
        /*
         * Viene invocato il costruttore della super() e vengono impostate le seguenti
         * opzioni del frame: dimensione, veridicità ridimensionamento, opzione di
         * chiusura del frame.
         */
        super("Morra Cinese");
        setSize(800, 450); // Imposta le dimensioni del frame.
        setResizable(false); // Non permette di modificare le dimensioni del frame.
        setLocationRelativeTo(null); // Centra il frame nello schermo.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Imposta l'opzione di chiusura.

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        /*
         * INTESTAZIONE del frame. Contiene il titolo dato come testata all'inizio del
         * frame.
         */
        JPanel intestazione = new JPanel();
        intestazione.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel titolo = new JLabel("Morra Cinese");
        titolo.setFont(fontTitolo);
        /*
         * Si utilizza un JPanel per centrare il JLabel nell'intestazione del frame. Il
         * JPanel centra in automatico il contenuto.
         */
        intestazione.add(titolo);

        pane.add(intestazione, BorderLayout.NORTH);

        /*
         * CENTRO del frame. Contiene i bottoni per la scelta della mossa della morra.
         */
        JPanel centro = new JPanel();
        /*
         * Il GridLayout crea una griglia con tutte le celle della stessa dimensione.
         * Una dimensione, non entrambe, può essere messa a 0; ciò significa che è
         * possibile posizionare un numero qualsiasi di oggetti in una riga o in una
         * colonna.
         */
        centro.setLayout(new GridLayout(0, 1));

        // Inizializza un nuovo oggetto di tipo MorraCinese.
        morra = new MorraCinese();

        icone = MorraCineseGUI.setIcone();

        JLabel scegli = new JLabel("Scegli la tua mossa");
        scegli.setFont(fontMossa);
        scegli.setHorizontalAlignment(JLabel.CENTER);
        centro.add(scegli);

        ActionListener listener = new Calcola();

        mosse = new JButton[3];

        mosse[0] = new JButton(icone[0]);
        mosse[1] = new JButton(icone[1]);
        mosse[2] = new JButton(icone[2]);

        JPanel bottoni = new JPanel();
        for (JButton e : mosse) {
            e.addActionListener(listener);
            e.setBackground(Color.WHITE);
            e.setOpaque(false);
            e.setBorderPainted(false);
            e.setBorder(null);
            bottoni.add(e);
        }
        bottoni.setBorder(new EmptyBorder(0, 50, 100, 50));
        centro.add(bottoni);

        pane.add(centro, BorderLayout.CENTER);

        /*
         * SINISTRA del frame Contiene le statistiche e il pulsante per il reset.
         */
        dati = new JLabel[4];

        JPanel stats = new JPanel();

        stats.setPreferredSize(new Dimension(180, 0));
        stats.setLayout(new GridLayout(0, 1));
        stats.setBorder(new EmptyBorder(5, 30, 5, 20));

        JLabel titoloStats = new JLabel("Statistiche");
        titoloStats.setFont(fontCapo);
        stats.add(titoloStats);
        for (int i = 0; i < dati.length; i++) {
            dati[i] = new JLabel();
            dati[i].setFont(fontDefault);
            stats.add(dati[i]);
        }
        aggiornaDati();

        JButton azzera = new JButton("Azzera");
        azzera.addActionListener(new Azzera());
        azzera.setBackground(Color.WHITE);
        azzera.setFont(fontDefault);
        stats.add(azzera);

        pane.add(stats, BorderLayout.WEST);

        /*
         * DESTRA del frame Contiene il salvataggio.
         */
        JPanel destra = new JPanel();
        destra.setLayout(new GridLayout(0, 1));
        destra.setBorder(new EmptyBorder(5, 30, 60, 30));

        JLabel sezione = new JLabel("Opzioni partita");
        sezione.setFont(fontCapo);
        destra.add(sezione);

        JLabel salvaLabel = new JLabel("Salva morra");
        salvaLabel.setFont(fontDefault);
        destra.add(salvaLabel);

        JButton salva = new JButton("Salva");

        salva.addActionListener(new SalvaListener());
        salva.setBackground(Color.WHITE);
        salva.setFont(fontDefault);
        destra.add(salva);

        JLabel apriLabel = new JLabel("Apri morra");
        apriLabel.setFont(fontDefault);
        destra.add(apriLabel);

        JButton apri = new JButton("Apri");

        apri.addActionListener(new ApriListener());
        apri.setBackground(Color.WHITE);
        apri.setFont(fontDefault);
        destra.add(apri);

        pane.add(destra, BorderLayout.EAST);

        /*
         * FINE del frame Contiene l'esito e il pulsante di uscita.
         */
        esito = new JLabel();

        JPanel fine = new JPanel();
        fine.setBorder(new EmptyBorder(10, 30, 10, 30));
        fine.setLayout(new BorderLayout());

        JLabel produttore = new JLabel("Favdy Games");
        produttore.setFont(fontProduttore);
        fine.add(produttore, BorderLayout.WEST);

        class Uscita implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }
        JButton esci = new JButton("Esci");
        esci.setBackground(Color.WHITE);
        esci.addActionListener(new Uscita());
        esci.setFont(fontDefault);
        fine.add(esci, BorderLayout.EAST);

        pane.add(fine, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Crea un array di ImageIcon con all'interno le icone desiderate prese da file
     * immagine e le scala tramite la conversione da ImageIcon a Image e l'uso del
     * metodo getScaledInstance(lunghezza, altezza, tipoDiScalatura).
     *
     * @return array di icone ridimensionate.
     */
    public static ImageIcon[] setIcone() {
        ImageIcon[] icone = new ImageIcon[6];
        icone[0] = new ImageIcon("./img/sasso.png");
        icone[1] = new ImageIcon("./img/carta.png");
        icone[2] = new ImageIcon("./img/forbice.png");
        icone[3] = new ImageIcon("./img/vittoria.png");
        icone[4] = new ImageIcon("./img/sconfitta.png");
        icone[5] = new ImageIcon("./img/pareggio.png");
        for (int i = 0; i < icone.length; i++) {
            Image immagine = icone[i].getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            icone[i] = new ImageIcon(immagine);
        }
        return icone;
    }

    /**
     * Permette di aggiornare il contatore di ogni JLabel nella sezione statistiche
     * all'avvio del frame e ad ogni volta che viene premuto il bottone di una
     * mossa, o viene azzerata la morra corrente.
     */
    public void aggiornaDati() {
        dati[0].setText("Vittorie: " + morra.getVinte() + ", " + morra.getPerVinte() + "%");
        dati[1].setText("Sconfitte: " + morra.getPerse() + ", " + morra.getPerPerse() + "%");
        dati[2].setText("Pareggi: " + morra.getPareggi() + ", " + morra.getPerPareggi() + "%");
        dati[3].setText("Totali: " + morra.getTotali());
    }

    /**
     * Main del progetto, istanzia solo una MorraCineseGUI anonima.
     *
     * @param args i parametri della riga di comando.
     */
    public static void main(String[] args) {
        new MorraCineseGUI();
    }
}
