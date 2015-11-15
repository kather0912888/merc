package NGram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class NGram {
	private class Gram implements Comparable<Gram>{
		String str;
		int times;
		Gram(String s, int t){
			str =s ;
			times = t;
		}
		
		@Override
		public int compareTo(Gram o) {
			// TODO Auto-generated method stub
			return o.times - times;
		}
	}
	HashMap<Integer, HashMap<String, Integer>> map = new HashMap<>();
	HashMap<Integer, List<Gram>> sortedMap = new HashMap<>();
	int from;
	int end;

	public NGram(String input, int from, int d) {
		map = new HashMap<>(input.length());
		this.from = from;
		this.end = d + 1;
		for (int i = from; i < end; i++)
			map.put(i, new HashMap<>());
		for (int i = from; i < end; i++)
			sortedMap.put(i, new LinkedList<>());
		
		// from-gram, from+1 - gram , ... end-gram
		for (int i = 0; i <= input.length() - from; i++) {
			for (int j = i + from; j < i + end && j <= input.length(); j++) {
				String temp = input.substring(i, j);
				if (map.get(j - i).get(temp) == null)
					map.get(j - i).put(temp, 1);
				else
					map.get(j - i).put(temp, map.get(j - i).get(temp) + 1);
			}
		}
		sort();
	}
	
	private void sort(){
		for (int i = from; i < end; i++) {
			HashMap<String, Integer> m = map.get(i);
			List<Gram> l = sortedMap.get(i);
			for(String k : m.keySet())
				l.add(new Gram(k,m.get(k)));
			Collections.sort(l);
		}
	}

	public void show() {
		for (int i = from; i < end; i++) {
			System.out.println(i + "gram : ");
			List<Gram> m = sortedMap.get(i);
			for (Gram k : m) {
				System.out.println("\t" + k.str + " - " + k.times);
			}
		}
	}
	
	public void show(int max) {
		for (int i = from; i < end; i++) {
			System.out.println(i + "gram : ");
			List<Gram> m = sortedMap.get(i);
			int j=0;
			for (Gram k : m) {
				System.out.println("\t" + k.str + " - " + k.times);
				if(++j>=max)
					break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String sCurrentLine;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("test"));
		String s = "";
		while ((sCurrentLine = br.readLine()) != null) {
			s+=sCurrentLine;
		}
		
		NGram n = new NGram(
				s,
				2, 10);
		n.show(20);
	}
}
