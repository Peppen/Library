package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;

	private String titolo;
	private List<String> autori;
	private String casaEditrice;
	private Stato stato;
	private String posizione;
	public Libro(String titolo, List<String> autori, String casaEditrice, Stato stato) {
		this.titolo=titolo;
		this.autori=autori;
		this.casaEditrice=casaEditrice;
		this.stato=stato;
	}
	public String getPosizione() {
		return posizione;
	}
	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public List<String> getAutori() {
		return autori;
	}
	public void setAutori(ArrayList<String> autori) {
		this.autori = autori;
	}
	public String getCasaEditrice() {
		return casaEditrice;
	}
	public void setCasaEditrice(String casaEditrice) {
		this.casaEditrice = casaEditrice;
	}


	@Override
	public String toString() {
		return "Libro [titolo=" + titolo + ", autori=" + autori + ", casaEditrice=" + casaEditrice + ", stato=" + stato
				+ ", posizione=" + posizione + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (autori == null) {
			if (other.autori != null)
				return false;
		} else if (!autori.equals(other.autori))
			return false;
		if (casaEditrice == null) {
			if (other.casaEditrice != null)
				return false;
		} else if (!casaEditrice.equals(other.casaEditrice))
			return false;
		if (posizione == null) {
			if (other.posizione != null)
				return false;
		} else if (!posizione.equals(other.posizione))
			return false;
		if (stato != other.stato)
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		return true;
	}

}
