package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class index {
	
	//Number of pivots at each step of algo1
	public int PIVOT_SIZE_TOTAL = 10;
	
	// Maximum number of finger prints allowed at the step 
	public int OUTLIERS_SIZE = 50;
	
	
	// set as data.data_set
	HashMap<Integer, finger_print> map;
	
	//size of the map
	int size;
	
	// required in algo2
	node head;
	
	//in algo1 it is the final set of nodes
	ArrayList<node> final_pivot_list;
	
	
	// set map, size and initialize pivot list
	public index(HashMap<Integer,finger_print> fp){
		map=fp;
		size=map.size();
		final_pivot_list=new ArrayList<node>();
	}
		
	/*
	 * Algo 1 calls make_pivot
	 * Algo2 calls make_pivot_hier and use head
	 */
	public void run(){				
		ArrayList<finger_print> outliers= new ArrayList<finger_print>();
		for(int i:map.keySet()){
			outliers.add(map.get(i));
		}			
		
		
		/*uncomment
		// calls make_pivot with outliers (oultiers are basically what is left at each stage)
		make_pivot(outliers);
		int i;
		for(i=0;i<final_pivot_list.size()-1;i++){
			final_pivot_list.get(i).next=final_pivot_list.get(i+1);			
		}
			final_pivot_list.get(i).next=null;
		
		*/
		
		
		/*new*/	
		
		head=new node(outliers);
		make_pivot_hier(head);
		
		
		/**/
			
			
	}
	
	
	public void make_pivot_hier(node head){
		
		// stop if so less than OUTLIERS_SIZE objects present
		if(head.list_objects.size() < PIVOT_SIZE_TOTAL){
			//add all children to the children and return
			for(finger_print f: head.list_objects){
				head.children.add(new node(f));
			}
			//System.out.println("XX "+head.list_objects.size());
			return;
		}
		
		ArrayList<node> pivot_lst= new ArrayList<node>();
		
		Random r= new Random();
		
		// choose pivots
		// has to be done better
		for(int i=0;i<PIVOT_SIZE_TOTAL;i++){			
			//choose random
			//int j=r.nextInt(head.list_objects.size());
			
			//choose far away points
			int j=get_farthest(head.list_objects,pivot_lst);
			
			pivot_lst.add(new node(head.list_objects.get(j)));
			head.list_objects.remove(j);	
		}
	
		// set closest pivot
		for(finger_print p : head.list_objects){
			p.set_sim_pivot(pivot_lst);
			p.closest_pivot.set_object(p);
		}

		// set the children to pivot_lst
		head.children=pivot_lst;	

		//recursive call
		for(node pivot : pivot_lst){
			make_pivot_hier(pivot);
		}
			
		return;
	}	
	
	
	
	private int get_farthest(ArrayList<finger_print> list_objects,
			ArrayList<node> pivot_lst) {
		
		Random r= new Random();
		if(pivot_lst.size()==0){
				// can we do something here.
				return r.nextInt(list_objects.size());
		}		
		
		int indx=0;
		double max_dist= Double.MIN_VALUE;
		for(int i=0;i<list_objects.size();i++){
				double lst_min=Double.MAX_VALUE;
				for(int j=0;j<pivot_lst.size();j++){
					if(list_objects.get(i).getDistance(pivot_lst.get(j).pivot) < lst_min){
						lst_min=list_objects.get(i).getDistance(pivot_lst.get(j).pivot);
					}
				}
				if(lst_min >max_dist){
					max_dist=lst_min;
					indx=i;
				}
		}
		
		return indx;
	}

	public void make_pivot(ArrayList<finger_print> outliers){
		
		
		////System.out.println("Total outliers :" +outliers.size());
		if(outliers.size()< OUTLIERS_SIZE  || outliers.size() < PIVOT_SIZE_TOTAL){
			for(int i=0;i<outliers.size();i++){
				final_pivot_list.add(new node(outliers.get(i)));
				
			}
			return;
		}
		
		ArrayList<node> pivot_lst= new ArrayList<node>();
						
		Random r= new Random();
		
		for(int i=0;i<PIVOT_SIZE_TOTAL;i++){			
			int j=r.nextInt(outliers.size());
			pivot_lst.add(new node(outliers.get(j)));
			outliers.remove(j);	
		}

		if(outliers.size()==0) {
			final_pivot_list.addAll(pivot_lst);
			return;
		}
		
		for(finger_print p : outliers){
			p.set_sim_pivot(pivot_lst);
	
		}
		
		
		Collections.sort(outliers);

		double median_similarity = getMedian_sim(outliers);
		
		
		for(int i=outliers.size()/2 -1;i<outliers.size();i++){
			if(outliers.get(i).getSim_closest_pivot() > median_similarity){
				for(int j=outliers.size()-1;j>=i;j--){
					outliers.get(j).getClosest_pivot().set_object(outliers.get(j));
					outliers.remove(j);
				}
				break;
			}
		}
		

		
		
		final_pivot_list.addAll(pivot_lst);
		node outlier_pvt=new node(outliers);
		final_pivot_list.add(outlier_pvt);
		outlier_pvt.setOutlier_pivot(true);
		

		make_pivot(outliers);
		
		return;
	}

	private double getMedian_sim(ArrayList<finger_print> outliers) {
		
		if(outliers.size()%2 !=0)
			return outliers.get((outliers.size()+1)/2).getSim_closest_pivot();
		else return (outliers.get(outliers.size()/2).getSim_closest_pivot()+ outliers.get(outliers.size()/2 +1).getSim_closest_pivot())/2 ;
		
	}
	
	// get closest compound
    // get closest compound
    public finger_print closest_finger(finger_print fp){
            double min_distance=Double.MAX_VALUE;
            finger_print fret=null;
            fp.comparisons=0;
            for(int i=0;i<final_pivot_list.size();i++){
                    fp.comparisons++;
                    node n=final_pivot_list.get(i);
                    double d=fp.getDistance(n.pivot);
                    if(n.isOutlier_pivot()){
                            if(d - n.farthest_child > min_distance){
                                    break;
                            }
                    }
                    else if(Math.abs(d - (n.farthest_child)) <= min_distance){
                            for(int j=0;j<n.list_objects.size();j++){
                                    fp.comparisons++;
                                    if(fp.getDistance(n.list_objects.get(j)) < min_distance){
                                            min_distance =fp.getDistance(n.list_objects.get(j));
                                            fret=n.list_objects.get(j);
                                    }
                            }
                            if(d < min_distance){
                                    min_distance =d;
                                    fret=n.pivot;
                            }
                    }
            }
            return fret;
    }
    
	public ArrayList<finger_print> range_finger(finger_print fp,double dis){
        ArrayList<finger_print> lst= new ArrayList<finger_print>();
        fp.comparisons=0;
        node n=final_pivot_list.get(0);        
        while(n!=null){
        	fp.comparisons++;
        	double d=fp.getDistance(n.pivot);
        	//System.out.println("pivot  "+n.pivot.id+" dist"+d);
        	if(n.isOutlier_pivot()){
        		if(d<dis){
        			lst.add(n.pivot);
        		}
        		if(d + n.farthest_child < dis){
        			node m=n.next;
        			while(m!=null){
        				//System.out.println("outlier next +"+fp.getDistance(m.pivot));
        				lst.add(m.pivot);
        				if(!m.isOutlier_pivot()){        					
        					lst.addAll(m.list_objects);
        					for(int k=0;k<m.list_objects.size();k++){
        						//System.out.println("Size "+fp.getDistance(m.list_objects.get(k)));
        					}
        				}
        				m=m.next;
        			}	
        			//System.out.println("Not chosen anything after this 1");
        			break;
        		}
        		if(d - n.farthest_child > dis){               		
        			//System.out.println("Not chosen anything after this 2 ");
            		break;
        			
        		}
        	}
        	else if((d + n.farthest_child < dis)){
        		lst.add(n.pivot);
        		lst.addAll(n.list_objects);
        		for(int k=0;k<n.list_objects.size();k++){
        			//System.out.println("chosen "+fp.getDistance(n.list_objects.get(k)));
        		}
        	}
        	else if(d - n.farthest_child > dis){               		
        		if(d<dis){
        			lst.add(n.pivot);
        		}
        		for(int k=0;k<n.list_objects.size();k++){
        			//System.out.println("Not chosen "+fp.getDistance(n.list_objects.get(k)));
        		}
        	}
        	else{
        		if(d<dis){
        			lst.add(n.pivot);
        		}
        		for(int j=0;j<n.list_objects.size();j++){
        			fp.comparisons++;
        			if(fp.getDistance(n.list_objects.get(j))<dis){
        				lst.add(n.list_objects.get(j));
        			}
        		}
        	}
        	n=n.next;
        }
        for(int j=0;j<lst.size();j++){
			//System.out.println("new "+lst.get(j).id);
		}
        	
        	
        	
        	
        
        return lst;
	}


	public void range_finger_algo2(finger_print fp,double dis,node headnode, ArrayList<finger_print> lst){
		double d=fp.getDistance(headnode.pivot);			
		fp.comparisons++;
		if(d + headnode.farthest_child < dis){
			add_child(headnode, lst);
			return;
		}
        if(d<dis){
			lst.add(headnode.pivot);
		}
		if(d - headnode.farthest_child > dis){               		
			return;
			
		}				
		for(node child: headnode.children){
			range_finger_algo2(fp, dis, child, lst);
		}       
        return;
	}
	
	public void add_child(node headnode, ArrayList<finger_print> lst ){
		lst.add(headnode.pivot);
		if(headnode.children.size()==0){
			return;
		}
		else{			
			for(node child: headnode.children){
				add_child(child, lst);
			}
		}
		return;
	}


}
