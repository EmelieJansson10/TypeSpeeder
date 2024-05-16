package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ChallengePerformanceTest {
    private static final int MAX_EXECUTION_TIME = 200;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;

    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
 /*   @Test
    public void testStartChallengePerformance() throws IOException {
        Challenge challenge = new Challenge();
        long startTime = System.nanoTime();
        challenge.startChallenge();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;
        assertTrue(duration <= MAX_EXECUTION_TIME, "Starting a challenge took too long. Execution time: " + duration + " ms.");
    }

        // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
  */
   /* @Test
    public void testLettersToTypePerformance() throws IOException {
        String input = "testest\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Challenge challenge = new Challenge();
        long startTime = System.nanoTime();
        challenge.lettersToType();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;
        assertTrue(duration <= MAX_EXECUTION_TIME, "Selecting letters to type took too long. Execution time: " + duration + " ms.");
    }*/
}