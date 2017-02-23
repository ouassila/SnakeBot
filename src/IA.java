import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class IA {

	private static ArrayList<Integer> directions;
	private ArrayList<String> listAction = new ArrayList<String>();
	private int nbrNodes;
	private ArrayList<JSONObject> memories = new ArrayList<JSONObject>();
	private String lastAction;
	private Map<String, Integer> sequences;
	
	public static int SCORE_LEFT = -5;
	public static int SCORE_RIGHT = -5;
	public static int SCORE_TOP = 10;
	public static int SCORE_BOTTOM = 5;
	public static int SCORE_COLLISION = -20;
	
	private int score;

	public IA(){
		score = 0;
		directions = new ArrayList<Integer>();
		directions.add(37);
		directions.add(38);
		directions.add(39);
		directions.add(40);

		listAction.add("left");
		listAction.add("right");
		listAction.add("top");
		listAction.add("bottom");
		
		sequences = new HashMap<String, Integer>();
		nbrNodes= 0;		
	}	

	public void updateMemory(String action, int result){
		try {
			JSONObject memory = new JSONObject();
			memory.put("action", action);
			memory.put("result", result);
			memories.add(memory);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public String chooseBestAction(){		
		if(memories.size() < listAction.size()){
			return listAction.get(memories.size());
		}
		else{
			try {
				lastAction = memories.get(memories.size()-1).getString("action");			

				for(int i = (int) Math.floor(Math.random() * memories.size()); i<memories.size(); i++){
					
					if(memories.get(i).getString("action")==lastAction){
						String tempSequence = memories.get(i).getString("action");

						for(int j=i; j<memories.size() && j<Math.floor(Math.random() * (i+20 - i)) + i; j++){

							if(i == j){
								sequences.put(tempSequence, memories.get(i).getInt("result"));
							}
							else{
								int tempResult = sequences.get(tempSequence);
								tempSequence+="."+memories.get(j).getString("action");
								sequences.put(tempSequence, memories.get(j).getInt("result") + tempResult);
							}
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int bestPerformance = 0;
			String selectedSequence = "";

			for(Entry<String, Integer> n : sequences.entrySet()) {

				System.out.println(n.getKey());
				if(n.getKey().substring(0, n.getKey().indexOf(".")) == lastAction){
					if(n.getValue()>bestPerformance){
						bestPerformance= n.getValue();
						selectedSequence = n.getKey();
					}
				}			    
			}

			for(Entry<String, Integer> n : sequences.entrySet()) {
				if(n.getKey().substring(0, n.getKey().indexOf("."))==lastAction){
					if(n.getValue()> bestPerformance){
						bestPerformance = n.getValue();
						selectedSequence = n.getKey();
					}
				}
			}
			if(selectedSequence != ""){
				//console.log(selectedSequence+ " : "+ sequences[selectedSequence]);
				//On retire l'action precendente de la sequence
				if(selectedSequence.indexOf(".")!=-1){
					selectedSequence= selectedSequence.substring(selectedSequence.indexOf(".")+1, selectedSequence.length());
				}
				//retour de l'action
				if(selectedSequence.indexOf(".")!=-1){
					return selectedSequence.substring(0, selectedSequence.indexOf("."));
				}else{
					return selectedSequence;
				}
			}

			String bestAction = "";
			do{
				bestAction = this.listAction.get((int)Math.floor(Math.random()*this.listAction.size()));
			}while(sequences.get(lastAction+"."+bestAction) != null && sequences.get(bestAction)!= null);

			return bestAction;	
		}
	}

	public void bouger(Serpent snake, Fenetre fenetre) throws IOException{
		
		/*Random randomizer = new Random();
		int direction = directions.get(randomizer.nextInt(directions.size()));
		
		System.out.println(direction);

		switch(direction){
		//gauche
		case 37:
			if(snake.getDirection()!='D'&&snake.getDirection()!='X')
				snake.setDirection('G');fenetre.Jouer(0);
				break;
		//up
		case 38:
			if(snake.getDirection()!='B')
				snake.setDirection('H');fenetre.Jouer(0);
				break;
		//droite
		case 39:
			if(snake.getDirection()!='G')
				snake.setDirection('D');fenetre.Jouer(0);
				break;
		//bas
		case 40:
			if(snake.getDirection()!='H')
				snake.setDirection('B');fenetre.Jouer(0);
				break;
		}*/
		
		String direct = chooseBestAction();
		System.out.println(direct);
		
		if(direct == "left" && snake.getDirection()!="right" && snake.getDirection()!= "X"){
			score = SCORE_LEFT;
			snake.setDirection("left");fenetre.Jouer(0);			
		}
		if(direct == "top" && snake.getDirection()!="bottom"){
			score = SCORE_TOP;
			snake.setDirection("top");fenetre.Jouer(0);			
		}
		if(direct == "right" && snake.getDirection()!= "left"){
			score = SCORE_RIGHT;
			snake.setDirection("right");fenetre.Jouer(0);			
		}
		if(direct == "bottom" && snake.getDirection()!="top"){
			score = SCORE_BOTTOM;
			snake.setDirection("bottom");fenetre.Jouer(0);			
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}	
}
