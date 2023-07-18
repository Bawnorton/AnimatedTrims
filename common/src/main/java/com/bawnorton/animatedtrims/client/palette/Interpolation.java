package com.bawnorton.animatedtrims.client.palette;

import java.awt.*;

public enum Interpolation {
    LINEAR,
    SMOOTH,
    SMOOTHER,
    STEP,
    QUADRATIC,
    CUBIC;

    public static Interpolation fromString(String name) {
        return switch (name.toLowerCase().strip()) {
            case "linear" -> LINEAR;
            case "smooth" -> SMOOTH;
            case "smoother" -> SMOOTHER;
            case "step" -> STEP;
            case "quadratic" -> QUADRATIC;
            case "cubic" -> CUBIC;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public int apply(int colour1, int colour2, float frameProgress) {
        float[] hsb1 = Color.RGBtoHSB((colour1 >> 16) & 0xFF, (colour1 >> 8) & 0xFF, colour1 & 0xFF, null);
        float[] hsb2 = Color.RGBtoHSB((colour2 >> 16) & 0xFF, (colour2 >> 8) & 0xFF, colour2 & 0xFF, null);
        return switch (this) {
            case LINEAR -> linearInterpolate(hsb1, hsb2, frameProgress);
            case SMOOTH -> smoothInterpolate(hsb1, hsb2, frameProgress);
            case SMOOTHER -> smootherInterpolate(hsb1, hsb2, frameProgress);
            case STEP -> stepInterpolate(hsb1, hsb2, frameProgress);
            case QUADRATIC -> quadraticInterpolate(hsb1, hsb2, frameProgress);
            case CUBIC -> cubicInterpolate(hsb1, hsb2, frameProgress);
        };
    }

    private int linearInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        float h = interpolate(hsb1[0], hsb2[0], frameProgress);
        float s = interpolate(hsb1[1], hsb2[1], frameProgress);
        float b = interpolate(hsb1[2], hsb2[2], frameProgress);
        return Color.HSBtoRGB(h, s, b);
    }

    private int smoothInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        float h = interpolateSmooth(hsb1[0], hsb2[0], frameProgress);
        float s = interpolateSmooth(hsb1[1], hsb2[1], frameProgress);
        float b = interpolateSmooth(hsb1[2], hsb2[2], frameProgress);
        return Color.HSBtoRGB(h, s, b);
    }

    private int smootherInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        float h = interpolateSmoother(hsb1[0], hsb2[0], frameProgress);
        float s = interpolateSmoother(hsb1[1], hsb2[1], frameProgress);
        float b = interpolateSmoother(hsb1[2], hsb2[2], frameProgress);
        return Color.HSBtoRGB(h, s, b);
    }

    private int quadraticInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        float h = interpolateQuadratic(hsb1[0], hsb2[0], frameProgress);
        float s = interpolateQuadratic(hsb1[1], hsb2[1], frameProgress);
        float b = interpolateQuadratic(hsb1[2], hsb2[2], frameProgress);
        return Color.HSBtoRGB(h, s, b);
    }

    private int cubicInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        float h = interpolateCubic(hsb1[0], hsb2[0], frameProgress);
        float s = interpolateCubic(hsb1[1], hsb2[1], frameProgress);
        float b = interpolateCubic(hsb1[2], hsb2[2], frameProgress);
        return Color.HSBtoRGB(h, s, b);
    }

    private float interpolate(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    private float interpolateSmooth(float start, float end, float progress) {
        float t = smoothStep(progress);
        return start + (end - start) * t;
    }

    private float smoothStep(float x) {
        return x * x * (3 - 2 * x);
    }

    private float interpolateQuadratic(float start, float end, float progress) {
        return start + (end - start) * progress * progress;
    }

    private float interpolateCubic(float start, float end, float progress) {
        float t = progress * progress;
        float a = end - start;
        return start + a * (3 * t - 2 * t * progress);
    }

    private float interpolateSmoother(float start, float end, float progress) {
        float t = smootherStep(progress);
        return start + (end - start) * t;
    }

    private float smootherStep(float x) {
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    private int stepInterpolate(float[] hsb1, float[] hsb2, float frameProgress) {
        return (frameProgress < 0.5) ? Color.HSBtoRGB(hsb1[0], hsb1[1], hsb1[2]) : Color.HSBtoRGB(hsb2[0], hsb2[1], hsb2[2]);
    }
}
