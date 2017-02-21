import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PlateauJeu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5694047727876528962L;
	private Serpent Snake;
    private Carré Objectif; 
	public PlateauJeu(Serpent Snake,Carré Objectif){
		this.Snake=Snake;
		this.Objectif=Objectif;
	}
	public void paintComponent(Graphics G){
		G.setColor(Color.BLUE);
		G.fillRect(0,0,this.getWidth(),this.getHeight());
		G.setColor(Color.WHITE);
		G.fillRect(20,20,20*30-20,20*30-20);
		G.setColor(Color.black);
		G.drawRect(19,19,20*30-20,20*30-20);
		G.drawRect(18,18,20*30-20,20*30-20);
		G.drawRect(17,17,20*30-20,20*30-20);
		G.setColor(Color.blue);
		G.fillRect(20+Snake.getTete().getX(),20+Snake.getTete().getY(),Snake.getTete().getTailleCarré(),Snake.getTete().getTailleCarré());
		G.setColor(Color.RED);
		G.drawRect(20+Snake.getTete().getX(),20+Snake.getTete().getY(),Snake.getTete().getTailleCarré()-1,Snake.getTete().getTailleCarré()-1);
       for(Carré CarréSuit:Snake.getSuit()){
			G.setColor(Color.DARK_GRAY);
			G.fillRect(20+CarréSuit.getX(),20+CarréSuit.getY(),CarréSuit.getTailleCarré()-1,CarréSuit.getTailleCarré());
	    	G.setColor(Color.RED);
	    	G.drawRect(20+CarréSuit.getX(),20+CarréSuit.getY(),CarréSuit.getTailleCarré()-1,CarréSuit.getTailleCarré()-1);
	     }
       G.setColor(Color.black);
		G.fillRect(20+Objectif.getX()+5,20+Objectif.getY()+5,Objectif.getTailleCarré()-10,Objectif.getTailleCarré()-10);
	}
}
