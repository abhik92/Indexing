package main;


import java.util.ArrayList;
import java.util.HashMap;



/*
 * Just contains the data set
 * Hash Map with keys as integers starting from 1 
 * 
 * 
 */

public class Data {
	public HashMap<Integer, finger_print> data_set;
	
	
	// initialize the hash set
	public Data() {
		data_set= new HashMap<Integer, finger_print>();
	}

	
	/*
	 *  find the brute force method of finding closest finger print
	 *	the distance metric is defined by getDistance()
	 */
	
	public finger_print closest_finger_brute(finger_print fp){
		double min_distance=Double.MAX_VALUE;
		finger_print fret=null;				
		for(int i=0;i<data_set.size();i++){
			if(fp.getDistance(data_set.get(i+1)) < min_distance){
				min_distance=fp.getDistance(data_set.get(i+1));
				fret=data_set.get(i+1);
			}
		}
		return fret;		
	}

	
	/*
	 * find all finger prints within a range of so much distance
	 * returns an ArrayList containing all finger prints satisfying the above condition
	 */
	public ArrayList<finger_print> range_finger_brute(finger_print fp,double dis){
		ArrayList<finger_print> lst=new ArrayList<finger_print>();		
		for(int i=0;i<data_set.size();i++){
			if(fp.getDistance(data_set.get(i+1)) < dis){
				lst.add(data_set.get(i+1));
			}
		}
		return lst;		
	}
}
