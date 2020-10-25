package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.KategorijaOsiguranja;
import model.Lekar;
import model.Pacijent;
import model.Pol;
import model.Uloga;
import model.ZdravstvenaKnjizica;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaKreiranjePacijenata extends JFrame {

	private Lekar lekar;
	private Pacijent pacijent;
	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Broj telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblLekar = new JLabel("Izabrani lekar:");
	private JLabel lblZdravstvenaKnjizica = new JLabel("Zdravstvena knjizica:");
	private JTextField txtIme = new JTextField(12);
	private JTextField txtPrezime = new JTextField(12);
	private JTextField txtJmbg = new JTextField(12);
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JTextField txtAdresa = new JTextField(12);
	private JTextField txtTelefon = new JTextField(12);
	private JTextField txtKorisnickoIme = new JTextField(12);
	private JTextField txtLozinka = new JTextField(12);
	private JComboBox cbLekari;
	private JComboBox cbZdravrsvenaKnjizica;
	private JButton btnPotvrdi = new JButton("Potvrdi");
	
	public IzmenaKreiranjePacijenata(Pacijent pacijent) {
		this.pacijent = pacijent;
		if(this.pacijent == null) {
			setTitle("Kreiranje pacijenta");
		}else {
			setTitle(this.pacijent.getKorisnickoIme() + " - izmena");;
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap");
		setLayout(layout);
		
		ArrayList<String> lekari = new ArrayList<String>();
		try {
			lekari = Utils.preuzmiKorImeLekara();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.cbLekari = new JComboBox(lekari.toArray());
		cbLekari.setSelectedIndex(0);
		
		ArrayList<Integer> knjizice = new ArrayList<Integer>();
		for(ZdravstvenaKnjizica k:Utils.getKnjizice()) {
			boolean potvrda = false;
			for(Pacijent p:Utils.getPacijenti()) {
				if(k.getBroj() == p.getZdravstvenaKnjizica().getBroj()) {
					potvrda = true;
				}
			}
			if(potvrda == false) {
				knjizice.add(k.getBroj());
			}
		}
		this.cbZdravrsvenaKnjizica = new JComboBox(knjizice.toArray());
		
		if(this.pacijent != null) {
			popunjavanjePolja();
		}
		
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblJmbg);
		add(txtJmbg);
		add(lblPol);
		add(cbPol);
		add(lblAdresa);
		add(txtAdresa);
		add(lblTelefon);
		add(txtTelefon);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblLozinka);
		add(txtLozinka);
		add(lblLekar);
		add(cbLekari);
		add(lblZdravstvenaKnjizica);
		add(cbZdravrsvenaKnjizica);
		add(new JLabel());
		add(btnPotvrdi);
	}
	
	private void popunjavanjePolja() {		
		txtIme.setText(this.pacijent.getIme());
		txtPrezime.setText(this.pacijent.getPrezime());
		txtJmbg.setText(this.pacijent.getJmbg());
		cbPol.setSelectedItem(this.pacijent.getPol());
		txtAdresa.setText(this.pacijent.getAdresa());
		txtTelefon.setText(this.pacijent.getBrojTelefona());
		txtKorisnickoIme.setText(this.pacijent.getKorisnickoIme());
		txtLozinka.setText(this.pacijent.getLozinka());
		cbZdravrsvenaKnjizica.setSelectedItem(this.pacijent.getZdravstvenaKnjizica().getBroj());
	}
	
	private void initActions() {
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true) {
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					String jmbg = txtJmbg.getText().trim();
					Pol pol = (Pol) cbPol.getSelectedItem();
					String adresa = txtAdresa.getText().trim();
					String brojTelefona = txtTelefon.getText().trim();
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = txtLozinka.getText().trim();
					Uloga uloga = Uloga.PACIJENT;
					Lekar lekar = Utils.pronadjiLekara(cbLekari.getSelectedItem().toString());
					ZdravstvenaKnjizica knjizica = Utils.pronadjiZdravstvenuKnjizicu(Integer.parseInt(cbZdravrsvenaKnjizica.getSelectedItem().toString()));
					if(pacijent == null) {
						pacijent = new Pacijent(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, lekar, knjizica);
						Utils.getPacijenti().add(pacijent);
						MedicinskaSestraPacijenti.DodajRedUTabelu(new Object[] {
								txtIme.getText(),
								txtPrezime.getText(),
								txtJmbg.getText(),
								cbPol.getSelectedItem(),
								txtAdresa.getText(),
								txtTelefon.getText(),
								txtKorisnickoIme.getText(),
								txtLozinka.getText(),
								Uloga.PACIJENT,
								lekar.getKorisnickoIme(),
								knjizica.getBroj(),
							});
					}else {
						pacijent.setIme(ime);
						pacijent.setPrezime(prezime);
						pacijent.setJmbg(jmbg);
						pacijent.setPol(pol);
						pacijent.setAdresa(adresa);
						pacijent.setBrojTelefona(brojTelefona);
						pacijent.setKorisnickoIme(korisnickoIme);
						pacijent.setLozinka(lozinka);
						pacijent.setUloga(uloga);
						pacijent.setIzabraniLekar(lekar);
						pacijent.setZdravstvenaKnjizica(knjizica);
					}
					Utils.snimiPacijente();
					IzmenaKreiranjePacijenata.this.dispose();
					IzmenaKreiranjePacijenata.this.setVisible(false);
				}
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Morate popuniti ";
		if (txtIme.getText().trim().equals("")) {
			poruka += "Ime;\n";
			ok = false;
		}
		if (txtPrezime.getText().trim().equals("")) {
			poruka += "Prezime;\n";
			ok = false;
		}
		if (txtKorisnickoIme.getText().trim().equals("")) {
			poruka += "Korisnicko ime;\n";
			ok = false;
		}
		
		if (txtLozinka.getText().trim().equals("")) {
			poruka += "Lozinka;\n";
			ok = false;
		}

		if (txtJmbg.getText().trim().equals("")) {
			poruka += "JMBG;\n";
			ok = false;
		}
		if (txtAdresa.getText().trim().equals("")) {
			poruka += "Adresa;\n";
			ok = false;
		}
		if (txtTelefon.getText().trim().equals("")) {
			poruka += "Broj telefona;\n";
			ok = false;
		}

		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}

}
