package com.atlassian.interview.controller;

import com.atlassian.interview.service.iStoryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoryPointController {

    @Autowired
    iStoryPointService storyPointService;

    @GetMapping("/api/issue/sum")
    public @ResponseBody void getTotalStoryPOints(@RequestParam String query,
            @RequestParam String name) {
        storyPointService.calculateStoryPointsSumAndPushToQueue(query, name);
    }
}
