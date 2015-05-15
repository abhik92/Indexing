package SOA_aung;

import java.util.ArrayList;
import java.util.HashMap;


public class finger_print implements Comparable<finger_print>{

	int id;
	HashMap<Integer, Double> feature_map; // for each fingerprint
	ArrayList<Double> sum_mag;
	double magnitude;	
	// how many fingerprints containsw this feature
	static HashMap<Integer, Integer> features= new HashMap<Integer, Integer>(); //common across all
	static ArrayList<features> lst_fe = new ArrayList<features>();
	static int count=0;
		
	
	public finger_print(int cnt) {
		// TODO Auto-generated constructor stub
		id=cnt;
		feature_map=new HashMap<Integer, Double>();
		sum_mag=new ArrayList<Double>();
		magnitude=0;
	}
	
	//add feature values
	public void add_feature(int feature_id, double feature_value){
			feature_map.put(feature_id, feature_value);
	}
		
	//add all features in string format
	public void add_all_features(String[] features_string){
		for(int i=0;i<features_string.length;i++){
			String[] feature_parsed= features_string[i].replace(":", " ").trim().split("\\s+");
			int f_id= Integer.parseInt(feature_parsed[0]);
			double f_val= Double.parseDouble(feature_parsed[1]);
				if(!features.containsKey(f_id)){
					features.put(f_id,count);
					count++;
					features f= new features(f_id);
					f.count=1;
					f.mag_count=f_val;
					lst_fe.add(f);
				}
				else{
					features f=lst_fe.get(features.get(f_id));
					f.count++;
					f.mag_count+=f_val;					
				}
			magnitude+=f_val;
			feature_map.put(f_id, f_val);
		}
	}
		
	@Override
	public int compareTo(finger_print o) {
		// TODO Auto-generated method stub
		
		if(this.magnitude > o.magnitude)
			return 1;
		else if(this.magnitude < o.magnitude)
			return -1;
		return 0;
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

	public double getSimilarity(finger_print fp){
		return this.getTanimotoSimilarity(fp);		
	}
}
