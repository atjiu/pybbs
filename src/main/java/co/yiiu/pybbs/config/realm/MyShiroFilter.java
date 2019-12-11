package co.yiiu.pybbs.config.realm;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyShiroFilter extends FormAuthenticationFilter {
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String code = request.getParameter("code");
        HttpSession session = ((HttpServletRequest) request).getSession();
        String captcha = (String) session.getAttribute("_captcha");
        if (!captcha.equalsIgnoreCase(code)) {
            session.setAttribute("error", "验证码不正确");
            ((HttpServletResponse) response).sendRedirect("/adminlogin?error");
            return false;
        } else {
            return super.executeLogin(request, response);
        }
    }
}
