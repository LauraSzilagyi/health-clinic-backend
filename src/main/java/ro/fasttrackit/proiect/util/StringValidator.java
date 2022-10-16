package ro.fasttrackit.proiect.util;

import ro.fasttrackit.proiect.doctor.enums.Department;
import ro.fasttrackit.proiect.exceptions.InvalidRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringValidator {

    private StringValidator() {
    }

    public static void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new InvalidRequestException("Please give us a valid email address!");
        }
    }

    public static void validateDepartment(String department) {
        if (!getDepartmentsAsAListOfStrings().contains(department.toUpperCase())) {
            throw new InvalidRequestException("Invalid department!");
        }
    }

    private static List<String> getDepartmentsAsAListOfStrings() {
        return Arrays.stream(Department.values())
                .toList()
                .stream()
                .map(Enum::name)
                .toList();

    }

    public static void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidRequestException("Must contains name!");
        }
    }
}
