package BN;

import java.util.HashMap;
import java.util.Map;

public class bayse {
	Map<String,Map<String,Map<String,Integer>>> labelToFeature = new HashMap<>();
	public void add(String label, String featureType,String feature, Integer number){
		
		// get label => get featureType => get feature
		
		// check and set label
		Map<String,Map<String,Integer>> m = labelToFeature.get(label);
		if(m==null){
			m = new HashMap<>();
			labelToFeature.put(label, m);
		}
		// check and set featureType
		Map<String,Integer> mm = m.get(featureType);
		if(mm==null){
			mm = new HashMap<>();
			m.put(featureType, mm);
		}
		// check and set number
		Integer num = mm.get(feature);
		if(num==null){
			mm.put(feature, number);
		}
		else{
			mm.put(feature, mm.get(feature)+number);
		}
		
		
//		
//		Map<String,Map<String,Integer>> m = labelToFeature.get(label);
//		if(m==null){
//			m = new HashMap<>();
//			labelToFeature.put(label, m);
//		}
//		if(m.get(featureType)==null){
//			Map<String,Integer> mm = new HashMap<>();
//			mm.put(feature, number);
//			m.put(featureType, mm);
//		}
//		else{
////			m.put(featureType, m.get(featureType)+number);
//			Map<String,Integer> mm = m.get(featureType);
//			if(mm==null){
//				mm = new HashMap<>();
//				m.put(feature, mm);
//				mm.put(feature, number);
//			}
//		}
		
	}
	public double P(String featureType, String feature, String givenLabel){
		int max = 0;
		for(Integer i : labelToFeature.get(givenLabel).get(featureType).values())
			max += i;
		try{
			return ((double)labelToFeature.get(givenLabel).get(featureType).get(feature))/((double)(max));
		}
		catch(Exception e){}
		return 1.0/(double)max;
	}
	
	public String guess(String[] featureTypes, String[] features){
		double max = 0.0;
		String LABEL = "";
		for(String label : labelToFeature.keySet()){
			double m = 0.0;
			for(int i=0;i<featureTypes.length;i++){
				String featureType = featureTypes[i];
				String feature     = features[i];
				m += this.P(featureType, feature, label);
			}
			if(m>max){
				LABEL = label;
				max = m;
			}
		}
		return LABEL;
	}
	public double maxp(String[] featureTypes, String[] features){
		double max = 0.0;
		String LABEL = "";
		for(String label : labelToFeature.keySet()){
			double m = 1.0;
			for(int i=0;i<featureTypes.length;i++){
				String featureType = featureTypes[i];
				String feature     = features[i];
				m *= this.P(featureType, feature, label);
			}
			if(m>max){
				LABEL = label;
				max = m;
			}
		}
		return max;
	}
}
