package cn.tedu.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
	
	/**
	 * 登錄攔截器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("uid") == null) {
			//為null,沒有uid:沒有登錄
			response.sendRedirect("../web/login.html");
			//攔截
			return false;
		}else {
			//非null,有uid:已經登錄
			return true;
		}
	}
	
}
