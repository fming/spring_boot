package bmw.lbs.demo.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;


@Service
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private final String flag = "Bearer ";
	
	@Autowired TokenService tokenService;
	
	
	/**
	 * custom filter
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		
		String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith(flag)) {
        	token = requestHeader.substring(flag.length());
        }
        
        if(token != null) {
        	UserDetails user = tokenService.getUserFromToken(token);
        	if(user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
	
	}
	

}
