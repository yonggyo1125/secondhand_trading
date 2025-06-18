package org.koreait.survey.diabetes.services;

import lombok.RequiredArgsConstructor;
import org.koreait.global.configs.PythonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PythonProperties.class)
public class DiabetesSurveyPredictService {

}
