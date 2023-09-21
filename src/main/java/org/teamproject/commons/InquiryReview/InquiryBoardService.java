package org.teamproject.commons.InquiryReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamproject.entities.InquiryBoard;
import org.teamproject.repositories.InquiryRepository;

@Service
public class InquiryBoardService {

    @Autowired
    private InquiryRepository inquiryRepository;

    public void write(InquiryBoard board) {
        inquiryRepository.saveAndFlush(board);
    }
}
