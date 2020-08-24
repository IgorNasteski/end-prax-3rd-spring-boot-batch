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
//UserWriter will have the responsibility of writing names and ages to an output file
public class UserWriter implements Tasklet, StepExecutionListener {

    private List<User> users;
    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.users = (List<User>) executionContext.get("users");
        //fu = new FileUtils("usersTaskletNovo.csv");
        fu = new FileUtils("src/main/resources/output/usersNOVO.csv");//src/main/resources/
        //logger.debug("Lines Writer initialized.");
    }

    //This method is where we'll add the logic for each step
    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {
        for (User user : users) {
            fu.writeLine(user);
            //logger.debug("Wrote line " + line.toString());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeWriter();
        //logger.debug("Lines Writer ended.");
        return ExitStatus.COMPLETED;
    }

}
