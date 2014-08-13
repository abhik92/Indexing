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
		
		size=count;
		
		index indx= new index(data.data_set);
		indx.PIVOT_SIZE_TOTAL=Integer.parseInt(args[1]);
		indx.OUTLIERS_SIZE=Integer.parseInt(args[2]);
		
		long startTime = System.nanoTime();		
		indx.run();
		long endTime = System.nanoTime();
		//System.out.println("Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/data.data_set.size() + " ms per compund for indexing "+data.data_set.size() +" with "+ indx.PIVOT_SIZE_TOTAL+" pivots" ); 
		System.out.println(Math.pow(10,-6)*1.0*(endTime - startTime)/data.data_set.size()); 
		
		
		
		
		Data testdata= new Data();
		br = new BufferedReader(new FileReader(new File("files/test500")));		
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.line=line;
			fp.add_all_features(features_list);
			testdata.data_set.put(count, fp);
		}
		
		double dis = Double.parseDouble(args[3]);
		
		ArrayList<Integer> indexed= new ArrayList<Integer>();
		ArrayList<Integer> linear= new ArrayList<Integer>();
		
		
		//indx.=data;
		startTime = System.nanoTime();		
		for(int i=1;i<=testdata.data_set.size();i++){
				
			ArrayList<finger_print> f= new ArrayList<finger_print>();
			indx.range_finger_algo2(testdata.data_set.get(i), dis, indx.head, f);	
			//finger_print f=indx.closest_finger(testdata.data_set.get(i));	
			//System.out.println(f.size());			
			//indexed.add(f.size());
		}
		endTime = System.nanoTime();
		
		//System.out.println();
		
		int comparisons=0;
		for(int i=1;i<=testdata.data_set.size();i++){
			comparisons+=testdata.data_set.get(i).comparisons;
		}
			//System.out.println("Avg Comparison "+1.0*comparisons/testdata.data_set.size() +" compared to "+data.data_set.size());
			//System.out.println("For querying using indexing Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size() + " ms per compund "); 
		
			System.out.println(1.0*comparisons/testdata.data_set.size());
			System.out.println(Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size()); 
		
		
		
		startTime = System.nanoTime();				
		
		

		
		for(int i=1;i<=testdata.data_set.size();i++){
			ArrayList<finger_print> f1=data.range_finger_brute(testdata.data_set.get(i),dis);			
			//finger_print f1=data.closest_finger_brute(testdata.data_set.get(i));	
			//System.out.println(f1.size());
			//linear.add(f1.size());
		}
			
		
		endTime = System.nanoTime();
		//System.out.println("For all pair similairty Took "+Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size() + " ms per compund "); 
		System.out.println(Math.pow(10,-6)*1.0*(endTime - startTime)/testdata.data_set.size()); 

		
		
		
		return;
	}
}
