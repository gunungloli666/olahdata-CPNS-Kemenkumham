package com.fjr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fjr.bean.Peserta;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class CariNilaiLebihBesar {
	
	
	
	public CariNilaiLebihBesar() throws IOException {
		// 30102028811 373 74.60 29.84 1 77.95 7101220 1 46.77 76.61
		File f = new File("E:/data-data/pengumuman CPNS 2014/Kemendikbud_3/");
		PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/data-data/pengumuman CPNS 2014/Kemendikbud_3/hasil/msss.txt"));
		PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/data-data/pengumuman CPNS 2014/Kemendikbud_3/hasil/msss1.txt"));

		Pattern p = Pattern.compile("([0-9]{11})\\s([0-9]{3})"); 
		
		ArrayList<Peserta> daftarPeserta = new ArrayList<>(); 
		
		if( f.isDirectory()) {
			System.out.println("is directory") ; 
			File[] contentDir = f.listFiles(); 
			for( File ff : contentDir ) {
				if(! ff.isDirectory() ) {
					try {
						PdfReader reader = new PdfReader(ff.getPath() ); 
						PdfReaderContentParser parser = new PdfReaderContentParser(reader);
						TextExtractionStrategy strategy;
						for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
							strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
				        	String result = strategy.getResultantText(); 
				        	out1.println(result);
				        	Matcher m = p.matcher(result); 
				        	while(m.find()) {
				        		String noUjian = m.group(1); 
				        		String nilai = m.group(2); 
				        		
				        		Peserta peserta = new Peserta(); 
				        		peserta.setNoUjian(noUjian);
				        		peserta.setSkorTKD(nilai);
				        		
				        		daftarPeserta.add(peserta); 
				        	}
						}
					}catch(Exception e) {
						System.out.println("faild"); 
					}
				}
			}
		}
		
      Collections.sort(daftarPeserta ,(a,b)->{
    	  	Integer a1 = Integer.parseInt(a.getSkorTKD()); 
    	  	Integer b1 = Integer.parseInt(b.getSkorTKD()); 
    		return b1.compareTo(a1); 
    	}
     ); 
      
	    daftarPeserta.forEach( (a) -> {
	    	out2.print(a.getNoUjian() + "|"); 
	    	out2.print(a.getSkorTKD()); 
	    	out2.println();
	    });

		out2.close();
		out1.close();
		
		System.out.println("FINISH"); 
	}
	
	
	public static void main(String[] args) throws IOException {
		
		new CariNilaiLebihBesar(); 
	}
	

}
