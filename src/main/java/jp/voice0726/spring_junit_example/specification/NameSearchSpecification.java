package jp.voice0726.spring_junit_example.specification;

import org.springframework.data.jpa.domain.Specification;

public class NameSearchSpecification<T> {
    public Specification<T> containsInName(String name) {
        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.or(
            criteriaBuilder.like(root.get("givenName"), "%" + name + "%"),
            criteriaBuilder.like(root.get("familyName"), "%" + name + "%")
        );
    }
}
