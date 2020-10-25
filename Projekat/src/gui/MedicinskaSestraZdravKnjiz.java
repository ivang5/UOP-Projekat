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

import model.Pregled;
import model.ZdravstvenaKnjizica;
import net.miginfocom.swing.MigLayout;
import utils.Utils;

public class MedicinskaSestraZdravKnjiz extends JFrame {

	private ZdravstvenaKnjizica knjizica;
	private static JTable tableZdravstveneKnjizice;
	private JButton btnDodaj = new JButton("Dodaj");
	private JButton btnIzmeni = new JButton("Izmeni");
	private JButton btnObrisi = new JButton("Obrisi");
	
	public MedicinskaSestraZdravKnjiz() {
		setTitle("Zdravstvene Knjizice");
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
		
		String[] header = new String[] { "Broj knjizice", "Datum isteka","Kategorija osiguranja" };
		
		int velicina = Utils.getKnjizice().size();
		Object[][] content = new Object[velicina][header.length];
		for(int i = 0; i < velicina; i++) {
			ZdravstvenaKnjizica knjizica = Utils.getKnjizice().get(i);
			content[i][0] = knjizica.getBroj();
			content[i][1] = knjizica.getDatumIsteka();
			content[i][2] = knjizica.getKategorijaOsiguranja();
		}
		
		DefaultTableModel model = new DefaultTableModel(content, header);
		tableZdravstveneKnjizice = new JTable(model);
		tableZdravstveneKnjizice.setRowSelectionAllowed(true);
		tableZdravstveneKnjizice.setColumnSelectionAllowed(false);
		tableZdravstveneKnjizice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableZdravstveneKnjizice.setDefaultEditor(Object.class, null);
		JScrollPane scrollPane = new JScrollPane(tableZdravstveneKnjizice);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		scrollPane.setMaximumSize(new Dimension(1000, 400));
		add(scrollPane, BorderLayout.CENTER);
		add(btnDodaj, "split 3, right");
		add(btnIzmeni);
		add(btnObrisi);
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IzmenaKreiranjeZdravKnjizice ikzk = new IzmenaKreiranjeZdravKnjizice(knjizica);
				ikzk.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableZdravstveneKnjizice.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					DefaultTableModel model = (DefaultTableModel) tableZdravstveneKnjizice.getModel();
					int broj = Integer.parseInt(model.getValueAt(red, 0).toString());
					ZdravstvenaKnjizica knjizica = Utils.pronadjiZdravstvenuKnjizicu(broj);
					if(knjizica != null) {
						IzmenaKreiranjeZdravKnjizice izk = new IzmenaKreiranjeZdravKnjizice(knjizica);
						izk.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Zdravstvena knjizica nije pronadjena, pokusajte ponovo!", "Greska",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int red = tableZdravstveneKnjizice.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Nije selektovan nijedan red!", "Greska",
							JOptionPane.WARNING_MESSAGE);
				}else {
					int broj = Integer.parseInt(tableZdravstveneKnjizice.getValueAt(red, 0).toString());
					ZdravstvenaKnjizica knjizica = Utils.pronadjiZdravstvenuKnjizicu(broj);
					if(knjizica!=null) {
						int izabrana = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Brisanje zdravstvene knjizice", JOptionPane.YES_NO_OPTION);
						if(izabrana == JOptionPane.YES_OPTION) {
							Utils.getPregledi().remove(knjizica);
							DefaultTableModel model = (DefaultTableModel) tableZdravstveneKnjizice.getModel();
							model.removeRow(red);
							Utils.snimiPregled();
						}
					}
				}
			}
		});
	}
	
	public static void DodajRedUTabelu(Object[] dataRow) {
		DefaultTableModel model = (DefaultTableModel)tableZdravstveneKnjizice.getModel();
		model.addRow(dataRow);
	}
}
