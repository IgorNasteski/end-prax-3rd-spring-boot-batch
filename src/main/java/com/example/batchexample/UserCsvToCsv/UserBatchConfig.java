package com.example.batchexample.UserCsvToCsv;

import com.example.batchexample.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class UserBatchConfig {

    private Resource outputResource = new FileSystemResource("src/main/resources/usersOutput.csv");

    //jedna obrada jednog fajla
    //schedule wrapper u okviru njega definisem jobove, u nekom config fajlu navedem ime joba i kazem izvrsi se u 11 uvece npr.
    @Qualifier("csvToCsvBeanConfig")
    @Bean
    public Job jobCsvToCsv(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<User> itemReader,
                           @Qualifier("csvToCsvProcessor") ItemProcessor<User, User> itemProcessor,
                   ItemWriter<User> writer) {

        Step step = stepBuilderFactory.get("Csv-to-csv-load")
                .<User, User>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(writer)
                .build();


        return jobBuilderFactory.get("Csv-to-csv")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean   //for reading csv file
    public FlatFileItemReader<User> reader() {

        FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
        flatFileItemReader.setName("CSV-Reader1");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapperCsvToCsv());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<User> lineMapperCsvToCsv() {

        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        //lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "email", "salary"});

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    //dodatni bean flatfileitemwritter kako bih upisivao u fajl
    /*@Bean
    public FlatFileItemWriter<User> writer(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource);

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor() {
                    {
                        setNames(new String[] { "id", "name", "email", "salary" });
                    }
                });
            }
        });

        return writer;
    }*/

    @Bean
    public FlatFileItemWriter<User> writer(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        //writer.setResource(new ClassPathResource("persons.csv"));
        writer.setResource(new FileSystemResource("src/main/resources/newUsers.csv"));

        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<User>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<User>  fieldExtractor = new BeanWrapperFieldExtractor<User>();
        fieldExtractor.setNames(new String[]{"id", "name", "email", "salary"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }


}
