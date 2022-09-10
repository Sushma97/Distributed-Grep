package com.cs425;

import java.net.URISyntaxException;
import java.util.Arrays;

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

    public void testRunGrepInfrequentLines() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("result.txt").getPath();
        
        GrepRequest request = new GrepRequest("Sherlock", path, Arrays.asList("c"));
        GrepResponse response = request.runGrep();

        assertTrue("Response not initialized", response.isInitialized());
        assertTrue("File not found", response.fileExists());

        String count = response.lines.get(0);

        assertEquals(count, "13");
    }

    public void testRunGrepFrequentLines() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("result.txt").getPath();
        
        GrepRequest request = new GrepRequest("a", path, Arrays.asList("c"));
        GrepResponse response = request.runGrep();

        assertTrue("Response not initialized", response.isInitialized());
        assertTrue("File not found", response.fileExists());

        String count = response.lines.get(0);

        assertEquals(count, "187");
    }

    public void testRunGrepLoadTestInfrequent() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("enwik8").getPath();
        
        GrepRequest request = new GrepRequest("hello", path, null);
        GrepResponse response = request.runGrep();

        assertTrue("Response not initialized", response.isInitialized());
        assertTrue("File not found", response.fileExists());

        assertEquals(153, response.lines.size());
    }

    public void testRunGrepLoadTestFrequent() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("enwik8").getPath();
        
        GrepRequest request = new GrepRequest("e", path, null);
        GrepResponse response = request.runGrep();

        assertTrue("Response not initialized", response.isInitialized());
        assertTrue("File not found", response.fileExists());

        assertEquals(693181, response.lines.size());
    }
}
