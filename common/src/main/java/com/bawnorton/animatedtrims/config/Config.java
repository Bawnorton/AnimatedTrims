package com.bawnorton.animatedtrims.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
    private static Config INSTANCE;

    public static Config getInstance() {
        if (INSTANCE == null) INSTANCE = new Config();
        return INSTANCE;
    }

    public static void update(Config config) {
        INSTANCE = config;
    }

    @Expose
    @SerializedName("wave_intensity_percent")
    public Integer waveIntensity = 50;

    @Expose
    @SerializedName("debug")
    public Boolean debug = false;


    @Override
    public String toString() {
        return "Config{" +
                "debug=" + debug +
                '}';
    }
}
