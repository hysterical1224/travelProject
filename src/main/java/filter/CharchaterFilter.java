package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CharchaterFilter", value = "/*")
public class CharchaterFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //父接口转换为子接口
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String method = httpServletRequest.getMethod();
        //GET 请求对于字符编码设置可能不是必需的，因为 GET 请求的参数通常包含在 URL 中，
        // 而不是请求正文中，因此字符编码设置通常不会影响到 URL 参数的解析。
        if (method.equalsIgnoreCase("post")) {
            System.out.println("我过滤了啊。。。。");
            httpServletRequest.setCharacterEncoding("UTF-8");
        }
//        httpServletResponse.setContentType("text/html;charset=utf-8");


        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}
