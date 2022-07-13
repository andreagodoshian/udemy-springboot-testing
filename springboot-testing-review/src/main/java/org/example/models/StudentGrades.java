package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StudentGrades {

    List<Double> mathGradeResults;

    public double addGradeResultsForSingleClass(List<Double> grades) {
        double result = 0;
        for (double i : grades) {
            result += i;
        }
        return result;
    }

    public double findGradePointAverage (List<Double> grades ) {
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();

    }

    public Boolean isGradeGreater(double gradeOne, double gradeTwo) {
        if (gradeOne > gradeTwo) {
            return true;
        }
        return false;
    }

    public Object checkNull(Object obj) {
        if ( obj != null ) {
            return obj;
        }
        return null;
    }

}