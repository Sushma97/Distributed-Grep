package com.cs425;

import java.io.File;
import java.util.List;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;

public class GrepRequest {
    private String filename;
    private String grepPattern;

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
        File file = new File(filename);
        List<Line> lines = Unix4j.grep(grepPattern, file).toLineList();

        return new GrepResponse(lines, filename);
    }

    public class GrepResponse {
        private List<Line> lines;
        private String filename;

        public GrepResponse(List<Line> lines, String filename) {
            this.lines = lines;
            this.filename = filename;
        }

        // TODO A consideration:
        // Is a string guaranteed to be large enough to actually hold all the data we need it to?
        public String toString() {
            String output = "Grep result from " + filename + "\n";
            for(Line line : lines) {
                // TODO line may already contain a newline char
                output += line + "\n";
            }
            output += "Number of matching lines [" + filename + "]: " + lines.size();
            return output;
        }
    }
}
