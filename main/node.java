package main;

import java.util.ArrayList;


public class node {
	
	
	finger_print pivot; // main finger print pivot
	ArrayList<finger_print> list_objects; // list of objects in the pivot
	node parent;	// parent node
	double farthest_child; // stores the max distance of its list of objects
	boolean outlier_pivot; // signals if this is an outlier pivot
	node next;     	// just a pointer to the next node

	//new
	ArrayList<node> children; // for the second method where we call the hierarchy system
	
	
	// 
	public node(finger_print fp){
		list_objects= new ArrayList<finger_print>(); // initialize list_objects
		pivot=fp;	// set pivot
		farthest_child=0.0;  // no child , hence farthest child set to 0
		outlier_pivot=false;	// it is not a outlier pivot
		next=null;  // it is set only at the end of simulation in algo1
		
		
		//new thing required for algo 2
		children= new ArrayList<node>();
	}
	
	
	public node(ArrayList<finger_print> outliers) {
		pivot=outliers.get(0); // choose first object as pivot
		outliers.remove(0);	 // remove the pivot
		list_objects= outliers;		// set remaining children as
		farthest_child=0.0;		// no children yet, hence 0
		setFarthest_child();	// this sets farthest child
		outlier_pivot=true;		// this is an outlier pivot
		next=null;			// again, this is set only at end of simulation
				
		

		//new thing required for algo 2
		children= new ArrayList<node>();
	}

	
	
	
	
	
		
	
	// adds object to the list of objects and checks if update required to farthest child
	public void set_object(finger_print object) {
		this.list_objects.add(object);
		if(this.pivot.getDistance(object) > farthest_child ){
			farthest_child=this.pivot.getDistance(object);
		}
		
	}

	// updates farthest child which is used for triangle inequality later
	public void setFarthest_child() {
		for(int i=0;i<list_objects.size();i++){
			if(this.pivot.getDistance(list_objects.get(i)) > farthest_child ){
				farthest_child=this.pivot.getDistance(list_objects.get(i));
			}
		}
	}
	
	
	/*
	 * Getters and Setters
	 * 
	 */
	public boolean isOutlier_pivot() {
		return outlier_pivot;
	}

	public void setOutlier_pivot(boolean outlier_pivot1) {
		this.outlier_pivot = outlier_pivot1;
	}

	public void setFarthest_child(double farthest_child) {
		this.farthest_child = farthest_child;
	}

	public void setPivot(finger_print pivot) {
		this.pivot = pivot;
	}

	public void setList_objects(ArrayList<finger_print> list_objects) {
		this.list_objects = list_objects;
		setFarthest_child();
	}

	public finger_print getPivot() {
		return pivot;
	}

	public ArrayList<finger_print> getList_objects() {
		return list_objects;
	}

	public node getParent() {
		return parent;
	}

	public void setParent(node parent) {
		this.parent = parent;
	}

	public double getFarthest_child() {
		return farthest_child;
	}

	
	
	
	
}
