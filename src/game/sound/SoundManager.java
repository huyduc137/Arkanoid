package game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundManager {
    private static final Map<String, Sound> sounds = new HashMap<>();

    //Hỗ trợ chơi một vài tiếng sử dụng nhiều lặp đi lặp lại cùng một lúc mà ko bị đứt đoạn
    private static final Map<String, List<Clip>> clipPool = new HashMap<>();

    private static float masterVolume = 1f;
    private static float sfxVolume = 1f;
    private static float musicVolume = 1f;
    private static boolean muted = false;

    public SoundManager() {}

    public static void loadAll() {
        load("background_test", "sounds/NiagaraFalls.wav", Sound.Type.MUSIC, 0.5f, 1);
        load("pause", "sounds/pause.wav", Sound.Type.SFX, 0.5f, 2);

        load("brick_destroy", "sounds/brick_destroy.wav", Sound.Type.SFX, 0.5f, 10);
        load("brick_hit", "sounds/brick_hit.wav", Sound.Type.SFX, 0.5f, 6);
        load("brick_hit_unbreakable", "sounds/brick_hit_unbreakable.wav", Sound.Type.SFX, 0.5f, 6);

        load("paddle_shoot", "sounds/paddle_shoot.wav", Sound.Type.SFX, 0.5f, 6);
        load("paddle_bounce", "sounds/paddle_bounce.wav", Sound.Type.SFX, 0.5f, 2);

        load("power_up", "sounds/power_up.wav", Sound.Type.SFX, 1f, 6);

        load("life_lose", "sounds/life_lose.wav", Sound.Type.SFX, 0.5f, 6);

        load("button_click", "sounds/button_click.wav", Sound.Type.SFX, 0.5f, 2);
    }

    public static void load(String id, String filePath, Sound.Type type, float baseVolume, int poolSize) {
        try (InputStream is = SoundManager.class.getResourceAsStream("/" + filePath)) {
            if (is == null) {
                System.err.println("Sound not found: " + filePath);
                return;
            }

            BufferedInputStream bis = new BufferedInputStream(is);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bis);

            Clip baseClip = AudioSystem.getClip();
            baseClip.open(audioIn);

            sounds.put(id, new Sound(baseClip, type, baseVolume));

            // poolSize > 1, tạo nhiều copy để chơi ko chèn nhau
            if (poolSize > 1) {
                List<Clip> pool = new ArrayList<>();

                for (int i = 0; i < poolSize; i++) {
                    try (InputStream pis = SoundManager.class.getResourceAsStream("/" + filePath)) {
                        if (pis == null) {
                            System.err.println("Sound not found creating pool: " + filePath);
                            continue;
                        }

                        BufferedInputStream pbis = new BufferedInputStream(pis);
                        AudioInputStream pin = AudioSystem.getAudioInputStream(pbis);

                        Clip clip = AudioSystem.getClip();
                        clip.open(pin);

                        pool.add(clip);
                    }
                }
                clipPool.put(id, pool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(String id) {
        Sound sound = sounds.get(id);
        if (sound == null || muted) return;

        Clip clip;

        if (sound.getType() == Sound.Type.SFX) {
            clip = getAvailableClip(id);
            if (clip == null) {
                // fallback to the base clip if no pool
                clip = sound.getClip();
            }
        } else {
            clip = sound.getClip();
        }

        clip.setFramePosition(0);

        //Tuỳ chỉnh âm lượng theo sound Type
        float globalVol = masterVolume * (sound.getType() == Sound.Type.MUSIC ? musicVolume : sfxVolume);

        //Tuỳ chỉnh âm lượng theo sound object riêng
        float finalVol = sound.getBaseVolume() * globalVol;

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (Math.log10(Math.max(finalVol, 0.0001)) * 20.0); // dùng decibels
            volume.setValue(gain);
        }

        if (sound.getType() == Sound.Type.MUSIC) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }

    public static void mute(boolean state) {
        muted = state;
    }

    public static void stop(String id) {
        Sound sound = sounds.get(id);
        if (sound != null && sound.getClip().isRunning()) {
            sound.getClip().stop();
        }
    }

    public void setVolume(float master, float sfx, float music) {
        masterVolume = master;
        sfxVolume = sfx;
        musicVolume = music;
    }

    private static Clip getAvailableClip(String id) {
        List<Clip> pool = clipPool.get(id);
        if (pool == null) return null;

        for (Clip clip : pool) {
            if (!clip.isRunning()) return clip;
        }
        return null;
    }
    public static boolean isMuted() {
        return muted;
    }
}

