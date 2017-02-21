
public class Carré {
  private int X,Y,TailleCarré;
	public Carré(int X,int Y,int TailleCarré){
		this.X=X;
		this.Y=Y;
		this.TailleCarré=TailleCarré;
    }
	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}
	public int getTailleCarré(){
		return this.TailleCarré;
	}
	public void setX(int X){
		this.X=X;
	}
	public void setY(int Y){
		this.Y=Y;
	}
	public void setTailleCarré(int TailleCarré){
		this.TailleCarré=TailleCarré;
	}
}
