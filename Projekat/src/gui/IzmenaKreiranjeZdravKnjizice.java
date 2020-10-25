package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.KategorijaOsiguranja;
import model.Pacijent;
import model.Uloga;
import model.ZdravstvenaKnjizica;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class IzmenaKreiranjeZdravKnjizice extends JFrame {

	private ZdravstvenaKnjizica knjizica;
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private JLabel lblBrojKnjizice = new JLabel("Broj knjizice:");
	private JLabel lblDatumIsteka = new JLabel("Datum isteka:");
	private JLabel lblKategorijaOsiguranja = new JLabel("Kategorija osiguranja:");
	private JTextField txtBrojKnjizice = new JTextField(10);
	private JTextField txtDatumIsteka = new JTextField(10);
	private JComboBox<KategorijaOsiguranja> cbKategorijaOsiguranja = new JComboBox<KategorijaOsiguranja>(KategorijaOsiguranja.values());
	private JButton btnPotvrdi = new JButton("Potvrdi");
	
	public IzmenaKreiranjeZdravKnjizice(ZdravstvenaKnjizica knjizica) {
		this.knjizica = knjizica;
		if(this.knjizica == null) {
			setTitle("Kreiranje knjizice");
		}else {
			setTitle("Izmena knjizice: " + this.knjizica.getBroj());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300,300);
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
		
		if(this.knjizica != null) {
			popunjavanjePolja();
		}
		
		add(lblBrojKnjizice);
		add(txtBrojKnjizice);
		add(lblDatumIsteka);
		add(txtDatumIsteka);
		add(lblKategorijaOsiguranja);
		add(cbKategorijaOsiguranja);
		add(new JLabel());
		add(btnPotvrdi);
	}
	
	private void popunjavanjePolja() {
		txtBrojKnjizice.setText(String.valueOf(this.knjizica.getBroj()));
		txtDatumIsteka.setText(String.valueOf(this.knjizica.getDatumIsteka()));
		cbKategorijaOsiguranja.setSelectedItem(this.knjizica.getKategorijaOsiguranja());
	}
	
	private void initActions() {
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validacija() == true) {
					int broj = Integer.parseInt(txtBrojKnjizice.getText());
					java.util.Date dateTime = null;
					while(dateTime == null) {
						String termin = txtDatumIsteka.getText();
						try {
							dateTime = DATE_TIME_FORMAT.parse(termin);
							//knjizica.setDatumIsteka(dateTime);
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Datum mora da bude u\ndd.MM.yyyy\nformatu.", "Greska", JOptionPane.WARNING_MESSAGE);
							txtDatumIsteka.setText("");
							return;
						}
					}
					KategorijaOsiguranja kategorijaOsiguranja = (KategorijaOsiguranja) cbKategorijaOsiguranja.getSelectedItem();
					if(knjizica == null) {
						knjizica = new ZdravstvenaKnjizica(broj, dateTime, kategorijaOsiguranja);
						Utils.getKnjizice().add(knjizica);
						MedicinskaSestraZdravKnjiz.DodajRedUTabelu(new Object[] {
								txtBrojKnjizice.getText(),
								txtDatumIsteka.getText(),
								cbKategorijaOsiguranja.getSelectedItem()
							});
					}else {
						knjizica.setBroj(broj);
						knjizica.setDatumIsteka(dateTime);
						knjizica.setKategorijaOsiguranja(kategorijaOsiguranja);
					}
					
					Utils.snimiZdravstveneKnjizice();
					IzmenaKreiranjeZdravKnjizice.this.dispose();
					IzmenaKreiranjeZdravKnjizice.this.setVisible(false);
				}
			}
		});
	}
	

	private boolean validacija() {
		boolean ok = true;
		String poruka = "Morate popuniti ";
		if(txtBrojKnjizice.getText().trim().equals("")) {
			poruka += "Broj\n";
			ok = false;
		}
		
		if (txtDatumIsteka.getText().trim().equals("")) {
			poruka += "Datum\n";
			ok = false;
		}
		
		if (ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Greska", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
}
