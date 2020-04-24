package jp.voice0726.spring_junit_example.helper;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
public class TestPasswordEncoder {
    @Test
    void createPassword() {
        BCryptPasswordEncoder en = new BCryptPasswordEncoder();
        String s = en.encode("123456");
        System.out.println(s);

    }
}
