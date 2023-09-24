package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teamproject.entities.InquiryBoard;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryBoard, Long> {
}
