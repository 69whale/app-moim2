package controller.moim;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.MOIM;
import repository.MOIMS;
import repository.Searchs;


@WebServlet("/moim/search")
public class MoimSearchController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String cate = req.getParameter("cate");
//		List<MOIM> list = Searchs.findCate(cate);
//		req.setAttribute("latest", list);
		
		
		String[] cates =req.getParameterValues("cate");
		// System.out.println(Arrays.toString(cates));
		
		List<MOIM> list =MOIMS.findByCate(cates);
		req.setAttribute("list", list);
		System.out.println(list);
		
		
		req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
	}

}
