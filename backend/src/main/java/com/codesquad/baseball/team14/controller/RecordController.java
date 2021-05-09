package com.codesquad.baseball.team14.controller;

import com.codesquad.baseball.team14.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Baseball API"}, description = "Baseball API made by Bat")
@RestController
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PatchMapping("/record/{playerName}")
    @ApiOperation(value = "기록 업데이트", notes = "플레이어의 기록을 업데이트합니다")
    public ResponseEntity<String> orderDish(@ApiParam("플레이어 식별자") @PathVariable("playerName") String playerName,
                                            @RequestParam(value = "hit&out", required = false) String hitOrOut) {
        hitOrOut = hitOrOut.toLowerCase();
        int hits;
        int outs;
        if (hitOrOut.equals("hit")) {
            hits = 1;
            outs = 0;
        } else {
            hits = 0;
            outs = 1;
        }
        recordService.updateRecord(playerName, hits, outs);
        return ResponseEntity.ok().body("업데이트 완료");
    }

}
