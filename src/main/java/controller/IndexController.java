package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.MOIM;
import repository.MOIMS;

/*
 * 웰컴 처리하는 컨트롤러
 * 
 */

@WebServlet("/index")
public class IndexController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 Date today = new Date();
		 req.setAttribute("today", today);
		 
		 SqlSessionFactory factory = (SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
		 
		 
		 SqlSession session = factory.openSession();
		 // select sql은 selectOne 또는 selectList를 써라
		 List<MOIM> result = session.selectList("moims.findLatest");
		 System.out.println(result);
		 //List<MOIM> list = MOIMS.findLatest();
		 session.close();
		 
		 req.setAttribute("latest", result);
		 //req.setAttribute("latest", list);
		 req.setAttribute("millis", System.currentTimeMillis());
		 
		 req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
		 
	}

}
