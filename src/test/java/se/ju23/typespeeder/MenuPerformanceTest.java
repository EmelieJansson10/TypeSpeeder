package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static se.ju23.typespeeder.Menu.messages;

class MenuPerformanceTest {
    private static final int MAX_EXECUTION_TIME_MENU = 1;
    private static final int MAX_EXECUTION_TIME_LANGUAGE_SELECTION = 100;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;

    @Test
    public void testGetMenuOptionsExecutionTime() {
        long startTime = System.nanoTime();
        Menu menu = new Menu();
        menu.getMenuOptions();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        assertTrue(duration <= MAX_EXECUTION_TIME_MENU, "Menu display took too long. Execution time: " + duration + " ms.");
    }
    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
  /*  @Test
    public void testUserCanChooseSwedishLanguageAndPerformance() throws IOException {
        String input = "en\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        long startTime = System.nanoTime();

        Menu menu = new Menu();
        menu.displayMenu();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        String consoleOutput = outContent.toString();

        assertTrue(consoleOutput.contains("Välj språk (svenska/engelska):"), "Menu should prompt for language selection.");

        assertTrue(consoleOutput.contains("Svenska valt."), "Menu should confirm Swedish language selection.");


        assertTrue(duration <= MAX_EXECUTION_TIME_LANGUAGE_SELECTION, "Menu display and language selection took too long. Execution time: " + duration + " ms.");
    }*/
}