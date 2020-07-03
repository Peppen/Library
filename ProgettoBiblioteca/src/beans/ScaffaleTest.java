package beans;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ScaffaleTest {
    private Scaffale scaffale = new Scaffale(1, 20);

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
    
	@Test
	public void testAdd() throws Exception {
		scaffale.add(l, "A", 1, 1);
		assertEquals(l, scaffale.getLibro("A", 1, 1));
	}
	
	@Test
	public void testPresta() throws Exception {
		scaffale.presta(l);
		assertEquals(Stato.PRESTATO, l.getStato());
	}
	@Test
	public void testRestituisci() {
		scaffale.restituisci(l);
		assertEquals(Stato.DISPONIBILE, l.getStato());
	}
	
	@Test
	public void testRemove() throws Exception {
		scaffale.remove("A", 1, 1);
		assertEquals(null, scaffale.getLibro("A", 1, 1));
	}
}
