package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.InquiryBoard;

public interface InquiryRepository extends JpaRepository<InquiryBoard, Long> {
}
