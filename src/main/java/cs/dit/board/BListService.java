package cs.dit.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BListService implements BoardService {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BoardDao dao = new BoardDao();
		int numberOfRecords = 10;
		int count = dao.recordCount(); //전체 레코드 수를 조회
		
		//전달되는 page 번호를 얻고 확인 : null값이거나 ""(빈문자열)이 아니라는 것을 확인
		String page_ = request.getParameter("p");
		int p = 1;  //페이지 초기 값
		
		//전달된 page 번호가 null이거나 ""빈문자열이 아닐경우 page_값을 int 변환하여 list에 전달
		if(page_ != null && !page_.equals(""))  
			p = Integer.parseInt(page_);
		
		//list(page, numOfRecords)호출 : 현재 페이지번호를 매개변수로 전달
		ArrayList<BoardDto> dtos = dao.list(p, numberOfRecords); 

		//화면에 출력될 첫 페이지 번호 값 계산
		int startNum = p-((p-1)% 5);  
		
		//마지막 출력 페이지 번호를 얻기 위해 10으로 나누고 올림하기
		int lastNum = (int)Math.ceil(count/10.0); 
		
		//requestScope에 저장  -> el에 사용
		request.setAttribute("dtos", dtos);
		request.setAttribute("p", p);  //현재페이지 출력에 사용
		request.setAttribute("startNum", startNum);
		request.setAttribute("lastNum", lastNum);
	}
}
