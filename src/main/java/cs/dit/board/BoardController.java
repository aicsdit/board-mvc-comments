package cs.dit.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 50
)
@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String viewPage =null;
		BoardService service = null;
		
		String uri = request.getRequestURI(); 	//uri :/member-mvc/insert.do
		String com= uri.substring(uri.lastIndexOf("/")+ 1, uri.lastIndexOf(".do")); //command :insert
		
		if(com !=null && com.trim().equals("list")) {
			
			service = new BListService();
			service.execute(request, response);
			viewPage = "/WEB-INF/view/list.jsp";
			
		}else if(com !=null && com.trim().equals("insertForm")) {
			viewPage = "/WEB-INF/view/insertForm.jsp";
			
		}else if(com !=null && com.trim().equals("insert")) {
			service = new BInsertService();
			service.execute(request, response);
			viewPage ="list.do";

		}else if(com !=null && com.trim().equals("updateForm")) {

			service = new BViewService();
			service.execute(request, response);
			viewPage = "/WEB-INF/view/updateForm.jsp";

		}
		else if(com !=null && com.trim().equals("update")) {

			service = new BUpdateService();
			service.execute(request, response);
			viewPage = "list.do";

		}
		else if(com !=null && com.trim().equals("delete")) {

			service = new BDeleteService();
			service.execute(request, response);
			viewPage = "list.do";

		}
		else if(com!=null && com.trim().equals("index")) {
			viewPage = "/WEB-INF/view/index.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
