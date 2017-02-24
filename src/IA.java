import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class IA {

	private static ArrayList<Integer> directions;
	private ArrayList<String> listAction = new ArrayList<String>();
	private int nbrNodes;
	private Map<String,JSONObject> memories = new HashMap<String,JSONObject>() ;
	private String lastAction;
	public String getLastAction() {
		return lastAction;
	}

	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	private Map<String, Integer> sequences;
	private String lastState;

	public static int SCORE_LEFT = 5;
	public static int SCORE_RIGHT = 5;
	public static int SCORE_TOP = 5;
	public static int SCORE_BOTTOM = 5;
	public static int SCORE_COLLISION = -15;
	public static int SCORE_POMME = 20;
	public static int SCORE_VERS_POMME = 10;
	public static int SCORE_NON_VERS_POMME = -5;

	private int score, bestValue;

	public IA(){
		score = 0;
		directions = new ArrayList<Integer>();
		directions.add(37);
		directions.add(38);
		directions.add(39);
		directions.add(40);
		lastAction="right";
		listAction.add("left");
		listAction.add("right");
		listAction.add("top");
		listAction.add("bottom");	

		nbrNodes= 0;	
		bestValue = 0;
	}	

	public String bestNodeMemory(){
		try {
			for(Entry<String, JSONObject> entry : memories.entrySet()) {
				if(memories.get(entry.getKey()).getInt("result") == bestValue){
					return entry.getKey();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateMemory(String action, int result){
		try {
			//System.out.println("Score result :" + result);
			String state = action +":"+result;
			//System.out.println("resultat " +result);
			System.out.println("state : " + state);
			if(memories.get(state) == null){
				JSONObject memory = new JSONObject();
				memory.put("action", action);
				memory.put("result", result);
				memory.put("adjacentTo", new ArrayList<String>());
				memories.put(state, memory);
			}


			if(lastState!=null){
				Type listType = new TypeToken<ArrayList<String>>(){}.getType();
				List<String> adj = new Gson().fromJson(memories.get(lastState).get("adjacentTo").toString(), listType);
				System.out.println(memories.get(lastState).get("adjacentTo").toString());
				if(adj.indexOf(state)==-1){					
					adj.add(state);
				}
				memories.get(state).put("adjacentTo",adj);
			}
			//score+= result;
			lastState= state;
			if(result>bestValue){
				bestValue= result;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public String chooseBestAction() throws JsonSyntaxException, JSONException{	
		String action = "";

		if(memories.size() < 10){
			do{
				String futurAction = listAction.get((int) Math.floor(Math.random()*this.listAction.size()));

				if(lastAction != null && isActionPossible(futurAction)){
					//	System.out.println("Last Action : "+lastAction);
					//	System.out.println("Futuraction : "+ futurAction);
					lastAction = futurAction;
					return futurAction;
				}
				else if(lastAction == null){
					//	System.out.println("Last Action : "+lastAction);
					//	System.out.println("Futuraction : "+ futurAction);
					lastAction = futurAction;
					return futurAction;
				}

			}while(true);
		} 
		else {
			Type listTypeString = new TypeToken<ArrayList<String>>(){}.getType();
			lastAction = (String) memories.get(lastState).get("action");
			List<String> adja = new Gson().fromJson(memories.get(lastState).get("adjacentTo").toString(), listTypeString);

			if(adja.size()<10-1){
				//	System.out.println("Else adjacent size<10");
				//	System.out.println("adjacent : " + adj.size());
				do{
					action = this.listAction.get((int)Math.floor(Math.random()*this.listAction.size()));
				}while(adja.indexOf(action)!=-1 && isActionPossible(action));
				lastAction = action;
				return action;
			}
			else{
				ArrayList<String> visitedNode = new ArrayList<String>();
				ArrayList<String> exploredNode = new ArrayList<String>();
				String bestNode = bestNodeMemory();
				String currentState = lastState;
				HashMap<Integer, JSONObject> scoresValues = new HashMap<Integer, JSONObject>();
				int currentLevel = 0;
				exploredNode.add(lastState);
				int parcourVal = -100000;
				while(visitedNode.indexOf(bestNode)==-1){
					String selectedNode = null;
					int selectedValue = -100000;
					int parcourValue = -100000;
					Type listType = new TypeToken<ArrayList<String>>(){}.getType();
					List<String> adj = new Gson().fromJson(memories.get(currentState).get("adjacentTo").toString(), listType);
					for(String item : adj){
						if(selectedValue<(memories.get(item)).getInt("result") && visitedNode.indexOf(item)==-1){
							selectedNode = item;
							selectedValue = memories.get(item).getInt("result");
							if(scoresValues.get(selectedValue) == null){
								JSONObject memory = new JSONObject();
								memory.put("passBy", selectedNode);
								memory.put("score", memories.get(selectedNode).getInt("result"));
								memory.put("level", currentLevel);
								scoresValues.put(selectedValue, memory);
							}
							else {
								int scoreFinal = scoresValues.get(selectedValue).getInt("score")+memories.get(selectedNode).getInt("result");
								JSONObject memory = new JSONObject();
								memory.put("passBy", selectedNode);
								memory.put("score",scoreFinal);
								memory.put("level", currentLevel);
								scoresValues.put(selectedValue, memory);
							}
						}
						visitedNode.add(item);
					}
					ArrayList<JSONObject> selectNextNodeToExplore = null;
					for(Entry<Integer, JSONObject> entry : scoresValues.entrySet()) {
						if(scoresValues.get(entry.getKey()).getInt("score")> parcourValue && scoresValues.get(entry.getKey()).getInt("level")==currentLevel){
							parcourValue = scoresValues.get(entry.getKey()).getInt("score");
							parcourVal = parcourValue;
							selectNextNodeToExplore = new ArrayList<JSONObject>();
							JSONObject memory = new JSONObject();
							memory.put("action", scoresValues.get(entry.getKey()).getString("passBy"));
							selectNextNodeToExplore.add(memory);
						}
						else if(scoresValues.get(entry.getKey()).getInt("score")==parcourValue && scoresValues.get(entry.getKey()).getInt("level")==currentLevel){
							JSONObject memory = new JSONObject();
							memory.put("action", scoresValues.get(entry.getKey()).getString("passBy"));
							selectNextNodeToExplore.add(memory);
						}
					}
					currentLevel++;
				}
				for(Entry<Integer, JSONObject> entry : scoresValues.entrySet()){
					if(scoresValues.get(entry.getKey()).getInt("score")>=parcourVal&& scoresValues.get(entry.getKey()).getInt("level")==0){
						currentState = scoresValues.get(entry.getKey()).getString("passBy");
					}
				}
				lastAction = memories.get(currentState).getString("action");
				return memories.get(currentState).getString("action");
			}
		}
	}
	private boolean isActionPossible(String action) {
		if(action == "left" && lastAction == "right")
			return false;
		if(action == "right" && lastAction == "left")
			return false;
		if(action == "top" && lastAction == "bottom")
			return false;
		if(action == "bottom" && lastAction == "top")
			return false;
		return true;
	}

	public void bouger(Serpent snake, Fenetre fenetre) throws IOException{

		String direct="";
		try {
			direct = chooseBestAction();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		//System.out.println("Score " + score);
		return score;
	}
}
