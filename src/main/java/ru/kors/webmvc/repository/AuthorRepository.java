package ru.kors.webmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.webmvc.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
