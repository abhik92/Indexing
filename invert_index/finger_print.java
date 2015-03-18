package invert_index;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

public class finger_print{

	/***************************/
	
	/***********STATIC VARIABLES ***************.
	
	/***/
	// Invert index contains a hashmap for every feature value as key
	// The key of the second hashmap is a point id and value is the feature vlaue for the point
	public static HashMap<Integer, HashMap<Integer, Double>> invert_index = new HashMap<Integer,HashMap<Integer,Double>>();
	
	// how many point sin the database contain the feature
	public static HashMap<Integer,Integer> f_points=new HashMap<Integer,Integer>();
	
	//public static ArrayList<Integer> f_sort = new ArrayList<Integer>();
	
	// A comparator to sort the features of query point
	public static Comparator<Integer> comp = new MyComparator();
	
	// The minimum number of features contained by the points for a particular feature 
	public static HashMap<Integer,Integer> mn_ft_allpts_infeat= new HashMap<Integer,Integer>();
	
	
	/**non binary*/
	
	// Minimum non zero value taken by any point in the feature
	public static HashMap<Integer,Double> min_ft_val= new HashMap<Integer,Double>();
	
	// Maximum non zero value taken by any point in the feature
	public static HashMap<Integer,Double> max_ft_val= new HashMap<Integer,Double>();
	
	// Minimum sum of feature values among all points for a particular feature
	public static HashMap<Integer,Double> min_sum_ft_val= new HashMap<Integer,Double>();	
	
	// index step
	public static boolean index=true;
	public static boolean non_bin=true;
		
	
	/***************************/
	
	/*****************ClASS VARIABLES************/
	
	public double feat_sum=0;						// sum of feature values
	public int id;									// finger print id
	//public double sim_closest_pivot;				// similarity to closest pivot (not required)
 	public HashMap<Integer, Double> feature_map;	// Feature values map
	public ArrayList<Integer> feat_present;			// List of features present
	public int feature_size;						// No of features
	public String line;								// Feature string	 
	//public int comparisons;						// No of comparisons for query
	
	//initialize the data item
	public finger_print(int id_num) {
		id=id_num;
		feature_map= new HashMap<Integer, Double>();
		//sim_closest_pivot=-1;
		//comparisons=0;
		feature_size=0;
		if(!index){
			feat_present=new ArrayList<Integer>();
		}
		feat_sum=0;
	}

	//add feature values
	public void add_feature(int feature_id, double feature_value){
		feature_map.put(feature_id, feature_value);
		feature_size++;
	}
	
	
	//During index step
	//add all features in string format
	public void add_all_features(String[] features_string){
		for(int i=0;i<features_string.length;i++){
			String[] feature_parsed= features_string[i].replace(":", " ").trim().split("\\s+");
			int f_id= Integer.parseInt(feature_parsed[0]);			
			if(!index){
				feat_present.add(f_id); // uncomment if not required
			}
			double f_val= Double.parseDouble(feature_parsed[1]);
			feat_sum+=f_val;
			if(index && !f_points.containsKey(f_id)){
				f_points.put(f_id,1);  //Check this
				
				//f_sort.add(f_id);  // uncomment fsort
				
				/**/
				if(index){
					invert_index.put(f_id, new HashMap<Integer,Double>());
				/**/
					
				}
					
			}
			else if(index){
			
				f_points.put(f_id,f_points.get(f_id)+1);
				
			}
			
			
			if(index){
				if(!mn_ft_allpts_infeat.containsKey(f_id)){
					mn_ft_allpts_infeat.put(f_id, features_string.length);
					
					
					/***non-binary***/
					
					if(non_bin){
						min_ft_val.put(f_id, f_val);
						max_ft_val.put(f_id, f_val);
					}
					/***/
				}
				else{
					mn_ft_allpts_infeat.put(f_id, Math.min(features_string.length, mn_ft_allpts_infeat.get(f_id)));							
				
					/***non-binary***/
					if(non_bin){
						min_ft_val.put(f_id, Math.min(f_val, min_ft_val.get(f_id)));
						max_ft_val.put(f_id, Math.max(f_val, max_ft_val.get(f_id)));		
					}
					/***/
				
				
				}
			
				invert_index.get(f_id).put(this.id, f_val);
				
			}
			
			/**/
			feature_map.put(f_id, f_val);			
			/**/
			
			
		
		}
		this.feature_size=features_string.length;
		//System.out.println(this.feature_size);
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
	



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String toString() {
        return (""+this.id);
   }

	
	
	public static HashMap<Integer, HashMap<Integer, Double>> getInvert_index() {
		return invert_index;
	}

	public static void setInvert_index(
			HashMap<Integer, HashMap<Integer, Double>> invert_index) {
		finger_print.invert_index = invert_index;
	}

	public static HashMap<Integer, Integer> getF_points() {
		return f_points;
	}

	public static void setF_points(HashMap<Integer, Integer> f_points) {
		finger_print.f_points = f_points;
	}



	public static Comparator<Integer> getComp() {
		return comp;
	}

	public static void setComp(Comparator<Integer> comp) {
		finger_print.comp = comp;
	}

	public static HashMap<Integer, Integer> getMn_ft_allpts_infeat() {
		return mn_ft_allpts_infeat;
	}

	public static void setMn_ft_allpts_infeat(
			HashMap<Integer, Integer> mn_ft_allpts_infeat) {
		finger_print.mn_ft_allpts_infeat = mn_ft_allpts_infeat;
	}

	public static HashMap<Integer, Double> getMin_ft_val() {
		return min_ft_val;
	}

	public static void setMin_ft_val(HashMap<Integer, Double> min_ft_val) {
		finger_print.min_ft_val = min_ft_val;
	}

	public static HashMap<Integer, Double> getMax_ft_val() {
		return max_ft_val;
	}

	public static void setMax_ft_val(HashMap<Integer, Double> max_ft_val) {
		finger_print.max_ft_val = max_ft_val;
	}

	public static HashMap<Integer, Double> getMin_sum_ft_val() {
		return min_sum_ft_val;
	}

	public static void setMin_sum_ft_val(HashMap<Integer, Double> min_sum_ft_val) {
		finger_print.min_sum_ft_val = min_sum_ft_val;
	}

	public double getFeat_sum() {
		return feat_sum;
	}

	public void setFeat_sum(double feat_sum) {
		this.feat_sum = feat_sum;
	}

	public static boolean isIndex() {
		return index;
	}

	public static void setIndex(boolean index) {
		finger_print.index = index;
	}

	public static boolean isNon_bin() {
		return non_bin;
	}

	public static void setNon_bin(boolean non_bin) {
		finger_print.non_bin = non_bin;
	}

	public HashMap<Integer, Double> getFeature_map() {
		return feature_map;
	}

	public void setFeature_map(HashMap<Integer, Double> feature_map) {
		this.feature_map = feature_map;
	}

	public int getFeature_size() {
		return feature_size;
	}

	public void setFeature_size(int feature_size) {
		this.feature_size = feature_size;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

		
}


