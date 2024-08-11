package org.example.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SentimentAnalysisTest {

    @Test
    public void testAnalyzeSentiments_withMixedSentiments() {
        List<String> comments = Arrays.asList(
                "I love this product! It's amazing!",
                "This is the worst experience I've ever had.",
                "It's okay, not great but not terrible.",
                "Absolutely fantastic! Highly recommended.",
                "I hate this, it's awful."
        );

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertTrue(averageSentimentScore > 0 && averageSentimentScore <= 5);
    }

    @Test
    public void testAnalyzeSentiments_withPositiveSentiments() {
        List<String> comments = Arrays.asList(
                "This is the best thing ever!",
                "Absolutely love it!",
                "Fantastic.",
                "I'm so happy with this.",
                "Very positive experience."
        );

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertEquals(5, averageSentimentScore, 1.0);
    }

    @Test
    public void testAnalyzeSentiments_withNegativeSentiments() {
        List<String> comments = Arrays.asList(
                "I hate this.",
                "Terrible experience.",
                "This is the worst.",
                "Very negative and disappointing.",
                "Awful, wouldn't recommend."
        );

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertEquals(1, averageSentimentScore, 1.0);
    }

    @Test
    public void testAnalyzeSentiments_withNeutralSentiments() {
        List<String> comments = Arrays.asList(
                "It's fine, nothing special.",
                "Just okay.",
                "Neither good nor bad.",
                "Neutral, no strong feelings either way.",
                "Meh, it's alright."
        );

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertEquals(3, averageSentimentScore, 1.0);
    }

    @Test
    public void testAnalyzeSentiments_withEmptyList() {
        List<String> comments = Arrays.asList();

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertEquals(0.0, averageSentimentScore, 1.0);
    }

    @Test
    public void testAnalyzeSentiments_withSingleComment() {
        List<String> comments = Arrays.asList("This is an average product.");

        double averageSentimentScore = SentimentAnalysis.analyzeSentiments(comments);
        assertEquals(3, averageSentimentScore, 1.0);
    }
}
