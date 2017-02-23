import java.util.ArrayList;


public class Serpent {
	private int NbrCarré;
	private Carré Tete;
	private String Direction;
	private ArrayList<Carré> Suit= new ArrayList<Carré>();
	
	public Serpent(String Direction){
		NbrCarré=3;
		this.Direction=Direction;
		Tete=new Carré(60,280,20);
		Suit.add(new Carré(Tete.getX()-Tete.getTailleCarré(),Tete.getY(),Tete.getTailleCarré()));
		for(int i=1;i<NbrCarré;i++)
			Suit.add(new Carré(Suit.get(i-1).getX()-Suit.get(i-1).getTailleCarré(),Suit.get(i-1).getY(),Tete.getTailleCarré()));
	}
	public void DeplacementCarré(){
		for(int i=NbrCarré-1;i>=1;i--)
		{Suit.get(i).setX(Suit.get(i-1).getX());
		Suit.get(i).setY(Suit.get(i-1).getY());
		}
		Suit.get(0).setX(Tete.getX());
		Suit.get(0).setY(Tete.getY());
	}
	public void AvancerSerpent(){
		
		if(Direction == "top"){
			DeplacementCarré();
			Tete.setY(Tete.getY()-Tete.getTailleCarré());
		}
		if(Direction == "bottom"){
			DeplacementCarré();
			Tete.setY(Tete.getY()+Tete.getTailleCarré());
		}
		if(Direction == "left"){
			DeplacementCarré();
			Tete.setX(Tete.getX()-Tete.getTailleCarré());
		}
		if(Direction == "right"){
			DeplacementCarré();
			Tete.setX(Tete.getX()+Tete.getTailleCarré());
		}		
	}
	public void Réinitialiser(){
		this.Suit.clear();
		NbrCarré=3;
		this.Direction="X";
		Tete=new Carré(60,280,20);
		Suit.add(new Carré(Tete.getX()-Tete.getTailleCarré(),Tete.getY(),Tete.getTailleCarré()));
		for(int i=1;i<NbrCarré;i++)
			Suit.add(new Carré(Suit.get(i-1).getX()-Suit.get(i-1).getTailleCarré(),Suit.get(i-1).getY(),Tete.getTailleCarré()));
	}
	public void AjouterCarré(){
		int X=Suit.get(this.NbrCarré-1).getX(),
				Y=Suit.get(this.NbrCarré-1).getY();
		AvancerSerpent();
		this.NbrCarré++;
		Suit.add(new Carré(X,Y,this.Tete.getTailleCarré()));
	}
	public void setDirection(String Direction){
		this.Direction=Direction;
	}
	public String getDirection(){
		return this.Direction;
	}
	public Carré getTete(){
		return this.Tete;
	}
	public ArrayList<Carré> getSuit(){
		return this.Suit;
	}

}
