package hello.springmvc.basic;

import lombok.Data;

@Data   // @Data 에서 ToString 구현되어있다.
public class HelloData {
    private String username;
    private int age;
}
