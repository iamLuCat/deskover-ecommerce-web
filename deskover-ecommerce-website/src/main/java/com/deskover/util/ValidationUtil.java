package com.deskover.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;



public class ValidationUtil {

    /**
     * Convert BindingResult to List<ValidationError>
     * @param bindingResult BindingResult to convert
     * @return List<ValidationError>
     */
	
    public static ValidationError ConvertValidationErrors(BindingResult bindingResult) {
        List<ValidationError> errors = new ArrayList<>();
        String result = "";
        if (bindingResult != null) {
            bindingResult.getFieldErrors().forEach(results -> {
                String message = results.getDefaultMessage();
      
                ValidationError error = new ValidationError();
                error.setMessage(message);
                errors.add(error);
            });
        }
        for (ValidationError validationError : errors) {
        	result+=validationError.getMessage()+", ";
		}
        ValidationError error = new ValidationError();
        error.setMessage(result);
        return error;
    }
}
