package com.example.batchexample.taskletCsvToCsv;

import com.example.batchexample.model.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private String fileName;
    private CSVReader CsvReader;
    private CSVWriter CsvWriter;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private File file;

    public FileUtils(String fileName) {
        this.fileName = fileName;
    }

    public User readLine() throws Exception {
        if (CsvReader == null)
            initReader();
        String[] user = CsvReader.readNext();
        if (user == null)
            return null;
        return new User.UserBuilder().withName(user[1]).withEmail(user[2]).build();
                //User(Integer.parseInt(user[0]), user[1]  );
        //(
          //      line[0]);
                /*,
                LocalDate.parse(
                        line[1],
                        DateTimeFormatter.ofPattern("MM/dd/yyyy")));*/
    }

    public void writeLine(User user) throws Exception {
        if (CsvWriter == null)
            initWriter();
        String[] userStr = new String[2];
        userStr[0] = user.getName();
        userStr[1] = user.getEmail();
        /*userStr[2] = user.getEmail();
        userStr[1] = user
                .getName()
                .toString();*/


        //lineStr[2] = user.getSalary();
        CsvWriter.writeNext(userStr);
    }

    private void initReader() throws Exception {
        /*ClassLoader classLoader = this
                .getClass()
                .getClassLoader();*/

        ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();

        if (file == null) file = (File) ResourceUtils.getFile("classpath:users.csv");
                //File(classLoader1.getResource(fileName).getFile());

                /*new File(classLoader
                .getResource(fileName)
                .getFile());*/
        if (fileReader == null) fileReader = new FileReader(file);
        if (CsvReader == null) CsvReader = new CSVReader(fileReader);
    }
    private void initWriter() throws Exception {
        if (file == null) {
            file = new File(fileName);
            file.createNewFile();
        }
        if (fileWriter == null) fileWriter = new FileWriter(file, true);
        if (CsvWriter == null) CsvWriter = new CSVWriter(fileWriter);
    }

    public void closeWriter() {
        try {
            CsvWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            //logger.error("Error while closing writer.");
            System.out.println("Error while closing writer.");
        }
    }

    public void closeReader() {
        try {
            CsvReader.close();
            fileReader.close();
        } catch (IOException e) {
            //logger.error("Error while closing reader.");
            System.out.println("Error while closing reader.");
        }
    }

}
