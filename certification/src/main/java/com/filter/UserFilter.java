package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.UserDto;
import com.managedBean.UserBean;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter(filterName = "/UserFilter", urlPatterns = { "/*" })
public class UserFilter implements Filter {

	public UserFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserBean userBean = (session != null) ? (UserBean) session.getAttribute("userBean") : null;
		UserDto userDto = (userBean != null) ? userBean.getUser() : null;
		String currentPath = ((HttpServletRequest) request).getRequestURL().toString();

		if (userDto != null) {

			if ((currentPath.contains("login.xhtml") || currentPath.contains("error403.xhtml"))
					&& !allowed(currentPath)) {
				res.sendRedirect("home.xhtml");
			} else if ((!currentPath.contains(userDto.getRole() + "/") && !pageAllowed(currentPath))
					&& !allowed(currentPath)) {
				res.sendError(403);
			} else {
				chain.doFilter(request, response);
			}

		} else

		{
			if (!currentPath.contains("login.xhtml") && !allowed(currentPath))

			{
				res.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	private boolean allowed(String path) {
		return path.contains("javax.faces.resource") || path.contains(".png") || path.contains("resources");
	}

	private boolean pageAllowed(String url) {
		if (url.contains("home.xhtml") || url.contains("profile.xhtml") || url.contains("generalError.xhtml")) {
			return true;
		}
		return false;
	}
}
