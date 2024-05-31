package com.jvwac.jgvwa.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RequestMapping(value = "/traversal")
@RestController
public class PathTraversalController {

    /**
     * Undo List
     * java.nio.file.Path.of since JDK11
     */

    @ApiOperation(value = "java.io.File#<init>")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public String V1(@RequestParam(value = "path") String path) throws IOException {
        File f = new File(path);
        return new String(Files.readAllBytes(f.toPath()));
    }

    @ApiOperation(value = "java.nio.path.Paths#get")
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public String V2(@RequestParam(value = "path") String path) throws IOException {
        Path p = java.nio.file.Paths.get(path);
        return new String(Files.readAllBytes(p));
    }

    @ApiOperation(value = "java.nio.file.FileSystem#getPath")
    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    public String V3(@RequestParam(value = "path") String path) throws IOException {
        Path p = java.nio.file.FileSystems.getDefault().getPath(path);
        return new String(Files.readAllBytes(p));
    }

    @ApiOperation(value = "java.nio.file.Path#resolveSibling")
    @RequestMapping(value = "/v4", method = RequestMethod.GET)
    public String V4(@RequestParam(value = "path1") String path1, @RequestParam(value = "path2") String path2) throws IOException {
        Path p = Paths.get(path1).resolveSibling(Paths.get(path2));
        return new String(Files.readAllBytes(p));
    }

    @ApiOperation(value = "java.nio.file.Path#resolve")
    @RequestMapping(value = "/v5", method = RequestMethod.GET)
    public String V5(@RequestParam(value = "path1") String path1, @RequestParam(value = "path2") String path2) throws IOException {
        Path p = Paths.get(path1).resolve(Paths.get(path2));
        return new String(Files.readAllBytes(p));
    }

    @ApiOperation(value = "java.io.FileWriter#<init>")
    @RequestMapping(value = "/v6", method = RequestMethod.GET)
    public void V6(@RequestParam(value = "path") String path) throws IOException {
        FileWriter fw = new java.io.FileWriter(path);
        String content = "java.io.FileWriter#<init>#v6";
        for (int i = 0; i < content.length(); i++)
            fw.write(content.charAt(i));
        fw.close();
    }

    @ApiOperation(value = "java.io.FileReader#<init>")
    @RequestMapping(value = "/v7", method = RequestMethod.GET)
    public String V7(@RequestParam(value = "path") String path) throws IOException {
        FileReader fr = new java.io.FileReader(path);
        char[] data = new char[100];
        fr.read(data);
        fr.close();
        return String.valueOf(data);
    }

    @ApiOperation(value = "java.io.FileInputStream#<init>")
    @RequestMapping(value = "/v8", method = RequestMethod.GET)
    public String V8(@RequestParam(value = "path") String path) throws IOException {
        FileInputStream fileInputStream = new java.io.FileInputStream(path);
        byte[] data = new byte[100];
        fileInputStream.read(data);
        fileInputStream.close();
        return Arrays.toString(data);
    }

    @ApiOperation(value = "java.io.FileOutputStream#<init>")
    @RequestMapping(value = "/v9", method = RequestMethod.GET)
    public void V9(@RequestParam(value = "path") String path) throws IOException {
        FileOutputStream fileOutputStream = new java.io.FileOutputStream(path);
        String content = "java.io.FileOutputStream#<init>#v9";
        for (int i = 0; i < content.length(); i++)
            fileOutputStream.write(content.charAt(i));
        fileOutputStream.close();
    }
}
