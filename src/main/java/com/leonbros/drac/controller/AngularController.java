package com.leonbros.drac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller forwards unmatched patterns back to index.html. Allowing Angular to provide the static resources.
 *
 */
@Controller
public class AngularController {
  @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/**/{x:[\\w\\-]+}" })
  public String redirect() {
    return "forward:/index.html";
  }
}
