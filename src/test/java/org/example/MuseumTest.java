package org.example;

import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.Test;

import java.util.Collections;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class MuseumTest {
    @Test
    public void testAddOne() {
        Museum m = new Museum();
        assertEquals(0, m.getAmount());
        m.addOne();
        m.addOne();
        m.addOne();
        assertEquals(3, m.getAmount());
        m.addOne();
        assertEquals(4, m.getAmount());
        m.subOne();
        assertEquals(3, m.getAmount());
        m.subOne();
        m.subOne();
        m.subOne();
        assertEquals(0, m.getAmount());
        m.subOne();
        m.subOne();
        assertEquals(0, m.getAmount());// кол-во посетителей не может быть меньше 0
    }
    @Test
    public void testEastAndWest() throws InterruptedException {// для использования sleep
        //int delay=0;// если сделать задержку 0, то тест упадет, что значит, что приложение не может работать синхронно, как и должно быть.
        int delay=1;   //
        Museum museum = new Museum();
        East eastThread = new East(museum);
        West westThread = new West(museum);
        Control controlThread = new Control(museum);
        Director directorThread = new Director(museum);

        eastThread.start();
        westThread.start();
        controlThread.start();
        directorThread.start();
        Thread.sleep(delay);// Подождем, чтобы потоки запустились

        synchronized (museum) {
            museum.setCommand(1); // Посетитель входит в музей
            museum.notifyAll();
        }

        Thread.sleep(delay);
        assertEquals(1, museum.getAmount());

        for(int i=0;i<5;i++){
            synchronized (museum) {
                museum.setCommand(1); //вошли 5
                museum.notifyAll();
            }
            Thread.sleep(delay);
        }
        assertEquals(6, museum.getAmount());

        synchronized (museum) {
            museum.setCommand(2);
            museum.notifyAll();
        }
        Thread.sleep(delay);
        assertEquals(5, museum.getAmount());
        for(int i=0;i<5;i++){
            synchronized (museum) {
                museum.setCommand(2);
                museum.notifyAll();
            }
            Thread.sleep(delay);
        }
        assertEquals(0, museum.getAmount());
        synchronized (museum) {
            museum.setCommand(2);
            museum.notifyAll();
        }
        Thread.sleep(delay);// уже 0 человек внутри
        assertEquals(0, museum.getAmount());

    }

    @Test
    public void testDirectorAndController() throws InterruptedException {// для использования sleep
        for(int delay=1;delay<10;delay+=2) {// протестированно с различными задержками


            Museum museum = new Museum();
            East eastThread = new East(museum);
            West westThread = new West(museum);
            Control controlThread = new Control(museum);
            Director directorThread = new Director(museum);

            eastThread.start();
            westThread.start();
            controlThread.start();
            directorThread.start();
            Thread.sleep(delay);// Подождем, чтобы потоки запустились

            synchronized (museum) {
                museum.setCommand(1); // Посетитель входит в музей
                museum.notifyAll();
            }
            Thread.sleep(delay);
            assertEquals(1, museum.getAmount());
            synchronized (museum) {
                museum.setCommand(3); // Закрываем музей
                museum.notifyAll();
            }
            Thread.sleep(delay);
            synchronized (museum) {
                museum.setCommand(1);
                museum.notifyAll();
            }
            Thread.sleep(delay);
            assertEquals(1, museum.getAmount());// никто больше не может войти


            synchronized (museum) {
                museum.setCommand(2);
                museum.notifyAll();
            }
            Thread.sleep(delay);
            assertEquals(0, museum.getAmount());// но может выйти


            synchronized (museum) {
                museum.setCommand(1);
                museum.notifyAll();
            }
            Thread.sleep(delay);
            assertEquals(0, museum.getAmount());// никто больше не может войти

            synchronized (museum) {
                museum.setCommand(4);// открываем музей
                museum.notifyAll();
            }
            Thread.sleep(delay);

            for (int i = 0; i < 3; i++) {
                synchronized (museum) {
                    museum.setCommand(1);// теперь вход возможен
                    museum.notifyAll();
                }
                Thread.sleep(delay);
            }

            assertEquals(3, museum.getAmount());
            for (int i = 0; i < 3; i++) {
                synchronized (museum) {
                    museum.setCommand(2);// и выход тоже
                    museum.notifyAll();
                }
                Thread.sleep(delay);
            }
            assertEquals(0, museum.getAmount());
        }
    }
}
