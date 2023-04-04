package businesslogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import beans.Libro;
import beans.Scaffale;
import beans.Stanza;

import java.util.Set;

public class Inizializzazione {
	private final static String STANZA = "Stanza";
	private final static String A = "A";
	private final static String B = "B";
	private final static String C = "C";
	private HashMap<String,Stanza> map = new HashMap<>();
	private String path;

	private String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public HashMap<String, Stanza> getMap() {
		return map;
	}
	private void setMap(HashMap<String,Stanza> map) {
		this.map = map;
	}
	public void popola() throws Exception {
		File file = getPath()!= null?new File(getPath()):null;
		if(file!=null && file.exists()) {
			setMap(popolaFromFile());
		}
		else {
			setMap(popolaManual());
		}
	}
	@SuppressWarnings("unchecked")
	private HashMap<String,Stanza> popolaFromFile() throws Exception {
		HashMap<String, Stanza> rooms = null;
		if (getPath() != null) {
			File f = new File(getPath());
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			rooms = (HashMap<String, Stanza>)ois.readObject();
			ois.close();
		}
		return rooms;

	}

	private HashMap<String,Stanza> popolaManual() throws Exception {
		HashMap<String,Stanza> rooms = new HashMap<>();
		Stanza stanzaA = new Stanza(2,A);
		Stanza stanzaB = new Stanza(25,B);
		Stanza stanzaC = new Stanza(20,C);
		rooms.put(A,stanzaA);
		rooms.put(B,stanzaB);
		rooms.put(C,stanzaC);
		InizializzaScaffali(stanzaA);
		InizializzaScaffali(stanzaB);
		InizializzaScaffali(stanzaC);
		return rooms;
	}
	public Object[][] ricercaTitolo(String titolo) {
		ArrayList<Libro> books = new ArrayList<>();
		Iterator<Entry<String, Stanza>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Stanza> entry = it.next();
			Stanza st = entry.getValue();
			for (Scaffale shelf : st.getScaffali()) {
				for (Libro lb : shelf.getLibri().values()) {
					if ((lb.getTitolo().toLowerCase().indexOf(titolo.toLowerCase()) != -1)) {
						books.add(lb);
					}
				}
			}

		}
		Collections.sort(books, new ComparatorTitolo());

		Object[][] data = new Object[books.size() >=5 ? books.size() : 5][5];
		int i = 0;
		for (Libro lb : books) {
			data[i][0] = lb.getTitolo();
			data[i][1] = lb.getStato();
			data[i][2] = lb.getCasaEditrice();
			data[i][3] = lb.getPosizione();
			data[i][4] = lb.getAutori();
			i++;
		}

