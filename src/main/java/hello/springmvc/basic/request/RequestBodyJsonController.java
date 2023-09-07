package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {
 *      "username" : "hello",
 *      "age" : 20
 * }
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        // Type 대로 읽어온다.
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("HelloData={}", helloData);

        response.getWriter().write("ok");

    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("HelloData={}", helloData);

        return "ok";
    }

    /**
     * @RequestBody 객체 파라미터 즉, 직접 만든 객체를 지정할 수 있다.
     * HttpMessageConverter 가 동작함
     * 생략 불가능 (생략하게 되면 ModelAttribute 로 판단해서 String=null, int=0 으로 들어온다)
     * 생략하게 되면 -> String, int, Integer 같은 단순 타입 : @RequestParam
     * @param helloData
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("HelloData={}", helloData);

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> data) {
        HelloData helloData = data.getBody();
        log.info("HelloData={}", helloData);

        return "ok";
    }

    /**
     * JSON 요청 -> HTTP 메시지 컨버터 -> 객체
     * 객체 -> HTTP 메시지 컨버터 -> JSON 응답
     * Request 로 받은 데이터 JSON 으로 변환 후 데이터 HTTP 응답에도 설정된다.
     * (요청 보낼 때 Header Accept: application/json 확인필요)
     * @param helloData
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("HelloData={}", helloData);

        return helloData;
    }
}
