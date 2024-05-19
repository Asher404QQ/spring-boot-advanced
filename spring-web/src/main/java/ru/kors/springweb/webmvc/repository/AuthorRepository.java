package ru.kors.springweb.webmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.springweb.webmvc.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
