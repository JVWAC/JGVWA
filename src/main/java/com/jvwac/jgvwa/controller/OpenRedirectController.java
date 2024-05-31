package com.jvwac.jgvwa.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/redirect")
@RestController
public class OpenRedirectController {
    @ApiOperation(value = "org.springframework.web.servlet.ModelAndView#<init>")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public ModelAndView V1(@RequestParam(value = "url") String url) {
        return new ModelAndView("redirect:" + url);
    }

    @ApiOperation(value = "javax.servlet.http.HttpServletResponse#sendRedirect")
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public void V2(@RequestParam(value = "url") String url, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }

    @ApiOperation(value = "javax.servlet.http.HttpServletResponse#setStatus&setHeader")
    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    public void V3(@RequestParam(value = "url") String url, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", url);
    }

    @ApiOperation(value = "org.springframework.web.servlet.view.RedirectView#<init>")
    @RequestMapping(value = "/v4", method = RequestMethod.GET)
    public RedirectView V4(@RequestParam(value = "url") String url) {
        return new RedirectView(url);
    }
}


