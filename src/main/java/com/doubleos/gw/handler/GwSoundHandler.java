package com.doubleos.gw.handler;

import com.doubleos.gw.util.Reference;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

public class GwSoundHandler
{



    public static ArrayList<String> m_soundListString = new ArrayList<>();

    public static HashMap<String, SoundEvent> m_hashStringGetSoundEvent = new HashMap<>();
    public static HashMap<String, Sound> m_hashStringGetCustomSoundEvent = new HashMap<>();

    public static SoundEvent ELECHARGE;
    public static SoundEvent HAMMER;
    public static SoundEvent NOTI;
    public static SoundEvent OILCHARGE;
    public static SoundEvent PHONE_BELL;
    public static SoundEvent SPANNER;
    public static SoundEvent SUBWAY;

    public static SoundEvent PHONETOUCH;

    public static SoundEvent BLOODSTICK;
    public static SoundEvent BOOM;
    public static SoundEvent CARDREADER;
    public static SoundEvent DOOR_OPEN;
    public static SoundEvent EAT_OTHER;
    public static SoundEvent DRINK_WATER;
    public static SoundEvent ELE_KIOSK;
    public static SoundEvent HARDTACK;
    public static SoundEvent OIL_KIOSK;
    public static SoundEvent ROBOT_AMBIENT;
    public static SoundEvent ROBOT_CALL;
    public static SoundEvent ROBOT_DEATH;

    public static SoundEvent CALL_DISCONNET;

    public static SoundEvent DAY_SFX;

    public static SoundEvent DOOR;

    public static SoundEvent SUBWAY_UNLOCK;


//jamsil_mainbuilding_inside
    public static void registerSounds()
    {
        BLOODSTICK = registerSound("BLOODSTICK");
        BOOM = registerSound("BOOM");
        CALL_DISCONNET = registerSound("CALL_DISCONNET");
        CARDREADER = registerSound("CARDREADER");
        DAY_SFX = registerSound("DAY_SFX");
        DOOR = registerSound("DOOR");
        DOOR_OPEN = registerSound("DOOR_OPEN");
        DRINK_WATER = registerSound("DRINK_WATER");
        EAT_OTHER = registerSound("EAT_OTHER");
        ELECHARGE = registerSound("ELECHARGE");
        ELE_KIOSK = registerSound("ELE_KIOSK");
        HAMMER = registerSound("HAMMER");
        HARDTACK = registerSound("HARDTACK");
        NOTI = registerSound("NOTI");
        OILCHARGE = registerSound("OILCHARGE");
        OIL_KIOSK = registerSound("OIL_KIOSK");
        PHONETOUCH = registerSound("PHONETOUCH");
        PHONE_BELL = registerSound("PHONE_BELL");
        ROBOT_AMBIENT = registerSound("ROBOT_AMBIENT");
        ROBOT_CALL = registerSound("ROBOT_CALL");
        ROBOT_DEATH = registerSound("ROBOT_DEATH");
        SPANNER = registerSound("SPANNER");
        SUBWAY = registerSound("SUBWAY");
        SUBWAY_UNLOCK = registerSound("SUBWAY_UNLOCK");


    }

    static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent soundEvent = new SoundEvent(location);

        soundEvent.setRegistryName(name);

        m_soundListString.add(name);
        m_hashStringGetSoundEvent.put(name, soundEvent);

        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
        return soundEvent;
    }

}
