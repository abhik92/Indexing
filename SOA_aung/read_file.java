package SOA_aung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;



public class read_file {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		split_index idx= new split_index();
		
		int size;
		Scanner in= new Scanner(System.in);
		
		BufferedReader br = new BufferedReader(new FileReader(new File("files/"+args[0])));
		
		double dis = Double.parseDouble(args[1]);
		
		idx.splt_sze=Integer.parseInt(args[2]);
		
		String line;
		int count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.add_all_features(features_list);
			//data_set.add(fp);
			idx.vert_splt.add(fp);
		}
		
		idx.lst_fe = finger_print.lst_fe;
		
		// it does Collections.sort(data_set) && Collections.sort(lst_fe);
		idx.run();
		
		
		//for(features f: lst_fe){
			//System.out.println(f.count);
		//}
		
		ArrayList<finger_print> test_set= new ArrayList<finger_print>();		
		br = new BufferedReader(new FileReader(new File("files/"+args[3])));		
		count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			finger_print fp= new finger_print(count);
			fp.add_all_features(features_list);
			test_set.add(fp);
		}
		

		for(int j=1;j<=9;j++){
			dis=0.1*j;
		
			long startTime = System.nanoTime();		
			for(int i=1;i<=test_set.size();i++){
				finger_print f = test_set.get(i-1);
				idx.create_splits(f);
				ArrayList<finger_print> search = idx.range_search(f,dis);			
			}
			
			long endTime = System.nanoTime();
		
		
			System.out.println("dis  --- "+j+" "+Math.pow(10,-6)*1.0*(endTime - startTime)/test_set.size()); 
		}
		
		
		
		
		
		
		
		
		
		return;
	}

}
