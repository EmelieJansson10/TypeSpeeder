package se.ju23.typespeeder;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRankingRepository extends JpaRepository <PlayerRanking, Integer> {
    PlayerRanking findByUserId(Long userId);
    List<PlayerRanking> findAllByOrderByLevelDescScoreDesc();
}
