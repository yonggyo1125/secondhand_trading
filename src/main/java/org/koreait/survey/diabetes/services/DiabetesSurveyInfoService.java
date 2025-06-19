package org.koreait.survey.diabetes.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.constants.Gender;
import org.koreait.global.search.CommonSearch;
import org.koreait.global.search.ListData;
import org.koreait.survey.diabetes.constants.SmokingHistory;
import org.koreait.survey.diabetes.entities.DiabetesSurvey;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Lazy
@Service
@RequiredArgsConstructor
public class DiabetesSurveyInfoService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 설문지 한개 조회
     *
     * @param seq
     * @return
     */
    public DiabetesSurvey get(Long seq) {

        return null;
    }

    public ListData<DiabetesSurvey> getList(CommonSearch search) {

        return null;
    }

    private DiabetesSurvey mapper(ResultSet rs, int i) throws SQLException {
        DiabetesSurvey item = new DiabetesSurvey();
        item.setSeq(rs.getLong("seq"));
        item.setMemberSeq(rs.getLong("memberSeq"));
        item.setGender(Gender.valueOf(rs.getString("gender")));
        item.setAge(rs.getInt("age"));
        item.setDiabetes(rs.getBoolean("diabetes"));
        item.setBmi(rs.getDouble("bmi"));
        item.setHeight(rs.getDouble("height"));
        item.setWeight(rs.getDouble("weight"));
        item.setHypertension(rs.getBoolean("hypertension"));
        item.setHeartDisease(rs.getBoolean("heartDisease"));
        item.setHbA1c(rs.getDouble("hbA1c"));
        item.setBloodGlucoseLevel(rs.getDouble("bloodGlucoseLevel"));
        item.setSmokingHistory(SmokingHistory.valueOf(rs.getString("smokingHistory")));


    }
}
