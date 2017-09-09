package com.fjr;


import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.OutputDeviceAssigned;

import com.itextpdf.text.List;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class Test1 {
	
	class Daftar{
		private String nama;
		private String  noKartu; 
		private String number; 
		private String tanggal; 
		private String jam; 
		private String sesi; 

		public String getSesi() {
			return sesi;
		}
		public void setSesi(String sesi) {
			this.sesi = sesi;
		}
		public String getJam() {
			return jam;
		}
		public void setJam(String jam) {
			this.jam = jam;
		}
		public String getTanggal() {
			return tanggal;
		}
		public void setTanggal(String tanggal) {
			this.tanggal = tanggal;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getNama() {
			return nama;
		}
		public void setNama(String nama) {
			this.nama = nama;
		}
		public String getNoKartu() {
			return noKartu;
		}
		public void setNoKartu(String noKartu) {
			this.noKartu = noKartu;
		}
	
	}
	
	public Test1()  throws Exception{
		
        PdfReader reader = new PdfReader("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/SULTENG.pdf");
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/msi.txt"));
        TextExtractionStrategy strategy;

        PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/msss.txt"));

        
        ArrayList<Daftar > listName = new ArrayList<>(); 
        ArrayList<Daftar> listTemp = new ArrayList<>(); 
        for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
        	strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
        	String result = strategy.getResultantText(); 
        	out1.println(result+ "??"); 
        	String rgx1 = "([0-9]{1,4})\\s([A-Za-z\\`\\'\\s\\.\\,]+)\\s?(([0-9]{14}))\\s?([A-Za-z]+,[0-9]{2}-[0-9]{2}-[0-9]{4})"
        			+ "\\s([0-9]{2}\\:[0-9]{2}\\s(s\\.d\\.)\\s[0-9]{2}\\:[0-9]{2})\\s([0-9]{1})" ; 
        	Pattern p = Pattern.compile(rgx1); 
        	Matcher m = p.matcher(result); 
        	while(m.find()) {
        		String number = m.group(1); 
        		String name = m.group(2); 
        		String noKartu = m.group(4);
        		String tanggal = m.group(5);
        		String jam = m.group(6); 
        		String sesi = m.group(8); 
        		Daftar d = new Daftar(); 
        		d.setNama(name); 
        		d.setNoKartu(noKartu);
        		d.setNumber(number);
        		d.setTanggal(tanggal);
        		d.setJam(jam);
        		d.setSesi(sesi);
        		listName.add(d); 
        		listTemp.add(d);
        	}
        }
        
        Collections.sort(listName ,(a,b)->{
        		return a.getNama().compareTo(b.getNama()); 
        	}
		); 
        
        PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/msss1.txt"));
        
        int prev = 0 ; 
        for(int kk  =0 ; kk < listTemp.size(); kk++) {
        	Daftar p  = listTemp.get(kk ); 
        	int m = Integer.parseInt(p.getNumber()); 
        	String token = ""; 
        	if( m - prev  > 1) {
        		token = "==>"; 
        	}
        	prev = m; 
        	out.print(p.getNumber() + "|");
        	out.print(p.getNama());
        	out.print("|");  
        	out.print(p.getNoKartu() + "|" ); 
        	out.print(p.getTanggal() + "|" + token);
        	out.print(p.getJam()+"|"); 
        	out.print("sesi:"+p.getSesi()); 
        	
        	out.println(); 
        	
        }
        
        listName.forEach(p -> { 
//        		out2.print(p.getNumber() + "|");
	        	out2.print(p.getNama());
	        	out2.print("|"); 
	        	out2.print(p.getNoKartu()  + "|"); 
	        	out2.print(p.getTanggal() + "|");
	        	out2.print(p.getJam()+ "|"); 
	        	out2.print("sesi:"+p.getSesi()); 
	        	
	        	out2.println(); 
	        
        	}
        ); 
        
        out.flush();
        out.close();
        out1.flush();
        out1.close(); 
        out2.flush();
        out2.close(); 
        
        System.out.println("finish"); 
		

	}

	
	public static void main(String[] args)  throws Exception {
		new Test1(); 
	}
}
