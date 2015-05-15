import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class createFeatureset {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader(new File("files/dud2006.feat")));
		String line;
		while((line=br.readLine()) !=null){
			String[] buff=line.split(" ");
			boolean first=true;
			System.out.print("{");
			for(int i=1;i<buff.length;i++){
				if(Integer.parseInt(buff[i].split(":")[1]) !=0){
					if(first){
						first=false;
						System.out.print(buff[i]);
					}
					else{
						System.out.print(" , "+buff[i]);
						
					}
				}
			}
			System.out.println("}");
			
		}
		
	}

}
