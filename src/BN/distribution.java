package BN;

import java.util.HashMap;
import java.util.Map;

public class distribution {
	
	private String name;
	private Map<String,Integer> category = new HashMap<>();
	
	private distribution from;
	private Map<String,distribution> to = new HashMap<>();
	
	private Map<String,Double> prob = new HashMap<>();
	
	public distribution(String n){
		name = n;
		from = null;
	}
	
	
	public void add(String category, int num){
		Integer i = this.category.get(category);
		if(i==null){
			this.category.put(category, num);
		}
		else{
			this.category.put(category, i + num);
		}
	}
	
	
	public int num(){
		int r=0;
		for(Integer i : category.values())
			r += i;
		return r;
	}
	
	public double P(String category){
		Integer i = this.category.get(category);
		if(i==null)
			return 0;
		return ((double)i)/num();
//		return 0;
	}
	
	public double P(String category,String given){
		return to.get(given).P(category);
	}
}
