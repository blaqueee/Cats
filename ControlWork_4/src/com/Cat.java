package com;

import com.State.Interaction;
import com.Strategy.CatStrategy;
import com.Strategy.MiddleCat;
import com.Strategy.OldCat;
import com.Strategy.YoungCat;

import java.util.Random;

public class Cat {
    private String name;
    private int age;
    private int satiety;
    private int mood;
    private int health;
    private transient int averageState;
    private transient CatStrategy ageStrategy;
    private transient Interaction interactionState;

    public Cat() {
        Random rnd = new Random();
        this.satiety = rnd.nextInt(20, 81);
        this.mood = rnd.nextInt(20, 81);
        this.health = rnd.nextInt(20, 81);
        this.interactionState = Interaction.NOT_INTERACTED;
        updateAverageState();
    }

    public Cat(String name) {
        this();
        this.name = name;
        this.age = new Random().nextInt(1, 19);
        setAgeStrategy();
    }

    public Cat(String name, int age) {
        this();
        this.name = name;
        this.age = age;
        setAgeStrategy();
    }

    public Interaction getInteractionState() {
        return interactionState;
    }

    public void setInteractionState(Interaction interactionState) {
        this.interactionState = interactionState;
    }

    public void setInitialState() {
        this.interactionState = Interaction.NOT_INTERACTED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public int getSatiety() {
        return satiety;
    }

    public int getMood() {
        return mood;
    }

    public int getHealth() {
        return health;
    }

    public int getAverageState() {
        return averageState;
    }

    public CatStrategy getAgeStrategy() {
        return ageStrategy;
    }

    public void setSatiety(int satiety) {
        if (satiety > 100)
            this.satiety = 100;
        else if (satiety < 0)
            this.satiety = 0;
        else
            this.satiety = satiety;
    }

    public void setMood(int mood) {
        if (mood > 100)
            this.mood = 100;
        else if (mood < 0)
            this.mood = 0;
        else
            this.mood = mood;
    }

    public void setNextDayParameters() {
        Random rnd = new Random();

        setSatiety(this.satiety - rnd.nextInt(1, 6)); //вторые числа в скобках не включительны
        setMood(this.mood + rnd.nextInt(-3, 4));
        setHealth(this.health + rnd.nextInt(-3, 4));
        updateAverageState();
    }

    public void updateAverageState() {
        this.averageState = (this.satiety + this.mood + this.health) / 3;
    }

    public void setHealth(int health) {
        if (health > 100)
            this.health = 100;
        else if (health < 0)
            this.health = 0;
        else
            this.health = health;
    }

    public void feed() {
        interactionState.feed(this);
    }

    public void play() {
        interactionState.play(this);
    }

    public void goToVet() {
        interactionState.goToVet(this);
    }

    public void setAgeStrategy() {
        if (this.age >= 1 && this.age <= 5)
            this.ageStrategy = new YoungCat();
        else if (this.age >= 6 && this.age <= 10)
            this.ageStrategy = new MiddleCat();
        else
            this.ageStrategy = new OldCat();
    }
}