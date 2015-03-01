package invert_index;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;


public class invert_index_main {

	/**
	 * @param args
	 */

	public static void main(String[] args) throws IOException {

		Data data = new Data();
		int size;
		Scanner in = new Scanner(System.in);

		BufferedReader br = new BufferedReader(new FileReader(new File("files/"+args[0])));
		String line;
		int count = 0;
		int inn=0;
		while ((line = br.readLine()) != null) {
			count++;
			String[] features_list = line.replace("{", "").replace("}", "")
					.split(",");
			finger_print fp = new finger_print(count);
			fp.line = line;
			fp.add_all_features(features_list);
			data.data_set.put(count, fp);
			//System.out.println(inn++);
		}

		double dis = Double.parseDouble(args[1]);
		
		finger_print.index = false;

		
		
		
		/****improved*/
		
		/*for(int i=0;i<finger_print.f_sort.size();i++){
			finger_print.f_sort_values.add(finger_print.f_points.get(finger_print.f_sort.get(i)));
		}*/
		//System.out.println(finger_print.f_sort.size());
		//System.out.println(finger_print.f_sort_values.size());
		
		
		
		
		Collections.sort(finger_print.f_sort,finger_print.comp);
	
		
		//for(int i=0;i<finger_print.f_sort.size();i++){
		//	System.out.println(finger_print.f_points.get(finger_print.f_sort.get(i)));
		//}
		
		/***/
		
		
		
		Data testdata = new Data();
		br = new BufferedReader(new FileReader(new File("files/testdatabin")));
		count = 0;
		while ((line = br.readLine()) != null) {
			count++;
			String[] features_list = line.replace("{", "").replace("}", "")
					.split(",");
			finger_print fp = new finger_print(count);
			fp.line = line;
			fp.add_all_features(features_list);
			testdata.data_set.put(count, fp);
		}

		// indx.=data;
		double startTime = System.nanoTime();
		for (int i = 1; i <= testdata.data_set.size(); i++) {

			improved_range_search(testdata.data_set.get(i),dis,data);
		}

		double endTime = System.nanoTime();
		System.out.println(Math.pow(10, -6) * 1.0 * (endTime - startTime)
				/ testdata.data_set.size());
		

		return;
	}

	private static void point_search(finger_print fp) {
		// TODO Auto-generated method stub

		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (Integer fid : fp.feature_map.keySet()) {
			if (finger_print.invert_index.containsKey(fid)) {
				for (Integer potent : finger_print.invert_index.get(fid)
						.keySet()) {
					lst.add(potent);
				}
			}
			break;
		}
		for (Integer fid : fp.feature_map.keySet()) {
			double fval = fp.feature_map.get(fid);
			for (int i = 0; i < lst.size(); i++) {
				if (!finger_print.invert_index.containsKey(fid)) {
					lst.clear();
					break;
				}

				else if (!finger_print.invert_index.get(fid).containsKey(
						lst.get(i))) {
					lst.remove(i);
					i--;
				} else if (finger_print.invert_index.get(fid).get(lst.get(i)) != fval) {
					lst.remove(i);
					i--;
				}
			}
		}

		System.out.println(lst.size());

	}

	private static void direct_range_search(finger_print fp, double dis,Data data) {
		ArrayList<Integer> lst1 = new ArrayList<Integer>();
		HashMap<Integer,Integer> potent = new HashMap<Integer, Integer>(); 
		ArrayList<Integer> lst2 = new ArrayList<Integer>();
		
		for (Integer fid : fp.feature_map.keySet()) {
			if (finger_print.invert_index.containsKey(fid)) {
				for(Integer id: finger_print.invert_index.get(fid).keySet()){
					if(!potent.containsKey(id)){
						potent.put(id,1);
						lst1.add(id);
					}
				}
			}
		}
		
		for(int i=0;i<lst1.size();i++){
			if(fp.getDistance(data.data_set.get(lst1.get(i))) < dis){
				lst2.add(lst1.get(i));
			}
		}
		
		
		System.out.println(lst2.size());
	}
	private static void improved_range_search(finger_print fp, double dis,Data data) {
		ArrayList<Integer> lst1 = new ArrayList<Integer>();
		HashMap<Integer,Integer> potent = new HashMap<Integer, Integer>(); 
		ArrayList<Integer> lst2 = new ArrayList<Integer>();
		
		int q_feat=fp.feature_size;
		ArrayList<Integer> feat_neglect= new ArrayList<Integer>();
		int max=0;
		boolean add=true;
		for (Integer fid : fp.feature_map.keySet()) {
			add=true;
			if(finger_print.mn_ft_allpts_infeat.containsKey(fid)){
				if(  (feat_neglect.size()+1.0) / (q_feat -1 - feat_neglect.size() + Math.max(max, finger_print.mn_ft_allpts_infeat.get(fid))) < 1-dis ){
					max= Math.max(max,finger_print.mn_ft_allpts_infeat.get(fid) );
					//System.out.println((  feat_neglect.size()+1.0) / (q_feat -1 - feat_neglect.size() + Math.max(max, finger_print.mn_ft_allpts_infeat.get(fid))));
					add=false;
					feat_neglect.add(fid);
				}
			
				if(add){
			
					if (finger_print.invert_index.containsKey(fid)) {
						for(Integer id: finger_print.invert_index.get(fid).keySet()){
							if(!potent.containsKey(id)){
								potent.put(id,1);
								lst1.add(id);
							}
						}
					}
				}
			}
		}
		for(int i=0;i<lst1.size();i++){
			if(fp.getDistance(data.data_set.get(lst1.get(i))) < dis){
				lst2.add(lst1.get(i));
			}
		}
		
		System.out.println(lst2.size());
	}

}
