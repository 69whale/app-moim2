package controller.moim;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Reply;
import data.User;

@WebServlet("/moim/add-reply-task")
public class MoimAddReplyTaskController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// post 요청이기 때문에 encoding
				req.setCharacterEncoding("utf-8");

				String ment = req.getParameter("ment");
				String moimId = req.getParameter("moimId");
				
				SqlSessionFactory factory = (SqlSessionFactory) req.getServletContext().getAttribute("sqlSessionFactory");
				SqlSession sqlSession = factory.openSession();
				
				Map<String, Object> parameter = new HashMap<>();
				
				User user=(User)req.getSession().getAttribute("logonUser");
				parameter.put("writer", user.getId());
				parameter.put("ment", ment);
				parameter.put("moimId", moimId);
				int r = sqlSession.insert("replys.create", parameter);
				
				sqlSession.commit();
				sqlSession.close();
				
				
				resp.sendRedirect("/moim/detail?id=" +moimId);
				
				
				/*
				Reply r = new Reply();
				r.setWriter(user.getId());
				r.setMent(ment);
				r.setMoimId(moimId);
				
				sqlSession.insert("replys.createByVo", r);
				*/
	}

}
