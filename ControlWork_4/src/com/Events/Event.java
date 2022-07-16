package com.Events;

import com.Cat;
import com.State.Interaction;

public enum Event {
    POISONING {
        final int stepDown = 10;
        @Override
        public void doEvent(Cat cat) {
            cat.setMood(cat.getMood() - stepDown);
            cat.setHealth(cat.getHealth() - stepDown);
            cat.setInteractionState(Interaction.INTERACTED);

            cat.updateAverageState();
            System.out.printf("\nYou fed cat %s, %s years old!%n", cat.getName(), cat.getAge());
            System.out.printf("Cat %s got POISONING while eating!%n" +
                    "Mood: -%s points%n" +
                    "Health: -%s points%n\n", cat.getName(), stepDown, stepDown);
            cat.setName("* " + cat.getName());
        }
    },
    TRAUMA {
        final int stepDown = 10;
        @Override
        public void doEvent(Cat cat) {
            cat.setMood(cat.getMood() - stepDown);
            cat.setHealth(cat.getHealth() - stepDown);
            cat.setInteractionState(Interaction.INTERACTED);

            cat.updateAverageState();
            System.out.printf("\nYou fed cat %s, %s years old!%n", cat.getName(), cat.getAge());
            System.out.printf("Cat %s got BROKEN LEG while playing!%n" +
                    "Mood: -%s points%n" +
                    "Health: -%s points%n\n", cat.getName(), stepDown, stepDown);
            cat.setName("* " + cat.getName());
        }
    };

    public abstract void doEvent(Cat cat);
}
