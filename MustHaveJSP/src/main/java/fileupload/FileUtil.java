package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.lang3.ArrayUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class FileUtil {

	//파일 업로드
	public static String uploadFile(HttpServletRequest request, String sDirectory) 
			throws ServletException, IOException {
//		Part part = request.getPart("ofile");
		Part part = request.getPart("attachedFile");
		String partHeader = part.getHeader("content-disposition");
		
		String[] phArr = partHeader.split("filename=");
		String originalFileName = phArr[1].trim().replace("\"", "");
		
		if(!originalFileName.isEmpty()) {
			part.write(sDirectory + File.separator + originalFileName);
		}
		return originalFileName;

	}
	
	//파일명 변경
	public static String renameFile(String sDirectory, String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
		String newFileName = now+ext;
		
		File oldFile = new File(sDirectory + File.separator + fileName);
		File newFile = new File(sDirectory + File.separator + newFileName);
		oldFile.renameTo(newFile);
		
		System.out.println("FileUtil - sDirectory : " + sDirectory);
		
		return newFileName;
	}

	
	//multiple 속성 추가로 2개 이상의 파일 업로드
	public static List<String> multipleFile(HttpServletRequest request, String saveDirectory) throws IOException, ServletException {
		List<String> listFileName = new ArrayList<>();
		Collection<Part> parts = request.getParts();
		
		for(Part part : parts) {
			if(!part.getName().equals("attachedFile"))
				continue;
			
			String partHeader = part.getHeader("content-disposition");
			String[] phArr = partHeader.split("filename=");
			
			System.out.println("FileUtil - phArr[0] : " + phArr[0]);
			System.out.println("FileUtil - phArr[1] : " + phArr[1]);
			System.out.println("saveDirectory : " + saveDirectory);
			
			String originalFileName = phArr[1].trim().replace("\"", "");
			if(!originalFileName.isEmpty()) {
				part.write(saveDirectory + File.separator + originalFileName);
			}
			listFileName.add(originalFileName);
		}
		
		return listFileName;
	}
	
	
	//명시한 파일을 찾아 다운로드합니다.
	public static void download(HttpServletRequest request, HttpServletResponse response, String directory, String sfileName, String ofileName) {
		
		String sDirectory = request.getServletContext().getRealPath(directory);
		System.out.println("download sDirectory : " + sDirectory);
		
		try {
			
			//파일을 찾아 입력 스트림 생성
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			String encodeFileName = URLEncoder.encode(ofileName, "UTF-8").replace("\\+", "%20");
			
			//파일 다운로드용 응답 헤더 설정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + encodeFileName + "\"");
			response.setHeader("Content-Length", "" +  file.length() );
			
			//reponse 내장 객체로부터 새로운 출력 스트림 생성
			OutputStream oStream = response.getOutputStream();
			
			//출력 스트림에 파일 내용 출력
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while( (readBuffer = iStream.read(b)) > 0) {
				oStream.write(b, 0, readBuffer);
			}
			
			//입/출력 스트림 닫음
			iStream.close();
			oStream.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
		}
	}
	
	


}
