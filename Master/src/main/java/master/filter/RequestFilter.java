package master.filter;

import master.global.TableManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "accessTokenValidateFilter")
public class RequestFilter extends OncePerRequestFilter {
    private final static String REGISTER_URL = "/register";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains(REGISTER_URL)) {
            String auth = request.getHeader("Authentication");
            if (TableManager.table.getDatabases().stream().anyMatch(x -> x.getAccessToken().equals(auth))) {
                RequestContextHolder.currentRequestAttributes().setAttribute("Role", 1, RequestAttributes.SCOPE_REQUEST);
            } else {
                response.sendError(403, "Access Denied");
            }
            System.out.println(request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
