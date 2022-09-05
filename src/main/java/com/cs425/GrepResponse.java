package com.cs425;

import java.io.Serializable;

public class GrepResponse implements Serializable {
    private Integer lines;
    private String filename;
    public static final long serialVersionUID = -539960512249034449L;

    public GrepResponse(Integer lineCount, String filename) {
        this.lines = lineCount;
        this.filename = filename;
    }

    // TODO A consideration:
    // Is a string guaranteed to be large enough to actually hold all the data we need it to?
    public String toString() {
        String output = "Grep result from " + filename + "\n";
//        for(Line line : lines) {
//            // TODO line may already contain a newline char
//            output += line + "\n";
//        }
        output += "Number of matching lines [" + filename + "]: " + lines;
        return output;
    }
}
