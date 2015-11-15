package BN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class test {
	public static void main(String[] args) throws IOException {
		// only care about /[T]標題/, [A]作者群, [Y]時間, /[R]出處/
		bayse bayse = new bayse();
		String sCurrentLine;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("train"));

		while ((sCurrentLine = br.readLine()) != null) {
			// 只切，。（）()
			String[] tokens = sCurrentLine.split("\\(|\\)| |，|。");
			String preType = "H";
			for (int i = 0; i < tokens.length; i++) {
				// get label first
				String l = "NULL";
				String str2 = tokens[i];
				if (tokens[i].length() >= 3 && tokens[i].charAt(0) == '[' && tokens[i].charAt(2) == ']') {
					if (tokens[i].charAt(1) == 'A') {
						l = "A";
						str2 = str2.substring(3);
					} else if (tokens[i].charAt(1) == 'Y') {
						l = "Y";
						str2 = str2.substring(3);
					} else if (tokens[i].charAt(1) == 'T') {
						l = "T";
						str2 = str2.substring(3);
					}
				}
				bayse.add(l, "length", getLength(str2), 1);
				bayse.add(l, "allDigit", allDigit(str2), 1);
				bayse.add(l, "leftChar", getLeftChar(str2,sCurrentLine), 1);
				bayse.add(l, "preType", preType, 1);
				bayse.add(l, "startChar", getStartChar(str2), 1);
				preType = l;
			}
		}

		// now we can test
		String preType="H";
		String sss = "曹建宇、張長義 2008  地震災害經驗與調適行為之比較研究─以臺南縣白河、臺中縣東勢居民為例，華岡地理學報，21：52-75。 ";
//		sss = "梁曉興，《僑光學報》，2009年10月，頁95-110。";
//		sss = "莊初升、劉鎮發，〈巴色會傳教士與客家方言研究〉，《韶關學院學報（社會科學版）》，第23卷第7期（2002年3月），頁1-8。";
//		sss = "羅清吉、黃國誠 (2003) 全球化對台灣農村產業型態變遷之影響，台灣鄉村社會學會九十二年年會暨全球衝擊與鄉村調適研討會論文集。";
//		sss = "陳恆均、張國偉 (2006) 組織協力與組織績效之研究：以雲林縣蔬菜產銷班為例，公共行政學報，19: 1-54";
//		sss = "[12] 陳建忠、紀人豪、吳勝宏、陳政洞、蔡秀芬，「石化工業廠房建築物火災調查分析之研究」，內政部建築研究所研究報告， 100年12月。";
		String[] tokens = sss.split("\\(|\\)| |，|。");
		System.out.println(sss);
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i]);
		}
		for (int i = 0; i < tokens.length; i++) {
			String[] featureTypes = {"length","allDigit","leftChar","preType","startChar"};
			String[] features = {getLength(tokens[i]) , allDigit(tokens[i]) , getLeftChar(tokens[i],sss) , preType , getStartChar(tokens[i]) };
			if(bayse.guess(featureTypes, features)!="NULL"){
				System.out.println("\t" + tokens[i]);
				System.out.println("\t" + bayse.guess(featureTypes, features));
				System.out.println("\t" + bayse.maxp(featureTypes, features));
				System.out.println();
			}
			preType = bayse.guess(featureTypes, features);
		}
	}

	public static String getLength(String input) {
		return String.valueOf(input.length());
	}
	
	public static String getStartChar(String input) {
		if(input.length()>=1)
		return String.valueOf(input.charAt(0));
		else
			return null;
	}
	
	public static String getLeftChar(String input, String original) {
		int index = original.indexOf(input);
		if(index==0)
			return "HEAD";
		else{
			return String.valueOf(original.charAt(index-1));
		}
	}

	// public static String getLeftLabel(String input){
	//
	// }

	public static String allDigit(String input) {
		for (int i = 0; i < input.length(); i++)
			if (!(input.charAt(i) >= '0' && input.charAt(i) <= '0'))
				return "F";
		return "T";
	}
}
