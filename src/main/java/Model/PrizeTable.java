package Model;

import java.util.*;

/**
 * PrizeTable class manages the prize structure and odds for different spot games.
 */
public class PrizeTable {
    // Prize table mapping spots to hits and their corresponding prizes
    private static final Map<Integer, Map<Integer, Integer>> PRIZE_TABLE = new HashMap<>();
    // Odds table mapping spots to their odds
    private static final Map<Integer, Double> ODDS_TABLE = new HashMap<>();

    // A static block to initialize the prize and odds tables
    // to avoid redundancy and improve performance
    static {
        Map<Integer, Integer> spot1 = new HashMap<>();
        spot1.put(1, 2);
        PRIZE_TABLE.put(1, spot1);

        Map<Integer, Integer> spot4 = new HashMap<>();
        spot4.put(2, 1);
        spot4.put(3, 5);
        spot4.put(4, 75);
        PRIZE_TABLE.put(4, spot4);

        Map<Integer, Integer> spot8 = new HashMap<>();
        spot8.put(6, 50);
        spot8.put(4, 2);
        spot8.put(5, 12);
        spot8.put(7, 750);
        spot8.put(8, 10000);
        PRIZE_TABLE.put(8, spot8);

        Map<Integer, Integer> spot10 = new HashMap<>();
        spot10.put(0, 5);
        spot10.put(5, 2);
        spot10.put(6, 15);
        spot10.put(7, 40);
        spot10.put(8, 450);
        spot10.put(9, 4250);
        spot10.put(10, 100000);
        PRIZE_TABLE.put(10, spot10);

        ODDS_TABLE.put(1, 4.00);
        ODDS_TABLE.put(4, 3.86);
        ODDS_TABLE.put(8, 9.77);
        ODDS_TABLE.put(10, 9.05);
    }

    /**
     * Get the prize table for a specific number of spots.
     * @param spots - Number of spots
     * @return Map of hits to prizes
     */
    public static Map<Integer, Integer> getPrizeTableForSpots(int spots) {
        return PRIZE_TABLE.getOrDefault(spots, new HashMap<>());
    }

    /**
     * Check if a given spots and hits combination is valid.
     * @param spots - number of spots
     * @param hits - number of hits
     * @return true if valid, false otherwise
     */
    public static boolean isValidPrize(int spots, int hits) {
        if (PRIZE_TABLE.containsKey(spots)) {
            return PRIZE_TABLE.get(spots).containsKey(hits);
        }
        return false;
    }

    /**
     * Get the prize for a specific spots and hits combination.
     * @param spots - number of spots
     * @param hits - number of hits
     * @return prize amount, or 0 if invalid combination
     */
    public static Integer getPrizeForHits(int spots, int hits) {
        if (PRIZE_TABLE.containsKey(spots)) {
            if(PRIZE_TABLE.get(spots).containsKey(hits)) {
                return PRIZE_TABLE.get(spots).get(hits);
            }
        }
        return 0;
    }

    /**
     * Get the odds for a specific number of spots.
     * @param spots - Number of spots
     * @return odds value
     */
    public static double getOdds(int spots) {
        if (ODDS_TABLE.containsKey(spots)) {
            return ODDS_TABLE.get(spots);
        }
        return 0.0;
    }
}
