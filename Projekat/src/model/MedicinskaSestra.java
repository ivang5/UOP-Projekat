package model;

public class MedicinskaSestra extends Korisnik {
	
	private double plata;
	private Sluzba sluzba;
	
	
	public MedicinskaSestra(String ime, String prezime, String jmbg, Pol pol, String adresa, String brojTelefona,
			String korisnickoIme, String lozinka, Uloga uloga, double plata, Sluzba sluzba) {
		super(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga);
		this.plata = plata;
		this.sluzba = sluzba;
	}

	
	public double getPlata() {
		return plata;
	}


	public void setPlata(double plata) {
		this.plata = plata;
	}


	public Sluzba getSluzba() {
		return sluzba;
	}


	public void setSluzba(Sluzba sluzba) {
		this.sluzba = sluzba;
	}


	@Override
	public String toString() {
		return "MedicinskaSestra [plata=" + plata + ", sluzba=" + sluzba + "]";
	}
	
}
