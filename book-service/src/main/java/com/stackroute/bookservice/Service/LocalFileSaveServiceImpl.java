package com.stackroute.bookservice.Service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileSaveServiceImpl implements LocalFileSaveService  {
	@Value("${fileserver.host}")
	public String fileserver;

	public String saveFile(MultipartFile file) {
		try{
	        byte[] bytes = file.getBytes();
	        String fileName = file.getOriginalFilename();
	        String filePath = "/usr/local/UploadedFiles/" + fileName; // Change this to your desired save path
			//String filePath = "C:\\Users\\ama63\\Workspace\\UpdateScholae\\ScholaeUpdated\\book-service\\Test" + fileName; // Change this to your desired save path

	        // Save the file
	        File dest = new File(filePath);
	        file.transferTo(dest);
	        return fileserver+filePath;
		}catch(Exception e) {
			return "Error";
		}

	}
}
