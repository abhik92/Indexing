package SOA_aung;

import java.util.ArrayList;
import java.util.Collections;

public class split_index {

	ArrayList<finger_print> vert_splt;
	int splt_sze;
	static ArrayList<features> lst_fe = new ArrayList<features>();

	public split_index() {
		vert_splt = new ArrayList<finger_print>();
	}

	public void run() {
		Collections.sort(vert_splt);
		Collections.sort(lst_fe);

		// for aungs paper
		create_splits();

	}

	private void create_splits() {
		// TODO Auto-generated method stub
		for (finger_print f : this.vert_splt) {
			int i = 0;
			while (i < lst_fe.size()) {
				double sum = 0;
				for (int j = 0; j < this.splt_sze && i < lst_fe.size(); i++, j++) {
					if (f.feature_map.containsKey(lst_fe.get(i).id)) {
						sum += f.feature_map.get(lst_fe.get(i).id);
					}
				}
				f.sum_mag.add(sum);
			}

		}
	}

	public void create_splits(finger_print f) {
		// TODO Auto-generated method stub
		int i = 0;
		while (i < lst_fe.size()) {
			double sum = 0;
			for (int j = 0; j < this.splt_sze && i < lst_fe.size(); i++, j++) {
				if(f.feature_map.containsKey(lst_fe.get(i).id)) {
					sum += f.feature_map.get(lst_fe.get(i).id);
				}
			}
			f.sum_mag.add(sum);
		}

	}

	public ArrayList<finger_print> range_search(finger_print fp, double dis) {
		ArrayList<finger_print> srch = new ArrayList<finger_print>();
		double low_tr = fp.magnitude * (1 - dis);
		double high_tr = fp.magnitude / (1 - dis);
		// System.out.println(low_tr+" "+high_tr);

		int len = vert_splt.size();
		int low = bin_search(0, len - 1, low_tr);
		int high = bin_search(0, len - 1, high_tr);

		// System.out.println(low+" "+high);

		// for swmaidass paper
		for (int i = low; i <= high; i++) {
			if (1 - this.vert_splt.get(i).getSimilarity(fp) < dis) {
				srch.add(this.vert_splt.get(i));
			}
		}
		//System.out.println(srch.size());


		// System.out.println(lst_fe.size());
		return srch;
	}

	public int bin_search(int low, int high, double tr) {
		if (low == high)
			return low;

		if (low > high)
			System.out.println("Something wrong");

		int mid = (low + high) / 2;

		if (vert_splt.get(mid).magnitude > tr)
			return bin_search(low, mid, tr);

		else if (vert_splt.get(mid).magnitude < tr)
			return bin_search(mid + 1, high, tr);

		return mid;
	}

	public ArrayList<finger_print> range_search_aung(finger_print f, double dis) {
		
		
		
		return null;
	}

}
