package com.fjr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fjr.bean.Nama;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class SortirHasilPengumuman {
	

	
	public SortirHasilPengumuman() {
		ArrayList<Nama> daftarnama = new ArrayList<>();
		daftarnama.clear();
		File f = new File("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/pemeriksapaten_lampiran1.pdf"); 
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/hasil_sort.txt"));
			PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/hasil_sort_mentah.txt"));
			PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/hasil_sort_mentah_1.txt"));

			
			PdfReader reader = new PdfReader( f.getPath() ); 
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy;
			
			String rgx1 = "([0-9]{1,4})\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s?(([0-9]{14}))\\s?([A-Za-z]+,[0-9]{2}-[0-9]{2}-[0-9]{4})"
        			+ "\\s([0-9]{2}\\:[0-9]{2}\\s(s\\.d\\.)\\s[0-9]{2}\\:[0-9]{2})\\s([0-9]{1})" ; 
			
			String rg = "[0-9]{1,4}\\s([0-9]{14})\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s[0-9]{1,3}\\s[0-9]{1,3}\\s[0-9]{1,3}\\s([0-9]{1,3})"; 
			Pattern p = Pattern.compile(rg);
			
		
			for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
				
				strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
	        	String result = strategy.getResultantText(); 
	        	out1.println(result);
	        	
	        	Matcher m = p.matcher(result); 
	        	
	        	while(m.find()) {
	        		out2.println(m.group());
	        		
	        		String noUjian = m.group(1);
	        		String nama = m.group(2); 
	        		String nilaiAkhir = m.group(3);
	        		
	        		Nama nn = new Nama(); 
	        		nn.setNama(nama);
	        		nn.setNilai(noUjian);
	        		nn.setNilaiAkhir(nilaiAkhir);
	        		
	        		daftarnama.add(nn); 
	        	}
			}
			
			System.out.println(daftarnama.size()); 
			
			 Collections.sort(daftarnama,(a,b)->{
				 Integer a1 = Integer.parseInt(a.getNilaiAkhir());
				 Integer b1 = Integer.parseInt(b.getNilaiAkhir());
					
				 return b1.compareTo(a1);
				 
		    	}
		     ); 

			 int yy = 0; 
			 for(Nama nama: daftarnama) {
				 out.print((++yy)+ "|");
				 out.print(nama.getNilai() + "|");
				 out.print(nama.getNama() + "|");
				 out.print(nama.getNilaiAkhir());
				 out.println();

			 }
			 			
			out.close();
			out1.close();
			out2.close();
			System.out.println("finish"); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	int getInc(int a) {
		return a++; 
	}
	
	
	
	public static void main(String[] args) {
		new SortirHasilPengumuman();
	}

}
