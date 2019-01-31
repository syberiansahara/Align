package ru.ninefoldcomplex.align.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ninefoldcomplex.align.service.ITextService;

@RestController
@RequestMapping("/text")
public class TextController {
    @Autowired
    private ITextService textService;

    @PostMapping(value="/postText")
    //http://localhost:8888/text/postText
    public ResponseEntity<?> updateRanksInQueue(@RequestBody String rawText) {

        return new ResponseEntity<>(rawText.substring(0, Integer.min(5, rawText.length() - 1)), HttpStatus.OK);
    }

    @GetMapping(value="/getTheme")
    //http://localhost:8888/text/postText
    public ResponseEntity<?> getTheme() {
        textService.selectTheme();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
