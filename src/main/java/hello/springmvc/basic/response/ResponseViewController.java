package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView modelAndView = new ModelAndView("response/hello") // View 이름
                .addObject("data", "hello!");

        return modelAndView;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        //@Controller 이면서 String 을 반환하면 -> View 의 논리적 이름
        //@ResponseBody 또는 @RestController 쓰게 되면 View 로 안가고 HTTP 메시지 바디에 문자열 등록됨
        return "response/hello";
    }

    // 권장하지 않는 방법이지만, (명시성이 떨어짐)
    // Controller의 경로 이름과(RequestMapping 으로 들어온 url) 이랑 반환해야할 view 이름이 똑같으면
    // 관례적으로 생략가능하게 Spring 이 해준다
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
