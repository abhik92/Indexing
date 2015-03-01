package invert_index;

import java.util.Comparator;


	public class MyComparator implements Comparator<Integer> {
	    @Override
	    public int compare(Integer i1, Integer i2) {
	        return finger_print.f_points.get(i2.intValue()).compareTo(finger_print.f_points.get(i1.intValue()));
	    }
	}
