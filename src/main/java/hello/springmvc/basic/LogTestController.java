package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI 에서 String 반환 가능하다.
//@Controller // View 를 반환해야한다.
@Slf4j // lombok이 제공하는 애너테이션
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = "+ name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        // *** 주의 !! ***
        log.trace("trace my log=" + name); // + 연산이 일어나면서 메모리 사용 증가
        // trace 쓰지 않는데 리소스 사용하는 거임 (,) 쓰면 파라미터만 넘기기 때문에 로직 중지되어
        // 끝남. 즉 의미없는 연산 일어나지 않음!!

        return "ok";
    }
}
