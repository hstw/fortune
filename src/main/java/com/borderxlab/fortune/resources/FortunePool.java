package com.borderxlab.fortune.resources;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

// FortunePool is a singleton class
public class FortunePool {
    private static FortunePool single_instance = null;

    private final AtomicInteger counter;
    private final Random rand;

    private ArrayList<Fortune> fortunes;
    // Fortune id to location in list
    private HashMap<Integer, Integer> idLocationMap;
    // Fortune content to id set map
    private HashMap<String, HashSet<Integer>> contentIdMap;
    // Fortune id to content map
    private HashMap<Integer, String> idContentMap;

    private FortunePool() {
        counter = new AtomicInteger();
        rand = new Random();
        fortunes = new ArrayList<Fortune>();
        idLocationMap = new HashMap<Integer, Integer>();
        contentIdMap = new HashMap<String, HashSet<Integer>>();
        idContentMap = new HashMap<Integer, String>();
    }

    // static method to create instance of Singleton class
    public static FortunePool getInstance() {
        if (single_instance == null)
            single_instance = new FortunePool();

        return single_instance;
    }


    /**
     * Inserts a fortune to the list.
     * 
     * @return the inserted fortune object.
     */
    public Fortune insertFortune(String fortuneContent) {
        int fortuneId = counter.getAndIncrement();

        if (!contentIdMap.containsKey(fortuneContent))
            contentIdMap.put(fortuneContent, new HashSet<Integer>());

        contentIdMap.get(fortuneContent).add(fortuneId);
        idContentMap.put(fortuneId, fortuneContent);
        idLocationMap.put(fortuneId, fortunes.size());

        Fortune f = new Fortune(fortuneId, fortuneContent);
        fortunes.add(f);

        return f;
    }

    /**
     * Removes a fortune from the list.
     * 
     * @throws NoSuchElementException if the fortune is not in the list.
     */
    public void removeFortune(int fortuneId) throws NoSuchElementException {
        if (!idContentMap.containsKey(fortuneId)) {
            throw new NoSuchElementException();
        }

        int loc = idLocationMap.get(fortuneId);
        if (loc < fortunes.size() - 1) {
            Fortune lastElem = fortunes.get(fortunes.size() - 1);
            fortunes.set(loc, lastElem); // Copy the last elem to the delete position

            idLocationMap.put(lastElem.getId(), loc); // Update the last elem's location
        }

        fortunes.remove(fortunes.size() - 1); // Remove the last elem

        // Remove the id from contentIdMap and idContentMap
        String content = idContentMap.get(fortuneId);
        contentIdMap.get(content).remove(fortuneId);
        idContentMap.remove(fortuneId);
    }

    /**
     * Get a random fortune from the list *
     * 
     * @throws IllegalArgumentException if the fortune list is empty.
     * 
     */
    public Fortune getRandomFortune() throws IllegalArgumentException {
        if (fortunes.isEmpty())
            throw new IllegalArgumentException();

        return fortunes.get(rand.nextInt(fortunes.size()));
    }

    /** Get the list of fortunes */
    public Fortune[] getAllFortunes() {
        return fortunes.toArray(new Fortune[0]);
    }
}
