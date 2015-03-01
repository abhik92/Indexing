import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.finger_print;


public class FileCon {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		BufferedReader br = new BufferedReader(new FileReader(new File("files/indexdata1000")));
		String line;
		int count=0;
		while((line=br.readLine()) !=null){
			count++;
			String[] features_list=line.replace("{", "").replace("}", "").split(",");
			System.out.print("{");
			for(int i=0;i<features_list.length;i++){
				if(i!=0){
					System.out.print(",");
				}
					
				String[] feature_parsed= features_list[i].replace(":", " ").trim().split("\\s+");
				int f_id= Integer.parseInt(feature_parsed[0]);
				
				System.out.print(f_id+": 1 ");
			}	
			System.out.println("}");
			
			
		}
		
		
	}

}
