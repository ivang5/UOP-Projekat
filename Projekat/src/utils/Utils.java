package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import model.KategorijaOsiguranja;
import model.Korisnik;
import model.Lekar;
import model.MedicinskaSestra;
import model.Pacijent;
import model.Pol;
import model.Pregled;
import model.Sluzba;
import model.StatusPregleda;
import model.Uloga;
import model.ZdravstvenaKnjizica;

public class Utils {

	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private static SimpleDateFormat SIMPLE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
	private static ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	public static ArrayList<Lekar> lekari = new ArrayList<Lekar>();
	public static ArrayList<MedicinskaSestra> medicinskeSestre = new ArrayList<MedicinskaSestra>();
	public static ArrayList<Pacijent> pacijenti = new ArrayList<Pacijent>();
	public static ArrayList<ZdravstvenaKnjizica> zdravstveneKnjizice = new ArrayList<ZdravstvenaKnjizica>();
	public static ArrayList<Pregled> pregledi = new ArrayList<Pregled>();
	
	
	public static void ucitavanjeLekara() {
		lekari.clear();
		try {
			File fajl = new File("src/fajlovi/lekari.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] split=line.split("\\|");
				String ime = split[0];
				String prezime=split[1];
				String jmbg=split[2];
				Pol pol = Pol.valueOf(split[3]);
				String adresa = split[4];
				String brojTelefona = split[5];
				String korisnickoIme = split[6];
				String lozinka = split[7];
				Uloga uloga = Uloga.valueOf(split[8]);
				double plata = Double.parseDouble(split[9]);
				String specijalizacija = split[10];
				Sluzba sluzba = Sluzba.valueOf(split[11]);
				Lekar lekar = new Lekar(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, specijalizacija,sluzba);
				lekari.add(lekar);
			}
			reader.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Ne postoji fajl lekari.txt", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		for(Lekar lekar:lekari) {
			System.out.println(lekar);
		}
	}
		
	
	public static void ucitavanjeMedicinskihSestara() {
		medicinskeSestre.clear();
		try {
			File fajl = new File("src/fajlovi/medicinskeSestre.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] split=line.split("\\|");
				String ime = split[0];
				String prezime=split[1];
				String jmbg=split[2];
				Pol pol = Pol.valueOf(split[3]);
				String adresa = split[4];
				String brojTelefona = split[5];
				String korisnickoIme = split[6];
				String lozinka = split[7];
				Uloga uloga = Uloga.valueOf(split[8]);
				double plata = Double.parseDouble(split[9]);
				Sluzba sluzba = Sluzba.valueOf(split[10]);
				MedicinskaSestra medicinskaSestra = new MedicinskaSestra(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, sluzba);
				medicinskeSestre.add(medicinskaSestra);
			}
			reader.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Ne postoji fajl medicinskeSestre.txt", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		for (MedicinskaSestra medicinskaSestra:medicinskeSestre) {
			System.out.println(medicinskaSestra);
		}
	}

	
	public static void ucitavanjePacijenata() {
		pacijenti.clear();
		ucitavanjeZdravstvenihKnjizica();
		try {
			File fajl = new File("src/fajlovi/pacijenti.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] split=line.split("\\|");
				String ime = split[0];
				String prezime= split[1];
				String jmbg=split[2];
				Pol pol = Pol.valueOf(split[3]);
				String adresa = split[4];
				String brojTelefona = split[5];
				String korisnickoIme = split[6];
				String lozinka = split[7];
				Uloga uloga = Uloga.valueOf(split[8]);
				Lekar lekar = pronadjiLekara(split[9]);
				ZdravstvenaKnjizica knjizica = pronadjiZdravstvenuKnjizicu(Integer.parseInt(split[10]));
				Pacijent pacijent = new Pacijent(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, lekar, knjizica);
				pacijenti.add(pacijent);
			}
			reader.close();
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Ne postoji fajl pacijenti.txt", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		for(Pacijent pac:pacijenti) {
			System.out.println(pac);
		}
	}
	
	
	public static void ucitavanjeZdravstvenihKnjizica() {
		zdravstveneKnjizice.clear();
		try {
			File fajl = new File("src/fajlovi/zdravstveneKnjizice.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] split=line.split("\\|");
				ZdravstvenaKnjizica knjizica = new ZdravstvenaKnjizica();
				knjizica.setBroj(Integer.parseInt(split[0]));
				Date dat = DATE_TIME_FORMAT.parse(split[1]);
				knjizica.setDatumIsteka(dat);
				knjizica.setKategorijaOsiguranja(KategorijaOsiguranja.valueOf(split[2]));
				zdravstveneKnjizice.add(knjizica);
			}
			reader.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ne postoji fajl zdravstveneKnjizice.txt", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		for(ZdravstvenaKnjizica knjizica:zdravstveneKnjizice) {
			System.out.println(knjizica);
		}
	}
	
	
	public static void ucitavanjeKorisnika() {
		ucitavanjeMedicinskihSestara();
		ucitavanjeLekara();
		ucitavanjePacijenata();
		
		for(MedicinskaSestra medicinskaSestra:medicinskeSestre) {
			korisnici.add(medicinskaSestra);
		}
		
		for (Lekar lekar:lekari) {
			korisnici.add(lekar);
		}
		
		for(Pacijent pacijent:pacijenti) {
			korisnici.add(pacijent);
		}
		
	}
	
	
	public static void ucitavanjePregleda() {
		pregledi.clear();
		try {
			File fajl = new File("src/fajlovi/pregledi.txt");
			BufferedReader reader = new BufferedReader(new FileReader(fajl));
			String line;
			while((line=reader.readLine()) != null) {
				String[] split=line.split("\\|");
				Pregled pregled = new Pregled();
				pregled.setId(Integer.parseInt(split[0]));
				pregled.setPacijent(pronadjiPacijenta(split[1]));
				pregled.setLekar(pronadjiLekara(split[2]));
				Date termin = SIMPLE_FORMATTER.parse(split[3]);
				pregled.setTermin(termin);
				pregled.setSoba(split[4]);
				pregled.setOpis(split[5]);
				pregled.setStatus(StatusPregleda.valueOf(split[6]));
				pregledi.add(pregled);
			}
			reader.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ne postoji fajl pregledi.txt", "Greska", JOptionPane.WARNING_MESSAGE);
		}
		for(Pregled pre:pregledi) {
			System.out.println(pre);
		}
	}
	
	
	public static void snimiLekare() {
		try {
			File lekariFajl = new File("src/fajlovi/lekari.txt");
			String content = "";
			for (Lekar lekar:lekari) {
				content += lekar.getIme() + "|" + lekar.getPrezime() + "|" + lekar.getJmbg() + "|" + lekar.getPol() + "|" + lekar.getAdresa() + "|" +
						lekar.getBrojTelefona() + "|" + lekar.getKorisnickoIme() + "|" + lekar.getLozinka() + "|" + lekar.getUloga() + "|" +
						lekar.getPlata() + "|" + lekar.getSpecijalizacija() + "|" + lekar.getSluzba() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(lekariFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajl!", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiMedicinskeSestre() {
		try {
			File medicinskeSestreFajl = new File("src/fajlovi/medicinskeSestre.txt");
			String content = "";
			for(MedicinskaSestra medicinskaSestra:medicinskeSestre) {
				content += medicinskaSestra.getIme() + "|" + medicinskaSestra.getPrezime() + "|" + medicinskaSestra.getJmbg() + "|" + 
						medicinskaSestra.getPol() + "|" + medicinskaSestra.getAdresa() + "|" + medicinskaSestra.getBrojTelefona() + "|" + 
						medicinskaSestra.getKorisnickoIme() + "|" + medicinskaSestra.getLozinka() + "|" + medicinskaSestra.getUloga() + "|" +
						medicinskaSestra.getPlata() + "|" + medicinskaSestra.getSluzba() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(medicinskeSestreFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajl!", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiPacijente() {
		try {
			File pacijentiFajl = new File("src/fajlovi/pacijenti.txt");
			String content = "";
			for(Pacijent pacijent:pacijenti) {
				content += pacijent.getIme() + "|" + pacijent.getPrezime() + "|" + pacijent.getJmbg() + "|" + pacijent.getPol() + "|" + 
						pacijent.getAdresa() + "|" + pacijent.getBrojTelefona() + "|" + pacijent.getKorisnickoIme() + "|" +
						pacijent.getLozinka() + "|" + pacijent.getUloga() + "|" + pacijent.getIzabraniLekar().getKorisnickoIme() + "|" +
						pacijent.getZdravstvenaKnjizica().getBroj() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(pacijentiFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajl!", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public static void snimiZdravstveneKnjizice() {
		try {
			File zdravstveneKnjiziceFajl = new File("src/fajlovi/zdravstveneKnjizice.txt");
			String content = "";
			for(ZdravstvenaKnjizica knjizica:zdravstveneKnjizice) {
				content += knjizica.getBroj() + "|" + DATE_TIME_FORMAT.format(knjizica.getDatumIsteka()) + "|" + knjizica.getKategorijaOsiguranja() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(zdravstveneKnjiziceFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajl!", "Greska", JOptionPane.WARNING_MESSAGE);

		}
	}
	
	
	public static void snimiZdravstvenuKnjizicuUListu(ZdravstvenaKnjizica knjizica) {
		zdravstveneKnjizice.add(knjizica);
		snimiZdravstveneKnjizice();
	}
	
	
	public static void snimiPregled() {
		try {
			File preglediFajl = new File("src/fajlovi/pregledi.txt");
			String content = "";
			for(Pregled pregled:pregledi) {
				content += pregled.getId() + "|" + pregled.getPacijent().getKorisnickoIme() + "|" + pregled.getLekar().getKorisnickoIme() + "|" + 
						SIMPLE_FORMATTER.format(pregled.getTermin()) + "|" + pregled.getSoba() + "|" + pregled.getOpis() + "|" +
						pregled.getStatus() + "\n";
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(preglediFajl));
			writer.write(content);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Greska prilikom upisa u fajl", "Greska", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public static void snimiPregledUListu(Pregled pregled) {
		pregledi.add(pregled);
		snimiPregled();
	}	
	
	
	public static Lekar pronadjiLekara(String korisnickoIme) {
		for(Lekar lekar:lekari) {
			if(lekar.getKorisnickoIme().equals(korisnickoIme)) {
				return lekar;
			}
		}
		return null;
	}
	
	
	public static MedicinskaSestra pronadjiMedicinskuSestru(String korisnickoIme) {
		for(MedicinskaSestra medicinskaSestra:medicinskeSestre) {
			if(medicinskaSestra.getKorisnickoIme().equals(korisnickoIme)) {
				return medicinskaSestra;
			}
		}
		return null;
	}
	
	
	public static Pacijent pronadjiPacijenta(String korisnickoIme) {
		for(Pacijent pacijent:pacijenti) {
			if(pacijent.getKorisnickoIme().equals(korisnickoIme)) {
				return pacijent;
			}
		}
		return null;
	}
	
	
	public static ZdravstvenaKnjizica pronadjiZdravstvenuKnjizicu(int broj) {
		for(ZdravstvenaKnjizica knjizica:zdravstveneKnjizice) {
			if(knjizica.getBroj() == broj) {
				return knjizica;
			}
		}
		return null;
	}
	
	
	public static Pregled pronadjiPregled(int id) {
		for(Pregled pregled:pregledi) {
			if(pregled.getId() == id) {
				return pregled;
			}
		}
		return null;
	}
	
	public static ArrayList<String> preuzmiKorImeLekara() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader("src/fajlovi/lekari.txt"));
		String line;
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null) {
				String[] lekar = line.split("\\|");
				lekari.add(lekar[6]);
			}
			return lekari;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> preuzmiKorImePacijenta() throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader("src/fajlovi/pacijenti.txt"));
		String line;
		ArrayList<String> pacijenti = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null) {
				String[] pacijent = line.split("\\|");
				pacijenti.add(pacijent[6]);
			}
			return pacijenti;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	public static ArrayList<MedicinskaSestra> getMedicinskeSestre() {
		return medicinskeSestre;
	}
	public static void setMedicinskeSestre(ArrayList<MedicinskaSestra> medicinskeSestre) {
		Utils.medicinskeSestre = medicinskeSestre;
	}
	public static ArrayList<Lekar> getLekari() {
		return lekari;
	}
	public static void setLekari(ArrayList<Lekar> lekari) {
		Utils.lekari = lekari;
	}
	public static ArrayList<Pacijent> getPacijenti() {
		return pacijenti;
	}
	public static void setPacijenti(ArrayList<Pacijent> pacijenti) {
		Utils.pacijenti = pacijenti;
	}
	public static ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}
	public static void setSviKorisnici(ArrayList<Korisnik> korisnici) {
		Utils.korisnici = korisnici;
	}
	public static ArrayList<ZdravstvenaKnjizica> getKnjizice() {
		return zdravstveneKnjizice;
	}
	public static void setKnjizice(ArrayList<ZdravstvenaKnjizica> knjizice) {
		Utils.zdravstveneKnjizice = knjizice;
	}
	public static ArrayList<Pregled> getPregledi() {
		return pregledi;
	}
	public static void setPregledi(ArrayList<Pregled> pregledi) {
		Utils.pregledi = pregledi;
	}
	
}
