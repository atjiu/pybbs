package co.yiiu.pybbs.interceptor;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class XssSanitizingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XssHttpServletRequestWrapper wrappedRequest = new XssHttpServletRequestWrapper(httpServletRequest);
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private static class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

        public XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return sanitize(value);
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) {
                return null;
            }
            String[] encodedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                encodedValues[i] = sanitize(values[i]);
            }
            return encodedValues;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> parameterMap = super.getParameterMap();
            Map<String, String[]> newMap = new HashMap<>();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                String[] encodedValues = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    encodedValues[i] = sanitize(values[i]);
                }
                newMap.put(key, encodedValues);
            }
            return newMap;
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return sanitize(value);
        }

        private String sanitize(String value) {
            if (value == null || value.isEmpty()) {
                return value;
            }
            return Jsoup.clean(value, Whitelist.basic());
        }
    }
}
