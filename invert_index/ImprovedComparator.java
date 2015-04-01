package invert_index;

import java.util.Comparator;


	public class ImprovedComparator implements Comparator<Integer> {
	    @Override
	    public int compare(Integer i1, Integer i2) {
	    	if(!finger_print.f_points.containsKey(i2)){
	    		return 1;	    		
	    	}
	    	
	    	else if(!finger_print.f_points.containsKey(i1)) {
	    		return -1;
	    	}
	    	
	    	Integer a=finger_print.f_points.get(i2)*finger_print.mn_ft_allpts_infeat.get(i2);
	    	Integer b=finger_print.f_points.get(i1)*finger_print.mn_ft_allpts_infeat.get(i1);
	    	
	    	if(a>b){
	    		return 1;
	    	}
	    	else if(a<b){
	    		return -1;
	    	}
	    	else{
	    		return 0;
	    	}
	    	
	    	
//	    	return ((finger_print.f_points.get(i2.intValue()))).compareTo(finger_print.f_points.get(i1.intValue()));
	    
	    
	    
	    }
	}
