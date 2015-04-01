package invert_index;

import java.util.Comparator;


	public class MyComparator implements Comparator<Integer> {
	    @Override
	    public int compare(Integer i1, Integer i2) {
	    	if(!finger_print.f_points.containsKey(i2)){
	    		return 1;	    		
	    	}
	    	
	    	else if(!finger_print.f_points.containsKey(i1)) {
	    		return -1;
	    	}
	    	
	    	
    	return (finger_print.f_points.get(i2)).compareTo(finger_print.f_points.get(i1));
	    
	    
	    
	    }
	}
