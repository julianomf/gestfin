package br.com.nsol.gestfin.view.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.nsol.gestfin.dto.UserDTO;

/**
 * Classe de controle de login
 * @author 
 */
public class LoginFilter implements Filter{

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		UserDTO user = (UserDTO) ((HttpServletRequest)request).getSession().getAttribute("LOGGED_BUSINESS_USER");
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String contextPath = ((HttpServletRequest)request).getContextPath();
		if(user == null){
			httpResponse.sendRedirect(contextPath + "/login.xhtml");
		} else if("N".equals(user.getIsPasswordReseted()) && ((HttpServletRequest)request).getRequestURI().contains("trocar-senha")){
			httpResponse.sendRedirect(contextPath + "/pages/home/home.xhtml");
		}
		
		chain.doFilter(request, response);
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		// Auto-generated method stub
	}

}
