package BN;

public class ttt {
public static void main(String[] args) {
	for(String s : "紀人豪、沈子勝、除一量、陳政洞 1 1 1。「原有合法小規模旅館建築物防火避難改善方案之實驗驗證及電腦模擬分析」，內政部建築研究所委託研究報告，台北，第1-22頁(2010)。".split("\\(|\\)| |，|。"))
		System.out.println(s);
}
}
