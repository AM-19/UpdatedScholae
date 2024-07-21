package com.stackroute.bookservice.Service;

import org.springframework.web.multipart.MultipartFile;

public interface LocalFileSaveService {
	public String saveFile(MultipartFile file);
}
