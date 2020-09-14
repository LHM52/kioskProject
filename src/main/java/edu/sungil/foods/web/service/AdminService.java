package edu.sungil.foods.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import edu.sungil.foods.web.domain.AdminMapper;
import edu.sungil.foods.web.domain.dto.MenuInfo;

@Service
@Transactional
public class AdminService {
	 @Autowired
	 AdminMapper adminMapper;

	private final Path root = Paths.get("src/main/resources/media");
	
	public void addMenu(MenuInfo menuInfo) {
		//1. 파일생성
			String fileNm = String.valueOf(System.currentTimeMillis())
			 + "." + StringUtils.getFilenameExtension(menuInfo.getFileInfo().getOriginalFilename());
			try {
				Files.copy(menuInfo.getFileInfo().getInputStream(), root.resolve(fileNm));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//2. DB데이터저장
			menuInfo.setMenuImgNm(fileNm);
			adminMapper.insertMenuInfo(menuInfo);
	}
	 
}