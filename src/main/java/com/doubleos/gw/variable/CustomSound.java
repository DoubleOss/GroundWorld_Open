package com.doubleos.gw.variable;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public class CustomSound implements ISound
{

    private final Sound sound;
    private float volume;
    private float pitch;
    private float x;
    private float y;
    private float z;
    private boolean repeat;
    private int repeatDelay;

    public CustomSound(Sound sound, float volume, float pitch, float x, float y, float z) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.repeat = false;
        this.repeatDelay = 0;
    }
    @Override
    public ResourceLocation getSoundLocation() {
        return sound.getSoundLocation();
    }

    @Nullable
    @Override
    public SoundEventAccessor createAccessor(SoundHandler handler) {
        return null;
    }

    @Override
    public Sound getSound() {
        return null;
    }

    @Override
    public SoundCategory getCategory() {
        return null;
    }

    @Override
    public boolean canRepeat() {
        return repeat;
    }

    @Override
    public int getRepeatDelay() {
        return repeatDelay;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getXPosF() {
        return x;
    }

    @Override
    public float getYPosF() {
        return y;
    }

    @Override
    public float getZPosF() {
        return z;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return AttenuationType.NONE;
    }



}
