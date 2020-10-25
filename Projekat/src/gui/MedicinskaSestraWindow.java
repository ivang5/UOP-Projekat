package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Korisnik;

public class MedicinskaSestraWindow extends JFrame {
	
	public MedicinskaSestraWindow(Korisnik korisnik) {
		setTitle(korisnik.getKorisnickoIme() + " - Medicinska sestra");
		//setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000,500);
		setLocationRelativeTo(null);
		setResizable(false);
		Container c = this.getContentPane();
		c.setBackground(new Color(215, 145, 151));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("slike/Health.png");
		setIconImage(img);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu meniPregledi = new JMenu("Pregledi");
		JMenu meniKorisnici = new JMenu("Korisnici");
		JMenu meniZdravstveneKnjizice = new JMenu("Zdravstvene knjizice");
		
		JMenuItem pregledi = new JMenuItem("Pregledi");
		JMenuItem lekari = new JMenuItem("Lekari");
		JMenuItem medicinskeSestre = new JMenuItem("Medicinske sestre");
		JMenuItem pacijenti = new JMenuItem("Pacijenti");
		JMenuItem zdravstveneKnjizice = new JMenuItem("Zdravstvene knjizice");
		
		menuBar.add(meniPregledi);
		menuBar.add(meniKorisnici);
		menuBar.add(meniZdravstveneKnjizice);
		
		meniPregledi.add(pregledi);
		meniKorisnici.add(lekari);
		meniKorisnici.add(medicinskeSestre);
		meniKorisnici.add(pacijenti);
		meniZdravstveneKnjizice.add(zdravstveneKnjizice);
		
		pregledi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MedicinskaSestraPregledi msp = new MedicinskaSestraPregledi();
				msp.setVisible(true);
			}
		});
		
		lekari.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MedicinskaSestraLekari msl = new MedicinskaSestraLekari();
				msl.setVisible(true);
			}
		});
		
		medicinskeSestre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MedicinskaSestraMedSes msms = new MedicinskaSestraMedSes();
				msms.setVisible(true);
			}
		});
		
		pacijenti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MedicinskaSestraPacijenti msp = new MedicinskaSestraPacijenti();
				msp.setVisible(true);
			}
		});
		
		zdravstveneKnjizice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MedicinskaSestraZdravKnjiz mszk = new MedicinskaSestraZdravKnjiz();
				mszk.setVisible(true);
			}
		});
	}
}
