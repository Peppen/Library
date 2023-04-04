package businesslogic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import beans.Libro;
import beans.Scaffale;
import beans.Stanza;
import beans.Stato;

public class InizializzazioneTest {
	private Inizializzazione in;
	private Stanza room = new Stanza(1,"A");
	private Scaffale shelf = new Scaffale(1, 20);
	private Libro create()

	{
		String autore = "A";
		ArrayList<String> autori = new ArrayList<>();
		autori.add(autore);
		Stato s = Stato.DISPONIBILE;
		Libro l = new Libro("B",autori,"Casa",s);
		return l;
	}

	private Libro l = create();
	
	@Before
    public void doSettings() throws Exception {
     in = new Inizializzazione();
     in.setPath("C:/Temp/test.dat");
     in.popola();
    }
	@Test
	public void testRicercaTitolo() throws Exception {
		room.add(shelf);
		shelf.setStanza(room);
		in.addLibroInScaffale(l, room.getLetteraIdentificativa(), shelf.getNumeroIdentificativo(), 16);
		in.write();
		Object[][] data = in.ricercaTitolo(l.getTitolo());
		List<Libro> libri = new ArrayList<>();
		if (data != null) {
			int numRows = data.length;
			for (int i=0; i<numRows - 1; i++)
			{
				if (data[i][0] != null)
				{
					@SuppressWarnings("unchecked")
					Libro libro = new Libro(data[i][0].toString(), (List<String>)data[i][4], data[i][2].toString(), Stato.valueOf(data[i][1].toString()));
					libro.setPosizione(data[i][3].toString());
					libri.add(libro);
				}

			}
		} 

		assertTrue(libri.contains(l));
	}
	@Test
	public void testRicercaStanza() throws Exception {
		room.add(shelf);
		shelf.setStanza(room);
		in.addLibroInScaffale(l, room.getLetteraIdentificativa(), shelf.getNumeroIdentificativo(), 15);
		in.write();
		Object[][] data = in.ricercaStanza(room.getLetteraIdentificativa());
		List<Libro> libri = new ArrayList<>();
		if (data != null) {
			int numRows = data.length;
			for (int i=0; i<numRows - 1; i++)
			{
				if (data[i][0] != null)
				{
					@SuppressWarnings("unchecked")
					Libro libro = new Libro(data[i][0].toString(), (List<String>)data[i][4], data[i][2].toString(), Stato.valueOf(data[i][1].toString()));
					libro.setPosizione(data[i][3].toString());
					libri.add(libro);
				}

			}
		} 

		assertTrue(libri.contains(l));
	}
	

}
