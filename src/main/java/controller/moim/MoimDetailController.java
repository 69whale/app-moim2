package controller.moim;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Attendance;
import data.MOIM;
import data.Reply;
import data.User;
import repository.Attendances;
import repository.MOIMS;
import repository.Users;


//링크타고 올때는 doGet
@WebServlet("/moim/detail")
public class MoimDetailController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		MOIM moim = MOIMS.findById(id);
		
		if(moim == null) {
			resp.sendRedirect("/moim/search");
			return;
		}
		req.setAttribute("moim", moim);

		User manager = 	Users.findById(moim.getManagerId());
		req.setAttribute("manager", manager);
		
		
		List<Attendance> attendances = Attendances.findByMoimId(id);
		
		for(Attendance a : attendances) {
			User found = Users.findById(a.getUserId());
			a.setUserAvatarURL(found.getAvatarURL());
			a.setUserName(found.getName());
		}
		req.setAttribute("attendances", attendances);
		
		
		
		
		//모임 댓글 가져오기 ===============================
		SqlSessionFactory factory = (SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
	    SqlSession sqlSession = factory.openSession();
		List<Reply> replys =sqlSession.selectList("replys.findByMoimId", id);
		sqlSession.close();
				

	    req.setAttribute("replys", replys);
		
		
		
		
		
		
		//로그인을 안하고 들어오면 터짐
		User logonUser = (User)req.getSession().getAttribute("logonUser");
		if(logonUser == null) {
			
			req.setAttribute("status", -1);  //참가 버튼 못 누르게 막음 //로그인 안한 상태
		
		}else {
			
            int status = Attendances.findUserStatusInMoim(id, logonUser.getId());

//			if(found == null) {
//				req.setAttribute("status", 0);	
//			}else {
//				req.setAttribute("status", found.getStatus());
//			}

            req.setAttribute("status", status);
		
		}
		
//		String logonId = logonUser.getId();
//		for(Attendance a : attendances) {
//			a.getUserId().equals(logonId);
//		}
//		
//		
//		System.out.println(moim);
//		System.out.println(manager);
//		System.out.println(attendances);
//		
//		req.setAttribute("moim", moim);
//		req.setAttribute("manager", manager);
//		req.setAttribute("attendances", attendances);
		
		
		
		
		
		
		
		//뷰로 넘기는 작업은 패스
		
		
		
		
		req.getRequestDispatcher("/WEB-INF/views/moim/detail.jsp").forward(req, resp);
		
	}
}



