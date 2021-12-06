package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.logic.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Tag findById(@PathVariable("id") long id) {
        return tagService.findById(id);
    }

    @GetMapping("/null")
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "Hello world!";
    }
}
