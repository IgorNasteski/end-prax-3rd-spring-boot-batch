package com.example.batchexample;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class BatchExampleApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(BatchExampleApplication.class, args);
	}

	@Autowired
	JobLauncher jobLauncher;

	//autowiring job from config SpringBatchConfig.java class
	@Autowired
	@Qualifier("csvToDbBeanConfig")
	Job job2;

	//autowiring job from config UserBatchConfig.java class
	@Autowired
	@Qualifier("csvToCsvBeanConfig")
	Job job;

	@Autowired
	@Qualifier("taskletJobCsvToCsv")
	Job jobTasklet;

	//@Scheduled(cron = "0 */1 * * * ?")
	@Scheduled(cron = "0 47 1 * * ?")
	public BatchStatus loadJobCsvToCsv() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(job, parameters);

		System.out.println("JobExecution:  " + jobExecution.getStatus());

		System.out.println("Batch is Running... ");
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}

		return jobExecution.getStatus();
	}

	//@Scheduled(cron = "${batch.cron}") ako hocu u application.properties fajlu da zadam kada hocu da se
	//trigeruje job
	//tamo ce biti npr
	//batch:
	//	cron: 0 * * * * *

	//ovako mogu fiksirati tacno vreme kada ce se odradjivati job, npr svaki dan u 20:00
	//@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")

	//Fires at 12 PM every day:
	//@Scheduled(cron = "0 0 12 * * ?")

	//@Scheduled(cron = "0 */1 * * * ?")	//svaki minut

	@Scheduled(cron = "0 46 1 * * ?")
	public BatchStatus loadJobCsvToDb() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(job2, parameters);

		System.out.println("JobExecution: " + jobExecution.getStatus());

		System.out.println("Batch is Running... ");
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}

		return jobExecution.getStatus();
	}

	/*public void perform() throws Exception
	{
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);
	}*/

	@Scheduled(cron = "0 49 21 * * ?")
	public BatchStatus loadTaskletJobCsvToCsv() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(jobTasklet, parameters);

		System.out.println("JobExecution: " + jobExecution.getStatus());

		System.out.println("Batch is Running...  ");
		while (jobExecution.isRunning()) {
			System.out.println("...");
		}

		return jobExecution.getStatus();
	}

}
