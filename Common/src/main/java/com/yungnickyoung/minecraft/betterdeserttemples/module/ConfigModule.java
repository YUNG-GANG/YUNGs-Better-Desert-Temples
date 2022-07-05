package com.yungnickyoung.minecraft.betterdeserttemples.module;

public class ConfigModule {
    public General general = new General();

    public static class General {
        public boolean disableVanillaPyramids = true;
        public boolean applyMiningFatigue = true;
    }
}
