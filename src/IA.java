import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IA {

	private static ArrayList<Integer> directions;
	private ArrayList<String> listAction = new ArrayList<String>();
	private int nbrNodes;
	private Map<String,JSONObject> memories = new HashMap<String,JSONObject>() ;
	private String lastAction;
	private Map<String, Integer> sequences;
	private String lastState;

	public static int SCORE_LEFT = 5;
	public static int SCORE_RIGHT = 50;
	public static int SCORE_TOP = 5;
	public static int SCORE_BOTTOM = 5;
	public static int SCORE_COLLISION = -80;
	public static int SCORE_POMME = 100;
	public static int SCORE_VERS_POMME = 30;
	public static int SCORE_NON_VERS_POMME = -20;

	private int score, bestValue;

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

		nbrNodes= 0;	
		bestValue = 0;
	}	
	
	public int bestNodeMemory(){
		try {
			for(int i = 0; i < memories.size(); i++){

				if(memories.get(i).getInt("value") == bestValue){
					return i;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public void updateMemory(String action, int result){
		try {
			String state = action +":"+result;

			if(memories.get(state) == null){
				JSONObject memory = new JSONObject();
				memory.put("action", action);
				memory.put("result", result);
				memory.put("adjacentTo", new ArrayList<String>());
				memories.put(state, memory);
			}

			if(lastState!=null){
				Type listType = new TypeToken<ArrayList<String>>(){}.getType();
				//List<String> adj = new Gson().fromJson(memories.get(lastState).get("adjacentTo"), listType);
				/*
				if(adj.indexOf(state)==-1){					
					adj.add(state);
				}
				*/
			}
			score+= result;
			lastState= state;
			if(result>bestValue){
				bestValue= result;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public String chooseBestAction(){	
		String action = "";
		if(memories.size() < 10){
			return listAction.get((int) Math.floor(Math.random()*this.listAction.size()));
		} else
			try {
				if(((ArrayList<String>)memories.get(lastState).get("adjacentTo")).size()<10-1){
					do{
						action = this.listAction.get((int)Math.floor(Math.random()*this.listAction.size()));
					}while(((ArrayList<String>)memories.get(lastState).get("adjacentTo")).indexOf(action)!=-1);
					return action;
				}
				else{			
					
					int selectedValue= -1000;
					ArrayList<JSONObject> selectedResult = new ArrayList<JSONObject>();
					
					for(String item : ((ArrayList<String>)memories.get(lastState).get("adjacentTo"))){
						
						if(memories.get(item).getInt("result") > selectedValue){
							selectedValue = memories.get(item).getInt("result");
							selectedResult = new ArrayList<JSONObject>();
							JSONObject tmp = new JSONObject();
							tmp.put("action", memories.get(item).getString("action"));
							selectedResult.add(tmp);
						}
						else if(memories.get(item).getInt("result")==selectedValue){
							JSONObject tmp = new JSONObject();
							tmp.put("action", memories.get(item).getString("action"));
							selectedResult.add(tmp);
						}
					}
					return selectedResult.get((int) Math.floor(Math.random()*selectedResult.size())).getString("action");				
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "";
	}

	public void bouger(Serpent snake, Fenetre fenetre) throws IOException{

		String direct = chooseBestAction();
		System.out.println(direct);

		if(direct == "left" && snake.getDirection()!="right"){
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
}
