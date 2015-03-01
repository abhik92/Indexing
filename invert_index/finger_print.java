package invert_index;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

public class finger_print implements Comparable<finger_print>{

	
	/***/
	public static HashMap<Integer, HashMap<Integer, Double>> invert_index = new HashMap<Integer,HashMap<Integer,Double>>();
	
	/***/
	public int id;
	public double sim_closest_pivot;
	public HashMap<Integer, Double> feature_map;
	public int feature_size;
	public String line;
	public int comparisons;
	public static boolean index=true;
	
	public static HashMap<Integer,Integer> f_points=new HashMap<Integer,Integer>();
	public static ArrayList<Integer> f_sort = new ArrayList<Integer>();
	//public static ArrayList<Integer> f_sort_values = new ArrayList<Integer>();
	public static Comparator<Integer> comp = new MyComparator();
	
	public static HashMap<Integer,Integer> mn_ft_allpts_infeat= new HashMap<Integer,Integer>();
	//initialize the data item
	public finger_print(int id_num) {
		id=id_num;
		feature_map= new HashMap<Integer, Double>();
		sim_closest_pivot=-1;
		comparisons=0;
		feature_size=0;
		
	}

	//add feature values
	public void add_feature(int feature_id, double feature_value){
		feature_map.put(feature_id, feature_value);

		feature_size++;
	}
	
	//add all features in string format
	public void add_all_features(String[] features_string){
		for(int i=0;i<features_string.length;i++){
			String[] feature_parsed= features_string[i].replace(":", " ").trim().split("\\s+");
			int f_id= Integer.parseInt(feature_parsed[0]);			
			double f_val= Double.parseDouble(feature_parsed[1]);
			if(!f_points.containsKey(f_id)){
				f_points.put(f_id,1);
				f_sort.add(f_id);
				
				/**/
				if(index){
					invert_index.put(f_id, new HashMap<Integer,Double>());
				/**/
					
				}
					
			}
			else{
			
				f_points.put(f_id,f_points.get(f_id)+1);
				
			}
			if(index){
				if(!mn_ft_allpts_infeat.containsKey(f_id)){
					mn_ft_allpts_infeat.put(f_id, features_string.length);
				}
				else{
					mn_ft_allpts_infeat.put(f_id, Math.min(features_string.length, mn_ft_allpts_infeat.get(f_id)));							
				}
			
				invert_index.get(f_id).put(this.id, f_val);
				
			}
			
			/**/
			feature_map.put(f_id, f_val);			
			/**/
			
			
		
		}
		feature_size=features_string.length;
	}
	

	
	/* Tanimoto 
	 *
	 *
	 */
	public double getSimilarity(finger_print fp){
		return this.getTanimotoSimilarity(fp);		
		
		
		//return 1.0- getL2Distance(fp);
		
		//return 1 -  (1.0*getL1Distance(fp) / 514);
				
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
	public double getL1Distance(finger_print fp){
		double score=0;		
		for(Integer key : this.feature_map.keySet()){
			if(fp.feature_map.containsKey(key)){
				score+=Math.pow(this.feature_map.get(key)-fp.feature_map.get(key), 1);
			}
			else 
				score+=Math.pow(this.feature_map.get(key), 1);			
		}
		for(Integer key : fp.feature_map.keySet()){
			if(!this.feature_map.containsKey(key)){
				score+=Math.pow(fp.feature_map.get(key), 1);
			}
		}
		return score;		
	}
	public double getLinfDistance(finger_print fp){
		double score=0;		
		double max=0;
		for(Integer key : this.feature_map.keySet()){
			if(fp.feature_map.containsKey(key)){
				score=Math.pow(this.feature_map.get(key)-fp.feature_map.get(key), 1);
				if(score >max) max=score;
			}
			else {
				score=Math.pow(this.feature_map.get(key), 1);			
				if(score >max) max=score;
			}
		}
		for(Integer key : fp.feature_map.keySet()){
			if(!this.feature_map.containsKey(key)){
				score=Math.pow(fp.feature_map.get(key), 1);
				if(score >max) max=score;	
			}
		}
		return max;		
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
		
}


