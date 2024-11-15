package code;

import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        // Create a scanner object for taking input
        Scanner sc = new Scanner(System.in);

        // Define the total number of subjects
        int numberOfSubjects = 5; // You can change this as needed

        // Array to store marks for each subject
        int[] marks = new int[numberOfSubjects];

        // Input marks for each subject
        System.out.println("Enter marks for " + numberOfSubjects + " subjects (out of 100):");
        int totalMarks = 0;
        for (int i = 0; i < numberOfSubjects; i++) {
            System.out.print("Subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
            totalMarks += marks[i]; // Summing up marks
        }

        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numberOfSubjects;

        // Grade Calculation
        String grade = "";
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        // Display results
        System.out.println("\n--- Result ---");
        System.out.println("Total Marks: " + totalMarks + "/" + (numberOfSubjects * 100));
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);

        // Close the scanner
        sc.close();
    }
}
