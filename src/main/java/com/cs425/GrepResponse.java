package com.cs425;

import java.io.Serializable;
import java.util.List;

public class GrepResponse implements Serializable {
    public List<String> lines;
    private String filename;
    private boolean initialized;
    private boolean fileExists = true;

    public static final long serialVersionUID = -539960512249034449L;

    public GrepResponse() {
        this.initialized = false;
    }
    
    public GrepResponse(List<String> lines, String filename) {
        this.lines = lines;
        this.filename = filename;
        this.initialized = true;
    }

    // Constructor for when File doesn't exist
    public GrepResponse(String filename) {
        this.filename = filename;
        this.initialized = true;
        this.fileExists = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean fileExists() {
        return fileExists;
    }

    // TODO A consideration:
    // Is a string guaranteed to be large enough to actually hold all the data we need it to?
    public String toString() {
        if (!fileExists) {
            return "File not found: " + filename;
        }
        String[] filePath = filename.split("/");
        String output = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\nGrep results for " + filePath[filePath.length - 1] + ":\n";
        for(String line : lines) {
            output += line + "\n";
        }
        return output;
    }

    public boolean equals(GrepResponse other) {
        if (other == null) {
            return false;
        }

        if ((this.filename == null && other.filename != null) || (this.filename != null && !this.filename.equals(other.filename))) {
            return false;
        }

        if ((this.lines == null && other.lines != null) || (this.lines != null && !this.lines.equals(other.lines))) {
            return false;
        }

        if (this.initialized == other.initialized && this.fileExists == other.fileExists) {
            return true;
        } else {
            return false;
        }
    }
}
