package com.example.demo.job;

import com.example.demo.DVORApplication;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.ClientService;
import com.example.demo.service.EventService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Logger;

@EnableBatchProcessing
@Configuration
public class JobConfig {
    private static final Logger log = Logger.getLogger(DVORApplication.class.getName());

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EventService eventService;
    private final ClientService clientService;
    private final EventMapper eventMapper;
    private final ClientMapper clientMapper;

    public JobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EventService eventService,
                     ClientService clientService, EventMapper eventMapper, ClientMapper clientMapper) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.eventService = eventService;
        this.clientService = clientService;
        this.eventMapper = eventMapper;
        this.clientMapper = clientMapper;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("job")
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return stepBuilderFactory
                .get("step")
                .tasklet(stepProcessor())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public StepProcessor stepProcessor(){
        return new StepProcessor(eventService, clientService, eventMapper, clientMapper);
    }
}
