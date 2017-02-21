import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;


public class Fenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9134711996673092449L;
	private Carré Objectif=new Carré(0,0,20);
	private Serpent Snake=new Serpent('X');
	private PlateauJeu PlateauJeu1;
	private JLabel LabelScore=new JLabel("SCORE: 0");
	private JMenuBar MenuPrincipale=new JMenuBar();
	private JMenu MenuFichier=new JMenu("Fichier"),MenuAide=new JMenu("Aide"),
			Vitesse=new JMenu("Vitesse");
	private JMenuItem NouveauJeu=new JMenuItem("Nouveau Jeu"),
			Pause=new JMenuItem("Pause"),
			Quiter=new JMenuItem("Quiter"),
			Control=new JMenuItem("Controle"),
			Apropos=new  JMenuItem("À propos?");
	private JOptionPane Information=new JOptionPane();
	private JRadioButtonMenuItem TLent=new JRadioButtonMenuItem("Trés lente"),
			Lent=new JRadioButtonMenuItem("Lente"),
			Moyenne=new JRadioButtonMenuItem("Moyenne"),
			Rapide=new JRadioButtonMenuItem("Rapide"),
			TRapide=new JRadioButtonMenuItem("Trés rapide"); 
	private int vitesse=200,Score=0;
	private boolean pause=false;
	char direction; 
	private IA bot;

	public Fenetre(){

		bot = new IA();
		this.setTitle("Snake");
		this.setLocation(200,30);
		this.setSize(630,700);
		this.setResizable(false);
		this.setVisible(true);
		Menu();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RandObjectif();
		PlateauJeu1=new PlateauJeu(Snake,Objectif);
		this.add(PlateauJeu1,BorderLayout.CENTER);
		LabelScore.setBorder(BorderFactory.createBevelBorder(1));
		LabelScore.setFont(new Font("Arial",Font.BOLD,20));
		LabelScore.setForeground(Color.PINK);
		this.add(LabelScore,BorderLayout.SOUTH);
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub				
			}
		});		
		Start();
	}
	public void RandObjectif(){
		Random Rand=new Random();
		int IndexX=Rand.nextInt(28);
		int IndexY=Rand.nextInt(28);
		Objectif.setX(IndexX*20);
		Objectif.setY(IndexY*20);
		for(Carré T:Snake.getSuit())
			if(IndexX*20==T.getX()&&IndexY*20==T.getY()){
				this.RandObjectif();	
			}
		if(IndexX*20==Snake.getTete().getX()&&IndexY*20==Snake.getTete().getY()){
			this.RandObjectif();
		}

	}
	public void Start(){		
		while(true){			
			bot.bouger(Snake, this);
			Jouer(1);
			try {
				Thread.sleep(vitesse);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void Jouer(int a){
		if((Snake.getTete().getX()==Objectif.getX())&&(Snake.getTete().getY()==Objectif.getY()))
		{Snake.AjouterCarré();RandObjectif();Score+=10;
		LabelScore.setText("SCORE: "+Score);
		}
		else if(a!=0)Snake.AvancerSerpent();
		for(Carré T:Snake.getSuit())
			if(Snake.getTete().getX()==T.getX()&&Snake.getTete().getY()==T.getY()){
				System.out.println("GAME OVER");
				Information.showMessageDialog(null,"                Game Over\nCliquez sur OK pour recommencer","Information",JOptionPane.INFORMATION_MESSAGE);
				Snake.Réinitialiser();Score=0;LabelScore.setText("SCORE: 0");break;
			}
		if(Snake.getTete().getX()<0||Snake.getTete().getX()>20*30-40||Snake.getTete().getY()<0||Snake.getTete().getY()>20*30-40)
		{
			System.out.println(Snake.getTete().getX());
			System.out.println("GAME OVER");
			Information.showMessageDialog(null,"                Game Over\nCliquez sur OK pour recommencer","Information",JOptionPane.INFORMATION_MESSAGE);
			Snake.Réinitialiser();Score=0;LabelScore.setText("SCORE: 0");
		}
		PlateauJeu1.repaint();

	}
	public void Menu(){
		MenuPrincipale.add(MenuFichier);
		MenuPrincipale.add(MenuAide);
		MenuFichier.add(NouveauJeu);
		MenuFichier.add(Pause);
		Pause.setAccelerator(KeyStroke.getKeyStroke('p'));
		MenuFichier.add(Vitesse);
		MenuFichier.addSeparator();
		MenuFichier.add(Quiter);
		MenuAide.add(Control);
		MenuAide.add(Apropos);
		this.setJMenuBar(MenuPrincipale); 
		ButtonGroup BG=new ButtonGroup();
		BG.add(TLent);BG.add(Lent);BG.add(Moyenne);BG.add(Rapide);
		BG.add(TRapide);
		Moyenne.setSelected(true);
		Vitesse.add(TLent);Vitesse.add(Lent);Vitesse.add(Moyenne);
		Vitesse.add(Rapide);Vitesse.add(TRapide);
		TLent.addActionListener(new VitesseListener());
		Lent.addActionListener(new VitesseListener());
		Moyenne.addActionListener(new VitesseListener());
		Rapide.addActionListener(new VitesseListener());
		TRapide.addActionListener(new VitesseListener());
		NouveauJeu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Score=0;LabelScore.setText("SCORE: 0");
				Snake.Réinitialiser();
			}
		});
		Quiter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		Control.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Information.showMessageDialog(null,"Haut -> déplacez en haut\nBas -> déplacez en Bas\nDroite -> déplacez à droite\nGauche -> déplacez à gauche\n p   -> Pause","Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		Apropos.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Information.showMessageDialog(null,"Réaliser par:\n              ANJDEV","Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});

		Pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(pause){Snake.setDirection(direction);pause=false;}
				else{direction=Snake.getDirection();Snake.setDirection('x');pause=true;}
			}
		});

	}
	class VitesseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource()==TLent) vitesse=1000;
			if(arg0.getSource()==Lent) vitesse=500;
			if(arg0.getSource()==Moyenne) vitesse=200;
			if(arg0.getSource()==Rapide) vitesse=100;
			if(arg0.getSource()==TRapide) vitesse=20;
		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Fenetre();
	}
}




