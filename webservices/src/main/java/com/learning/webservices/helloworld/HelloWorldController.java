package com.learning.webservices.helloworld;

import com.learning.webservices.versioning.PersonV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

//REST API
@RestController
public class HelloWorldController {

    private final MessageSource messageSource;

    @Autowired
    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // hello-world
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public String helloWorldBean(){
        return "Hello World Bean";
    }

    @GetMapping("/hello/{name}")
    public PersonV1 helloName(@PathVariable("name")String name){
        return new PersonV1(name);
    }


    /**
     * good.morning.message
     *
     * `en` - English (Good Morning)
     * `fr` - French (Bonjour)
     */
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null, "Default",locale);

    }
}
