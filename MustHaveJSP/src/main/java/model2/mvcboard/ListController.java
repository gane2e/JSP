package model2.mvcboard;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.BoardPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("---doGet----");
		
		MVCBoardDAO dao = new MVCBoardDAO();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String searchFiled = request.getParameter("searchFiled");
		String searchWord = request.getParameter("searchWord");
		
		if(searchWord != null) {
			//쿼리스트링으로 전달받은 매개변수 중 검색어가 있다면 map에 저장
			map.put("searchFiled", searchFiled);
			map.put("searchWord", searchWord);
		}
		int totalCount = dao.selectCount(map);
		
		ServletContext application = getServletContext();

		
		/*페이지 처리 start*/
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE")); //10
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK")); //15
		int totalPage = (int)Math.ceil(totalCount / (double)pageSize);   //전체 페이지 수 반올림 23
		
		int pageNum = 1;
		
		String pageTemp = request.getParameter("pageNum"); 
		
		if(pageTemp != null && !pageTemp.equals("")) {
			pageNum = Integer.parseInt(pageTemp);
		}
		
		//각 page 출력될 범위
		int start = (pageNum -1)*pageSize +1; //1
		int end = pageNum* pageSize; //1*10=10
		
		map.put("start", start);
		map.put("end", end);
		/*페이지 처리 end*/
		
		
		//게시판 조건 맞는 전체 데이터 출력
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		
		dao.close();
		
		//뷰에 전달할 매개변수 추가
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "../mvcboard/list.do");
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		//전달할 데이터를 request 영역에 저장 후 List.jsp로 포워드
		request.setAttribute("boardLists", boardLists);
		request.setAttribute("map", map);
		request.getRequestDispatcher("/14MVCBoard/List.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("---doPost----");
		doGet(request, response);
	}

}
