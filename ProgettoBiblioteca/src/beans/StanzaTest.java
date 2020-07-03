package beans;

import static org.junit.Assert.*;
import org.junit.Test;

public class StanzaTest {
	private Stanza stanza = new Stanza(1,"A");
	private Scaffale scaffale = new Scaffale(1, 20);

	@Test
	public void testAdd() {
		stanza.add(scaffale);
		assertEquals(true, stanza.getScaffali().contains(scaffale));
	}

}
