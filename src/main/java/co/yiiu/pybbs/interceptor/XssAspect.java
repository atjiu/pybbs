package co.yiiu.pybbs.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class XssAspect {

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map && !(args[i] instanceof RedirectAttributes)) {
                args[i] = sanitizeMap((Map<String, String>) args[i]);
            }
        }
        return joinPoint.proceed(args);
    }

    private Map<String, String> sanitizeMap(Map<String, String> map) {
        Map<String, String> sanitized = new HashMap<>();
        map.forEach((k, v) -> sanitized.put(k, Jsoup.clean(v, Whitelist.basic())));
        return sanitized;
    }
}