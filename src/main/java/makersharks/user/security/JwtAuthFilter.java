package makersharks.user.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import makersharks.user.config.InternalServerException;
import makersharks.user.config.UnauthorizedException;
import makersharks.user.config.UserNotFoundException;
import makersharks.user.services.UserDetailService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		
		//get username from token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				throw new UnauthorizedException(e.getLocalizedMessage());
			} catch (Exception e) {
				throw new InternalServerException(e.getLocalizedMessage());
			}

		}
		
		//if security context is null and username exist.
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			try {
				final UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
				if(this.jwtUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(userToken);
				}else {
					throw new InternalServerException("Token is invalid! Unable to validate user!");
				}
				
			}catch(UsernameNotFoundException e) {
				throw new UserNotFoundException(e.getLocalizedMessage());
			} catch(Exception e) {
				throw new InternalServerException(e.getLocalizedMessage());
			}
		}
		
		filterChain.doFilter(request, response);

	}

}
