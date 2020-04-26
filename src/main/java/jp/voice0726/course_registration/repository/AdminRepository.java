package jp.voice0726.course_registration.repository;

import jp.voice0726.course_registration.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by akinori on 2020/04/23
 *
 * @author akinori
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
