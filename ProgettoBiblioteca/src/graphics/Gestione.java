package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import beans.Libro;
import beans.Stato;
import businesslogic.Inizializzazione;
import businesslogic.MouseTouch;

public class Gestione extends JDialog implements ActionListener  {
	private final Object[] COLUMS_NAMES = {"Scaffale", "Posizione", "Libro"};
	private Inizializzazione in;
	private final static String SELEZIONA_STANZA = "Seleziona Stanza";
	private final static String ELIMINA_LIBRO = "Elimina libro";
	private final static String AGGIUNGI_LIBRO = "Aggiungi libro";
	private final static String PRESTA_LIBRO = "Presta libro";
	private final static String RESTITUISCI_LIBRO = "Restituisci libro";
	private final static String MOSTRA_DETTAGLI = "Mostra dettagli";
	private JTable gestioneTable;
	private final static String LIBRO_NON_DISPONIBILE = "Libro non disponibile";
	private final static String TITOLO = "Titolo:";
	private final static String AUTORI = "Autori:";
	private final static String EDITORE = "Editore:";
	private final static String AGGIUNGI = "Aggiungi";
	private final static String CHIUDI = "Chiudi";
	private final static String LIBRO_NON_PRESENTE = "Libro non presente";
	private final static String CASAEDITRICE_NON_SPECIFICATA = "Casa editrice non specificata";
	private final static String AUTORE_NON_SPECIFICATO = "Autore non specificato";
	private final static String TITOLO_NON_SPECIFICATO = "Titolo non specificato";
	private final static String LIBRO_PRESENTE = "Libro già presente";
	private static final String ERRORE = "Errore";
	private String stanza;
	private JPopupMenu popupMenu = new JPopupMenu();
	private String getStanza() {
		return stanza;
	}

	private void setStanza(String stanza) {
		this.stanza = stanza;
	}

	private static final long serialVersionUID = 1L;
	public Gestione(JFrame f,Inizializzazione in) {
		super(f, SELEZIONA_STANZA, true);
		this.in=in;
		build();
	}

