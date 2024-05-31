package com.jvwac.jgvwa.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.exec.*;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

@RequestMapping(value = "ce")
@RestController
public class CommandExecController {

    @ApiOperation(value = "java.lang.Runtime#exec")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public String V1(@RequestParam(value = "cmd") String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        int exitCode = process.waitFor();
        return exitCode + "\n" + getProcessOutput(process);
    }


    @ApiOperation(value = "java.lang.ProcessBuilder#start")
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public String V2(@RequestParam(value = "cmd") String cmd) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmd);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            return exitCode + "\n" + getProcessOutput(process);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @ApiOperation(value = "org.apache.commons.exec.DefaultExecutor#execute", notes = "https://mvnrepository.com/artifact/org.apache.commons/commons-exec/1.3")
    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    public String V3(@RequestParam(value = "cmd") String cmd) throws IOException {
        CommandLine commandLine = new CommandLine(cmd);
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        defaultExecutor.setStreamHandler(streamHandler);
        defaultExecutor.execute(commandLine);
        return outputStream.toString();
    }


    @ApiOperation(value = "com.sun.jna.Native#load", notes = "https://mvnrepository.com/artifact/net.java.dev.jna/jna/5.13.0")
    @RequestMapping(value = "/v4", method = RequestMethod.GET)
    public String V4(@RequestParam(value = "cmd") String cmd) throws IOException {
        int exitCode = CLibrary.INSTANCE.system(cmd);
        return "command execute success";
    }


    @ApiOperation(value = "java.lang.ProcessImpl#start")
    @RequestMapping(value = "/v5", method = RequestMethod.GET)
    public String V5(@RequestParam(value = "cmd") String cmd) throws Exception {
        Class clazz = Class.forName("java.lang.ProcessImpl");
        Method method = clazz.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);
        Process process = (Process) method.invoke(null, new String[]{cmd}, null, null, null, false);
        int exitCode = process.waitFor();
        return exitCode + "\n" + getProcessOutput(process);
    }

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.load((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);

        int system(String cmd);
    }

    private static String getProcessOutput(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
            output.append(System.getProperty("line.separator"));
        }
        return output.toString();
    }
}
