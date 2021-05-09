package com.codesquad.baseball.team14.service;

import com.codesquad.baseball.team14.domain.Player;
import com.codesquad.baseball.team14.domain.Record;
import com.codesquad.baseball.team14.domain.RecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Record getRecordByPlayer(String playerName) {
        return recordRepository.findRecordByPlayer(playerName);
    }

    public LinkedHashMap<String, Record> getRecordByPlayer(List<Player> players) {
        LinkedHashMap<String, Record> recordList = new LinkedHashMap<>();
        for (Player player : players) {
            recordList.put(player.getPlayerName(), recordRepository.findRecordByPlayer(player.getPlayerName()));
        }
        return recordList;
    }

    public void updateRecord(String playerName, int hits, int outs) {
        Record record = recordRepository.findRecordByPlayer(playerName);
        record.update(hits, outs);
        recordRepository.updateRecord(record.getAtBat(), record.getHits(), record.getOuts(), playerName);
    }

}
