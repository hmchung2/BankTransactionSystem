package kr.ac.kopo.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class FileClose {
	
	public static void close(InputStream is) {
		
		if(is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void close(OutputStream os) {
		
		if(os != null) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void close(InputStream is , OutputStream os) {
		FileClose.close(is);
		FileClose.close(os);
	}
	
	
	public static void close(InputStream is , InputStream bis ) {
		FileClose.close(bis);
		FileClose.close(is);
	}
	
	public static void close(OutputStream os , OutputStream bos) {
		FileClose.close(bos);
		FileClose.close(os);
	}
	
	public static void close(Reader r){
		if(r !=null) {
			try {
				r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Writer w) {
		if(w != null) {
			try {
				w.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void close(Reader r , Writer w) {
		close(r);
		close(w);
	}
	
}