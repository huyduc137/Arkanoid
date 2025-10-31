package game.sound;

import javax.sound.sampled.Clip;

public class Sound {
    public enum Type {
        SFX,
        MUSIC
    }

    private final Clip clip;
    private final Type type;
    private final float baseVolume;

    public Sound(Clip clip, Type type, float baseVolume) {
        this.clip = clip;
        this.type = type;
        this.baseVolume = baseVolume;
    }

    public Clip getClip() {
        return clip;
    }

    public Type getType() {
        return type;
    }

    public float getBaseVolume() {
        return baseVolume;
    }
}

