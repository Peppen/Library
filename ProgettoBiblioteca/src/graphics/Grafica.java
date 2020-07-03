package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businesslogic.Inizializzazione;

public class Grafica {
	private static Inizializzazione in;
	private JFrame f;
	private JDialog dialogGestione;
	private JButton gestione = new JButton(GESTIONE);
	private JButton salvataggio = new JButton(SALVATAGGIO);
	private JButton ricerca = new JButton(RICERCA);

	private JTable scaffaliTable;
	private static final String GESTIONE = "Gestione";
	private static final String SALVATAGGIO = "Salvataggio";
	private static final String RICERCA = "Ricerca";
	private static final String STANZA = "Stanza";
	private static final String SCAFFALE = "Scaffale";
	private static final String NUMERO_LIBRI = "Numero Libri";
	private static final String NUMERO_SCAFFALI = "Numero Scaffali";
	private static final String TITOLO = "Titolo";
	private static final String STATO = "Stato";
	private static final String CASA_EDITRICE = "Casa Editrice";
	private static final String POSIZIONE = "Posizione";
	private static final String AUTORI = "Autori";
	private static final Object[] COLUMN_NAME_INFO_SCAFFALI = {STANZA, SCAFFALE, NUMERO_LIBRI};
	private static final Object[] COLUMN_NAME_INFO_STANZE = {STANZA, NUMERO_SCAFFALI};
	private static final Object[] COLUMN_NAME_LIBRI = {TITOLO, STATO,CASA_EDITRICE,POSIZIONE,AUTORI};
	private static final String RICERCA_STANZA = "Ricerca Stanza";
	private static final String RICERCA_TITOLO = "Ricerca Titolo";
	private static final String RISULTATO_RICERCA = "Risultato Ricerca";
	private static final String FILE_SALVATO = "File Salvato";
	private static final String BIBLIOTECA = "Biblioteca";
	private static final String ERRORE = "Errore";
	private static final String UNO = "1";
	private static final String DUE = "2";
	private static final String TRE = "3";
	private static final String QUATTRO = "4";
	public Grafica() {
		f = new JFrame(BIBLIOTECA);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void refreshScaffaliTable() throws Exception
	{
		in.popola();
		Object[][] infoScaffali = in.getInfoScaffali();
		DefaultTableModel model = new DefaultTableModel(infoScaffali, COLUMN_NAME_INFO_SCAFFALI);
		scaffaliTable.setModel(model);
	}

	public void setup() throws Exception {

		Object[][] infoStanze = in.getInfoStanze();
		JPanel mainPanel = new JPanel();
		BorderLayout layout = new BorderLayout();
		mainPanel.setLayout(layout);
		JPanel westPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();
		JTable stanzeTable = new JTable(infoStanze, COLUMN_NAME_INFO_STANZE);
		stanzeTable.setEnabled(false);
		JScrollPane scrollInfoStanze = new JScrollPane(stanzeTable);
		scrollInfoStanze.setPreferredSize(new Dimension(297,70));
		JTable ricercaTable = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(COLUMN_NAME_LIBRI, 5);
		ricercaTable.setModel(dataModel);
		JScrollPane scrollRicercaTable = new JScrollPane(ricercaTable);
		scrollRicercaTable.setPreferredSize(new Dimension(600,100));
		ricercaTable.setVisible(true);
		ricercaTable.setEnabled(false);
		mainPanel.add(westPanel,BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(northPanel,BorderLayout.NORTH);
		northPanel.add(Box.createRigidArea(new Dimension(92, 20)));
		northPanel.add(scrollInfoStanze);
		scaffaliTable = new JTable(null, COLUMN_NAME_INFO_SCAFFALI);
		scaffaliTable.setEnabled(false);
		refreshScaffaliTable();
		JScrollPane scrollInfoScaffali = new JScrollPane(scaffaliTable);
		scrollInfoScaffali.setPreferredSize(new Dimension(297,70));
		northPanel.add(scrollInfoScaffali);
		GridLayout grid = new GridLayout(5,1);
		westPanel.setLayout(grid);
		JButton j1 = new JButton();
		JButton j2 = new JButton();
		j1.setEnabled(false);
		j2.setEnabled(false);
		JRadioButton ricercaT = new JRadioButton(RICERCA_TITOLO);
		JRadioButton ricercaS = new JRadioButton(RICERCA_STANZA);
		JTextField field = new JTextField();
		ButtonGroup bg = new ButtonGroup();
		bg.add(ricercaT);
		bg.add(ricercaS);
		JPanel center = new JPanel();
		BoxLayout box = new BoxLayout(center,BoxLayout.Y_AXIS);
		center.setLayout(box);
		centerPanel.add(center);
		JPanel searchPanel = new JPanel();
		searchPanel.add(field);
		field.setPreferredSize(new Dimension(200,20));
		searchPanel.add(ricercaT);
		searchPanel.add(ricercaS);
		JButton result = new JButton(RISULTATO_RICERCA);
		searchPanel.add(result);
		center.add(searchPanel);
		center.add(Box.createRigidArea(new Dimension(10,10)));
		center.add(scrollRicercaTable);
		center.setVisible(false);
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object object = e.getSource();
				if(object instanceof JButton) {
					JButton jb = (JButton) object;
					String g = jb.getActionCommand();
					if(g.equals(UNO)) {
						center.setVisible(false);
						if(dialogGestione==null) {
							dialogGestione = new Gestione(f,in);
						}
						dialogGestione.setVisible(true);
					}
					else if(g.equals(DUE)) {
						try {
							in.write();
							refreshScaffaliTable();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(f,e1.getMessage(),ERRORE,JOptionPane.ERROR_MESSAGE);
						}
						JOptionPane.showMessageDialog(f, FILE_SALVATO);
					}
					else if(g.equals(TRE)) {
						field.setText("");
						DefaultTableModel dataModel = new DefaultTableModel(COLUMN_NAME_LIBRI, 5);
						ricercaTable.setModel(dataModel);
						bg.clearSelection();
						center.setVisible(true);
					}
					else if(g.equals(QUATTRO)) {
						String a = field.getText();
						if (a!=null && !a.equals("")) {
							if (bg.getSelection() != null) {
								String c = bg.getSelection().getActionCommand();
								if(c.equals(RICERCA_TITOLO)) {
									Object[][] infoLibri = in.ricercaTitolo(a);
									DefaultTableModel dataModel = new DefaultTableModel(infoLibri, COLUMN_NAME_LIBRI);
									ricercaTable.setModel(dataModel);
								}
								else if (c.equals(RICERCA_STANZA)) {
									Object[][] infoLibri = in.ricercaStanza(a);
									DefaultTableModel dataModel = new DefaultTableModel(infoLibri, COLUMN_NAME_LIBRI);
									ricercaTable.setModel(dataModel);
								}

							}
						}
						else {
							JOptionPane.showMessageDialog(f, ERRORE);
						}

					}
				}
			}
		};

		westPanel.add(gestione);
		gestione.setActionCommand(UNO);
		gestione.addActionListener(action);
		westPanel.add(j1);
		westPanel.add(salvataggio);
		salvataggio.setActionCommand(DUE);
		salvataggio.addActionListener(action);
		westPanel.add(j2);
		westPanel.add(ricerca);
		ricerca.setActionCommand(TRE);
		ricerca.addActionListener(action);
		ricercaT.setActionCommand(RICERCA_TITOLO);
		ricercaS.setActionCommand(RICERCA_STANZA);
		result.setActionCommand(QUATTRO);
		result.addActionListener(action);
		f.setContentPane(mainPanel);
		f.setSize(1000,500);
		f.setVisible(true);
	}
	public static void main(String args[]) {
		in = new Inizializzazione();
		try {
			String path = null;
			int len = args.length;
			if(len>0) {
				path = args[0];
			}
			in.setPath(path);
			in.popola();
			new Grafica().setup();
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
