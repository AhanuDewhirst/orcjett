package com.orcjett.api.base.application;

import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public final class Configuration {
    private final Preferences user;
    private final Preferences system;

    protected Configuration(Namespace namespace) {
        this.user = Preferences.userRoot().node(namespace.getNamespace());
        this.system = Preferences.systemRoot().node(namespace.getNamespace());
    }

    public String getVariable(String key) {
        return System.getenv(key);
    }

    public boolean userContains(String key) {
        try {
            return Arrays.asList(user.keys()).contains(key);
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean systemContains(String key) {
        try {
            return Arrays.asList(system.keys()).contains(key);
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserInt(String key) {
        return user.getInt(key, 0);
    }

    public void setUserInt(String key, int value) {
        user.putInt(key, value);
    }

    public float getUserFloat(String key) {
        return user.getFloat(key, 0.0f);
    }

    public void setUserFloat(String key, float value) {
        user.putFloat(key, value);
    }

    public double getUserDouble(String key) {
        return user.getDouble(key, 0.0d);
    }

    public void setUserDouble(String key, double value) {
        user.putDouble(key, value);
    }

    public long getUserLong(String key) {
        return user.getLong(key, 0L);
    }

    public void setUserLong(String key, long value) {
        user.putLong(key, value);
    }

    public byte[] getUserBytes(String key) {
        return user.getByteArray(key, new byte[0]);
    }

    public void setUserBytes(String key, byte[] value) {
        user.putByteArray(key, value);
    }

    public boolean getUserBoolean(String key) {
        return user.getBoolean(key, false);
    }

    public void setUserBoolean(String key, boolean value) {
        user.putBoolean(key, value);
    }

    public String getUserString(String key) {
        return user.get(key, "");
    }

    public void setUserString(String key, String value) {
        user.put(key, value);
    }

    public int getSystemInt(String key) {
        return system.getInt(key, 0);
    }

    public void setSystemInt(String key, int value) {
        system.putInt(key, value);
    }

    public float getSystemFloat(String key) {
        return system.getFloat(key, 0.0f);
    }

    public void setSystemFloat(String key, float value) {
        system.putFloat(key, value);
    }

    public double getSystemDouble(String key) {
        return system.getDouble(key, 0.0d);
    }

    public void setSystemDouble(String key, double value) {
        system.putDouble(key, value);
    }

    public long getSystemLong(String key) {
        return system.getLong(key, 0L);
    }

    public void setSystemLong(String key, long value) {
        system.putLong(key, value);
    }

    public byte[] getSystemBytes(String key) {
        return system.getByteArray(key, new byte[0]);
    }

    public void setSystemBytes(String key, byte[] value) {
        system.putByteArray(key, value);
    }

    public boolean getSystemBoolean(String key) {
        return system.getBoolean(key, false);
    }

    public void setSystemBoolean(String key, boolean value) {
        system.putBoolean(key, value);
    }

    public String getSystemString(String key) {
        return system.get(key, "");
    }

    public void setSystemString(String key, String value) {
        system.put(key, value);
    }
}
