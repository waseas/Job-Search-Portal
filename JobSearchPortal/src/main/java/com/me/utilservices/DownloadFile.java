package com.me.utilservices;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;

public class DownloadFile {
	

	public boolean download(String UserName , String type ,String Url) {
		 String dirName = "C:\\Users\\Abhilash Wase\\Downloads";
	     String extension =  FilenameUtils.getExtension(Url);  
	      System.out.println("Extension for file -- " +Url+ "---" +extension);
		 
	     String fileName = dirName + "\\"  +UserName + "." +type+ "." +extension ;
	        
	        String newUrl = Url.replace("\\", "/");
	        String newUrl1= "file:///" +newUrl;
	        System.out.println("Url after Changes -- " +newUrl1);
	       boolean isFileDownloaded = downloadFile(newUrl1,fileName );
	       return isFileDownloaded;
	     
	 }
	
	public boolean downloadFile(String url, String localname) {

        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
        		FileOutputStream OutputStream = new FileOutputStream(localname)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                OutputStream.write(dataBuffer, 0, bytesRead);
            }
            OutputStream.close();
            OutputStream.flush();
            inputStream.close();
           
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
}
	
	public static boolean checkExtension(String Url) {
		
		String extension =  FilenameUtils.getExtension(Url);
		if(extension.equalsIgnoreCase("pdf") ||extension.equalsIgnoreCase("doc")||extension.equalsIgnoreCase("docx")) {
			return true;
		}
		else {
			return false;
		}
	}


}
