package org.example;

class Museum {
    private int amount;
    private int command;
    private boolean museum_is_open = true;
    private boolean east_is_open = true;

    public void addOne() {
        amount++;
    }

    public void subOne() {
        if(amount>0)
            amount--;
    }

    public int getAmount() {
        return amount;
    }

    public int getCommand() {
        return command;
    }

    public boolean getMuseumIsOpen() {
        return museum_is_open;
    }

    public boolean getEastIsOpen() {
        return east_is_open;
    }

    public void openMuseum() {
        museum_is_open = true;
    }

    public void closeMuseum() {
        museum_is_open = false;
    }

    public void openEast() {
        east_is_open = true;
    }

    public void closeEast() {
        east_is_open = false;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
