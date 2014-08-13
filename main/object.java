package main;



// not required as of now
//Not used yet
public class object {
	finger_print obj;
	double cov_rad;
	double sim_closest_pivot;
	
	public void set_closest_pivot(finger_print fp){
		this.sim_closest_pivot=obj.getSimilarity(fp);
	}
	
}
