package mercury;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class m {
	public static Document parse(String string) throws DocumentException {
		SAXReader reader = new SAXReader();
		// Document document = reader.read(string);
		// reader.read(new File(string));
		return reader.read(new File(string));
	}

	// file(publiccation)
	// -> Issues
	// -> Issue
	// -> Documents
	// -> Document
	// -> DocMeta
	// -> Reference
	
	public static class PATTERN{
		Pattern pattern;
		String[] Explain;
		public PATTERN(Pattern p,String[] s){
			pattern = p;
			Explain = s;
		}
		public void show(){
			System.out.println(pattern);
			for(String s : Explain)
				System.out.print(s + "\t");
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws Exception {

		FileWriter fw = new FileWriter("out.txt");
		//
//		ArrayList<PATTERN> patternPool = new ArrayList<>();
//		patternPool.add(new PATTERN(Pattern.compile("《(.*)》，(.*)，([0-9]*|[0-9]*\\.[0-9]*|[0-9]*\\.[0-9]*\\.[0-9]*)。(（引用文本）)*"),new String[]{"作品","出版","時間"}));
//		patternPool.add(new PATTERN(Pattern.compile("《(.*)》，(.*)，([0-9]*|[0-9]*\\.[0-9]*|[0-9]*\\.[0-9]*\\.[0-9]*)(（引用文本）)*"),new String[]{"作品","出版","時間"}));
//		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		Files.walk(Paths.get("XML")).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				System.out.println(filePath);
				Document d;
				try {
					d = parse(filePath.toString());
					Element root = d.getRootElement();
					// root.elements()
					for (Element issues : (List<Element>) root.elements("Issues"))
						for (Element issue : (List<Element>) issues.elements("Issue"))
							for (Element documents : (List<Element>) issue.elements("Documents"))
								for (Element document : (List<Element>) documents.elements("Document"))
									for (Element docmeta : (List<Element>) document.elements("DocMeta"))
										for (Element reference : (List<Element>) docmeta.elements("Reference"))
											fw.write(reference.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		fw.close();
	}
}
