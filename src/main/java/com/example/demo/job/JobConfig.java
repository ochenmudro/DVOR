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

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private EventService eventService;
    private ClientService clientService;
    private EventMapper eventMapper;
    private ClientMapper clientMapper;

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
    public Job job(Step step) {
        return jobBuilderFactory
                .get("job")
                .start(step)
                .build();
    }

    @Bean
    public Step step() throws IOException {
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return stepBuilderFactory
                .get("step")
                .tasklet(new StepProcessor(eventService, clientService, eventMapper, clientMapper))
                .allowStartIfComplete(true)
                .build();
    }
}
