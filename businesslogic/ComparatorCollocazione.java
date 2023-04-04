package businesslogic;

import java.util.Comparator;

import beans.Libro;

public class ComparatorCollocazione implements Comparator<Libro> {

	@Override
	public int compare(Libro lb1, Libro lb2) {
		String id1 = getIdStanza(lb1);
		String id2 = getIdStanza(lb2);
		int compareStanza = id1.compareTo(id2);
		if(compareStanza!=0) {
			return compareStanza;
		}
		else {
			Integer id3 = getIdScaffale(lb1);
			Integer id4 = getIdScaffale(lb2);
			Integer compareScaffale = id3.compareTo(id4);
            if(compareScaffale!=0) {
            	return compareScaffale;
            }
            else {
            	Integer id5 = getPosInScaf(lb1);
            	Integer id6 = getPosInScaf(lb2);
            	return id5.compareTo(id6);
            }
		}
		
	}
	private String getIdStanza(Libro l) {
		String pos = l.getPosizione();
		int p = pos.indexOf("_");
		String idStanza = pos.substring(0, p);
		return idStanza;
		}
	private Integer getIdScaffale(Libro l) {
		String pos = l.getPosizione();
		int p1 = pos.indexOf("_");
		int p2 = pos.lastIndexOf("_");
		String idScaffale = pos.substring(p1+1, p2);
		return Integer.parseInt(idScaffale);
	}
	private Integer getPosInScaf(Libro l) {
		String pos = l.getPosizione();
		int p = pos.lastIndexOf("_");
		String posInScaf = pos.substring(p+1);
		return Integer.parseInt(posInScaf);
	}

}
