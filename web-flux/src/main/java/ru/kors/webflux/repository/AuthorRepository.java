package ru.kors.webflux.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.webflux.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
