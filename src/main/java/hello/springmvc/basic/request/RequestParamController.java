package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * GET 쿼리 파라미터 (/request-param?username=hello&age=20)
 * POST HTML Form 전송 방식은 형식이 같기 때문에 구분없이 조회 가능하다.
 * (HTTP Body 에 파라미터 정보 들어감)
 * == 요청 파라미터 조회
 */
@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
        // @Controller & String -> ViewResolver 찾게된다.
        // @RestController 로 변경해도 되고, @ResponseBody 넣어줘도 된다. (문자를 HTTP 응답메시지에 반환해준다)

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * String, int, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
     * (다수도 가능, 대신 변수명 같아야한다.)
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 필수값 조회
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        // age 안주면 500 Error -> Int 형에 null 들어가기 때문에 Integer 로 변경해야한다.
        // url 요청할 때, username= 이렇게만 보내면 "" 으로 판단해서 빈문자 들어온걸로 판단 -> ok
        return "ok";
    }

    /**
     * 파라미터가 넘어오지 않으면 defaultValue 로 설정한다.
     * 사실상 required 의미 없어짐
     * 빈문자의 경우에도 defaultValue 로 설정해준다 !!
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * RequestParam 모든 파라미터 Map으로 받기
     * Map, MultiValueMap으로 조회 가능
     * @RequestParam MultiValueMap<String, Object>
     *     ?userIds=id1&userIds=id2
     *
     *     파라미터의 값이 1개가 확실하다면 Map을 사용해도 되지만 그렇지 않다면 MultiValueMap 사용
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-multi-value-map")
    public String requestParamMultiValueMap(@RequestParam MultiValueMap<String, Object> paramMap) {

        paramMap.forEach((key, value) -> log.info("{}={}", key, value));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(/*@RequestParam String username, @RequestParam int age*/ @ModelAttribute HelloData helloData) {
        // 기존방식
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);

        log.info("username={}, age={}, helloData={}", helloData.getUsername(), helloData.getAge(), helloData);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        //@RequestParam 이랑 헷갈릴 수 있음
        // String, int, Integer 같은 단순 타입 = RequestParam 으로 판단
        // 나머지 @ModelAttribute ; 내가만든 클래스 (argument resolver 로 지정해둔 타입(HttpServletResponse 등) 외)
        // 객체의 변수명으로 파라미터가 넘어오면 된다.
        log.info("username={}, age={}, helloData={}", helloData.getUsername(), helloData.getAge(), helloData);
        return "ok";
    }
}