	private void build() {
		JPanel panel = new JPanel();
		this.add(panel);
		JPanel selectionPanel = new JPanel();
		JComboBox<String> combo = new JComboBox<String>(in.getStanze());
		selectionPanel.add(combo);
		DefaultTableModel tableModel = new DefaultTableModel(COLUMS_NAMES, 0);
		gestioneTable = new JTable();
		JMenuItem menuItemAdd = new JMenuItem(ELIMINA_LIBRO);
		JMenuItem menuItemRemove = new JMenuItem(AGGIUNGI_LIBRO);
		JMenuItem menuItemReturn = new JMenuItem(PRESTA_LIBRO);
		JMenuItem menuItemLend = new JMenuItem(RESTITUISCI_LIBRO);
		JMenuItem menuItemShowDetails = new JMenuItem(MOSTRA_DETTAGLI);
		menuItemAdd.addActionListener(this);
		menuItemRemove.addActionListener(this);
		menuItemReturn.addActionListener(this);
		menuItemLend.addActionListener(this);
		menuItemShowDetails.addActionListener(this);
		popupMenu.add(menuItemAdd);
		popupMenu.add(menuItemLend);
		popupMenu.add(menuItemReturn);
		popupMenu.add(menuItemRemove);
		popupMenu.add(menuItemShowDetails);
		gestioneTable.setModel(tableModel);
		gestioneTable.setComponentPopupMenu(popupMenu);
		gestioneTable.addMouseListener(new MouseTouch(gestioneTable));
		gestioneTable.setEnabled(false);
		gestioneTable.setDefaultRenderer(Object.class, new MyRenderer());
		combo.addActionListener(this);
		selectionPanel.add(Box.createRigidArea(new Dimension(10,50)));
		panel.add(selectionPanel);
		JScrollPane scrollGestioneTable = new JScrollPane(gestioneTable);
		panel.add(scrollGestioneTable);
		scrollGestioneTable.setVisible(true);
		scrollGestioneTable.setPreferredSize(new Dimension(400,390));
		this.setSize(500,500);
	}
	private Libro getLibro(int row) {
		Libro objLibro = null;
		String idScaffale = (String) gestioneTable.getModel().getValueAt(row, 0);
		String posInScaf = (String) gestioneTable.getModel().getValueAt(row, 1);
		String libro = (String) gestioneTable.getModel().getValueAt(row, 2);
		if (libro != null) {
			objLibro = in.getLibroInScaffale(getStanza(), Integer.parseInt(idScaffale), Integer.parseInt(posInScaf));
		}
		return objLibro;

	}
	private void aggiungiLibro(int row,Libro l) throws NumberFormatException, Exception {
		String idStanza = getStanza();
		String posInScaf = (String) gestioneTable.getModel().getValueAt(row, 1);
		String idScaffale = (String) gestioneTable.getModel().getValueAt(row, 0);
		in.addLibroInScaffale(l, idStanza, Integer.parseInt(idScaffale), Integer.parseInt(posInScaf));

	}
	private void rimuoviLibro(int row,Libro l) throws NumberFormatException, Exception {
		String idStanza = getStanza();
		String posInScaf = (String) gestioneTable.getModel().getValueAt(row, 1);
		String idScaffale = (String) gestioneTable.getModel().getValueAt(row, 0);
		in.rimuoviLibroFromScaffale(l, idStanza, Integer.parseInt(idScaffale), Integer.parseInt(posInScaf));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox) {
			@SuppressWarnings("unchecked")
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			String stanza = (String) combo.getSelectedItem();
			setStanza(stanza);
			DefaultTableModel tableModel = new DefaultTableModel(COLUMS_NAMES, 5);
			if(stanza!=null) {
				Object[][] data = in.getInfoScaffali(stanza);
				tableModel = new DefaultTableModel(data, COLUMS_NAMES);
			}
			gestioneTable.setModel(tableModel);

		}
		else if(e.getSource() instanceof JMenuItem) {
			String command = e.getActionCommand();
			switch(command) {
			case ELIMINA_LIBRO:
				int row = gestioneTable.getSelectedRow();
				Libro l = getLibro(row);
				if(l!=null) {
					try {
						if(l.getStato()==Stato.DISPONIBILE) {
							rimuoviLibro(row,l);
							gestioneTable.setValueAt("", row, 2);
							gestioneTable.repaint();
						}
						else {
							JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this,ERRORE);
					}

				}
				else {
					JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
				}

				break;
			case AGGIUNGI_LIBRO:
				int rowe = gestioneTable.getSelectedRow();
				Libro libb = getLibro(rowe);
				if(libb==null)
				{
				createDialog(AGGIUNGI_LIBRO);
				}
				else {
					JOptionPane.showMessageDialog(this, LIBRO_PRESENTE,ERRORE, JOptionPane.ERROR_MESSAGE);
				}
				break;
			case PRESTA_LIBRO:
				int r = gestioneTable.getSelectedRow();
				Libro li = getLibro(r);
				if(li!=null) {
					prestaLibro();
					gestioneTable.repaint();
				}
				else {
					JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
				}
				break;
			case RESTITUISCI_LIBRO:
				int re = gestioneTable.getSelectedRow();
				Libro lib = getLibro(re);
				if(lib!=null) {
					restituisciLibro();
					gestioneTable.repaint();
				}
				else {
					JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
				}
				break;
			case MOSTRA_DETTAGLI:
				int rer = gestioneTable.getSelectedRow();
				Libro libe = getLibro(rer);
				if(libe!=null) {
					createDialog(MOSTRA_DETTAGLI);
				}
				else {
					JOptionPane.showMessageDialog(this, LIBRO_NON_PRESENTE,ERRORE, JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
		}

	}

	private void createDialog(String type) {
		boolean readOnly = type.equals(MOSTRA_DETTAGLI);
		String titolo = "";
		String autori = "";
		String editore = "";
		if (readOnly) {
			int row = gestioneTable.getSelectedRow();
			Libro l = getLibro(row);
			titolo = l.getTitolo();
			for (String autore : l.getAutori()) {
				autori += autore + "\n";
			}
			editore = l.getCasaEditrice();
		}
		JDialog dialog = new JDialog(this, type);
		JPanel detailPanel = new JPanel();
		BoxLayout box = new BoxLayout(detailPanel,BoxLayout.Y_AXIS);
		dialog.add(detailPanel);
		detailPanel.setLayout(box);
		JPanel titoloPanel = new JPanel();
		JLabel titoloLabel = new JLabel(TITOLO);
		JTextField fieldTitolo = new JTextField();
		fieldTitolo.setEditable(!readOnly);
		fieldTitolo.setText(titolo);
		titoloPanel.add(titoloLabel);
		titoloPanel.add(fieldTitolo);
		detailPanel.add(titoloPanel);
		JPanel autoriPanel = new JPanel();
		JLabel autoriLabel = new JLabel(AUTORI);
		JTextArea fieldAutori = new JTextArea(5,16);
		fieldAutori.setEditable(!readOnly);
		fieldAutori.setText(autori);
		JScrollPane scrollAutori = new JScrollPane(fieldAutori);
		autoriPanel.add(autoriLabel);
		autoriPanel.add(scrollAutori);
		detailPanel.add(autoriPanel);
		JPanel casaEdPanel = new JPanel();
		JLabel casaEdLabel = new JLabel(EDITORE);
		JTextField fieldCasaEd = new JTextField();
		fieldCasaEd.setEditable(!readOnly);
		fieldCasaEd.setText(editore);
		casaEdPanel.add(casaEdLabel);
		casaEdPanel.add(fieldCasaEd);
		detailPanel.add(casaEdPanel);
		fieldTitolo.setPreferredSize(new Dimension(190,20));
		scrollAutori.setPreferredSize(new Dimension(190,80));
		fieldCasaEd.setPreferredSize(new Dimension(180,20));
		JPanel buttonPanel = new JPanel();
		JButton aggiungi = new JButton(type.equals(AGGIUNGI_LIBRO) ? AGGIUNGI : CHIUDI);
		buttonPanel.add(aggiungi);
		detailPanel.add(buttonPanel);
		aggiungi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (type.equals(MOSTRA_DETTAGLI)) {
					dialog.setVisible(false);
				} else {
					String titolo = fieldTitolo.getText().trim();
					if(titolo.equals("")) {
						JOptionPane.showMessageDialog(Gestione.this,TITOLO_NON_SPECIFICATO, ERRORE, JOptionPane.ERROR_MESSAGE);
						return;
					}
					String autore = fieldAutori.getText().trim();
					if(autore.equals("")) {
						JOptionPane.showMessageDialog(Gestione.this,AUTORE_NON_SPECIFICATO, ERRORE, JOptionPane.ERROR_MESSAGE);
						return;
					}
					String [] autori = autore.split("\n");
					List<String> authors = Arrays.asList(autori); 
					String casaEd = fieldCasaEd.getText().trim();
					if(casaEd.equals("")) {
						JOptionPane.showMessageDialog(Gestione.this,CASAEDITRICE_NON_SPECIFICATA, ERRORE, JOptionPane.ERROR_MESSAGE);
						return;
					}
					Libro l = new Libro(titolo,authors,casaEd,Stato.DISPONIBILE);
					int row = gestioneTable.getSelectedRow();
					try {
						aggiungiLibro(row,l);
						gestioneTable.setValueAt(titolo, row, 2);
						gestioneTable.repaint();
						dialog.setVisible(false);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Gestione.this,LIBRO_NON_PRESENTE, ERRORE, JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		dialog.setSize(500,500);
		dialog.setVisible(true);

	}

	private void prestaLibro() {
		int row = gestioneTable.getSelectedRow();
		Libro libro = getLibro(row);
		if(libro!=null) {
			if(libro.getStato()==Stato.DISPONIBILE) {
				libro.setStato(Stato.PRESTATO);
				gestioneTable.repaint();
			}
			else {
				JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
			}

		}
	}
	private void restituisciLibro() {
		int row = gestioneTable.getSelectedRow();
		Libro libro = getLibro(row);
		if(libro!=null) {
			if(libro.getStato()==Stato.PRESTATO) {
				libro.setStato(Stato.DISPONIBILE);
				gestioneTable.repaint();
			}
			else {
				JOptionPane.showMessageDialog(this, LIBRO_NON_DISPONIBILE,ERRORE, JOptionPane.ERROR_MESSAGE);
			}

		}
	}
	private class MyRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(
					table, value, isSelected, hasFocus, row, column);
			Libro libro = getLibro(row);
			if (libro != null) {
				if (libro.getStato() == Stato.DISPONIBILE) {
					setBackground(Color.GREEN);
					setForeground(Color.BLACK);
				} else if (libro.getStato() == Stato.PRESTATO) {
					setBackground(Color.RED);
					setForeground(Color.WHITE);
				}

			} else {
				setBackground(Color.WHITE);
				setForeground(Color.BLACK);
			}
			return this;
		}
	}

}


