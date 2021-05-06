package com.codesquad.baseball.team14.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {
    @Query("SELECT record.at_bat, record.hits, record.outs, record.average from baseball.record WHERE record.player_name = :playerName ORDER BY record.id;")
    List<Record> findRecordByPlayer(@Param("playerName") String playerName);

}
