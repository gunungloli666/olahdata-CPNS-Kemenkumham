package com.fjr;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fjr.bean.Daftar;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class SortirPesertaUjianCPNSKemenkumham {
	
	Map<String, Integer> map = new HashMap<>(); 
	
	public SortirPesertaUjianCPNSKemenkumham()  throws Exception{
		map.clear();
		
		map.put("Senin", 1);
		map.put("Selasa", 2);
		map.put("Rabu", 3);
		map.put("Kamis", 4);
		map.put("Jumat", 5);
		map.put("Sabtu", 6); 
		
        PdfReader reader = new PdfReader("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/SULTENG.pdf");
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/msi.txt"));
        TextExtractionStrategy strategy;

        PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/persiapan CPNS kemenhukam/pengumuman/msss.txt"));

        
        ArrayList<Daftar> listName = new ArrayList<>(); 
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
        
//        Collections.sort(listName ,(a,b)->{
//        	
//        		return a.getNama().compareTo(b.getNama()); 
//        	}
//		); 
        
        // sort by  date then session 
        Collections.sort(listName ,(a,b)->{
        	String haria = a.getTanggal().substring(0, a.getTanggal().indexOf(",") );  
        	String harib = b.getTanggal().substring(0, b.getTanggal().indexOf(",") ); 
        	Integer jj = map.get(haria); 
        	Integer kk = map.get(harib);
        	int hasil = jj.compareTo(kk) ;
//        	int hasil = jj.compareTo(harib ); 
        	if(hasil == 0) {
        		Integer a1 = Integer.parseInt(a.getSesi().substring(a.getSesi().length() - 1)) ;
        		Integer b1 = Integer.parseInt(b.getSesi().substring(b.getSesi().length() - 1 )); 
        		int hasil1 = a1.compareTo(b1 );
        		if(hasil1  == 0) {
        			return a.getNama().compareTo(b.getNama()); 
        		}
        		return hasil1 ; 
        	}
    		return hasil;
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
		new SortirPesertaUjianCPNSKemenkumham(); 
	}
}
