package model;

import java.time.LocalDate;
import java.util.Date;

public class ZdravstvenaKnjizica {
	
	private int broj;
	private Date datumIsteka;
	private KategorijaOsiguranja kategorijaOsiguranja;
	
	
	public ZdravstvenaKnjizica() {
		super();
	}


	public ZdravstvenaKnjizica(int broj, Date datumIsteka, KategorijaOsiguranja kategorijaOsiguranja) {
		super();
		this.broj = broj;
		this.datumIsteka = datumIsteka;
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}


	public int getBroj() {
		return broj;
	}


	public void setBroj(int broj) {
		this.broj = broj;
	}


	public Date getDatumIsteka() {
		return datumIsteka;
	}


	public void setDatumIsteka(Date datumIsteka) {
		this.datumIsteka = datumIsteka;
	}


	public KategorijaOsiguranja getKategorijaOsiguranja() {
		return kategorijaOsiguranja;
	}


	public void setKategorijaOsiguranja(KategorijaOsiguranja kategorijaOsiguranja) {
		this.kategorijaOsiguranja = kategorijaOsiguranja;
	}


	@Override
	public String toString() {
		return "ZdravstvenaKnjizica [broj=" + broj + ", datumIsteka=" + datumIsteka + ", kategorijaOsiguranja="
				+ kategorijaOsiguranja + "]";
	}
	
}
