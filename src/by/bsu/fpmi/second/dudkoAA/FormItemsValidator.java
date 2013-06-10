package by.bsu.fpmi.second.dudkoAA;

import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Checker for correct filling of different forms.
 */
public class FormItemsValidator {

    /** Maximum number of characters in input field. */
    private static final int MAX_CHARACTERS = 300;

    /** Minimum number of characters in input field for file. */
    private static final int MIN_FILE_FIELD_CHARACTERS = 1;

    /** Minimum number of characters in input field for file. */
    private static final int MIN_USER_FIELD_CHARACTERS = 6;

    /**
     * Validate value and add error messages to messages list.
     * @param value the value to validate
     * @param errors the messages list
     * @param fieldName the key of field name in properties file
     */
    public void validateFileStringField(final String value, final List<String> errors, final String fieldName) {
        if (value == null || value.trim().equals("")) {
            String errorMessage = format(Messager.getMessage("error.empty"), Messager.getMessage(fieldName));
            errors.add(errorMessage);
        } else if (value.length() > MAX_CHARACTERS) {
            String errorMessage = format(Messager.getMessage("error.field.length"),
                    Messager.getMessage(fieldName), MIN_FILE_FIELD_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
        }
    }

    /**
     * Checks the length of field value and its characters
     * consistence.
     * @param errors errors of user input strings
     * @param fieldName name of validated field
     * @param fieldValue value of validated field
     */
    public void validateUserStringField(final List<String> errors,
                                         final String fieldValue, final String fieldName) {
        isLengthCorrect(errors, fieldValue, fieldName);
        isConsistsOfLettersAndNumbers(errors, fieldValue, fieldName);
    }

    /**
     * Checks if the field value consists of letters
     * and numbers only and adds error messages to errors.
     * @param errors errors of user input strings
     * @param fieldName name of validated field
     * @param fieldValue value of validated field
     * @return if field value is correct
     */
    public boolean isConsistsOfLettersAndNumbers(final List<String> errors,
                                                  final String fieldValue, final String fieldName) {
        if (!fieldValue.matches("[0-9A-Za-z]+")) {
            String errorMessage = format(Messager.getMessage("error.field.letandnum"), Messager.getMessage(fieldName));
            errors.add(errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Checks the length of field value and adds error messages
     * to errors.
     * @param errors errors of user input strings
     * @param fieldValue value of checked field
     * @param fieldName name of checked field
     * @return if field value is correct
     */
    public boolean isLengthCorrect(final List<String> errors, final String fieldValue, final String fieldName) {
        int fieldValueLength = fieldValue.length();
        if (fieldValueLength > MAX_CHARACTERS || fieldValueLength < MIN_USER_FIELD_CHARACTERS) {
            String errorMessage = format(Messager.getMessage("error.field.length"),
                    Messager.getMessage(fieldName), MIN_USER_FIELD_CHARACTERS, MAX_CHARACTERS);
            errors.add(errorMessage);
            return false;
        }
        return true;
    }
}
