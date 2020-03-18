package com.weirdo.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * 日志封装
 */
@Controller
public abstract class AbstractController {

    protected final Logger log= LoggerFactory.getLogger(getClass());

}

























