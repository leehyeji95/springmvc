package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController // 응답값 문자 그대로 HTTP Body 에 넣어줌
public class RequestHeaderController {
    // Spring Annotation 기반의 Controller(RequestMappingHandlerAdaptor 동작) 는 다양한 동적인 파라미터를 받아줄 수 있다.
    // Spring MVC 가 제공하는 기능을 이용해 Header 기본값 꺼내기
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod, Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                            /* MultiValueMap : 하나의 키에 여러 값을 받을 수 있다.
                            * HTTP Header, HTTP 쿼리 파라미터에서 사용
                            * keyA=value1&keyA=value2
                            * */
                          @RequestHeader("host") String host,   // Header 의 필수 값
                          @CookieValue(value="myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";

    }

}
