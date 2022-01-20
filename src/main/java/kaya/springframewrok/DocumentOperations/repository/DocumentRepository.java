package kaya.springframewrok.DocumentOperations.repository;

import kaya.springframewrok.DocumentOperations.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
