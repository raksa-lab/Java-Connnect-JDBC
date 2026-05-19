package utils;

import java.time.LocalDate;

public record APIResponseTemplate <T> (
        int status,
        String message,
        LocalDate timestamp,
        T data
){
}
