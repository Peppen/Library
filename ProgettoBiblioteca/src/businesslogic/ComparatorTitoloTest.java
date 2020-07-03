package businesslogic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import beans.Libro;
import beans.Stato;

public class ComparatorTitoloTest {
		ArrayList<String> autori = new ArrayList<>();
		Stato s = Stato.DISPONIBILE;
		Libro l1 = new Libro("B",autori,"Casa",s);
		Libro l2 = new Libro("A",autori,"Casa",s);
	private ComparatorTitolo comparator = new ComparatorTitolo();
	@Test
	public void testComparatorTitolo1() {
		assertEquals(1, comparator.compare(l1, l2)); 
	}
	
}
