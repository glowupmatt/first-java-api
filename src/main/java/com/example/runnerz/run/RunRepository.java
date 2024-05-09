package com.example.runnerz.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.annotation.PostConstruct;

//This is the repository class that will be used to interact with the data
@Repository
public class RunRepository {
    private List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id) {
        return runs.stream().filter(run -> run.id() == id).findFirst();
    }

    //Post Request

    void create(@RequestBody Run run) {
        runs.add(run);
    }

    //Update Request
    void update(Integer id, Run run) {
        Optional<Run> existingRun = findById(id);
        if (existingRun.isPresent()) {
            runs.set(runs.indexOf(existingRun.get()), run);
        }
    }

    //Delete Request
    void delete(Integer id) {
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(run -> runs.remove(run));
    }


    //This is the data we will be using for the application
    @PostConstruct
    private void init() {
        runs.add(new Run(1, "First Run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.HOURS), 5,
                Location.OUTDOOR));
        runs.add(new Run(2, "Second Run", LocalDateTime.now(), LocalDateTime.now().plus(60, ChronoUnit.HOURS),  10,
                Location.INDOOR));
    }
}
