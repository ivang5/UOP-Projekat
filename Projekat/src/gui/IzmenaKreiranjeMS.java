package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.MedicinskaSestra;
import model.Pol;
import model.Sluzba;
import model.Uloga;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaKreiranjeMS extends JFrame {

	private MedicinskaSestra medicinskaSestra;
	private JLabel lblIme = new JLabel("Ime:");
	private JLabel lblPrezime = new JLabel("Prezime:");
	private JLabel lblJmbg = new JLabel("JMBG:");
	private JLabel lblPol = new JLabel("Pol:");
	private JLabel lblAdresa = new JLabel("Adresa:");
	private JLabel lblTelefon = new JLabel("Broj telefona:");
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
	private JLabel lblLozinka = new JLabel("Lozinka:");
	private JLabel lblPlata = new JLabel("Plata:");
	private JLabel lblSluzba = new JLabel("Sluzba:");
	private JTextField txtIme = new JTextField(10);
	private JTextField txtPrezime = new JTextField(10);
	private JTextField txtJmbg = new JTextField(10);
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values());
	private JTextField txtAdresa = new JTextField(10);
	private JTextField txtTelefon = new JTextField(10);
	private JTextField txtKorisnickoIme = new JTextField(10);
	private JTextField txtLozinka = new JTextField(10);
	private JTextField txtPlata = new JTextField(10);
	private JComboBox<Sluzba> cbSluzba = new JComboBox<Sluzba>(Sluzba.values());
	private JButton btnPotvrdi = new JButton("Potvrdi");
	
	public IzmenaKreiranjeMS(MedicinskaSestra medicinskaSestra) {
		this.medicinskaSestra = medicinskaSestra;
		if(this.medicinskaSestra == null) {
			setTitle("Kreiranje medicinske sestre");
		}else {
			setTitle(this.medicinskaSestra.getKorisnickoIme() + " - izmena");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 350);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
		initActions();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap 2");
		setLayout(layout);
		
		if(this.medicinskaSestra != null) {
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
		add(lblPlata);
		add(txtPlata);
		add(lblSluzba);
		add(cbSluzba);
		add(new JLabel());
		add(btnPotvrdi);
	}
	
	private void popunjavanjePolja() {
		txtIme.setText(this.medicinskaSestra.getIme());
		txtPrezime.setText(this.medicinskaSestra.getPrezime());
		txtJmbg.setText(this.medicinskaSestra.getJmbg());
		cbPol.setSelectedItem(this.medicinskaSestra.getPol());
		txtAdresa.setText(this.medicinskaSestra.getAdresa());
		txtTelefon.setText(this.medicinskaSestra.getBrojTelefona());
		txtKorisnickoIme.setText(this.medicinskaSestra.getKorisnickoIme());
		txtLozinka.setText(this.medicinskaSestra.getLozinka());
		txtPlata.setText(String.valueOf(this.medicinskaSestra.getPlata()));
		cbSluzba.setSelectedItem(this.medicinskaSestra.getSluzba());
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
					Uloga uloga = Uloga.MEDICINSKA_SESTRA;
					double plata = Double.parseDouble(txtPlata.getText().trim());
					Sluzba sluzba = (Sluzba) cbSluzba.getSelectedItem();
					if(medicinskaSestra == null) {
						medicinskaSestra = new MedicinskaSestra(ime, prezime, jmbg, pol, adresa, brojTelefona, korisnickoIme, lozinka, uloga, plata, sluzba);
						Utils.getMedicinskeSestre().add(medicinskaSestra);
						MedicinskaSestraMedSes.DodajRedUTabelu(new Object[] {
								txtIme.getText(),
								txtPrezime.getText(),
								txtJmbg.getText(),
								cbPol.getSelectedItem(),
								txtAdresa.getText(),
								txtTelefon.getText(),
								txtKorisnickoIme.getText(),
								txtLozinka.getText(),
								Uloga.MEDICINSKA_SESTRA,
								txtPlata.getText(),
								cbSluzba.getSelectedItem()
							});
					}else {
						medicinskaSestra.setIme(ime);
						medicinskaSestra.setPrezime(prezime);
						medicinskaSestra.setJmbg(jmbg);
						medicinskaSestra.setPol(pol);
						medicinskaSestra.setAdresa(adresa);
						medicinskaSestra.setBrojTelefona(brojTelefona);
						medicinskaSestra.setKorisnickoIme(korisnickoIme);
						medicinskaSestra.setLozinka(lozinka);
						medicinskaSestra.setUloga(uloga);
						medicinskaSestra.setPlata(plata);
						medicinskaSestra.setSluzba(sluzba);
					}
					Utils.snimiMedicinskeSestre();
					IzmenaKreiranjeMS.this.dispose();
					IzmenaKreiranjeMS.this.setVisible(false);
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
		if (txtPlata.getText().trim().equals("")) {
			poruka += "Plata;\n";
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
