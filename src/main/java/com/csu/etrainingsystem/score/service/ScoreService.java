package com.csu.etrainingsystem.score.service;

import com.csu.etrainingsystem.score.entity.Score;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public void addScore(Score score) {
        this.scoreRepository.save(score);
    }
    @Transactional
    public  Iterable<Score> getScoreBySid(String sid) {
        Iterable<Score> scores=scoreRepository.findScoreBySid(sid);
        return scores;
    }
    @Transactional
    public  Iterable<Score> getScoreBySidAndPro(String sid,String pro_name) {
        Iterable<Score> scores=scoreRepository.findScoreBySidAndPro_name(sid,pro_name);
        return scores;
    }
    @Transactional
    public Iterable<Score> getAllScore() {
        return this.scoreRepository.findAllScore();
    }

    @Transactional
    public void  updateScore(Score Score) {
        this.scoreRepository.saveAndFlush(Score);
    }

    @Transactional
    public void  deleteScoreBySid(String sid) {
        this.scoreRepository.deleteScoreBySid(sid);
    }
    @Transactional
    public void  deleteScoreBySidAndPro(String sid,String pro_name) {
           this.scoreRepository.deleteScoreBySidAndPro_name(sid,pro_name);
    }
    @Transactional
    public void  deleteScoreByPro(String pro_name) {
        this.scoreRepository.deleteScoreByPro_name(pro_name);
    }

}
