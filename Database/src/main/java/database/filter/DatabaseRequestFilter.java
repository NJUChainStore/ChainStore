package database.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "databaseTokenAddFilter")
public class DatabaseRequestFilter extends OncePerRequestFilter {
    private final static String URL_MASTER="/Master";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains(URL_MASTER)){
            String auth = request.getHeader("Authentication");
            if(true){
                //检查令牌
            }else{
                response.sendError(403, "Access Denied");

            }
            System.out.println(request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
