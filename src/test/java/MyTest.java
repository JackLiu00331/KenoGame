import static org.junit.jupiter.api.Assertions.*;

import Model.GameDrawings;
import Model.GameMode;
import Model.GameState;
import Service.GameService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

class MyTest {

    private static GameState gameState;
    private static GameService gameService;

    @BeforeAll
    static void setup() {
        gameState = new GameState();
        gameService = new GameService(gameState);
    }

	@Test
    @DisplayName("Test getMaxSelections method")
    void testGetMaxSelections() {
         gameState.setGameMode(GameMode.TEN_SPOT);
         assertEquals(10, gameService.getMaxSelections());
    }

    @Test
    @DisplayName("Test getMaxDrawings method")
    void testGetMaxDrawings() {
        gameState.setGameDrawings(GameDrawings.FOUR_DRAWING);
        assertEquals(4, gameService.getMaxDrawings());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 20, 40, 60, 80})
    @DisplayName("Test selectNumber method with valid inputs")
    void testSelectNumberValid(int number) {
        gameState.setSelectedNumbers(new ArrayList<>());
        boolean result = gameService.selectNumber(number);
        assertTrue(result);
        assertTrue(gameState.getSelectedNumbers().contains(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 81, -5, 100})
    @DisplayName("Test selectNumber method with invalid inputs")
    void testSelectNumberInvalid(int number) {
        gameState.setSelectedNumbers(new ArrayList<>());
        boolean result = gameService.selectNumber(number);
        assertFalse(result);
        assertFalse(gameState.getSelectedNumbers().contains(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30})
    @DisplayName("Test deselectNumber method")
    void testDeselectNumber(int number) {
        gameState.setSelectedNumbers(new ArrayList<>());
        gameState.getSelectedNumbers().add(number);
        boolean result = gameService.deselectNumber(number);
        assertTrue(result);
        assertFalse(gameState.getSelectedNumbers().contains(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 15, 25})
    @DisplayName("Test deselectNumber method with non-existing number")
    void testDeselectNumberNonExisting(int number) {
        gameState.setSelectedNumbers(new ArrayList<>());
        boolean result = gameService.deselectNumber(number);
        assertFalse(result);
    }

    @Test
    @DisplayName("Test isReadyToPlay method")
    void testIsReadyToPlay() {
        gameState.setGameMode(GameMode.FOUR_SPOT);
        gameState.setGameDrawings(GameDrawings.THREE_DRAWING);
        ArrayList<Integer> selectedNumbers = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            selectedNumbers.add(i);
        }
        gameState.setSelectedNumbers(selectedNumbers);
        assertTrue(gameService.isReadyToPlay());
    }

    @Test
    @DisplayName("Test isReadyToPlay method when not ready")
    void testIsNotReadyToPlay() {
        gameState.setGameMode(null);
        gameState.setGameDrawings(GameDrawings.THREE_DRAWING);
        gameState.setSelectedNumbers(new ArrayList<>());
        assertFalse(gameService.isReadyToPlay());
    }

    @Test
    @DisplayName("Test randomSelectNumbersForUser method")
    void testRandomSelectNumbersForUser() {
        gameState.setGameMode(GameMode.FOUR_SPOT);
        gameService.randomSelectNumbersForUser();
        assertEquals(4, gameState.getSelectedCount());
        for (Integer number : gameState.getSelectedNumbers()) {
            assertTrue(number > 0 && number < 81);
        }
    }

    @Test
    @DisplayName("Test randomSelectNumbersForSystem method")
    void testRandomSelectNumbersForSystem() {
        gameService.randomSelectNumbersForUser();
        List<Integer> radomNumbers = gameService.randomSelectNumbersForSystem();
        assertEquals(20, radomNumbers.size());
        for (Integer number : radomNumbers) {
            assertTrue(number > 0 && number < 81);
        }
    }

    @Test
    @DisplayName("Test getMatchedNumbers method")
    void testGetMatchedNumbers() {
        List<Integer> userNumbers = List.of(1, 2, 3, 4, 5);
        List<Integer> systemNumbers = List.of(4, 5, 6, 7, 8);
        gameState.setSelectedNumbers(new ArrayList<>(userNumbers));
        List<Integer> matchedNumbers = gameService.getMatchedNumbers(systemNumbers);
        assertEquals(2, matchedNumbers.size());
        assertTrue(matchedNumbers.contains(4));
        assertTrue(matchedNumbers.contains(5));
    }

    @Test
    @DisplayName("Test getMatchedNumbers method with no matches")
    void testGetMatchedNumbersNoMatches() {
        List<Integer> userNumbers = List.of(1, 2, 3);
        List<Integer> systemNumbers = List.of(4, 5, 6);
        gameState.setSelectedNumbers(new ArrayList<>(userNumbers));
        List<Integer> matchedNumbers = gameService.getMatchedNumbers(systemNumbers);
        assertEquals(0, matchedNumbers.size());
    }

    @Test
    @DisplayName("Test calculatePrize method")
    void testCalculatePrize() {
        gameState.setGameMode(GameMode.TEN_SPOT);
        assertEquals(5, gameService.calculatePrize(0));
        assertEquals(2, gameService.calculatePrize(5));
        assertEquals(15, gameService.calculatePrize(6));
        assertEquals(40, gameService.calculatePrize(7));
        assertEquals(450, gameService.calculatePrize(8));
        assertEquals(4250, gameService.calculatePrize(9));
        assertEquals(100000, gameService.calculatePrize(10));
    }

    @Test
    @DisplayName("Test calculatePrize method with invalid hits")
    void testCalculatePrizeInvalidHits() {
        gameState.setGameMode(GameMode.TEN_SPOT);
        assertEquals(0, gameService.calculatePrize(11));
    }

    @Test
    @DisplayName("Test isWin method")
    void testIsWin() {
        gameState.setGameMode(GameMode.TEN_SPOT);
        assertTrue(gameService.isWin(10));
        assertTrue(gameService.isWin(9));
    }

    @Test
    @DisplayName("Test isWin method with non-jackpot hits")
    void testIsWinWithNoHit() {
        gameState.setGameMode(GameMode.TEN_SPOT);
        assertFalse(gameService.isWin(4));
    }
}
