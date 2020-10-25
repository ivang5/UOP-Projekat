package model;

public class Pacijent extends Korisnik {

	private Lekar izabraniLekar;
	private ZdravstvenaKnjizica zdravstvenaKnjizica;
	
	
	public Pacijent(String ime, String prezime, String jmbg, Pol pol, String adresa, String brojTelefona,
			String korisnickoIme, String lozinka, Uloga uloga, Lekar izabraniLekar,
			ZdravstvenaKnjizica zdravstvenaKnjizica) {
		super(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga);
		this.izabraniLekar = izabraniLekar;
		this.zdravstvenaKnjizica = zdravstvenaKnjizica;
	}


	public Lekar getIzabraniLekar() {
		return izabraniLekar;
	}


	public void setIzabraniLekar(Lekar izabraniLekar) {
		this.izabraniLekar = izabraniLekar;
	}


	public ZdravstvenaKnjizica getZdravstvenaKnjizica() {
		return zdravstvenaKnjizica;
	}


	public void setZdravstvenaKnjizica(ZdravstvenaKnjizica zdravstvenaKnjizica) {
		this.zdravstvenaKnjizica = zdravstvenaKnjizica;
	}


	@Override
	public String toString() {
		return "Pacijent [izabraniLekar=" + izabraniLekar + ", zdravstvenaKnjizica=" + zdravstvenaKnjizica + "]";
	}
	
}
