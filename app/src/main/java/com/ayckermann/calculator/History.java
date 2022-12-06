package com.ayckermann.calculator;

public class History {
    public int id;
    String history;

    public History(int id, String history) {
        this.id = id;
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return history;
    }
}
