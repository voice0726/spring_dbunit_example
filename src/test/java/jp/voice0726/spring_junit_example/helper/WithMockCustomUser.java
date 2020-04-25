package jp.voice0726.spring_junit_example.helper;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long id() default 1L;

    String name() default "test";

    String username() default "111111T";

    String password() default "123456";

    String role() default "ROLE_USER";
}
