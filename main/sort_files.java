package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class sort_files {
	public static void main(String[] args) throws IOException{
		
		Data data= new Data();
		int size;
		
		BufferedReader br = new BufferedReader(new FileReader(new File("files/indexdata")));
		String line;
		int count=0;

		
		
		FileWriter fw1= new FileWriter(new File("files/indexdata1000"));		
		FileWriter fw2= new FileWriter(new File("files/indexdata10000"));		
		FileWriter fw3= new FileWriter(new File("files/indexdata100000"));
		FileWriter fw4= new FileWriter(new File("files/indexdata200000"));
		
		int test1=0,test2=0,test3=0,test4=0;
		Random r = new Random();
		while((line=br.readLine()) !=null){
			count++;
			if(test1 <1000 && r.nextDouble() < 0.4){
				fw1.write(line);
				if(test1!=999)
					fw1.write("\n");				
				test1++;
			}
			if(test2 <10000 && r.nextDouble() < 0.4){
				fw2.write(line);

				if(test2!=9999)
					fw2.write("\n");
				test2++;
			}
			if(test3 <100000 && r.nextDouble() < 0.8){
				fw3.write(line);
				if(test3!=99999)
					fw3.write("\n");
				test3++;
			}
			if(test4 <200000 && r.nextDouble() < 0.95){
				fw4.write(line);
				if(test4!=199999) fw4.write("\n");
				test4++;
			}
			//String[] features_list=line.replace("{", "").replace("}", "").split(",");
			//finger_print fp= new finger_print(count);
			//fp.add_all_features(features_list);
			//data.data_set.put(count, fp);
		}
		fw1.flush();
		fw1.close();
		fw2.flush();
		fw2.close();
		fw3.flush();
		fw3.close();
		fw4.flush();
		
		size=count;
		System.out.println(size);
		
		
	}
		
}
