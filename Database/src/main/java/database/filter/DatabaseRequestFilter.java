package database.filter;

import database.model.GlobalData;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DatabaseRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        GlobalData state = GlobalData.getInstance();
        String header = request.getHeader("Authentication");
        if (!header.equals(state.getMasterToken())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        filterChain.doFilter(request, response);
    }
}
