package com.fjr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fjr.bean.PesertaBMKG;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class BMKGDaftarUjianLolosVerifikasiBerkas {
	
	ArrayList<PesertaBMKG> list = new ArrayList<>();
	
	public BMKGDaftarUjianLolosVerifikasiBerkas() throws Exception {
		File f = new File("E:/birokrasi/lamaran kerja/CPNS BMKG/pengumuman lolos verifikasi berkas.pdf");
		PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/pengumuman lolos verifikasi berkas.txt"));
		PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/hasil sortir.txt"));
		
		PdfReader reader = new PdfReader(f.getPath()); 
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		
		String rg = "[0-9]{1,4}\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s([0-9]{10})\\s(UMUM|LULUSAN TERBAIK)"; 
		Pattern p = Pattern.compile(rg);
		for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
        	String result = strategy.getResultantText(); 
        	out1.println(result);

        	Matcher m =  p.matcher(result); 
        	while(m.find()) {
        		// out2.println(m.group(1));
        		String nama = m.group(1); 
        		String noReg = m.group(2);
        		String jenisFormasi = m.group(3); 
        		
        		PesertaBMKG bmkg = new PesertaBMKG(); 
        		bmkg.setNama(nama);
        		bmkg.setNoRegistrasi(noReg); 
        		bmkg.setJenisFormasi(jenisFormasi); 
        		
        		list.add(bmkg); 
        	}
        	
		}
		
		
		Collections.sort(list, (a, b ) -> {
			Integer a1 = Integer.parseInt(a.getNoRegistrasi());
			Integer a2 = Integer.parseInt(b.getNoRegistrasi()); 
			return a1.compareTo(a2);
		});
		
		int iter = 1; 
		for(PesertaBMKG x : list) {
			out2.print(Integer.toString(iter ++) + "|");
			out2.print(x.getNama() + "|");
			out2.print(x.getNoRegistrasi() + "|"); 
			out2.print(x.getJenisFormasi());
			
			out2.println();
		}
		
		
		out1.close(); 
		out2.close();
		System.out.println("FINISH"); 
	
	}
	
	
	public static void main(String[] args) throws Exception {
		new BMKGDaftarUjianLolosVerifikasiBerkas(); 
	}

}
