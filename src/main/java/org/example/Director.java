package org.example;

class Director extends Thread {// выход
    private Museum storage;

    public Director(Museum storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (storage) {
                // Ждем уведомления о наличии новой команды
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int command = storage.getCommand();
                if (command == 3) {
                    if (storage.getMuseumIsOpen()) {
                        storage.closeMuseum();
                        System.out.println("Сообщение от директора: Я закрываю музей!");
                        storage.notifyAll();// изменилось состояние музея(с открыто на закрыто), об этом должен узнать контроллер
                    } else {
                        System.out.println("Сообщение от директора: Музей уже закрыт!");
                    }
                } else if (command == 4) {
                    if (!storage.getMuseumIsOpen()) {
                        storage.openMuseum();
                        System.out.println("Сообщение от директора: Я открываю музей!");
                        storage.notifyAll();
                    } else {
                        System.out.println("Сообщение от директора: Музей уже открыт!");
                    }
                }
            }
        }
    }
}

