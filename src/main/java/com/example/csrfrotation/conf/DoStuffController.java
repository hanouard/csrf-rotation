package com.example.csrfrotation.conf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.random.RandomGenerator;

@RestController
@RequestMapping("/do-stuff")
public class DoStuffController {

    @GetMapping
    public int getRandomValue() {
        return RandomGenerator.getDefault().nextInt();
    }

    @PostMapping
    public void someValue(String value) {
        System.out.println(value);
    }
}
