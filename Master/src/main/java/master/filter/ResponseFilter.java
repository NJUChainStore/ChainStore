package master.filter;

import master.global.TableManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "masterTokenAddFilter")
public class ResponseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String masterToken = TableManager.table.getDatabases().stream().filter(x -> request.getRequestURI().contains(x.getIp())).findFirst().get().getMasterToken();
        response.addHeader("Authentication", masterToken);
        filterChain.doFilter(request, response);
    }


}