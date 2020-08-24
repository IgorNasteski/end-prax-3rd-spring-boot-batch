package com.example.batchexample.taskletCsvToCsv;

import com.example.batchexample.model.User;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//UserProcessor will calculate the age for every person in the file
public class UserProcessor implements Tasklet, StepExecutionListener {

    private List<User> users;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.users = (List<User>) executionContext.get("users");
        //logger.debug("Users Processor initialized.");
    }

    //This method is where we'll add the logic for each step
    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {
        /*for (User user : users) {
            long age = ChronoUnit.YEARS.between(
                    line.getDob(),
                    LocalDate.now());
            logger.debug("Calculated age " + age + " for line " + line.toString());
            line.setAge(age);
        }*/
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        //logger.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
    }

}
