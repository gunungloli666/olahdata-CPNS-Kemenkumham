package com.fjr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fjr.bean.BeanKemendikbud;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class CekDaerahCPNSKemendikbud {
	
	
	public CekDaerahCPNSKemendikbud() throws Exception{
		File f = new File("E:/birokrasi/lamaran kerja/cpns periode II/hasil seleksi administrasi/kemendikbud/");
//		File f1 = new File("E:/birokrasi/lamaran kerja/CPNS BMKG/pemeriksapaten_lampiran1.pdf"); 
		
		PrintWriter out1 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/cpns periode II/hasil seleksi administrasi/kemendikbud/hasil_1.txt"));
		PrintWriter out2 = new PrintWriter(new FileOutputStream("E:/birokrasi/lamaran kerja/cpns periode II/hasil seleksi administrasi/kemendikbud/hasil_2.txt"));
	
		
		ArrayList<BeanKemendikbud> list1 = new ArrayList<>( ); 
		String rg = "[0-9]{1,4}\\s+([0-9]{16})\\sDKB\\-[0-9]{1}\\-[0-9]{7}\\-[0-9]{2}\\-[0-9]{7}\\s(([A-Z\\`\\'\\s\\.\\,]+\\s)+)(([A-Za-z]{1}[a-z]+\\s\n?)+)"; 
		Pattern p = Pattern.compile(rg);
		if(f.isDirectory()) {
			File[] ff = f.listFiles(); 
			for(File f1 : ff) {
				if( ! f1.isDirectory()) {
					try {
						if( f1.getPath().endsWith(".txt")) {
							System.out.println(f1.getPath());
							continue;
						}
						PdfReader reader = new PdfReader(f1.getPath()); 
						PdfReaderContentParser parser = new PdfReaderContentParser(reader);
						TextExtractionStrategy strategy;
						for(int i = 1; i  <=  reader.getNumberOfPages(); i++) {
							strategy = parser.processContent(i , new SimpleTextExtractionStrategy()); 
				        	String result = strategy.getResultantText(); 
				        	out1.println(result);
				        	Matcher m =  p.matcher(result); 
				        	while( m.find()) {
				        		String ktp = m.group(1); 
				        		String nama = m.group(2); 
				        		String penempatan = m.group(4).replace("\n", "" ); 
				        		
				        		BeanKemendikbud bean = new BeanKemendikbud(); 
				        		bean.setKodeKTP(ktp);
				        		bean.setNama(nama);
				        		bean.setUnitPenempatan(penempatan);
				        		
				        		list1.add(bean); 
				        	}
						}
					}catch(Exception re) {
						
					}
					
				}
			}
		}
		
		
		List<BeanKemendikbud> l1 = list1.stream().filter(m -> m.getKodeKTP().startsWith("72")).collect(Collectors.toList()); 
		
		Collections.sort( l1, (a,b) ->{
			String n1 = a.getUnitPenempatan(); 
			String n2 = b.getUnitPenempatan(); 
			int nn = n1.compareTo(n2);
			if(nn == 0) {
				String nn1 = a.getNama(); 
				String nn2 = b.getNama(); 
				return nn1.compareTo(nn2);
			}
			return nn;
		});
		
		int iter = 1; 
		out2.print("No.|Penempatan     							   |Nam                          |Kode KTP");
		out2.println();
		for(BeanKemendikbud b : l1) {
			out2.print((iter++) + "|");
			out2.print(b.getUnitPenempatan().trim() + "|");
		
			out2.print(b.getNama().trim() + "|"); 
			out2.print(b.getKodeKTP().trim() + "|"); 
		
			
			out2.println();
		}
		
		out1.close();
		out2.close(); 
		
		System.out.println("FINISH") ;
	}
	
	
	public static void main(String[] args) throws Exception {
		new CekDaerahCPNSKemendikbud();
	}

}
