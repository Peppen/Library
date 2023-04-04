package businesslogic;
import java.util.Comparator;

import beans.Libro;

public class ComparatorTitolo implements Comparator<Libro> {

	@Override
	public int compare(Libro lb1, Libro lb2) {
		return lb1.getTitolo().compareTo(lb2.getTitolo());
	}

}
