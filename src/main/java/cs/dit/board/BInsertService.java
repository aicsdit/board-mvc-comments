package cs.dit.board;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class BInsertService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		String filename =null;
		String dir =null;
		
		//HTTP 요청객체인 HttpServletRequest 객체로부터 Content-Type의 헤더값이 multipart/form-data 인지 확인
		String contentType = request.getContentType();
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			dir = request.getServletContext().getRealPath("/uploadfiles");//실제경로 
		}
		
		File f = new File(dir);//File(String pathname):지정된 경로 문자열을 추상 경로로 변환하여 새 File 객체 생성
		if(!f.exists()) { //생성된 객체가 존재하지 않으면 폴더가 없는 것이므로
			f.mkdir();    //해당 경로에 폴더 생성
		}
		
		//request.getParts() 메서드를 통해 여러 개의 Part를 Collection 에 담아 리턴
		Collection<Part> parts = request.getParts();
		
		for(Part p :parts) {
			//part의 Content-Disposition 헤더가 filename=을 포함하면 파일로 구분
			if(p.getHeader("Content-Disposition").contains("filename=")) {
				if(p.getSize()>0) {
					filename = p.getSubmittedFileName();//업로드한 파일명 리턴
					String filePath = dir + File.separator + filename; //File.separator : \
					
					p.write(filePath); //디스크에 파일 쓰기
					
					p.delete();//임시저장된 파일 데이터를 수동으로 제거, 일반적으로 http 요청이 처리되고 응답을 출력하는 시점에 자동으로 제거됨
				}
			}
		}

		BoardDto dto = new BoardDto(0, subject, content, writer, null, filename); 
		BoardDao dao = new BoardDao();
		
		dao.insert(dto);
	}

}
