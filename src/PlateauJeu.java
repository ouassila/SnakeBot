import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PlateauJeu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5694047727876528962L;
	private Serpent Snake;
    private Carr� Objectif; 
	public PlateauJeu(Serpent Snake,Carr� Objectif){
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
		G.fillRect(20+Snake.getTete().getX(),20+Snake.getTete().getY(),Snake.getTete().getTailleCarr�(),Snake.getTete().getTailleCarr�());
		G.setColor(Color.RED);
		G.drawRect(20+Snake.getTete().getX(),20+Snake.getTete().getY(),Snake.getTete().getTailleCarr�()-1,Snake.getTete().getTailleCarr�()-1);
       for(Carr� Carr�Suit:Snake.getSuit()){
			G.setColor(Color.DARK_GRAY);
			G.fillRect(20+Carr�Suit.getX(),20+Carr�Suit.getY(),Carr�Suit.getTailleCarr�()-1,Carr�Suit.getTailleCarr�());
	    	G.setColor(Color.RED);
	    	G.drawRect(20+Carr�Suit.getX(),20+Carr�Suit.getY(),Carr�Suit.getTailleCarr�()-1,Carr�Suit.getTailleCarr�()-1);
	     }
       G.setColor(Color.black);
		G.fillRect(20+Objectif.getX()+5,20+Objectif.getY()+5,Objectif.getTailleCarr�()-10,Objectif.getTailleCarr�()-10);
	}
}
