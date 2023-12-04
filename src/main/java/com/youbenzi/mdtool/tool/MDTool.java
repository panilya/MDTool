package com.youbenzi.mdtool.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.youbenzi.mdtool.export.HTMLDecorator;
import com.youbenzi.mdtool.markdown.Analyzer;
import com.youbenzi.mdtool.markdown.bean.Block;

public class MDTool {

	private static final String DEFAULT_CHARSET = "UTF-8";

	public static String markdown2Html(File file) throws Exception {
		return markdown2Html(file, DEFAULT_CHARSET);
	}

	public static String markdown2Html(File file, String charset) throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));){
			String lineStr = null;

			StringBuffer sb = new StringBuffer();
			while ((lineStr = reader.readLine())!=null) {
				sb.append(lineStr).append("\n");
			}
			return markdown2Html(sb.toString());
		}
	}

	public static String markdown2Html(String mdStr) throws RuntimeException {
		if (mdStr==null) {
			return null;
		}

		List<Block> list = Analyzer.analyze(mdStr);
		HTMLDecorator decorator = new HTMLDecorator();

		decorator.decorate(list);
		return decorator.outputHtml();
	}

	public static void main(String[] args) {
		try {
			String s = MDTool.markdown2Html(" Month    | Savings |\n" +
					"| -------- | ------- |\n" +
					"| January  | $250    |\n" +
					"| February | $80     |\n" +
					"| March    | $420    |");
			System.out.println(s);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
