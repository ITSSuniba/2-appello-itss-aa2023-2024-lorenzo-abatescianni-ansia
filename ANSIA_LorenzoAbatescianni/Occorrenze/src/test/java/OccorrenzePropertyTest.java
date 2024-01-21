import net.jqwik.api.*;
import net.jqwik.api.statistics.Histogram;
import net.jqwik.api.statistics.Statistics;
import net.jqwik.api.statistics.StatisticsReport;
import org.example.Occorrenze;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class OccorrenzePropertyTest {

    private Occorrenze occorrenze = new Occorrenze();

    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void createWordListPrPTest(
            /* We generate a list with 100 numbers, ranging from -1000 to 1000. Range chosen randomly. */
            @ForAll("arbitrarytestoRomanzo") String testoRomanzo
    ) {
        HashMap<String, Integer> result = occorrenze.createWordList(testoRomanzo);
        if (result == null) {
            Statistics.collect("TestoRomanzo is null");
        } else {
            Statistics.collect("TestoRomanzo is not null");
        }
    }

    @Provide
    Arbitrary<String> arbitrarytestoRomanzo() {
        return Arbitraries.strings().alpha().numeric().whitespace().ascii().ofMinLength(0).ofMaxLength(200).injectNull(0.1);
    }

    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void sortByOccurrencesPrPTest(
            /* We generate a list with 100 numbers, ranging from -1000 to 1000. Range chosen randomly. */
            @ForAll("arbitrarywordList") HashMap<String, Integer> wordList
    ) {
        TreeMap<String, Integer> sortedList = occorrenze.sortByOccurrences(wordList);
        if (wordList == null) {
            // If wordList is null, sortedList should also be null
            assertNull(sortedList);
            Statistics.collect("Null maps");
        } else if (wordList.isEmpty()) {
            Statistics.collect("Empty maps");
        }

        if (sortedList != null && !sortedList.isEmpty()) {
            List<Map.Entry<String, Integer>> wordListEntries = wordList.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            List<Map.Entry<String, Integer>> sortedListEntries = sortedList.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            assertEquals(wordListEntries, sortedListEntries);
            boolean noNullsOrDuplicates = true;
            for (Integer value : wordList.values()) {
                if (value == null) {
                    Statistics.collect("Contains null values");
                    noNullsOrDuplicates = false;
                    break;
                } else if (value.equals(wordList.values().iterator().next())) {
                    Statistics.collect("Contains duplicate values");
                    noNullsOrDuplicates = false;
                    break;
                }
            }

            if (noNullsOrDuplicates) {
                Statistics.collect("No nulls or duplicates");
            }
        }
    }

    @Provide
    Arbitrary<HashMap<String, Integer>> arbitrarywordList() {
        return Arbitraries.oneOf(
                // Generate a non-null map
                Arbitraries.integers().between(1, 1000).injectNull(0.1)
                        .filter(size -> size != null)
                        .map(size -> {
                            HashMap<String, Integer> wordList = new HashMap<>();
                            for (int i = 0; i < size; i++) {
                                Integer randomValue = Arbitraries.integers().between(1, 1000).injectNull(0.1).sample();
                                wordList.put("Key" + i, randomValue);
                            }
                            return wordList;
                        }),
                // Generate an empty map
                Arbitraries.just(new HashMap<String, Integer>()),
                // Generate a null map
                Arbitraries.just(null)
        );
    }

    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void sortByAlphabeticalOrderPrPTest(
            /* We generate a list with 100 numbers, ranging from -1000 to 1000. Range chosen randomly. */
            @ForAll("arbitrarywordListAlphabetical") HashMap<String, Integer> wordList
    ) {
        TreeMap<String, Integer> sortedList = occorrenze.sortByAlphabeticalOrder(wordList);

        if (wordList == null) {
            // If wordList is null, sortedList should also be null
            assertNull(sortedList);
            Statistics.collect("Null maps");
        } else if (wordList.isEmpty()) {
            Statistics.collect("Empty maps");
        }
        if (sortedList != null && !sortedList.isEmpty()) {
            // Convert both maps to sorted lists before comparison
            List<Map.Entry<String, Integer>> wordListEntries = wordList.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            List<Map.Entry<String, Integer>> sortedListEntries = sortedList.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            assertEquals(wordListEntries, sortedListEntries);
            boolean notNormal = true;
            for (String key : wordList.keySet()) {
                try {
                    // Attempt to parse the key as a numeric value
                    Double.parseDouble(key);
                } catch (NumberFormatException e) {
                    // Key is not a numeric value, collect statistics or perform other checks
                    Statistics.collect("Contains a numeric key");
                    notNormal = false;
                }
                if (!key.matches("[a-zA-Z0-9]+")) {
                    // Key contains special characters, collect statistics or perform other checks
                    Statistics.collect("Contains a special characters key");
                    notNormal = false;
                }
                if (Pattern.compile("\\p{Punct}").matcher(key).find()) {
                    // Key contains punctuation signs, collect statistics or perform other checks
                    Statistics.collect("Contains a punctuation signs key");
                    notNormal = false;
                }
            }
            if (notNormal) {
                Statistics.collect("Contains letters only");
            }
        }
    }

    @Provide
    Arbitrary<HashMap<String, Integer>> arbitrarywordListAlphabetical() {
        return Arbitraries.oneOf(
                // Generate a non-null map
                Arbitraries.strings()
                        .alpha()
                        .numeric()
                        .whitespace()
                        .ascii()
                        .ofMinLength(0)
                        .ofMaxLength(25)
                        .list()
                        .uniqueElements()
                        .ofSize(25)
                        .map(strings -> {
                            HashMap<String, Integer> wordList = new HashMap<>();
                            for (String str : strings) {
                                // Rimuovi i caratteri speciali e converti le lettere in minuscolo
                                str = str.toLowerCase();
                                // Assegna un valore intero casuale o costante, come necessario
                                wordList.put(str, 42);
                            }
                            return wordList;
                        }),
                // Generate a null map
                Arbitraries.just(null),
                // Generate an empty map
                Arbitraries.just(new HashMap<String, Integer>())
        );
    }


    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void checkWordExistencePrPTest(
            /* We generate a list with 100 numbers, ranging from -1000 to 1000. Range chosen randomly. */
            @ForAll("arbitrarywordListAlphabetical") HashMap<String, Integer> wordList,
            @ForAll("arbitrarytestoRomanzo") String inputWord

    ) {
        if (wordList == null) {
            Statistics.collect("Null wordList");
        } else if (wordList.isEmpty()) {
            Statistics.collect("Empty wordList");
        }
        if (inputWord == null) {
            Statistics.collect("Null inputWord");
        } else if (inputWord.isEmpty()) {
            Statistics.collect("Empty inputWord");
        }
        boolean result = occorrenze.checkWordExistence(wordList, inputWord);
        if (result) {
            Statistics.collect("Key found");
        } else {
            Statistics.collect("Key not found");
        }

    }
}