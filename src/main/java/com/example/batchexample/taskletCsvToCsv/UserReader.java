package com.example.batchexample.taskletCsvToCsv;

import com.example.batchexample.model.User;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;

import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//LinesReader will be in charge of reading data from the input file
public class UserReader implements Tasklet, StepExecutionListener {

    private List<User> users;
    private FileUtils fu;

    //implementacija StepExecutionListener interfejsa
    @Override
    public void beforeStep(StepExecution stepExecution) {
        users = new ArrayList<>();
        fu = new FileUtils("users.csv");
                //FileUtils("src/main/resources/users.csv");//C:\Users\Igor\Documents\praksa%20projekti%20endava\batch-example\target\classes\
    }

    //implementacija taskelt interfejsa
    //This method is where we'll add the logic for each step
    //@Nullable
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        User user = fu.readLine();
        while (user != null) {
            users.add(user);
            user = fu.readLine();
        }
        return RepeatStatus.FINISHED;
    }

    //implementacija StepExecutionListener interfejsa
    //@Nullable
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        //fu.closeReader();
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("users", this.users);
        //logger.debug("Lines Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
