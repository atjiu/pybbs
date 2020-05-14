//package co.yiiu.pybbs.config.realm;
//
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 * 不使用自定义的filter处理登录逻辑了
 * 换回Controller处理
 * 好处：shiro的错误信息可以捕获到，然后进行一些处理再返回给用户，避免出现一些莫名其妙的问题
 */
//public class MyShiroFilter extends FormAuthenticationFilter {
//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        String code = request.getParameter("code");
//        HttpSession session = ((HttpServletRequest) request).getSession();
//        String captcha = (String) session.getAttribute("_captcha");
//        if (!captcha.equalsIgnoreCase(code)) {
//            session.setAttribute("error", "验证码不正确");
//            ((HttpServletResponse) response).sendRedirect("/adminlogin?error");
//            return false;
//        } else {
//            return super.executeLogin(request, response);
//        }
//    }
//}
