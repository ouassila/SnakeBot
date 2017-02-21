import java.util.ArrayList;
import java.util.Random;

public class IA {

	private static ArrayList<Integer> directions;
	
	public IA(){
		directions = new ArrayList<Integer>();
		directions.add(37);
		directions.add(38);
		directions.add(39);
		directions.add(40);
	}
	
	public void bouger(Serpent snake, Fenetre fenetre){
		Random randomizer = new Random();
		int direction = directions.get(randomizer.nextInt(directions.size()));
		
		System.out.println(direction);
		
		switch(direction){
			case 37:
				if(snake.getDirection()!='D'&&snake.getDirection()!='X')
					snake.setDirection('G');fenetre.Jouer(0);
			break;
			case 38:
				if(snake.getDirection()!='B')
					snake.setDirection('H');fenetre.Jouer(0);
				break;
			case 39:
				if(snake.getDirection()!='G')
					snake.setDirection('D');fenetre.Jouer(0);
				break;
			case 40:
				if(snake.getDirection()!='H')
					snake.setDirection('B');fenetre.Jouer(0);
				break;
		}		
	}
}
