package org.teamproject.models.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.repositories.BoardViewRepository;

/**
 *
 *
 * 조회수 업데이트
 */
@Service
@RequiredArgsConstructor
public class UpdateHitService {
    private BoardViewRepository boardViewRepository;
    public void update(Long id){

    }
}
