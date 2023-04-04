package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/test")
public class TestController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 쿠키?
		// 브라우저에서 관리되는 데이터가 담긴 일종의 파일이라고 보면 된다.
		// 쿠키에는 도메인 값이 있는데 (쿠키 발행처)이 있는데 브라우저는 웹 요청을 보낼 때 
		// 발행처가 같다면 쿠키를 같이 포함시켜 전송하게 되어있다.
		
		// 백엔드쪽에서 클라이언트가 보낸 쿠키들을 확인하는게 가능하다.
		
		Cookie[] cookies = req.getCookies();
		
		if(cookies != null) {
		for(Cookie c : cookies) {
			System.out.println("===>" + c.toString());
		
		   }
		}else {
			System.out.println("not found cookie");
		}
		
		req.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(req, resp);
		
		
		
	}
	
	
	
//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		
//		String message = "1q2w3e4r";
//
//
//		for (int cnt = 1; cnt <= 5; cnt++) {
//			String hashed = BCrypt.hashpw(message, BCrypt.gensalt());	// 
//			System.out.println(hashed);
//			boolean f = message.equals(hashed);
//			System.out.println("string.equals = "+ f);
//			
//			boolean ff  =BCrypt.checkpw(message, hashed);
//			System.out.println("BCrypt.checkpw = "+ ff);			
//		}
		
        
        
		
//		String hashed = BCrypt.hashpw(message, "sdmaksdmak123z");
//		System.out.println(hashed);
//		
//		PrintWriter out = resp.getWriter();
//		out.println(hashed);
		
	
}
