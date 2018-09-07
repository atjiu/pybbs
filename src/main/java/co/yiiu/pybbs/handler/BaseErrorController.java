package co.yiiu.pybbs.handler;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class BaseErrorController extends BasicErrorController {

  public BaseErrorController(ServerProperties serverProperties) {
    super(new DefaultErrorAttributes(), serverProperties.getError());
  }

  @Override
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request,
        isIncludeStackTrace(request, MediaType.ALL));
    HttpStatus status = getStatus(request);

    //输出自定义的Json格式
    Map<String, Object> map = new HashMap<>();
    map.put("code", status.value());
    map.put("description", body.get("message"));

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    return new ResponseEntity<>(map, headers,  HttpStatus.OK);
  }
}