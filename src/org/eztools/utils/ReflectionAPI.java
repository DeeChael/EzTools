package org.eztools.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionAPI {

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... arguments) {
        try {
            return clazz.getConstructor(arguments);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... arguments) {
        try {
            return clazz.getDeclaredMethod(name, arguments);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }

    public static Class<?> getArrayClassFromClass(Class<?> classToTransferToArrayClass) {
        return Array.newInstance(classToTransferToArrayClass, 1).getClass();
    }

    public static Class<?> getClassFromArrayClass(Class<?> arrayClassToTransferToClass) {
        return arrayClassToTransferToClass.getComponentType();
    }

    public static Class<?> getClass(String path) {
        try {
            return Class.forName(path);
        } catch (ClassNotFoundException ignored) {
        }
        return Object.class;
    }

    /**
     * Attention: This method is not supported by 1.17+ because spigot 1.17+ removed nms package version
     * @param name Class name, if the class is in a sub package, e.p. "net.minecraft.server.{version}.command.CommandExample", you can access by "command.CommandExample"
     * @return Nms Class, if not exist return null
     */
    public static Class<?> getNmsClass(String name) {
        return getClass("net.minecraft.server." + getServerVersion() + "." + name);
    }

    /**
     * CraftBukkit still has obc version in 1.17+
     * @param name Class name, if the class is in a sub package, e.p. "org.bukkit.craftbukki.{version}.command.CommandExample", you can access by "command.CommandExample"
     * @return Obc Class, if not exist return null
     */
    public static Class<?> getObcClass(String name) {
        return getClass("org.bukkit.craftbukkit." + getServerVersion() + "." + name);
    }

    public static String getServerVersion() {
        String version = null;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (Exception e) {
            try {
                version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[1];
            } catch (Exception ignored) {
            }
        }
        return version;
    }

    public static int getVersion() {
        switch (getServerVersion()) {
            case "v1_8_R1": return 1;
            case "v1_8_R2": return 2;
            case "v1_8_R3": return 3;
            case "v1_9_R1": return 4;
            case "v1_9_R2": return 5;
            case "v1_10_R1": return 6;
            case "v1_11_R1": return 7;
            case "v1_12_R1": return 8;
            case "v1_13_R1": return 9;
            case "v1_13_R2": return 10;
            case "v1_14_R1": return 11;
            case "v1_15_R1": return 12;
            case "v1_16_R1": return 13;
            case "v1_16_R2": return 14;
            case "v1_16_R3": return 15;
            case "v1_17_R1": return 16;
            default: return -1;
        }
    }

}
