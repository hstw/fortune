package com.borderxlab.fortune.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

import com.borderxlab.fortune.core.Fortune;
import com.borderxlab.fortune.db.FortuneDAO;


// FortunePool is a singleton class
public class FortunePool {
    private final FortuneDAO fortuneDAO;
    private final AtomicInteger counter;
    private final Random rand;

    private ArrayList<Fortune> fortunes;
    private HashMap<Integer, Integer> idLocationMap; // Fortune id to location in list
    private HashMap<String, HashSet<Integer>> contentIdMap; // Fortune content to id set map
    private HashMap<Integer, String> idContentMap; // Fortune id to content map


    public FortunePool(FortuneDAO dao) {
        counter = new AtomicInteger();
        rand = new Random();
        fortunes = new ArrayList<Fortune>();
        idLocationMap = new HashMap<Integer, Integer>();
        contentIdMap = new HashMap<String, HashSet<Integer>>();
        idContentMap = new HashMap<Integer, String>();
        fortuneDAO = dao;

        recoverFromDatabase();
    }

    /**
     * Loads fortunes in DB to memory
     */
    private void recoverFromDatabase() {
        fortuneDAO.createTableIfNotExists();

        List<Fortune> fortunes = fortuneDAO.getFortunes();

        int maxId = 0;
        for (Fortune f : fortunes) {
            maxId = Math.max(maxId, f.getId());
            insertFortuneHelper(f.getId(), f.getContent());
        }

        // Set the counter to max id + 1 to avoid conflict
        counter.set(maxId + 1);
    }

    /**
     * Inserts a fortune to the list and update it to db.
     * 
     * @param fortuneContent content of the fortune to be inserted
     * 
     * @return the inserted fortune object.
     */
    public Fortune insertFortune(String fortuneContent) {
        int fortuneId = counter.getAndIncrement();

        Fortune fortune = insertFortuneHelper(fortuneId, fortuneContent);

        // Create a new thread to update DB
        Thread updateDBThread = new Thread(new Runnable() {
            public void run() {
                fortuneDAO.insertFortune(fortuneId, fortuneContent);
            }
        });
        updateDBThread.start();

        return fortune;
    }

    /**
     * Inserts a fortune to the list.
     * 
     * @return the inserted fortune object.
     */
    private Fortune insertFortuneHelper(int fortuneId, String fortuneContent) {
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

        // Create a new thread to update DB
        Thread updateDBThread = new Thread(new Runnable() {
            public void run() {
                fortuneDAO.deleteFortune(fortuneId);
            }
        });
        updateDBThread.start();
    }


    /**
     * Get the fortune with specified id
     * 
     * @throws NoSuchElementException if the id is not in the list.
     */
    public Fortune getFortune(int fortuneId) throws NoSuchElementException {
        if (!idLocationMap.containsKey(fortuneId)) {
            throw new NoSuchElementException();
        }

        return fortunes.get(idLocationMap.get(fortuneId));
    }

    /**
     * Get a random fortune from the list
     * 
     * @throws IllegalArgumentException if the fortune list is empty.
     */
    public Fortune getRandomFortune() throws IllegalArgumentException {
        if (fortunes.isEmpty())
            throw new IllegalArgumentException();

        return fortunes.get(rand.nextInt(fortunes.size()));
    }

    /** Get the list of fortunes */
    public Fortune[] getAllFortunes() {
        // Zere-length array is faster than an array with actual size (JVM optimization)
        return fortunes.toArray(new Fortune[0]);
    }
}
