package com.lalaalal.yummy.misc;

import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public class PhaseManager {
    protected final LivingEntity entity;
    private final float[] phaseHealthArray;
    private int prevPhase = 1;
    private final BossEvent.BossBarColor[] bossBarColors;
    private final ArrayList<PhaseChangeListener> listeners = new ArrayList<>();

    public PhaseManager(float[] phaseHealthArray, BossEvent.BossBarColor[] bossBarColors, LivingEntity entity) {
        this.entity = entity;
        this.phaseHealthArray = phaseHealthArray;
        this.bossBarColors = bossBarColors;
    }

    public void updatePhaseValueOnly() {
        prevPhase = getCurrentPhase();
    }

    public int getMaxPhase() {
        return phaseHealthArray.length;
    }

    public int getCurrentPhase() {
        float leftHealth = entity.getMaxHealth();
        for (int phase = 0; phase < phaseHealthArray.length; phase++) {
            float phaseMinHealth = (leftHealth -= phaseHealthArray[phase]);
            if (entity.getHealth() > phaseMinHealth)
                return phase + 1;
        }
        return phaseHealthArray.length;
    }

    public float getPhaseMaxHealth(int phase) {
        return phaseHealthArray[phase - 1];
    }

    public float getCurrentPhaseHealth() {
        int phase = getCurrentPhase();
        float health = entity.getHealth();
        for (int i = 0; i < phaseHealthArray.length - phase; i++) {
            int index = phaseHealthArray.length - i - 1;
            health -= phaseHealthArray[index];
        }

        return health;
    }

    public float getActualCurrentPhaseMaxHealth() {
        int phase = getCurrentPhase();
        float health = 0;
        for (int i = 0; i < phaseHealthArray.length - phase + 1; i++) {
            int index = phaseHealthArray.length - i - 1;
            health += phaseHealthArray[index];
        }

        return health;
    }

    private BossEvent.BossBarColor getPhaseColor(int phase) {
        return bossBarColors[phase - 1];
    }

    public boolean isPhaseChanged() {
        return prevPhase != getCurrentPhase();
    }

    public void updateBossProgressBar(BossEvent bossEvent) {
        int phase = getCurrentPhase();
        if (prevPhase != phase) {
            BossEvent.BossBarColor color = getPhaseColor(phase);
            bossEvent.setColor(color);
            for (PhaseChangeListener listener : listeners)
                listener.onPhaseChange(phase);
            prevPhase = phase;
        }
        bossEvent.setProgress(calculateProgress(phase));
    }

    private float calculateProgress(int phase) {
        float maxPhaseHealth = phaseHealthArray[phase - 1];
        float currentPhaseHealth = getCurrentPhaseHealth();

        return currentPhaseHealth / maxPhaseHealth;
    }

    public void addPhaseChangeListener(PhaseChangeListener listener) {
        listeners.add(listener);
    }

    public void addPhaseChangeListener(Runnable runnable, int phase) {
        listeners.add(new SpecificPhaseChangeListener(runnable, phase));
    }

    @FunctionalInterface
    public interface PhaseChangeListener {
        void onPhaseChange(int phase);
    }

    private record SpecificPhaseChangeListener(Runnable runnable, int targetPhase) implements PhaseChangeListener {
        @Override
        public void onPhaseChange(int phase) {
            if (targetPhase == phase)
                runnable.run();
        }
    }
}
