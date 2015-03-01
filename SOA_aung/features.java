package SOA_aung;

public class features implements Comparable<features>{
	int id;
	int count;
	double mag_count;
	
	public features(int id_num) {
		// TODO Auto-generated constructor stub
		id=id_num;
		count=0;
		mag_count=0;
		
	}

	@Override
	public int compareTo(features o) {
		// TODO Auto-generated method stub
		if(this.count < o.count) return 1;
		if(this.count > o.count) return -1;		
		return 0;
	}
	
	
	
}
