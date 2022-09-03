package com.cs425;

import org.unix4j.Unix4j;
import org.unix4j.line.Line;

import java.io.File;
import java.util.List;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        File file = new File("filename.txt");
        List<Line> lines = Unix4j.grep("scandal", file).toLineList();
        System.out.println(lines.size());
    }
}