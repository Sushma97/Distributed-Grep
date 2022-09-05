package com.cs425;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;

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

    // TODO, look into more grep options, maybe for line numbers?
    public GrepResponse runGrep() {
        File file = new File("src/main/resources/" + filename);
        List<Line> lines = Unix4j.grep(grepPattern, file).toLineList();
        return new GrepResponse(lines.size(), filename);
    }

    @Override
    public String toString() {
        return "GrepRequest{" +
                "filename='" + filename + '\'' +
                ", grepPattern='" + grepPattern + '\'' +
                '}';
    }
}


