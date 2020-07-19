/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davec.imagefxdata;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageReadException;

/**
 *
 * @author dave collins
 */
public class ProcessStep extends Observable implements Runnable {

    private static final Object lock = new Object();
    private static int time = 0;
    private final int step;
    private final String threadName;
    private final ArrayList<String> detail;
    private volatile long startTime;
    private volatile long endTime;
    private volatile long elapsedTime;

    public ProcessStep(int step, String threadName) {
        this.step = step;
        this.threadName = threadName;
        System.out.println("Creating " + step + " threadName " + threadName);
        detail = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {      
        startTime = System.nanoTime();
        System.out.println("Running thread  ... :");
        try {
            synchronized (lock) {
                while (time != step) {
                    lock.wait();
                }
                String[] pathnames;
                File f = new File("Res/images/");
                pathnames = f.list();
                File file;
                for (String pathname : pathnames) {
                    System.out.println(pathname);
                    file = new File("Res/images/" + pathname.trim());
                    try {
                        MetadataExample.metadataExample(file);
                    } catch (ImageReadException ex) {
                        Logger.getLogger(DetailController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ProcessStep.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                time++;
                lock.notifyAll(); // Use notifyAll() instead of notify()
                endTime = System.nanoTime();
                elapsedTime = endTime - startTime;
                System.out.println("Running Finish   :" + elapsedTime + " nano seconds");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt(); // Reset interrupted status
        }
    }
}
