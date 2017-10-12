package com.fjr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fjr.bean.BeanKTP;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class SortirKTPBMKG {
	
	ArrayList<BeanKTP> list = new ArrayList<>();

	public SortirKTPBMKG() throws Exception {
		
		File f = new File("E:/birokrasi/lamaran kerja/CPNS BMKG/jadwal ujian.pdf");
		PrintWriter out1 = new PrintWriter(
				new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/sortir jadwal ujian.txt"));
		PrintWriter out2 = new PrintWriter(
				new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/sortir jadwal ujian 1.txt"));

		PdfReader reader = new PdfReader(f.getPath());
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;

		String rg = "[0-9]{1,4}\\s\n?([A-Za-z\\`\\'\\.\\,\\s\n]+)\\s\n?([0-9]{16})\\s([0-9]{14})";

		Pattern p = Pattern.compile(rg);
		for (int i = 69; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			String result = strategy.getResultantText();
			out1.println(result);

			Matcher m = p.matcher(result);
			while (m.find()) {
				String nama = m.group(1);
				String noKTP = m.group(2);
				String noUjian = m.group(3);

				BeanKTP bean = new BeanKTP();
				bean.setNama(nama);
				bean.setNoKTP(noKTP);
				bean.setNoUjian(noUjian);

				list.add(bean);
			}

		}

		Collections.sort(list, (a, b) -> {
			Long a1 = Long.parseLong(a.getNoKTP());
			Long a2 = Long.parseLong(b.getNoKTP());
			return a1.compareTo(a2);
		});
		//

		int iter = 1;
		for (BeanKTP x : list) {
			if (x.getNoKTP().startsWith("72") 
//					|| x.getNoKTP().startsWith("73")
					) {
				out2.print(Integer.toString(iter++) + "|");
				out2.print(x.getNama().replace("\n", "") + "|");
				out2.print(x.getNoKTP() + "|");
				out2.print(x.getNoUjian());

				out2.println();
			}
		}

		out1.close();
		out2.close();
		System.out.println("FINISH");

	}

	public static void main(String[] args) throws Exception {
		new SortirKTPBMKG();
	}

}
