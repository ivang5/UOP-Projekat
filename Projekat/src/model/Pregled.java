package model;

import java.util.Date;

public class Pregled {

	private int id;
	private Pacijent pacijent;
	private Lekar lekar;
	private Date termin;
	private String soba;
	private String opis;
	private StatusPregleda status;
	
	
	public Pregled() {
		super();
	}


	public Pregled(int id, Pacijent pacijent, Lekar lekar, Date termin, String soba, String opis, StatusPregleda status) {
		super();
		this.id = id;
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.termin = termin;
		this.soba = soba;
		this.opis = opis;
		this.status = status;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Pacijent getPacijent() {
		return pacijent;
	}


	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}


	public Lekar getLekar() {
		return lekar;
	}


	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}


	public Date getTermin() {
		return termin;
	}


	public void setTermin(Date termin) {
		this.termin = termin;
	}


	public String getSoba() {
		return soba;
	}


	public void setSoba(String soba) {
		this.soba = soba;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public StatusPregleda getStatus() {
		return status;
	}


	public void setStatus(StatusPregleda status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Pregled [id=" + id + ", pacijent=" + pacijent + ", lekar=" + lekar + ", termin=" + termin + ", soba=" + soba
				+ ", opis=" + opis + ", status=" + status + "]";
	}
	
}
