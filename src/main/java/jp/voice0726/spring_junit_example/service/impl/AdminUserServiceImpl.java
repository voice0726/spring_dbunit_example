package jp.voice0726.spring_junit_example.service.impl;

import jp.voice0726.spring_junit_example.entity.Admin;
import jp.voice0726.spring_junit_example.repository.AdminRepository;
import jp.voice0726.spring_junit_example.user.AdminUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> op = adminRepository.findByUsername(username);
        Admin admin = op.orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new AdminUser(admin.getId(), admin.getName(), admin.getUsername(), admin.getPassword());
        // todo change adminuser to loginuser
    }
}
