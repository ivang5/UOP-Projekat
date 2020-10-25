package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Pacijent;
import model.Pregled;
import model.ZdravstvenaKnjizica;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class MedicinskaSestraPacijenti extends JFrame {

	private Pacijent pacijent;
	private static JTable tablePacijenti;
	private JButton btnIzmeni = new JButton("Izmeni");
	private JButton btnDodaj = new JButton("Dodaj");
	private JButton btnObrisi = new JButton("Obrisi");
	
	public MedicinskaSestraPacijenti() {
		setTitle("Pacijenti");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
	}
	
	private void initGUI() {
		MigLayout layout = new MigLayout("wrap", "[]", "[]5[]");
		setLayout(layout);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Lekar", "Knjizica"};
		
		int velicina = Utils.getPacijenti().size();
		Object[][] content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			Pacijent pacijent = Utils.getPacijenti().get(i);
			content[i][0] = pacijent.getIme();
			content[i][1] = pacijent.getPrezime();
			content[i][2] = pacijent.getJmbg();
			content[i][3] = pacijent.getPol();
			content[i][4] = pacijent.getAdresa();
			content[i][5] = pacijent.getBrojTelefona();
			content[i][6] = pacijent.getKorisnickoIme();
			content[i][7] = pacijent.getLozinka();
			content[i][8] = pacijent.getUloga();
			content[i][9] = pacijent.getIzabraniLekar().getKorisnickoIme();
			content[i][10] = pacijent.getZdravstvenaKnjizica().getBroj();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tablePacijenti = new JTable(model);
		tablePacijenti.setRowSelectionAllowed(true);
		tablePacijenti.setColumnSelectionAllowed(false);
		tablePacijenti.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tablePacijenti.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tablePacijenti);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		btnIzmeni = new JButton("Izmeni");
		btnDodaj = new JButton("Dodaj");
		btnObrisi = new JButton("Obrisi");
		add(btnIzmeni, "split3, right");
		add(btnDodaj);
		add(btnObrisi);
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePacijenti.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tablePacijenti.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					Pacijent pacijent = Utils.pronadjiPacijenta(korisnickoIme);
					if(pacijent != null) {
						IzmenaKreiranjePacijenata ip = new IzmenaKreiranjePacijenata(pacijent);
						ip.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Pacijent nije pronadjen, pokusajte ponovo!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IzmenaKreiranjePacijenata ip = new IzmenaKreiranjePacijenata(pacijent);
				ip.setVisible(true);
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tablePacijenti.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tablePacijenti.getValueAt(red, 6).toString();
					int broj = Integer.parseInt(tablePacijenti.getValueAt(red, 10).toString());
					Pacijent pacijent = Utils.pronadjiPacijenta(korisnickoIme);
					ZdravstvenaKnjizica knjizica = Utils.pronadjiZdravstvenuKnjizicu(broj);
					if(pacijent!=null&&knjizica!=null) {
						int izabran = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje pacijenta", JOptionPane.YES_NO_OPTION);
						if(izabran == JOptionPane.YES_OPTION) {
							Utils.getPacijenti().remove(pacijent);
							Utils.getKnjizice().remove(knjizica);
							DefaultTableModel model = (DefaultTableModel) tablePacijenti.getModel();
							model.removeRow(red);
							Utils.snimiPacijente();
							Utils.snimiZdravstveneKnjizice();
						}
					}
				}
			}
		});
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tablePacijenti.getModel();
		model.addRow(dataRow);
	}
}
