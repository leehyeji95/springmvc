package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 메시지 Body를 통해 직접 데이터가 넘어오는 경우
 * @RequestParam, @ModelAttribute 사용할 수 없다.
 * (HTML Form 형식은 요청 파라미터로 인정)
 * PostMan 사용해서 요청 보내기
 *
 * 요청 파라미터 : GET 에 쿼리파라미터 / HTML Form 방식인 경우에만 사용하는 것
 * 나머지는 직접 데이터를 꺼내야한다. (HttpEntity 사용)
 *
 */
@Slf4j
@Controller
public class RequestBodyStringController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        // Stream 은 항상 byte 코드 -> byte코드를 문자로 받을때 인코딩 지정해줘야한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * Spring 에서 InputStream, Reader, OutputsStream, Writer 를 직접 받을 수 있다.
     * @param inputStream
     * @param responseWriter
     * @throws IOException
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
//       Spring이 제공하는 기능으로 <String> 으로 판단해서 HTTP Body 에 있는 내용을 문자로 변환해주는 HttpMessageConverter 동작한다.
        String messageBody = httpEntity.getBody();
        // Header 정보도 가져올 수 있음
        // View 조회 X
        httpEntity.getHeaders();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v3_1")
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        httpEntity.getUrl();
        log.info("messageBody={}", messageBody);

        // 메시지, 상태코드
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        // 추가로 Header 정보 가져오고 싶으면 @RequestHeader 사용하면 된다.

        // @ResponseBody = 응답결과를 HTTP 메시지 바디에 넣어서 반환해준다 (view 사용하지 않음)
        return "responseBody ok";
    }
}
