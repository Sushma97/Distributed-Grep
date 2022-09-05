package com.cs425;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.unix4j.Unix4j;
import org.unix4j.unix.Grep;

public class GrepRequest implements Serializable {
    private String filename;
    private String grepPattern;
    public static final long serialVersionUID = -5399605122490343339L;

    // No setters, since we don't ever want to modify a grep request in-flight.
    // We just use the constructor to set values
    public GrepRequest(String grepPattern, String filename) {
        this.grepPattern = grepPattern;
        this.filename = filename;

    }

    public String getFilename() {
        return filename;
    }

    public String getGrepPattern() {
        return grepPattern;
    }

    public GrepResponse runGrep() {
        // Fault tolerance when file not found
        // (This is treated as a RuntimeException by Unix4j.grep)
        try {
            File file = new File(filename);
            // TODO pass options, for example Grep.Options.n:
            List<String> lines = Unix4j.grep(Grep.Options.n, grepPattern, file).toStringList();
            return new GrepResponse(lines, filename);
        } catch (Exception exception) {
            return new GrepResponse(filename);
        }
    }

    @Override
    public String toString() {
        return "GrepRequest{" +
                "filename='" + filename + '\'' +
                ", grepPattern='" + grepPattern + '\'' +
                '}';
        
    }
}


