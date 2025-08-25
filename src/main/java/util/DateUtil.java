package util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for date-related operations.
 */
public class DateUtil {

    /**
     * Returns the current date as a formatted string.
     *
     * @param datePattern the pattern describing the date and time format
     * @return the formatted current date string
     */
    public static String getCurrentDateFormatted(String datePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        Date currentDate = new Date();
        return simpleDateFormat.format(currentDate);
    }

    /**
     * Returns a date string by adding a specified number of months to the current date.
     *
     * @param datePattern the pattern describing the date format
     * @param monthsToAdd the number of months to add to the current date
     * @return the formatted date string after adding the specified months
     */
    public static String getDateAfterAddingMonths(String datePattern, int monthsToAdd) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate updatedDate = currentDate.plusMonths(monthsToAdd);
        return updatedDate.format(dateTimeFormatter);
    }
}