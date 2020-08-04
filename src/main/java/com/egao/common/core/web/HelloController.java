package com.egao.common.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * hello
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    public void sayHello(){
        System.out.println("这是我说的话");
    }

    public void sayByBy(){
        System.out.println("再见，下次见");
    }
}
