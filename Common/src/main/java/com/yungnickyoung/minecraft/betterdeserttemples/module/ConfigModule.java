package com.yungnickyoung.minecraft.betterdeserttemples.module;

public class ConfigModule {
    public General general = new General();

    public static class General {
        public int startMinY = 61;
        public int startMaxY = 200;
        public boolean disableVanillaPyramids = true;
        public boolean applyMiningFatigue = true;
    }
}
