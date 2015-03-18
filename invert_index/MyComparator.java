package invert_index;

import java.util.Comparator;


	public class MyComparator implements Comparator<Integer> {
	    @Override
	    public int compare(Integer i1, Integer i2) {
	    	if(!finger_print.f_points.containsKey(i2.intValue())){
	    		return 1;	    		
	    	}
	    	
	    	else if(!finger_print.f_points.containsKey(i1.intValue())) {
	    		return -1;
	    	}
//	    	
//	    	double a=finger_print.f_points.get(i2.intValue())/finger_print.mn_ft_allpts_infeat.get(i2).intValue();
//	    	double b=finger_print.f_points.get(i1.intValue())/finger_print.mn_ft_allpts_infeat.get(i1).intValue();
//	    	
//	    	if(a>b){
//	    		return -1;
//	    	}
//	    	else return 1;
//	    	
	    	return (finger_print.f_points.get(i2.intValue())).compareTo(finger_print.f_points.get(i1.intValue()));
	    
	    
	    
	    }
	}
