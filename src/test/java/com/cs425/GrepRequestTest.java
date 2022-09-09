package com.cs425;

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GrepRequestTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GrepRequestTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GrepRequestTest.class);
    }

    public void testGetFileName() {
        String filename = "someFile";
        GrepRequest request = new GrepRequest("pattern", filename, null);

        assertEquals(filename, request.getFilename());
    }

    public void testGetGrepPattern() {
        String pattern = "somePattern";
        GrepRequest request = new GrepRequest(pattern, "someFile", Arrays.asList("c"));

        assertEquals(pattern, request.getGrepPattern());
    }

    public void testToString() {
        GrepRequest request = new GrepRequest("pattern", "file", Arrays.asList("c"));

        assertEquals(request.toString(),
        "GrepRequest{filename='file', grepPattern='pattern'}");
    }

    public void testEqualsBothNull() {
        GrepRequest one = new GrepRequest(null, null, null);
        GrepRequest two = new GrepRequest(null, null, null);

        assertTrue(one.equals(two));
    }

    public void testEquals() {
        GrepRequest one = new GrepRequest("pattern", "file", Arrays.asList("c"));
        GrepRequest two = new GrepRequest("pattern", "file", Arrays.asList("c"));

        assertTrue(one.equals(two));
    }

    public void testNotEqualsPattern() {
        GrepRequest one = new GrepRequest("pattern", "file", Arrays.asList("c"));
        GrepRequest two = new GrepRequest("differentpattern", "file", Arrays.asList("c"));

        assertFalse(one.equals(two));
    }

    public void testNotEqualsFile() {
        GrepRequest one = new GrepRequest("pattern", "file", Arrays.asList("c"));
        GrepRequest two = new GrepRequest("pattern", "differentfile", Arrays.asList("c"));

        assertFalse(one.equals(two));
    }

    public void testNotEqualsOptions() {
        GrepRequest one = new GrepRequest("pattern", "file", Arrays.asList("c"));
        GrepRequest two = new GrepRequest("pattern", "file", Arrays.asList("n"));

        assertFalse(one.equals(two));
    }

    public void testNotEqualsOneNull() {
        GrepRequest one = new GrepRequest(null, null, null);
        GrepRequest two = new GrepRequest("pattern", "file", Arrays.asList("c"));

        assertFalse(one.equals(two));
        assertFalse(two.equals(one));
    }

    // TODO
    public void testRunGrep() {
        assertTrue(true);
    }
}
