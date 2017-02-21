import java.util.ArrayList;


public class Serpent {
	private int NbrCarr�;
	private Carr� Tete;
	private char Direction;
	private ArrayList<Carr�> Suit= new ArrayList<Carr�>();
	
	public Serpent(char Direction){
		NbrCarr�=3;
		this.Direction=Direction;
		Tete=new Carr�(60,280,20);
		Suit.add(new Carr�(Tete.getX()-Tete.getTailleCarr�(),Tete.getY(),Tete.getTailleCarr�()));
		for(int i=1;i<NbrCarr�;i++)
			Suit.add(new Carr�(Suit.get(i-1).getX()-Suit.get(i-1).getTailleCarr�(),Suit.get(i-1).getY(),Tete.getTailleCarr�()));
	}
	public void DeplacementCarr�(){
		for(int i=NbrCarr�-1;i>=1;i--)
		{Suit.get(i).setX(Suit.get(i-1).getX());
		Suit.get(i).setY(Suit.get(i-1).getY());
		}
		Suit.get(0).setX(Tete.getX());
		Suit.get(0).setY(Tete.getY());
	}
	public void AvancerSerpent(){
		switch(this.Direction){
			case 'H':DeplacementCarr�();
				Tete.setY(Tete.getY()-Tete.getTailleCarr�());break;
			case 'B':DeplacementCarr�();
				Tete.setY(Tete.getY()+Tete.getTailleCarr�());break;
			case 'G':DeplacementCarr�();
				Tete.setX(Tete.getX()-Tete.getTailleCarr�());break;
			case 'D':DeplacementCarr�();
				Tete.setX(Tete.getX()+Tete.getTailleCarr�());break;
		}
	}
	public void R�initialiser(){
		this.Suit.clear();
		NbrCarr�=3;
		this.Direction='X';
		Tete=new Carr�(60,280,20);
		Suit.add(new Carr�(Tete.getX()-Tete.getTailleCarr�(),Tete.getY(),Tete.getTailleCarr�()));
		for(int i=1;i<NbrCarr�;i++)
			Suit.add(new Carr�(Suit.get(i-1).getX()-Suit.get(i-1).getTailleCarr�(),Suit.get(i-1).getY(),Tete.getTailleCarr�()));
	}
	public void AjouterCarr�(){
		int X=Suit.get(this.NbrCarr�-1).getX(),
				Y=Suit.get(this.NbrCarr�-1).getY();
		AvancerSerpent();
		this.NbrCarr�++;
		Suit.add(new Carr�(X,Y,this.Tete.getTailleCarr�()));
	}
	public void setDirection(char Direction){
		this.Direction=Direction;
	}
	public char getDirection(){
		return this.Direction;
	}
	public Carr� getTete(){
		return this.Tete;
	}
	public ArrayList<Carr�> getSuit(){
		return this.Suit;
	}

}
