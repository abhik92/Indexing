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

		BufferedReader br = new BufferedReader(new FileReader(new File("files/"
				+ args[0])));
		String line;
		int count = 0;
		int inn = 0;
		
		
		double startTime = System.nanoTime();
		
		while ((line = br.readLine()) != null) {
			count++;
			String[] features_list = line.replace("{", "").replace("}", "")
					.split(",");
			finger_print fp = new finger_print(count);
			fp.line = line;
			fp.add_all_features(features_list);
			data.data_set.put(count, fp);
			// System.out.println(inn++);
		}

		double dis;
		//double dis= Double.parseDouble(args[1]);

		finger_print.index = false;

		/**** improved */

		 /*for(int i: finger_print.invert_index.keySet()){
			 finger_print.f_sort.add(i);
		 }
		 Collections.sort(finger_print.f_sort, finger_print.comp); // uncomment fsort

		 
		 
		 int p=finger_print.invert_index.get(finger_print.f_sort.get(0)).keySet().size();
		 for(int i=0;i<10;i++){
			 //System.out.println(finger_print.invert_index.get(finger_print.f_sort.get(i)).keySet().size());				
			 ArrayList<Integer> s= new ArrayList<Integer>();
			 for(int j: finger_print.invert_index.get(finger_print.f_sort.get(i)).keySet()){
				 s.add(data.data_set.get(j).feature_size);
			 }
			 Collections.sort(s);
			 for(int k: s)
				 System.out.print(k +" ");
			 
			 for(int q=finger_print.invert_index.get(finger_print.f_sort.get(i)).keySet().size();q<p;q++){
				 System.out.print("0 ");
			 }
			 System.out.println();
		 }
		 */
		 
		/**** non bin ****/

		if (finger_print.non_bin) {
			//for (Integer fid : finger_print.f_sort) {    // uncomment fsort
			for (Integer fid : finger_print.f_points.keySet()) {    
				double min_sum = Double.MAX_VALUE;
				for (Integer pts : finger_print.invert_index.get(fid).keySet()) {
					min_sum = Math
							.min(min_sum, data.data_set.get(pts).feat_sum);
				}
				finger_print.min_sum_ft_val.put(fid, min_sum);
			}
		}
		double endTime = System.nanoTime();
		System.out.println("Indexing time " + Math.pow(10, -6)	* 1.0 * (endTime - startTime) / data.data_set.size());
		
		// for(int i=0;i<finger_print.f_sort.size();i++){
		// System.out.println(finger_print.f_points.get(finger_print.f_sort.get(i)));
		// }

		/***/

		Data testdata = new Data();
		br = new BufferedReader(new FileReader(new File("files/"+args[1])));
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
		
		/*for(Integer fid: finger_print.f_points.keySet()){
			System.out.println("Feature id: "+fid);
			ArrayList<Integer> print_ft_val= new ArrayList<Integer>();
			
			if(finger_print.invert_index.containsKey(fid)){
			
				for(Integer  i:finger_print.invert_index.get(fid).keySet()){
					print_ft_val.add(data.data_set.get(i).feature_size);
				}
				Collections.sort(print_ft_val);
				System.out.println(print_ft_val);
				System.out.println();
		
			}
		}*/
		for (int j = 1; j <= 9; j++) {
			dis = 0.1 * j;
			startTime = System.nanoTime();
			//for (int i = 1; i <= testdata.data_set.size(); i++) {
			for (int i = 1; i <= testdata.data_set.size(); i++) {
			
				// System.out.println("Query Point "+i);
				improved_range_search_binary (testdata.data_set.get(i), dis, data);
				//System.out.println();
				}

			endTime = System.nanoTime();
			System.out.println("dis  --- " + j + "  " + Math.pow(10, -6)	* 1.0 * (endTime - startTime) / testdata.data_set.size());
		}

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

	private static void direct_range_search(finger_print fp, double dis,
			Data data) {
		ArrayList<Integer> lst1 = new ArrayList<Integer>();
		HashMap<Integer, Integer> potent = new HashMap<Integer, Integer>();
		ArrayList<Integer> lst2 = new ArrayList<Integer>();

		// to be commented
		/*for (int i : data.data_set.keySet()) {
			if (fp.getDistance(data.data_set.get(i)) < dis) {
				lst2.add(i);
				//System.out.print(" " + fp.getDistance(data.data_set.get(i)));
			}
		}
		System.out.println(lst2);
		lst2.clear();
		*/
		for (Integer fid : fp.feature_map.keySet()) {
			if (finger_print.invert_index.containsKey(fid)) {
				for (Integer id : finger_print.invert_index.get(fid).keySet()) {
					if (!potent.containsKey(id)) {
						potent.put(id, 1);
						lst1.add(id);
					}
				}
			}
		}

		for (int i = 0; i < lst1.size(); i++) {
			if (fp.getDistance(data.data_set.get(lst1.get(i))) < dis) {
				lst2.add(lst1.get(i));
			}
		}
		
		//System.out.println(lst1.size());
		//System.out.println();
	}

	private static void improved_range_search(finger_print fp, double dis,
			Data data) {
		//System.out.println(fp.id);
		ArrayList<Integer> lst1 = new ArrayList<Integer>();
		HashMap<Integer, Integer> potent = new HashMap<Integer, Integer>();
		ArrayList<Integer> lst2 = new ArrayList<Integer>();

		// to be commented
//		for (int i : data.data_set.keySet()) {
//			if (fp.getDistance(data.data_set.get(i)) < dis) {
//				lst2.add(i);
//				//System.out.print(" " + fp.getDistance(data.data_set.get(i)));
//			}
//		}
//		System.out.println(lst2);
//		int s=lst2.size();
//		lst2.clear();
		
		int q_feat = fp.feature_size;

		/** binary **/

//		int feat_count = 0;
		double min_fts = 0;

		/***/

		/** non binary **/

		double num = 0;
		double denom = fp.feat_sum;

		/*****/
		boolean add = true;
		int f = 0;
		
		int gen=0;
		
		Collections.sort(fp.feat_present, finger_print.comp); // uncomment fsort

		//System.out.println();
		for (Integer fid : fp.feat_present) {
			add = true;
			if (finger_print.mn_ft_allpts_infeat.containsKey(fid)) {
				//System.out.println("No fo0 points in feature "+fid+" "+finger_print.invert_index.get(fid).keySet().size()* finger_print.mn_ft_allpts_infeat.get(fid));

				
				double temp = 0, max_sim = 0;

				/** binary **/
//				temp = Math.min(min_fts, finger_print.mn_ft_allpts_infeat.get(fid));
//				max_sim = (feat_count + 1.0) / (q_feat - (1 + feat_count) + temp);
				/****/

				/** non binary **/
				double q_sum = 0;
				double f_sum = 0;
				double temp_num = 0;
				double temp_denom = 0;

								
//				if (finger_print.non_bin) {
					double q_f = fp.feature_map.get(fid);
					temp_num = num
							+ Math.min(q_f, finger_print.max_ft_val.get(fid));

					temp = Math.min(min_fts, finger_print.min_sum_ft_val.get(fid));
					double a = fp.feature_map.get(fid);
					double b = finger_print.min_ft_val.get(fid);
					q_sum += a;
					f_sum += b;
					temp_denom = denom + temp - q_sum - f_sum + Math.max(a, b);
					max_sim = 1.0 * temp_num / temp_denom;
//				}
				/****/
				//System.out.println(dis);
				if (max_sim < 1 - dis) {
					
					//System.out.println("Rejected "+fid + " "+finger_print.invert_index.get(fid).containsKey(553)+" feature no "+gen+" "+fp.getDistance(data.data_set.get(553)));
					
					//f++;
					min_fts = temp;

					/*** non-bin ***/
					num = temp_num;
					denom = temp_denom;
					/****/
					
					// System.out.println(( feat_neglect.size()+1.0) / (q_feat
					// -1 - feat_neglect.size() + Math.max(max,
					// finger_print.mn_ft_allpts_infeat.get(fid))));
					
					add = false;
					//System.out.println("Feature no : "+ fid+ );
//					feat_count++;
				}

				if (add) {

					if (finger_print.invert_index.containsKey(fid)) {
						for (Integer id : finger_print.invert_index.get(fid)
								.keySet()) {
							if (!potent.containsKey(id)) {
								potent.put(id, 1);
								lst1.add(id);
							}
						}
					}
				}
			}
		}
		//System.out.println("percent pruned "+1.0*f/fp.feat_present.size());
		//System.out.println("Features pruned "+feat_count);
		//System.out.println("rehected no of feats " + f);
		for (int i = 0; i < lst1.size(); i++) {
			int j = lst1.get(i);
			if (fp.getDistance(data.data_set.get(j)) < dis) {
				lst2.add(j);
				//System.out.print(" "+ fp.getDistance(data.data_set.get(lst1.get(i))));
				// System.out.println("Point "+j);
			}
		}
		
//		if(lst2.size()!=s){
//			System.out.println("NNNNNNNNNNNNNNNNNNNOOOOOOOOOOOOOOOO");
//		}
		
		//System.out.println(lst2);
		//System.out.println("Candidate point list size "+lst1.size());
		//System.out.println();
	}

	private static void improved_range_search_binary(finger_print fp, double dis,
			Data data) {
		//System.out.println(fp.id);
		ArrayList<Integer> lst1 = new ArrayList<Integer>();
		HashMap<Integer, Integer> potent = new HashMap<Integer, Integer>();
		ArrayList<Integer> lst2 = new ArrayList<Integer>();

		// to be commented
//		for (int i : data.data_set.keySet()) {
//			if (fp.getDistance(data.data_set.get(i)) < dis) {
//				lst2.add(i);
//				//System.out.print(" " + fp.getDistance(data.data_set.get(i)));
//			}
//		}
//		System.out.println(lst2);
//		int s=lst2.size();
//		lst2.clear();
		
		int q_feat = fp.feature_size;

		/** binary **/

		int feat_count = 0;
		double min_fts = 0;

		boolean add = true;
		int f = 0;
		
		int gen=0;
		
		Collections.sort(fp.feat_present, finger_print.comp); // uncomment fsort

		//System.out.println();
		for (Integer fid : fp.feat_present) {
			add = true;
			if (finger_print.mn_ft_allpts_infeat.containsKey(fid)) {
				//System.out.println("No fo0 points in feature "+fid+" "+finger_print.invert_index.get(fid).keySet().size()* finger_print.mn_ft_allpts_infeat.get(fid));

				
				double temp = 0, max_sim = 0;

				/** binary **/
				temp = Math.min(min_fts, finger_print.mn_ft_allpts_infeat.get(fid));
				max_sim = (feat_count + 1.0) / (q_feat - (1 + feat_count) + temp);
				/****/
								
				//System.out.println(dis);
				if (max_sim < 1 - dis) {
					
					//System.out.println("Rejected "+fid + " "+finger_print.invert_index.get(fid).containsKey(553)+" feature no "+gen+" "+fp.getDistance(data.data_set.get(553)));
					
					//f++;
					min_fts = temp;

					
					// System.out.println(( feat_neglect.size()+1.0) / (q_feat
					// -1 - feat_neglect.size() + Math.max(max,
					// finger_print.mn_ft_allpts_infeat.get(fid))));
					
					add = false;
					//System.out.println("Feature no : "+ fid+ );
					feat_count++;
				}

				if (add) {

					if (finger_print.invert_index.containsKey(fid)) {
						for (Integer id : finger_print.invert_index.get(fid)
								.keySet()) {
							if (!potent.containsKey(id)) {
								potent.put(id, 1);
								lst1.add(id);
							}
						}
					}
				}
			}
		}
		//System.out.println("percent pruned "+1.0*f/fp.feat_present.size());
		//System.out.println("Features pruned "+feat_count);
		//System.out.println("rehected no of feats " + f);
		for (int i = 0; i < lst1.size(); i++) {
			int j = lst1.get(i);
			if (fp.getDistance(data.data_set.get(j)) < dis) {
				lst2.add(j);
				//System.out.print(" "+ fp.getDistance(data.data_set.get(lst1.get(i))));
				// System.out.println("Point "+j);
			}
		}
		
//		if(lst2.size()!=s){
//			System.out.println("NNNNNNNNNNNNNNNNNNNOOOOOOOOOOOOOOOO");
//		}
		
		//System.out.println(lst2);
		//System.out.println("Candidate point list size "+lst1.size());
		//System.out.println();
	}
}
