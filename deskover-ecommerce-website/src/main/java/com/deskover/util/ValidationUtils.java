package com.deskover.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;



public class ValidationUtils {

    /**
     * Convert BindingResult to List<ValidationError>
     * @param bindingResult BindingResult to convert
     * @return List<ValidationError>
     */
    public static List<ValidationError> ConvertValidationErrors(BindingResult bindingResult) {
        List<ValidationError> errors = new ArrayList<>();
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(results -> {
                String message = results.getDefaultMessage();
                String field = results.getField();
                
                ValidationError error = new ValidationError();
                //error.setCode(field);
                error.setDefaultMessage(message);
                error.setField(field);
                
                errors.add(error);
            });
        }  
        return errors;
    }
}
