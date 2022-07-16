package com.Strategy;

import com.Cat;

public class MiddleCat implements CatStrategy {
    private static final int stepUp = 5;
    private static final int stepDown = 5;
    @Override
    public void feed(Cat cat) {
        cat.setSatiety(cat.getSatiety() + stepUp);
        cat.setMood(cat.getMood() + stepUp);

        cat.updateAverageState();
    }

    @Override
    public void play(Cat cat) {
        cat.setMood(cat.getMood() + stepUp);
        cat.setHealth(cat.getHealth() + stepUp);
        cat.setSatiety(cat.getSatiety() - stepDown);

        cat.updateAverageState();
    }

    @Override
    public void goToVet(Cat cat) {
        cat.setHealth(cat.getHealth() + stepUp);
        cat.setMood(cat.getMood() - stepDown);
        cat.setSatiety(cat.getSatiety() - stepDown);

        cat.updateAverageState();
    }
}
