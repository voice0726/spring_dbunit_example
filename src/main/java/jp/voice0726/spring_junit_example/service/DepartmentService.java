package jp.voice0726.spring_junit_example.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by akinori on 2020/04/25
 *
 * @author akinori
 */
@Service
public interface DepartmentService {
    /**
     * すべての学部一覧をIDと名前のMapの形で返します。
     *
     * @return map
     */
    Map<Long, String> findAllMap();
}
