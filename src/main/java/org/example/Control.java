package org.example;

class Control extends Thread {// выход
    private Museum storage;

    public Control(Museum storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        boolean flag_is_open = true;
        while (true) {
            synchronized (storage) {
                // Ждем уведомления о наличии новой команды
                try {
                    storage.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean new_flag = storage.getMuseumIsOpen();
                if (new_flag != flag_is_open) {
                    flag_is_open = new_flag;
                    if (flag_is_open) {
                        storage.openEast();
                        System.out.println("Сообщение от контролера: Я открываю восточный вход!");
                    } else {
                        storage.closeEast();
                        System.out.println("Сообщение от контролера: Я закрываю восточный вход!");
                    }
                }
            }
        }
    }
}

