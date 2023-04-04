package beans;
import java.io.Serializable;
import java.util.HashMap;

public class Scaffale implements Serializable  {

	private static final long serialVersionUID = 1L;

	private int numeroIdentificativo;
	private int numeroPosizioni;
	private Stanza stanza;
	private HashMap<String,Libro> libri; 
	private static final String POSIZIONE_OCCUPATA = "Posizione occupata";
	public Scaffale(int numeroIdentificativo, int numeroPosizioni) {
		this.numeroIdentificativo=numeroIdentificativo;
		libri= new HashMap<>(numeroPosizioni);
		this.numeroPosizioni=numeroPosizioni;
	}

	public Stanza getStanza() {
		return stanza;
	}
	public void setStanza(Stanza stanza) {
		this.stanza = stanza;
	}
	public int getNumeroIdentificativo() {
		return numeroIdentificativo;
	}
	public void setNumeroIdentificativo(int numeroIdentificativo) {
		this.numeroIdentificativo = numeroIdentificativo;
	}
	public int getNumeroPosizioni() {
		return numeroPosizioni;
	}
	public void setNumeroPosizioni(int numeroPosizioni) {
		this.numeroPosizioni = numeroPosizioni;
	}
	public HashMap<String, Libro> getLibri() {
		return libri;
	}
	public void setLibri(HashMap<String, Libro> libri) {
		this.libri = libri;
	}
	public void add(Libro l,String idStanza, Integer scaffale, Integer posInScaf) throws Exception {
		int dimensione = libri.size();
		if(dimensione<getNumeroPosizioni()) {
			String posizione = idStanza + "_" + scaffale + "_" + posInScaf;
			Libro l1 = libri.get(posizione);
			if(l1!=null) {
				throw new Exception(POSIZIONE_OCCUPATA);
			}
			libri.put(posizione,l);
			l.setPosizione(posizione);
		}
	}
	public void remove(String idStanza, int scaffale, int posInScaf) {
		String posizione = idStanza + "_" + scaffale + "_" + posInScaf;
		libri.remove(posizione);
	}

	@Override
	public String toString() {
		return "Scaffale [numeroIdentificativo= " + numeroIdentificativo + ", numeroPosizioni= " + numeroPosizioni
				+ ", stanza= " + stanza + "]";
	}
	public boolean presta(Libro l) {
		if(l.getStato()==Stato.DISPONIBILE) {
			l.setStato(Stato.PRESTATO);
			return true;
		}
		return false;
	}
	public void restituisci(Libro l) {
		l.setStato(Stato.DISPONIBILE);
	}
	public Libro getLibro(String stanza, Integer scaffale, Integer posInScaf) {
		String posizione = stanza + "_" + scaffale + "_" + posInScaf;
		return libri.get(posizione);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scaffale other = (Scaffale) obj;
		if (libri == null) {
			if (other.libri != null)
				return false;
		} else if (!libri.equals(other.libri))
			return false;
		if (numeroIdentificativo != other.numeroIdentificativo)
			return false;
		if (numeroPosizioni != other.numeroPosizioni)
			return false;
		if (stanza == null) {
			if (other.stanza != null)
				return false;
		} else if (!stanza.equals(other.stanza))
			return false;
		return true;
	}

}
