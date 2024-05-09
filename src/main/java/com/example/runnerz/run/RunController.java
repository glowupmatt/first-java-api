package com.example.runnerz.run;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;


//This is the controller class that will be used to interact with the data
//This will allow up to view the data based on the request mapping the user inputs in the URL
@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository){
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }
    
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {

        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }
    
    //Post Request

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run body) {
        runRepository.create(body);
        throw new ResponseStatusException(HttpStatus.CREATED, "Run created");
    }

    // Update Request
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    void update(@Valid @PathVariable Integer id, @RequestBody Run body) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found");
        }
        runRepository.update(id, body);
        System.out.println("Run updated _________" + body + "__________");
        throw new ResponseStatusException(HttpStatus.OK, "Run updated");
    }

    // Delete Request
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Run not found");
        }
        runRepository.delete(id);
        System.out.println("Run deleted _________" + run + "__________");
        throw new ResponseStatusException(HttpStatus.OK, "Run deleted");
    }
}
