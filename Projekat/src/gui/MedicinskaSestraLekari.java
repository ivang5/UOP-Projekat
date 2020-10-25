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

import model.Lekar;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class MedicinskaSestraLekari extends JFrame {

	private Lekar lekar;
	private static JTable tableLekari;
	private JButton btnIzmeni = new JButton("Izmeni");
	private JButton btnDodaj = new JButton("Dodaj");
	private JButton btnObrisi = new JButton("Obrisi");
	
	public MedicinskaSestraLekari() {
		setTitle("Lekari");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		initGUI();
	}
	
	public void initGUI() {
		MigLayout layout = new MigLayout("wrap", "[]", "[]5[]");
		setLayout(layout);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		String[] header = new String[] { "Ime", "Prezime", "JMBG", "Pol", "Adresa", "Br. telefona", "Korisnicko ime",
				"Lozinka", "Uloga", "Plata", "Specijalizacija", "Sluzba" };
		
		int velicina = Utils.getLekari().size();
		Object[][] content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			Lekar lekar = Utils.getLekari().get(i);
			content[i][0] = lekar.getIme();
			content[i][1] = lekar.getPrezime();
			content[i][2] = lekar.getJmbg();
			content[i][3] = lekar.getPol();
			content[i][4] = lekar.getAdresa();
			content[i][5] = lekar.getBrojTelefona();
			content[i][6] = lekar.getKorisnickoIme();
			content[i][7] = lekar.getLozinka();
			content[i][8] = lekar.getUloga();
			content[i][9] = lekar.getPlata();
			content[i][10] = lekar.getSpecijalizacija();
			content[i][11] = lekar.getSluzba();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableLekari = new JTable(model);
		tableLekari.setRowSelectionAllowed(true);
		tableLekari.setColumnSelectionAllowed(false);
		tableLekari.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLekari.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableLekari);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnIzmeni, "split 3, right");
		add(btnDodaj);
		add(btnObrisi);
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableLekari.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableLekari.getModel();
					String korisnickoIme = model.getValueAt(red, 6).toString();
					Lekar lekar = Utils.pronadjiLekara(korisnickoIme);
					if(lekar != null) {
						IzmenaKreiranjeLekara ikl = new IzmenaKreiranjeLekara(lekar);
						ikl.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Lekar nije pronadjen, pokusajte ponovo!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IzmenaKreiranjeLekara ikl = new IzmenaKreiranjeLekara(lekar);
				ikl.setVisible(true);
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableLekari.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Lekar nije pronadjen, pokusajte ponovo!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					String korisnickoIme = tableLekari.getValueAt(red, 6).toString();
					Lekar lekar = Utils.pronadjiLekara(korisnickoIme);
					if(lekar != null) {
						int izabraniLekar = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje lekara", JOptionPane.YES_NO_OPTION);
						if(izabraniLekar == JOptionPane.YES_OPTION) {
							Utils.getLekari().remove(lekar);
							DefaultTableModel model = (DefaultTableModel) tableLekari.getModel();
							model.removeRow(red);
							Utils.snimiLekare();
						}
					}
				}
			}
		});
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tableLekari.getModel();
		model.addRow(dataRow);
	}
}
