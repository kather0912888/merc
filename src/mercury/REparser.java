package mercury;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REparser extends parser {
	
	private class rule{
		Pattern pattern;
		String[] names;
		int successCount = 0;
	}
	
	// rule pool
	List<rule> rulePool = new ArrayList<>();
	
	private List<pair> innerParse(String str){
		if(str==null)
			return null;
		List<pair> l = new ArrayList<>();
		boolean ambiguous=false;
		boolean matched=false;
		for(rule r : rulePool){
			Matcher m = r.pattern.matcher(str);
			if(m.matches() && matched){
				ambiguous = true;
				break;
			}
			if(m.matches()){
				r.successCount++;
				for(int i=1;i<m.groupCount();i++){
					l.add(new pair(r.names[i-1],m.group(i)));
				}
				matched=true;
			}
		}
		if(l.size()==0)
			return null;
		return l;
	}

	@Override
	List<pair> parse(String str) {
		// TODO Auto-generated method stub
		return innerParse(str);
	}

}
