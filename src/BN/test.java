package BN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class test {
	public static void main(String[] args) throws IOException {
		// only care about /[T]���D/, [A]�@�̸s, [Y]�ɶ�, /[R]�X�B/
		bayse bayse = new bayse();
		String sCurrentLine;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("train"));

		while ((sCurrentLine = br.readLine()) != null) {
			// �u���A�C�]�^()
			String[] tokens = sCurrentLine.split("\\(|\\)| |�A|�C");
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
		String sss = "��ئt�B�i���q 2008  �a�_�a�`�g��P�վA�欰�������s�w�H�O�n���ժe�B�O�����F�թ~�����ҡA�ة��a�z�ǳ��A21�G52-75�C ";
//		sss = "��忳�A�m�����ǳ��n�A2009�~10��A��95-110�C";
//		sss = "����ɡB�B��o�A�q�ڦ�|�ǱФh�P�Ȯa�訥��s�r�A�m�����ǰ|�ǳ��]���|��Ǫ��^�n�A��23����7���]2002�~3��^�A��1-8�C";
//		sss = "ù�M�N�B����� (2003) ���y�ƹ�x�W�A�����~���A�ܾE���v�T�A�x�W�m�����|�Ƿ|�E�Q�G�~�~�|�[���y�����P�m���վA��Q�|�פ嶰�C";
//		sss = "�����B�i�갶 (2006) ��´��O�P��´�Z�Ĥ���s�G�H���L�����沣�P�Z���ҡA���@��F�ǳ��A19: 1-54";
//		sss = "[12] ���ة��B���H���B�d�ӧ��B���F�}�B���q��A�u�ۤƤu�~�t�Ыؿv�����a�լd���R����s�v�A���F���ؿv��s�Ҭ�s���i�A 100�~12��C";
		String[] tokens = sss.split("\\(|\\)| |�A|�C");
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