		return data;
	}
	private void InizializzaScaffali(Stanza stanza) {
		int numScaffali = stanza.getNumeroScaffali();
		for(int i=1;i<=numScaffali; i++) {
			Scaffale scaffale = new Scaffale(i,20);
			stanza.add(scaffale);
		}
	}
	public Object[][] ricercaStanza(String letteraId) {
		ArrayList<Libro> books = new ArrayList<>();
		Iterator<Entry<String, Stanza>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Stanza> entry = it.next();
			Stanza st = entry.getValue();
			for (Scaffale shelf : st.getScaffali()) {
				for (Libro lb : shelf.getLibri().values()) {
					if (st.getLetteraIdentificativa().toLowerCase().indexOf(letteraId.toLowerCase()) !=-1 ) {
						books.add(lb);
					}
				}
			}

		}
		Collections.sort(books, new ComparatorCollocazione());

		Object[][] data = new Object[books.size() >=5 ? books.size() : 5][5];

		int i = 0;
		for (Libro lb : books) {
			data[i][0] = lb.getTitolo();
			data[i][1] = lb.getStato();
			data[i][2] = lb.getCasaEditrice();
			data[i][3] = lb.getPosizione();
			data[i][4] = lb.getAutori();
			i++;
		}

		return data;
	}

	public Object[][] getInfoStanze() {
		Object[][] data = new Object[map.size()  >=3 ? map.size() : 3][2];
		Iterator<Entry<String, Stanza>> it = map.entrySet().iterator();
		int i=0;
		while (it.hasNext()) {
			Entry<String, Stanza> entry = it.next();
			Stanza st = entry.getValue();
			int numScaffali =  st.getScaffali().size();
			data[i][0] = st.getLetteraIdentificativa();
			data[i][1] = numScaffali;
			i++;
		}

		return data;
	}

	public Object[][] getInfoScaffali() {
		ArrayList<String> lines = new ArrayList<>();
		Iterator<Entry<String, Stanza>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Stanza> entry = it.next();
			Stanza st = entry.getValue();
			for (Scaffale shelf : st.getScaffali()) {
				String line = st.getLetteraIdentificativa() + "," + shelf.getNumeroIdentificativo() + "," + shelf.getLibri().size();
				lines.add(line);
			}
		}

		Object[][] data = new Object[lines.size() >=3 ? lines.size() : 3][3];
		for (int i=0; i<lines.size(); i++) {
			String line = lines.get(i);
			data[i] = line.split(",");
		}

		return data;
	}
	public void addLibroInScaffale(Libro libro, String idStanza, int idScaffale, int posInScaf) throws Exception {
		Stanza stanza = map.get(idStanza);
		ArrayList<Scaffale> scaffali = stanza.getScaffali();
		if (scaffali != null) {
			for (Scaffale scaffale : scaffali) {
				if (scaffale.getNumeroIdentificativo() == idScaffale) {
					scaffale.add(libro, idStanza, idScaffale, posInScaf);
					break;
				}
			}
		}
	}
	public void rimuoviLibroFromScaffale(Libro libro, String idStanza, int idScaffale, int posInScaf) throws Exception {
		Stanza stanza = map.get(idStanza);
		ArrayList<Scaffale> scaffali = stanza.getScaffali();
		if (scaffali != null) {
			for (Scaffale scaffale : scaffali) {
				if (scaffale.getNumeroIdentificativo() == idScaffale) {
					scaffale.remove(idStanza, idScaffale, posInScaf);
					break;
				}
			}
		}
	}
	public Libro getLibroInScaffale(String idStanza, int idScaffale, int posInScaf) {
		Libro libro = null;
		Stanza stanza = map.get(idStanza);
		ArrayList<Scaffale> scaffali = stanza.getScaffali();
		if (scaffali != null) {
			for (Scaffale scaffale : scaffali) {
				if (scaffale.getNumeroIdentificativo() == idScaffale) {
					HashMap<String, Libro> libriInScaffale = scaffale.getLibri();
					if (libriInScaffale != null) {
						libro = scaffale.getLibro(idStanza, idScaffale, posInScaf);
					}

					break;
				}
			}
		}

		return libro;
	}
	public Object[][] getInfoScaffali(String idStanza) {
		ArrayList<String> libriInScaffale = new ArrayList<>();
		Stanza stanza = map.get(idStanza);
		if(stanza!=null) {
			ArrayList<Scaffale> scaffali = stanza.getScaffali();
			for(Scaffale scaffale : scaffali) {
				int idScaffale = scaffale.getNumeroIdentificativo();
				int numPosizioni = scaffale.getNumeroPosizioni();
				for(int i=1;i<=numPosizioni;i++) {
					Libro libro = scaffale.getLibro(idStanza, idScaffale, i);
					libriInScaffale.add(idScaffale + "," + i + "," + (libro!=null?libro.getTitolo():""));
				}
			}
			Object[][] data = new Object[libriInScaffale.size()][3];
			int i = 0;
			for(String libro : libriInScaffale) {
				data[i++] = libro.split(",");

			}
			return data;

		}
		return null;
	}

	public String[] getStanze() {
		Set<String> stanze = map.keySet();
		ArrayList<String> listStanze = new ArrayList<>();
		listStanze.addAll(stanze);
		Collections.sort(listStanze);
		listStanze.add(0, STANZA);
		String[] rooms = new String[listStanze.size()];
		return listStanze.toArray(rooms);
	}
	public void write() throws IOException {
		File f = new File(getPath());
		FileOutputStream fs = new FileOutputStream(f);
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(map);
		os.close();


	}


}

