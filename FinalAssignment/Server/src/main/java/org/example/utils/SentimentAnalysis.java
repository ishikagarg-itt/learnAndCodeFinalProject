package org.example.utils;

import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.example.Dto.CommentSentiment;

import java.util.*;
import java.util.stream.Collectors;

public class SentimentAnalysis {

    public static List<CommentSentiment> analyzeSentiments(List<String> comments) {
        List<CommentSentiment> sentimentList = new ArrayList<>();

        // Set up Stanford CoreNLP pipeline
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,parse,sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (String comment : comments) {
            Annotation annotation = pipeline.process(comment);
            int sentimentScore = 0;
            int count = 0;

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                sentimentScore += convertSentimentToScore(sentiment);
                count++;
            }

            sentimentList.add(new CommentSentiment(comment, sentimentScore / count));
        }

        // Sort the list by sentiment score
        return sentimentList.stream()
                .sorted(Comparator.comparingInt(CommentSentiment::getSentiment))
                .collect(Collectors.toList());
    }

    private static int convertSentimentToScore(String sentiment) {
        switch (sentiment.toLowerCase()) {
            case "very positive":
                return 2;
            case "positive":
                return 1;
            case "neutral":
                return 0;
            case "negative":
                return -1;
            case "very negative":
                return -2;
            default:
                return 0;
        }
    }
}
