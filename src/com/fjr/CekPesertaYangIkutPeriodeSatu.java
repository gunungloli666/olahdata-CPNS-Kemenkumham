package com.fjr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fjr.bean.InnerJoinBean;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

//import org.jooq.lambda.tuple.Tuple;
//import org.jooq.lambda.tuple. ;


/**
 * untuk memeriksa peserta mana saja yang sudah mengikuti seleksi CPNS periode I 
 * di kementerian hukum dan HAM
 * @author warni-pc
 *
 */
public class CekPesertaYangIkutPeriodeSatu {
	
	public CekPesertaYangIkutPeriodeSatu() throws Exception{
		File f = new File("E:/birokrasi/lamaran kerja/CPNS BMKG/pengumuman lolos verifikasi berkas.pdf");
		File f1 = new File("E:/birokrasi/lamaran kerja/CPNS BMKG/pemeriksapaten_lampiran1.pdf"); 
		
		PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/inner_join_1.txt"));
		PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/CPNS BMKG/inner_join_2.txt"));
		
		PdfReader reader = new PdfReader(f.getPath()); 
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		
		ArrayList<InnerJoinBean> list1 = new ArrayList<>( ); 
		
		String rg = "[0-9]{1,4}\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s([0-9]{10})\\s(UMUM|LULUSAN TERBAIK)"; 
		Pattern p = Pattern.compile(rg);
		for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
        	String result = strategy.getResultantText(); 
        	// out1.println(result);
        	Matcher m =  p.matcher(result); 
        	
        	while(m.find()) {
        		String nama = m.group(1); 
        		String noReg = m.group(2);
        	
        		InnerJoinBean bean = new InnerJoinBean(); 
        		bean.setNama(nama);
        		bean.setNoUjian(noReg);
        		
        		list1.add(bean); 
        	}
		}
		
		int iter = 1;
		for(InnerJoinBean x : list1 ) {	
			out1.print(Integer.toString(iter ++ )+ "|") ;
			out1.print(x.getNama() + "|"); 
			out1.print(x.getNoUjian() + ""); 
			out1.println(); 
		}
		
		PdfReader reader1 = new PdfReader(f1.getPath()); 
		PdfReaderContentParser parser1 = new PdfReaderContentParser(reader1);
		TextExtractionStrategy strategy1; 

		String rgex = "[0-9]{1,4}\\s([0-9]{14})\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s[0-9]{1,3}\\s[0-9]{1,3}\\s[0-9]{1,3}\\s([0-9]{1,3})"; 
		Pattern pattern_1  = Pattern.compile(rgex);

		ArrayList<InnerJoinBean> list2 = new ArrayList<>(); 

		
		for(int i = 1; i  <=  reader1.getNumberOfPages(); i++) {
			strategy1 = parser1.processContent(i , new SimpleTextExtractionStrategy()); 
        	String result = strategy1.getResultantText(); 
        	
        	Matcher m =  pattern_1.matcher(result); 
        	while(m.find()) {
        		out2.println(m.group()); 
        		
        		InnerJoinBean b = new InnerJoinBean(); 
        		b.setNama(m.group(2)); 
        		b.setNilai(m.group(3));
        		b.setNoUjian(m.group(1)); 
        		
        		list2.add(b); 
        	}
		}
		out2.println("<--->");
		int mm = 1; 
		for( InnerJoinBean x : list2) {
			out2.print(Integer.toString(mm ++ ) + "|" ); 
			out2.print(x.getNama() + "|"); 
			out2.print(x.getNoUjian() + "|"); 
			out2.print(x.getNilai() + "|"); 
			
			out2.println(); 
		}
		
		out2.println("2<--->2");
		
		
		ArrayList<InnerJoinBean > list3 = new ArrayList<>(); 
		for(InnerJoinBean x1 : list1 ) {
			for(InnerJoinBean x2 : list2) {
				if(x1.getNama().equalsIgnoreCase(x2.getNama())) {
					InnerJoinBean jb = new InnerJoinBean(); 
					jb.setNama(x1.getNama()); 
					jb.setNilai(x2.getNilai()); 
					jb.setNoUjian(x1.getNoUjian()); 
					list3.add(jb);
				}
			}
		}
		
		Collections.sort(list3, (a,b )->{
			Integer nilaia = Integer.parseInt(a.getNilai()); 
			Integer nilaib = Integer.parseInt(b.getNilai()); 
 			return nilaib.compareTo(nilaia); 
		});
		 
			mm = 1; 
			for( InnerJoinBean x : list3) {
				out2.print(Integer.toString(mm ++ ) + "|" ); 
				out2.print(x.getNama() + "|"); 
				out2.print(x.getNoUjian() + "|"); 
				out2.print(x.getNilai() + "|"); 
				
				out2.println(); 
			}
			
		 
		out1.close();
		out2.close();
		
		System.out.println("FINISH");
	}

	
	public static void main(String[] args) throws Exception {
		new CekPesertaYangIkutPeriodeSatu(); 
	}
}
