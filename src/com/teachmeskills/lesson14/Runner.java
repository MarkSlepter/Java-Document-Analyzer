package com.teachmeskills.lesson14;

import java.io.*;
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the file: ");
        String inputFilePath = scanner.nextLine();
        scanner.close();

        validateDocuments(inputFilePath);
    }

    private static void validateDocuments(String inputFilePath) {
        String outputDocNumFilePath = "valid_docnum.txt";
        String outputContractFilePath = "valid_contract.txt";
        String outputInvalidDocNumFilePath = "invalid_docnum.txt";
        String errorLogFilePath = "error_log.txt";
        String executionLogFilePath = "execution_log.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter docNumWriter = new BufferedWriter(new FileWriter(outputDocNumFilePath));
             BufferedWriter contractWriter = new BufferedWriter(new FileWriter(outputContractFilePath));
             BufferedWriter invalidDocNumWriter = new BufferedWriter(new FileWriter(outputInvalidDocNumFilePath));
             BufferedWriter errorLogWriter = new BufferedWriter(new FileWriter(errorLogFilePath, true));
             BufferedWriter executionLogWriter = new BufferedWriter(new FileWriter(executionLogFilePath, true))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidDocNum(line)) {
                    if (line.startsWith("docnum")) {
                        docNumWriter.write(line + System.lineSeparator());
                    } else if (line.startsWith("contract")) {
                        contractWriter.write(line + System.lineSeparator());
                    } else {
                        invalidDocNumWriter.write(line + " - Invalid prefix " + System.lineSeparator());
                    }
                } else {
                    invalidDocNumWriter.write(line + " - Invalid length " + System.lineSeparator());
                }
            }

            executionLogWriter.write("Program completed successfully." + System.lineSeparator());

        } catch (IOException e) {
            try (BufferedWriter errorLogWriter = new BufferedWriter(new FileWriter(errorLogFilePath, true))) {
                errorLogWriter.write("Error reading/writing files: " + e.getMessage() + System.lineSeparator());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private static boolean isValidDocNum(String docNum) {
        return docNum.length() == 15 && (docNum.startsWith("docnum") || docNum.startsWith("contract"));
    }

}