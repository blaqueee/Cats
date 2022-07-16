package com.State;

import com.Cat;
import com.Events.Event;

import java.util.Random;

public enum Interaction {
    INTERACTED {
        @Override
        public void feed(Cat cat) {
            System.out.printf("\nYou have already INTERACTED with cat %s TODAY!%n\n", cat.getName());
        }

        @Override
        public void play(Cat cat) {
            System.out.printf("\nYou have already INTERACTED with cat %s TODAY!%n\n", cat.getName());
        }

        @Override
        public void goToVet(Cat cat) {
            System.out.printf("\nYou have already INTERACTED with cat %s TODAY!%n\n", cat.getName());
        }
    },
    NOT_INTERACTED {
        @Override
        public void feed(Cat cat) {
            if (new Random().nextInt(100) + 1 <= 20) {    // шанс 1 из 5 или 20%
                Event.POISONING.doEvent(cat);
                return;
            }
            cat.getAgeStrategy().feed(cat);
            cat.setInteractionState(INTERACTED);
            System.out.printf("\nYou fed cat %s, %s years old!%n\n", cat.getName(), cat.getAge());
        }

        @Override
        public void play(Cat cat) {
            if (new Random().nextInt(100) + 1 <= 20) {    // шанс 1 из 5 или 20%
                Event.TRAUMA.doEvent(cat);
                return;
            }
            cat.getAgeStrategy().play(cat);
            cat.setInteractionState(INTERACTED);
            System.out.printf("\nYou fed cat %s, %s years old!%n\n", cat.getName(), cat.getAge());
        }

        @Override
        public void goToVet(Cat cat) {
            cat.getAgeStrategy().goToVet(cat);
            cat.setInteractionState(INTERACTED);
            System.out.printf("\nYou went to vet with the cat %s, %s years old!%n\n", cat.getName(), cat.getAge());
        }
    };
    public abstract void feed(Cat cat);
    public abstract void play(Cat cat);
    public abstract void goToVet(Cat cat);
}
