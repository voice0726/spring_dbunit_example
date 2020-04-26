package jp.voice0726.course_registration.service.impl;

import jp.voice0726.course_registration.entity.Admin;
import jp.voice0726.course_registration.repository.AdminRepository;
import jp.voice0726.course_registration.user.LoginUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@SpringJUnitConfig(AdminUserServiceImplTest.Config.class)
class AdminUserServiceImplTest {

    @Configuration
    @ComponentScan(
            basePackages = "jp.voice0726.course_registration",
            useDefaultFilters = false,
            includeFilters = @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = {AdminUserServiceImpl.class})
    )
    static class Config {
    }

    @MockBean
    AdminRepository adminRepository;

    @Autowired
    AdminUserServiceImpl adminUserService;


    @Test
    void loadUserByUsername() {
        long id = 1L;
        String name = "testAdmin";
        String password = "pass";
        String username = "test";
        String role = "ROLE_ADMIN";

        Admin admin = new Admin();
        admin.setId(id);
        admin.setName(name);
        admin.setPassword(password);
        admin.setUsername(username);

        LoginUser expected = new LoginUser(id, name, username, password, role);
        when(adminRepository.findByUsername(any(String.class))).thenReturn(Optional.of(admin));
        LoginUser test = (LoginUser) adminUserService.loadUserByUsername("Test");
        assertThat(test).usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void loadUserByUsernameNotFound() {
        when(adminRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adminUserService.loadUserByUsername("Test"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
