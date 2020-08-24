package com.example.batchexample.taskletCsvToCsv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class TaskletConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private UserReader userReader;

    @Autowired
    private UserProcessor userProcessor;

    @Autowired
    private UserWriter userWriter;

    @Bean
    protected Step readLines() {
        return steps
                .get("readUser")
                .tasklet(userReader)
                .build();
    }

    /*@Bean
    protected Step processLines() {
        return steps
                .get("processUser")
                .tasklet(userProcessor)
                .build();
    }*/

    @Bean
    protected Step writeLines() {
        return steps
                .get("writeUser")
                .tasklet(userWriter)
                .build();
    }

    @Bean
    @Qualifier("taskletJobCsvToCsv")
    public Job jobTasklet() {
        return jobs
                .get("createCsvJob")
                .start(readLines())
                //.next(processLines())
                .next(writeLines())
                .build();
    }








}
