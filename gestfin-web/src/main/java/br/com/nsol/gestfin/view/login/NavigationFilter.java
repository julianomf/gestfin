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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * Classe de controle de login
 * @author 
 */
public class NavigationFilter implements Filter{
	private static final Logger LOG = Logger.getLogger(BaseView.class);

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
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String contextPath = ((HttpServletRequest) request).getContextPath();

		if (session != null && !session.isNew()) {
			LOG.debug("doFilter: tem sessão");

			UserDTO user = (UserDTO) ((HttpServletRequest) request).getSession()
					.getAttribute("LOGGED_BUSINESS_USER");
			
			LOG.debug("doFilter: objeto do usuário >> " + user);
			
			if (user == null) {
				LOG.debug("doFilter: sem usuário");
				httpResponse.sendRedirect(contextPath);
			} else if ("S".equals(user.getIsPasswordReseted())) {
				LOG.debug("doFilter: com usuário que deu reset");
				httpResponse.sendRedirect(contextPath + "/users/trocar-senha.xhtml");
			}
			chain.doFilter(request, response);
		} else {
			LOG.debug("doFilter: não tem sessão");
			
			((HttpServletRequest)request).getSession().invalidate();
			httpResponse.sendRedirect(contextPath);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		// Auto-generated method stub
	}

}
