package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class Occorrenze {

    /**
     * Crea una mappa delle parole presenti nel testo con il numero di occorrenze.
     *
     * @param testoRomanzo Il testo da analizzare.
     * @return Una mappa delle parole con il numero di occorrenze.
     */
    public HashMap<String, Integer> createWordList(String testoRomanzo) {
        HashMap<String, Integer> wordList = new HashMap<>();
        if (testoRomanzo != null && !testoRomanzo.isEmpty()) {
            // Utilizza un'espressione regolare per dividere il testo in parole considerando la punteggiatura
            String[] words = testoRomanzo.split("\\b|\\p{Punct}|\\b");

            for (String word : words) {
                // Rimuovi eventuali spazi vuoti iniziali e finali
                word = word.trim();

                // Converti la parola in minuscolo
                word = word.toLowerCase();

                // Se la parola non è vuota, aggiorna la mappa delle parole
                if (!word.isEmpty()) {
                    if (wordList.containsKey(word)) {
                        wordList.put(word, wordList.get(word) + 1);
                    } else {
                        wordList.put(word, 1);
                    }
                }
            }
        } else {
            wordList = null;
        }

        return wordList;
    }

    /**
     * Ordina le parole in base al numero di occorrenze.
     *
     * @param wordList La mappa delle parole da ordinare.
     * @return Una mappa ordinata per numero di occorrenze.
     */
    public TreeMap<String, Integer> sortByOccurrences(HashMap<String, Integer> wordList) {
        if (wordList == null || wordList.isEmpty()) {
            return null;
        } else {
            return (TreeMap<String, Integer>) sortedByValues(wordList);
        }
    }

    /**
     * Ordina le parole in ordine alfabetico considerando anche numeri e caratteri speciali.
     *
     * @param wordList La mappa delle parole da ordinare.
     * @return Una mappa ordinata in ordine alfabetico considerando numeri e caratteri speciali.
     */
    public TreeMap<String, Integer> sortByAlphabeticalOrder(HashMap<String, Integer> wordList) {
        // Controllo se wordList è null o vuota
        if (wordList == null || wordList.isEmpty()) {
            return null;
        }
        TreeMap<String, Integer> alphabeticalOrder = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                // Tratta le chiavi nulle come stringhe vuote durante la comparazione
                str1 = (str1 == null) ? "" : str1;
                str2 = (str2 == null) ? "" : str2;

                // Gestisci le stringhe vuote
                if (str1.isEmpty()) {
                    return -1; // La stringa vuota viene considerata inferiore a una non vuota
                } else if (str2.isEmpty()) {
                    return 1; // La stringa vuota viene considerata superiore a una non vuota
                }

                // Ordina le lettere in modo alfabetico
                if (Character.isLetter(str1.charAt(0)) && Character.isLetter(str2.charAt(0))) {
                    return str1.compareToIgnoreCase(str2);
                }

                // Lettere prima dei numeri
                if (Character.isLetter(str1.charAt(0))) {
                    return -1;
                } else if (Character.isLetter(str2.charAt(0))) {
                    return 1;
                }

                // Numeri prima dei caratteri speciali
                if (Character.isDigit(str1.charAt(0))) {
                    return -1;
                } else if (Character.isDigit(str2.charAt(0))) {
                    return 1;
                }
                if (!Character.isLetterOrDigit(str1.charAt(0))) {
                    return str2.compareToIgnoreCase(str1);
                } else {
                    return str2.compareToIgnoreCase(str1);
                }
            }
        });

        alphabeticalOrder.putAll(wordList);
        return alphabeticalOrder;
    }



    /**
     * Verifica se una parola esiste nella mappa delle parole.
     *
     * @param wordList  La mappa delle parole.
     * @param inputWord La parola da cercare.
     * @return True se la parola esiste, altrimenti false.
     */
    public boolean checkWordExistence(HashMap<String, Integer> wordList, String inputWord) {
        if (wordList == null || wordList.isEmpty() || inputWord == null || inputWord.isEmpty()) {
            return false;
        }
        return wordList.containsKey(inputWord);
    }


    /**
     * Metodo interno che permette di ordinare una mappa mediante il numero di occorrenze.
     *
     * @param map La mappa da ordinare.
     * @param <K> Tipo della chiave.
     * @param <V> Tipo del valore.
     * @return Una mappa ordinata per il numero di occorrenze.
     */
    private static <K, V extends Comparable<V>> TreeMap<K, V> sortedByValues(final HashMap<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            V value1 = map.get(k1);
            V value2 = map.get(k2);

            //Sostituisce null con 0
            value1 = (value1 == null) ? (V) Integer.valueOf(0) : value1;
            value2 = (value2 == null) ? (V) Integer.valueOf(0) : value2;

            // Ordinamento normale
            int compare = value2.compareTo(value1);

            // Se i valori sono uguali, ordina per chiave in modo decrescente
            if (compare == 0) {
                return k2.toString().compareToIgnoreCase(k1.toString());
            }

            return compare;
        };

        TreeMap<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);

        return sortedByValues;
    }
}
