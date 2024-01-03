package org.example;

class East extends Thread {// вход
    private Museum storage;

    public East(Museum storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (storage) {  // запрещаем доступ к starage остальным потокам
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int command = storage.getCommand();
                if (command == 1) {
                    if (storage.getEastIsOpen()) {
                        storage.addOne();
                        System.out.println("Сообщение от восточного входа: Один посетитель вошел");
                    } else {
                        System.out.println("Сообщение от восточного входа: Музей закрыт. Вход невозможен");
                    }
                } else if (command == 6) {
                    System.exit(0);
                }
            }
        }
    }
}