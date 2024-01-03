package org.example;

class West extends Thread {// выход
    private Museum storage;

    public West(Museum storage) {
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
                if (command == 2) {
                    if (storage.getAmount() > 0) {
                        storage.subOne();
                        System.out.println("Сообщение от западного выхода: Один посетитель вышел");
                    } else
                        System.out.println("Сообщение от западного выхода: Происходит что-то странное. В музее никого нет, но кто-то вышел...");
                } else if (command == 6) {
                    System.exit(0);
                }
            }
        }
    }
}
