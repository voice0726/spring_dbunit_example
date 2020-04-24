package jp.voice0726.spring_junit_example.user;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Getter
public class AdminUser extends User {

    private final long id;
    private final String name;

    public AdminUser(long id, String name, String username, String password) {
        super(username, password, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        this.id = id;
        this.name = name;
    }
}

