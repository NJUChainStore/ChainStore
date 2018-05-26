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
import java.util.Arrays;

@Component
@WebFilter(urlPatterns = {"/*"}, filterName = "accessTokenValidateFilter")
public class RequestFilter extends OncePerRequestFilter {
    private final static String REGISTER_URL = "/register";
    private final static String SAVEINFO_URL = "/saveInfoAndBroadcast";
    private final static String[] IGNORED_ROUTES = new String[]{"/register", "/saveInfoAndBroadcast", "/findBlockInfo"};


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (Arrays.stream(IGNORED_ROUTES).anyMatch(request.getRequestURI()::contains)) {
            filterChain.doFilter(request, response);
            return;
        }

        String auth = request.getHeader("authentication");
        if (TableManager.table.getDatabases().stream().anyMatch(x -> x.getAccessToken().equals(auth))) {
            RequestContextHolder.currentRequestAttributes().setAttribute("Role", 1, RequestAttributes.SCOPE_REQUEST);
        } else {
            response.sendError(403, "Access Denied");
            return;
        }
        System.out.println(request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
