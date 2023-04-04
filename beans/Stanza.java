package beans;
import java.io.Serializable;
import java.util.ArrayList;

public class Stanza implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numeroScaffali;
	private String letteraIdentificativa;
	private ArrayList<Scaffale> scaffali;
	public Stanza(int numeroScaffali, String letteraIdentificativa) {
		this.numeroScaffali=numeroScaffali;
		scaffali = new ArrayList<>(numeroScaffali);
		this.letteraIdentificativa=letteraIdentificativa;

	}
	public int getNumeroScaffali() {
		return numeroScaffali;
	}
	public void setNumeroScaffali(int numeroScaffali) {
		this.numeroScaffali = numeroScaffali;
	}
	public String getLetteraIdentificativa() {
		return letteraIdentificativa;
	}
	public void setLetteraIdentificativa(String letteraIdentificativa) {
		this.letteraIdentificativa = letteraIdentificativa;
	}
	public ArrayList<Scaffale> getScaffali() {
		return scaffali;
	}
	public void setScaffali(ArrayList<Scaffale> scaffali) {
		this.scaffali = scaffali;
	}
	public void add(Scaffale f) {
		int dimensione = scaffali.size();
		if(dimensione<getNumeroScaffali()) {
			scaffali.add(f);
			f.setStanza(this);
		}
	}
	@Override
	public String toString() {
		return "Stanza [numeroScaffali=" + numeroScaffali + ", letteraIdentificativa=" + letteraIdentificativa
				+ "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stanza other = (Stanza) obj;
		if (letteraIdentificativa == null) {
			if (other.letteraIdentificativa != null)
				return false;
		} else if (!letteraIdentificativa.equals(other.letteraIdentificativa))
			return false;
		if (numeroScaffali != other.numeroScaffali)
			return false;
		if (scaffali == null) {
			if (other.scaffali != null)
				return false;
		} else if (!scaffali.equals(other.scaffali))
			return false;
		return true;
	}


}
