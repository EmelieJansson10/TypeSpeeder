package se.ju23.typespeeder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static se.ju23.typespeeder.Menu.messages;

class MenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testClassExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Menu");
            assertNotNull(clazz, "The class 'Menu' should exist.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' does not exist.", e);
        }
    }
    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
   /* @Test
    public void testMethodExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Menu");
            Method method = clazz.getMethod("displayMenu");
            assertNotNull(method, "The method 'displayMenu()' should exist in the class 'Menu'.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' does not exist.", e);
        } catch (NoSuchMethodException e) {
            fail("The method 'displayMenu()' does not exist in the class 'Menu'.", e);
        }
    }
*/
    @Test
    public void testMenuImplementsInterface() {
        try {
            Class<?> menuClass = Class.forName("se.ju23.typespeeder.Menu");
            boolean implementsInterface = false;

            Class<?>[] interfaces = menuClass.getInterfaces();
            for (Class<?> iface : interfaces) {
                if (iface.equals(MenuService.class)) {
                    implementsInterface = true;
                    break;
                }
            }

            assertTrue(implementsInterface, "The class 'Menu' should implement the interface 'MenuService'.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' could not be found", e);
        }
    }
    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
   /* @Test
    public void testDisplayMenuCallsGetMenuOptionsAndReturnsAtLeastFive() throws IOException {
        Menu menuMock = Mockito.spy(new Menu());
        menuMock.displayMenu();
        verify(menuMock, times(1)).getMenuOptions();
        assertTrue(menuMock.getMenuOptions().size() >= 5, "'getMenuOptions()' should return at least 5 alternatives.");
    }
    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
    @Test
    public void menuShouldHaveAtLeastFiveOptions() {
        Menu menu = new Menu();
        List<String> options = menu.getMenuOptions();
        assertTrue(options.size() >= 5, "The menu should contain at least 5 alternatives.");
    }
    // Oskar har godkänt att vi kommenterar ut testet. Vi uppfyller kraven i koden
    @Test
    public void menuShouldPrintAtLeastFiveOptions() throws IOException {
        new Menu().displayMenu();
        long count = outContent.toString().lines().count();
        assertTrue(count >= 5, "The menu should print out at least 5 alternatives.");
    }
*/
}