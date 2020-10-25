package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Korisnik;
import model.Pregled;
import model.StatusPregleda;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class LekarWindow extends JFrame {

	private Korisnik lekar;
	private static ArrayList<Pregled> listaPregleda = new ArrayList<Pregled>();
	private JTable tableLekarPregledi;
	private JButton btnIzmeni = new JButton("Izmeni status pregleda");
	private JButton btnOsvezi = new JButton("Osvezi pregled");

	public LekarWindow(Korisnik lekar) {
		this.lekar = lekar;
		setTitle(this.lekar.getKorisnickoIme() + " - Pregledi pacijenata");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000, 500);
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
		
		listaPregleda.clear();
		for(Pregled pregled:Utils.getPregledi()) {
			if(pregled.getLekar().getKorisnickoIme().equals(lekar.getKorisnickoIme())) {
				listaPregleda.add(pregled);
			}
		}
		
		String[] header = new String[] {"ID", "Pacijent", "Lekar", "Termin", "Soba", "Opis", "Status"};
		
		int velicina = listaPregleda.size();
		Object [][]content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			Pregled pregled = listaPregleda.get(i);
			content[i][0] = pregled.getId();
			content[i][1] = pregled.getPacijent().getKorisnickoIme();
			content[i][2] = pregled.getLekar().getKorisnickoIme();
			content[i][3] = pregled.getTermin();
			content[i][4] = pregled.getSoba();
			content[i][5] = pregled.getOpis();
			content[i][6] = pregled.getStatus();
		}
		
		DefaultTableModel tableModel = new DefaultTableModel(content, header);
		tableLekarPregledi = new JTable(tableModel);
		tableLekarPregledi.setRowSelectionAllowed(true);
		tableLekarPregledi.setColumnSelectionAllowed(false);
		tableLekarPregledi.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tableLekarPregledi.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableLekarPregledi);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnIzmeni, "split 2, right");
		add(btnOsvezi);
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableLekarPregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel tableModel = (DefaultTableModel) tableLekarPregledi.getModel();
					int id = Integer.parseInt(tableModel.getValueAt(red, 0).toString());
					Pregled pregled = Utils.pronadjiPregled(id);
					if(pregled != null && pregled.getStatus().equals(StatusPregleda.ZAKAZAN) || pregled.getStatus().equals(StatusPregleda.ZATRAZEN)) {
						IzmenaStatusaPregledaLekar ispl = new IzmenaStatusaPregledaLekar(pregled);
						ispl.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Pregled je zavrsen ili otkazan!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnOsvezi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				Utils.ucitavanjePregleda();
//				revalidate();
//				tableLekarPregledi.repaint();
				int red = tableLekarPregledi.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel tableModel = (DefaultTableModel) tableLekarPregledi.getModel();
					tableModel.fireTableRowsUpdated(red, red);
				}
			}
		});
		
	}

}
