package main;


import java.util.ArrayList;
import java.util.HashMap;

public class finger_print implements Comparable<finger_print>{
	int id;
	double sim_closest_pivot;
	node closest_pivot;
	HashMap<Integer, Integer> feature_map;
	String line;
	int comparisons;
	
	//initialize the data item
	public finger_print(int id_num) {
		id=id_num;
		feature_map= new HashMap<Integer, Integer>();
		sim_closest_pivot=-1;
		comparisons=0;
	}

	//add feature values
	public void add_feature(int feature_id, int feature_value){
		feature_map.put(feature_id, feature_value);
	}
	
	//add all features in string format
	public void add_all_features(String[] features_string){
		for(int i=0;i<features_string.length;i++){
			String[] feature_parsed= features_string[i].replace(":", " ").trim().split("\\s+");
			int f_id= Integer.parseInt(feature_parsed[0]);
			int f_val= Integer.parseInt(feature_parsed[1]);
			feature_map.put(f_id, f_val);
		}
	}
	
	/* Tanimoto 
	 *
	 *
	 */
	public double getSimilarity(finger_print fp){
		return this.getTanimotoSimilarity(fp);		
		//return 1.0/getL2Distance(fp);
	}
	
	
	public double getTanimotoSimilarity(finger_print fp) {
		double num=0;
		double denom=0;
		for(Integer key : this.feature_map.keySet()){
			if(fp.feature_map.containsKey(key)){
				num+=1.0*Math.min(this.feature_map.get(key),fp.feature_map.get(key));
				denom+=1.0*Math.max(this.feature_map.get(key),fp.feature_map.get(key));			
			}
			else denom+=this.feature_map.get(key);
		}
		for(Integer key : fp.feature_map.keySet()){
			if(!this.feature_map.containsKey(key)){
				denom+=fp.feature_map.get(key);
			}
		}
		return 1.0*num/denom;		
	}

	public double getL2Distance(finger_print fp){
		double score=0;		
		for(Integer key : this.feature_map.keySet()){
			if(fp.feature_map.containsKey(key)){
				score+=Math.pow(this.feature_map.get(key)-fp.feature_map.get(key), 2);
			}
			else 
				score+=Math.pow(this.feature_map.get(key), 2);			
		}
		for(Integer key : fp.feature_map.keySet()){
			if(!this.feature_map.containsKey(key)){
				score+=Math.pow(fp.feature_map.get(key), 2);
			}
		}
		return Math.sqrt(score);		
	}
	
	
	public double getDistance(finger_print fp){
		
		//return getL2Distance(fp);
		
		//return -Math.log((getSimilarity(fp)))/Math.log(2);
	
		return 1.0-(getSimilarity(fp));
	}
	

	@Override
	public int compareTo(finger_print arg0) {
		if(sim_closest_pivot == -1 || arg0.sim_closest_pivot==-1){ 
			System.err.println("SImilarity was not set");
			System.exit(0);
		}
		
		if(this.sim_closest_pivot < arg0.sim_closest_pivot){
			return -1;
		}
		else if(this.sim_closest_pivot == arg0.sim_closest_pivot)
			return  0;
		else return 1;
	}

	public void set_sim_pivot(ArrayList<node> lst){		
		sim_closest_pivot=-1;
		for(node fp: lst){
			double sim= getSimilarity(fp.getPivot());
			if(sim_closest_pivot == -1 || sim_closest_pivot < sim){
				sim_closest_pivot=sim;
				closest_pivot=fp;
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSim_closest_pivot() {
		return sim_closest_pivot;
	}

	public void setSim_closest_pivot(double sim_closest_pivot) {
		this.sim_closest_pivot = sim_closest_pivot;
	}

	public node getClosest_pivot() {
		return closest_pivot;
	}

	public void setClosest_pivot(node closest_pivot) {
		this.closest_pivot = closest_pivot;
	}

	public HashMap<Integer, Integer> getFeature_map() {
		return feature_map;
	}

	public void setFeature_map(HashMap<Integer, Integer> feature_map) {
		this.feature_map = feature_map;
	}
		
}
