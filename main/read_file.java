package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class read_file {
	public static void main(String[] args) throws IOException{
		
		
		
		// USAGE:  args (inputfile pivotsize outliersize distance)
		
		
		Data data= new Data();
		int size;
		Scanner in= new Scanner(System.in);
		
		BufferedReader br = new BufferedReader(new FileReader(new File("files/"+args[0])));
		String line;
		int count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			data.data_set.put(count, fp);
		}
		//System.out.println(finger_print.f_values.size());
		ArrayList<Integer> x=finger_print.f_values;
		//System.out.println(finger_print.f_valin.size());
		ArrayList<Integer> y=finger_print.f_valin;
		
		size=count;
		
		index indx= new index(data.data_set);
		indx.PIVOT_SIZE_TOTAL=Integer.parseInt(args[1]);
		indx.OUTLIERS_SIZE=Integer.parseInt(args[2]);
		
		
		System.out.println("DATASET Size :"+args[0]);
		System.out.println("indx.PIVOT_SIZE_TOTAL "+indx.PIVOT_SIZE_TOTAL);
		System.out.println("indx.OUTLIERS_SIZE "+indx.OUTLIERS_SIZE);
		System.out.println();
		
		
		long startTime = System.nanoTime();		
		indx.run();
		long endTime = System.nanoTime();
		//System.out.println("Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/data.data_set.size() + " ms per compund for indexing "+data.data_set.size() +" with "+ indx.PIVOT_SIZE_TOTAL+" pivots" ); 
		System.out.println("Indexing time "+Math.pow(10,-6)*1.0*(endTime - startTime)/data.data_set.size()); 
		
		
////////////////////////////////////////////////////////////////////////////		
		//new dimension red. stuff
		/*
		Data orig1000= new Data();		
		br = new BufferedReader(new FileReader(new File("files/indexdatafirst1000")));
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			orig1000.data_set.put(count, fp);
		}
		
		Data orig= new Data();		
		br = new BufferedReader(new FileReader(new File("files/data_old_test")));
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			orig.data_set.put(count, fp);
		}
		
		Data testdata= new Data();		
		br = new BufferedReader(new FileReader(new File("files/data_new_test")));
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			testdata.data_set.put(count, fp);
		}
		
		
		*/
	//Remove this when done with your stuff	
///////////////////////////////////////////////////////////////////////////////		
		
		
		
		
		Data testdata= new Data();
		br = new BufferedReader(new FileReader(new File("files/"+args[3])));		
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			testdata.data_set.put(count, fp);
		}
		
		
		
		double dis;// = Double.parseDouble(args[3]);
		
		ArrayList<Integer> indexed= new ArrayList<Integer>();
		ArrayList<Integer> linear= new ArrayList<Integer>();
		
		
		//indx.=data;
		
		for(int j=1;j<=9;j++){
		dis=0.1*j;
		startTime = System.nanoTime();		
		for(int i=1;i<=testdata.data_set.size();i++){
				
			ArrayList<finger_print> f= new ArrayList<finger_print>();
			//indx.range_finger_algo2(testdata.data_set.get(i), dis, indx.head, f);	
			
			f=indx.range_finger(testdata.data_set.get(i), dis);	
			
			//finger_print f=indx.closest_finger(testdata.data_set.get(i));	
			
			//System.out.println(f);			
			//indexed.add(f.size());
		
		
		
			/////////////////////////////////////////remove

			/*	for(int j=0;j<f.size();j++){
					if(orig.data_set.get(i).getDistance(f.get(j)) < dis){
						
					}
				}
			 */
		
		}
		endTime = System.nanoTime();
		
		//System.out.println();
		System.out.println("dis ----- "+j+" "+Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size()); 
		
		}
		
		
		//change orig to testdata
		
		int comparisons=0;
		for(int i=1;i<=testdata.data_set.size();i++){
			comparisons+=testdata.data_set.get(i).comparisons;
		}
			//System.out.println("Avg Comparison "+1.0*comparisons/testdata.data_set.size() +" compared to "+data.data_set.size());
			//System.out.println("For querying using indexing Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size() + " ms per compund "); 
		
		//System.out.println("Comparisons "+1.0*comparisons/testdata.data_set.size());
		
			
			
		//startTime = System.nanoTime();				
		
		

		/*
		for(int i=1;i<=testdata.data_set.size();i++){
			ArrayList<finger_print> f1=orig1000.range_finger_brute(orig.data_set.get(i),dis);			
			//finger_print f1=data.closest_finger_brute(testdata.data_set.get(i));	
			System.out.println(f1.size());
			linear.add(f1.size());
		}
		*/
			
		
		//endTime = System.nanoTime();
		//System.out.println("For all pair similairty Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size() + " ms per compund "); 
		//System.out.println(Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size()); 

		
		
		
		return;
	}
}