package chap2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private static final String DEFAULT_DELIMITERS = ",|:";
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.*)\n.*");

    public int add(String numbers) {
        if (isEmptyOrNull(numbers)) {
            return 0;
        }

        String delimiters = getDelimiters(numbers);
        String numbersWithoutDelimiter = getNumbersWithoutDelimiter(numbers);
        String[] splitNumbers = splitNumbers(numbersWithoutDelimiter, delimiters);

        return calculateSum(splitNumbers);
    }

    private boolean isEmptyOrNull(String text) {
        return text == null || text.isEmpty();
    }

    private String getDelimiters(String numbers) {
        String customDelimiter = getCustomDelimiter(numbers);
        return customDelimiter.isEmpty() ? DEFAULT_DELIMITERS : DEFAULT_DELIMITERS + "|" + customDelimiter;
    }

    private String getCustomDelimiter(String numbers) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(numbers);
        return matcher.find() ? Pattern.quote(matcher.group(1)) : "";
    }

    private String getNumbersWithoutDelimiter(String numbers) {
        if (numbers.startsWith("//")) {
            return numbers.substring(numbers.indexOf("\n") + 1);
        }
        return numbers;
    }

    private String[] splitNumbers(String numbers, String delimiters) {
        return numbers.split(delimiters);
    }

    private int calculateSum(String[] numbers) {
        int sum = 0;
        List<Integer> negatives = new ArrayList<>();

        for (String number : numbers) {
            if (!number.isEmpty()) {
                int num = Integer.parseInt(number);
                checkAndCollectNegatives(num, negatives);
                sum += num;
            }
        }

        throwExceptionIfNegativesExist(negatives);

        return sum;
    }

    private void checkAndCollectNegatives(int number, List<Integer> negatives) {
        if (number < 0) {
            negatives.add(number);
        }
    }

    private void throwExceptionIfNegativesExist(List<Integer> negatives) {
        if (!negatives.isEmpty()) {
            throw new RuntimeException("Negatives not allowed: " + negatives);
        }
    }
}
