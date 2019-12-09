package com.SoftwareMatrix;

import java.util.HashSet;
import java.lang.Math;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

/**
 * used to describe some important features of method about OOM
 */
public class OOMMethod {
    private String name;
    private boolean isPublic;
    private boolean isPrivate;
    private boolean isProtected;
    private boolean isOverride;

    OOMMethod(String name, boolean isPublic, boolean isPrivate, boolean isProtected, boolean isOverride) {
        setName(name);
        setPublic(isPublic);
        setPrivate(isPrivate);
        setProtected(isProtected);
        setOverride(isOverride);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public void setOverride(boolean isOverride) {
        this.isOverride = isOverride;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isProtected() {
        return this.isProtected;
    }

    public boolean isOverride() {
        return this.isOverride;
    }
}