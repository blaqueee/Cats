package com.Strategy;

import com.Cat;

public interface CatStrategy {
    void feed(Cat cat);
    void play(Cat cat);
    void goToVet(Cat cat);
}
