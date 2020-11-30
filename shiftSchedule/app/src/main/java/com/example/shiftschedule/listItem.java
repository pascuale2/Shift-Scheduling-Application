package com.example.shiftschedule;

public class listItem {

    public String name;
    public String description;
    public boolean closing;
    public boolean opening;
    public String hiddenInfo;
    public listItem(String name, String description, boolean closing, boolean opening, String extraInfo) {
        this.name = name;
        this.description = description;
        this.closing = closing;
        this.opening = opening;
        this.hiddenInfo = extraInfo;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public boolean getClosing() { return closing; }
    public boolean getOpening() { return opening; }
    public void setName(String name) { this.name = name; }

    public String getHiddenInfo() {
        return hiddenInfo;
    }

    public void setDescription(String description) { this.description = description; }
}
