package org.example.utils;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.example.Dto.CommentSentiment;

import java.util.*;
import java.util.stream.Collectors;

public class SentimentAnalysis {

    public static double analyzeSentiments(List<String> comments) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,parse,sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        int totalSentimentScore = 0;
        int count = 0;

        for (String comment : comments) {
            Annotation annotation = pipeline.process(comment);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                totalSentimentScore += convertSentimentToScore(sentiment);
                count++;
            }
        }

        return  count > 0 ? totalSentimentScore / count : 0.0;
    }

    private static int convertSentimentToScore(String sentiment) {
        switch (sentiment.toLowerCase()) {
            case "very positive":
                return 5;
            case "positive":
                return 4;
            case "neutral":
                return 3;
            case "negative":
                return 2;
            case "very negative":
                return 1;
            default:
                return 0;
        }
    }
}
