package cs.dit.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BViewService implements BoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Dao의 selectOne 메소드를 사용하여 하나의 레코드만을 가져와 화면 출력

		request.setCharacterEncoding("utf-8");

		int bcode = Integer.parseInt(request.getParameter("bcode"));
		
		BoardDto dto = new BoardDto();
		BoardDao dao = new BoardDao();
		dto = dao.selectOne(bcode);
		request.setAttribute("dto", dto);

	}

}
