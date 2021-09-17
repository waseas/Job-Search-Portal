package com.me.utilservices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.me.pojo.User;


public class DirectoryManager {
	
public String createResumeJobFolder(String FolderName ,User u ) throws IOException {

	File resumeFolder = new File("C:\\JobSearchPortal\\Resume\\" +FolderName + "\\" + u.getUsername());
	resumeFolder.mkdirs();
	return resumeFolder.getAbsolutePath();
	}
	
public String createCoverJobFolder(String FolderName,User u ) throws IOException {
	
	File coverFolder = new File("C:\\JobSearchPortal\\CoverLetters\\" +FolderName + "\\" + u.getUsername());
	coverFolder.mkdirs();
	return coverFolder.getAbsolutePath();
}
	
private String generateFileName(MultipartFile multiPartFile) {
		
		return new Date().getTime()+"-" + multiPartFile.getOriginalFilename().replace(" ","_");
		
		
	}

}
