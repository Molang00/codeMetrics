package com.SoftwareMatrix;

import java.util.HashSet;
import java.lang.Math;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  used to describe some important features of attributes about OOM
 */
public class OOMAttribute {
    private String name;
    private boolean isPublic;
    private boolean isPrivate;
    private boolean isProtected;

    OOMAttribute(String name, boolean isPublic, boolean isPrivate, boolean isProtected) {
        setName(name);
        setPublic(isPublic);
        setPrivate(isPrivate);
        setProtected(isProtected);
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
}