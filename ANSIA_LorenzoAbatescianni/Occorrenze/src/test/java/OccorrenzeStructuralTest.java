import org.example.Occorrenze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OccorrenzeStructuralTest {

    //Verifico che il metodo createWordList restituisca la mappa delle parole con le rispettive occorrenze
    @Test
    @DisplayName("SoloTesto")
    void createWordList_ShouldHandleWordsOnly() {
        Occorrenze occorrenzeAnalyzer = new Occorrenze();
        String testText = "Testo di esempio senza numeri o segni di punteggiatura nel testo";
        HashMap<String, Integer> result = occorrenzeAnalyzer.createWordList(testText);
        assertNotNull(result);
        assertEquals(2, result.get("testo"));
        assertEquals(2, result.get("di"));
        assertEquals(1, result.get("esempio"));
        assertEquals(1, result.get("senza"));
        assertEquals(1, result.get("numeri"));
        assertEquals(1, result.get("o"));
        assertEquals(1, result.get("segni"));
        assertEquals(1, result.get("punteggiatura"));
        assertEquals(1, result.get("nel"));
    }

    // T4
    @Test
    @DisplayName("ConPunteggiatura")
    void createWordList_ShouldHandlePunctuation() {
        Occorrenze occorrenzeAnalyzer = new Occorrenze();
        String testText = "Testo di esempio, con segni di punteggiatura nel testo.";
        HashMap<String, Integer> result = occorrenzeAnalyzer.createWordList(testText);
        assertNotNull(result);
        assertEquals(2, result.get("testo"));
        assertEquals(2, result.get("di"));
        assertEquals(1, result.get("esempio"));
        assertEquals(1, result.get(","));
        assertEquals(1, result.get("con"));
        assertEquals(1, result.get("segni"));
        assertEquals(1, result.get("punteggiatura"));
        assertEquals(1, result.get("nel"));
        assertEquals(1, result.get("."));
    }


    // T5
    @Test
    @DisplayName("ConNumeri")
    void createWordList_ShouldHandleNumbers() {
        Occorrenze occorrenzeAnalyzer = new Occorrenze();
        String testText = "Testo di esempio con numeri come 123 e 456";
        HashMap<String, Integer> result = occorrenzeAnalyzer.createWordList(testText);
        assertNotNull(result);
        assertEquals(1, result.get("testo"));
        assertEquals(1, result.get("di"));
        assertEquals(1, result.get("esempio"));
        assertEquals(1, result.get("con"));
        assertEquals(1, result.get("numeri"));
        assertEquals(1, result.get("come"));
        assertEquals(1, result.get("123"));
        assertEquals(1, result.get("e"));
        assertEquals(1, result.get("456"));
    }

    // T6
    @Test
    @DisplayName("ConPunteggiaturaeNumeri")
    void createWordList_ShouldHandleMixedInput() {
        Occorrenze occorrenzeAnalyzer = new Occorrenze();
        String testText = "Questo e un testo di esempio con segni di punteggiatura e numeri, come 123 e 456!";
        HashMap<String, Integer> result = occorrenzeAnalyzer.createWordList(testText);
        assertNotNull(result);
        assertEquals(1, result.get("questo"));
        assertEquals(3, result.get("e"));
        assertEquals(1, result.get("un"));
        assertEquals(1, result.get("testo"));
        assertEquals(2, result.get("di"));
        assertEquals(1, result.get("esempio"));
        assertEquals(1, result.get("con"));
        assertEquals(1, result.get("segni"));
        assertEquals(1, result.get("punteggiatura"));
        assertEquals(1, result.get("numeri"));
        assertEquals(1, result.get("come"));
        assertEquals(1, result.get("123"));
        assertEquals(1, result.get("456"));
    }

    // -	Verifichiamo che il metodo sortByOccurrences ordini la mappa in base ai criteri stabiliti:
    @Test
    @DisplayName("OrdinaMappaOccorrenze")
    public void testSortByOccurrences() {
        Occorrenze sorter = new Occorrenze();

        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", 3);
        wordList.put("banana", 2);
        wordList.put("orange", 5);
        wordList.put("grape", 1);

        TreeMap<String, Integer> sortedMap = sorter.sortByOccurrences(wordList);
        assertEquals("orange", sortedMap.keySet().toArray()[0]);
        assertEquals("apple", sortedMap.keySet().toArray()[1]);
        assertEquals("banana", sortedMap.keySet().toArray()[2]);
        assertEquals("grape", sortedMap.keySet().toArray()[3]);
    }

    // T7
    @Test
    @DisplayName("OrdinaMappaDuplicati")
    public void testSortByOccurrencesDuplicates() {
        Occorrenze sorter = new Occorrenze();

        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", 3);
        wordList.put("banana", 2);
        wordList.put("orange", 5);
        wordList.put("grape", 3);

        TreeMap<String, Integer> sortedMap = sorter.sortByOccurrences(wordList);
        assertEquals("orange", sortedMap.keySet().toArray()[0]);
        assertEquals("grape", sortedMap.keySet().toArray()[1]);
        assertEquals("apple", sortedMap.keySet().toArray()[2]);
        assertEquals("banana", sortedMap.keySet().toArray()[3]);
    }

    // T8
    @Test
    @DisplayName("OrdinaMappaZero")
    public void testSortByOccurrencesZero() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("banana", 2);
        wordList.put("orange", 1);
        wordList.put("grape", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByOccurrences(wordList);
        assertEquals("grape", sortedMap.keySet().toArray()[0]);
        assertEquals("banana", sortedMap.keySet().toArray()[1]);
        assertEquals("orange", sortedMap.keySet().toArray()[2]);
        assertEquals("apple", sortedMap.keySet().toArray()[3]);
    }

    // T9
    @Test
    @DisplayName("OrdinaMappaEntrambi")
    public void testSortByOccurrencesBoth() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("banana", 2);
        wordList.put("orange", null);
        wordList.put("grape", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByOccurrences(wordList);
        assertEquals("grape", sortedMap.keySet().toArray()[0]);
        assertEquals("banana", sortedMap.keySet().toArray()[1]);
        assertEquals("orange", sortedMap.keySet().toArray()[2]);
        assertEquals("apple", sortedMap.keySet().toArray()[3]);
    }

    //Verifico che il metodo sortByAlphabeticalOrder restituisca la mappa ordinata in base all'ordinamento alfabetico della chiave
    @Test
    @DisplayName("OrdinaMappaAlfabeticoNormale")
    public void testSortByAlphabeticalOrder() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("banana", 2);
        wordList.put("orange", null);
        wordList.put("grape", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByAlphabeticalOrder(wordList);
        assertEquals("apple", sortedMap.keySet().toArray()[0]);
        assertEquals("banana", sortedMap.keySet().toArray()[1]);
        assertEquals("grape", sortedMap.keySet().toArray()[2]);
        assertEquals("orange", sortedMap.keySet().toArray()[3]);
    }

    // T10
    @Test
    @DisplayName("OrdinaMappaAlfabeticoNumeri")
    public void testSortByAlphabeticalOrderWithNumbers() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("321", 2);
        wordList.put("orange", null);
        wordList.put("grape", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByAlphabeticalOrder(wordList);
        assertEquals("apple", sortedMap.keySet().toArray()[0]);
        assertEquals("grape", sortedMap.keySet().toArray()[1]);
        assertEquals("orange", sortedMap.keySet().toArray()[2]);
        assertEquals("321", sortedMap.keySet().toArray()[3]);
    }

    // T11
    @Test
    @DisplayName("OrdinaMappaAlfabeticoCaratteriSpeciali")
    public void testSortByAlphabeticalOrderWithSpecialChar() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("***", 2);
        wordList.put("", 2);
        wordList.put("321", 2);
        wordList.put("312", 2);
        wordList.put("orange", null);
        wordList.put("###", null);
        wordList.put("!!!", 3);
        wordList.put("123", 3);
        wordList.put("$$$", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByAlphabeticalOrder(wordList);
        assertEquals("", sortedMap.keySet().toArray()[0]);
        assertEquals("apple", sortedMap.keySet().toArray()[1]);
        assertEquals("orange", sortedMap.keySet().toArray()[2]);
        assertEquals("312", sortedMap.keySet().toArray()[3]);
        assertEquals("123", sortedMap.keySet().toArray()[4]);
        assertEquals("321", sortedMap.keySet().toArray()[5]);
        assertEquals("***", sortedMap.keySet().toArray()[6]);
        assertEquals("$$$", sortedMap.keySet().toArray()[7]);
        assertEquals("###", sortedMap.keySet().toArray()[8]);
        assertEquals("!!!", sortedMap.keySet().toArray()[9]);



    }

    // T12
    @Test
    @DisplayName("OrdinaMappaAlfabeticoPunteggiatura")
    public void testSortByAlphabeticalOrderWithPunct() {
        // Crea un'istanza della classe che contiene i metodi di ordinamento
        Occorrenze sorter = new Occorrenze();

        // Caso di test con uno dei valori null
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", null);
        wordList.put("???", null);
        wordList.put("321", 2);
        wordList.put("@@@", 3);

        // Chiama il metodo di ordinamento
        TreeMap<String, Integer> sortedMap = sorter.sortByAlphabeticalOrder(wordList);
        assertEquals("apple", sortedMap.keySet().toArray()[0]);
        assertEquals("321", sortedMap.keySet().toArray()[1]);
        assertEquals("@@@", sortedMap.keySet().toArray()[2]);
        assertEquals("???", sortedMap.keySet().toArray()[3]);
    }

    //Verifico che se inserisco una inputword che esiste nella hashmap,
    // restituisce true, altrimenti false
    @Test
    @DisplayName("VerificaEsistenza")
    public void testCheckWordExistence() {
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", 1);
        wordList.put("banana", 2);
        wordList.put("orange", 3);
        // Creare un oggetto della classe contenente il metodo checkWordExistence
        Occorrenze wordExistenceChecker = new Occorrenze();
        boolean result = wordExistenceChecker.checkWordExistence(wordList, "apple");
        assertTrue(result);
        result = wordExistenceChecker.checkWordExistence(wordList, "grape");
        assertFalse(result);
    }

    // T1
    @Test
    @DisplayName("TestoRomanzo nullo o di lunghezza 0")
    public void testNoText() {
        String testoRomanzo = null;
        Occorrenze occorrenze = new Occorrenze();
        HashMap<String, Integer> wordList = occorrenze.createWordList(testoRomanzo);
        assertNull(wordList);
        testoRomanzo = "";
        wordList = occorrenze.createWordList(testoRomanzo);
        assertNull(wordList);
    }

    // T2
    @Test
    @DisplayName("wordList nullo o vuoto")
    public void testNoList() {
        HashMap<String, Integer> wordList = null;
        Occorrenze occorrenze = new Occorrenze();
        TreeMap<String, Integer> sortedList = occorrenze.sortByAlphabeticalOrder(wordList);
        assertNull(sortedList);
        HashMap<String, Integer> Emptymap = new HashMap<>();
        sortedList = occorrenze.sortByAlphabeticalOrder(Emptymap);
        assertNull(sortedList);
    }

    // T3
    @Test
    @DisplayName("map nullo o vuoto")
    public void testNoMap() {
        HashMap<String, Integer> map = null;
        Occorrenze occorrenze = new Occorrenze();
        TreeMap<String, Integer> sortedList = occorrenze.sortByOccurrences(map);
        assertNull(sortedList);
        HashMap<String, Integer> Emptymap = new HashMap<>();
        sortedList = occorrenze.sortByOccurrences(Emptymap);
        assertNull(sortedList);
    }

    // T13
    @Test
    @DisplayName("map nullo o vuoto e stringa nulla/vuota")
    public void testNoMapString() {
        HashMap<String, Integer> map = null;
        String inputWord = null;
        Occorrenze occorrenze = new Occorrenze();
        boolean result = occorrenze.checkWordExistence(map, inputWord);
        assertFalse(result);
    }

    // T14
    @Test
    @DisplayName("map nullo o vuoto e stringa non nulla/vuota")
    public void testNoMapWithString() {
        HashMap<String, Integer> map = null;
        String inputWord = "str";
        Occorrenze occorrenze = new Occorrenze();
        boolean result = occorrenze.checkWordExistence(map, inputWord);
        assertFalse(result);
    }

    // T15
    @Test
    @DisplayName("map non nullo o vuoto e stringa nulla/vuota")
    public void testMapNoString() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        String inputWord = null;
        Occorrenze occorrenze = new Occorrenze();
        boolean result = occorrenze.checkWordExistence(map, inputWord);
        assertFalse(result);
    }

    // T16
    @Test
    @DisplayName("map non nullo o vuoto e stringa non nulla/vuota")
    public void testMapWithString() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 3);
        String inputWord = "apple";
        Occorrenze occorrenze = new Occorrenze();
        boolean result = occorrenze.checkWordExistence(map, inputWord);
        assertTrue(result);
        inputWord = "str";
        result = occorrenze.checkWordExistence(map, inputWord);
        assertFalse(result);
    }

    // T17
    @ParameterizedTest
    @MethodSource("provideTestCasesCheckWordExistence")
    void testCheckWordExistence(HashMap<String, Integer> wordList, String inputWord, boolean expectedResult) {
        Occorrenze occorrenze = new Occorrenze();
        boolean result = occorrenze.checkWordExistence(wordList, inputWord);
        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> provideTestCasesCheckWordExistence() {
        return Stream.of(
                // Existing word
                Arguments.of(new HashMap<String, Integer>() {{
                    put("apple", 2);
                    put("banana", 3);
                    put("cherry", 1);
                }}, "banana", true),

                // Non-existing word
                Arguments.of(new HashMap<String, Integer>() {{
                    put("apple", 2);
                    put("banana", 3);
                    put("cherry", 1);
                }}, "orange", false),

                // Empty wordList
                Arguments.of(new HashMap<String, Integer>(), "grape", false),

                // Null wordList
                Arguments.of(null, "pear", false),

                // Null inputWord
                Arguments.of(new HashMap<String, Integer>() {{
                    put("dog", 5);
                    put("cat", 2);
                    put("fish", 7);
                }}, null, false),

                // Empty inputWord
                Arguments.of(new HashMap<String, Integer>() {{
                    put("dog", 5);
                    put("cat", 2);
                    put("fish", 7);
                }}, "", false),

                // Both start with non-letter, non-digit characters
                Arguments.of(new HashMap<String, Integer>() {{
                    put("@@@", 3);
                    put("###", 2);
                    put("$$$", 1);
                }}, "@@@", true)
        );
    }

}
