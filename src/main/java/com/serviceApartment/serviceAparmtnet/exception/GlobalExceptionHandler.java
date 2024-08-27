//package com.serviceApartment.serviceAparmtnet.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
//        System.out.println("CustomException occurred: Message = " + ex.getMessage() + ", Status = " + ex.getStatus().value());
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getMessage(),
//                ex.getStatus().value()  // Convert HttpStatus to int
//        );
//        return new ResponseEntity<>(errorResponse, ex.getStatus());
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//
//        String errorMessage = ex.getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(error -> error.getDefaultMessage())
//                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
//                .orElse("Validation error");
//        System.out.println("ValidationException occurred: Message = " + errorMessage + ", Status = " + HttpStatus.BAD_REQUEST.value());
//        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        System.out.println("GenericException occurred: Message = " + ex.getMessage() + ", Status = " + HttpStatus.INTERNAL_SERVER_ERROR.value());
//        ErrorResponse errorResponse = new ErrorResponse(
//                "Unable to process your request",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // Other exception handlers can be added here
//}
package com.serviceApartment.serviceAparmtnet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        System.out.println("CustomException occurred: Message = " + ex.getMessage() + ", Status = " + ex.getStatus().value());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                ex.getStatus().value()  // Convert HttpStatus to int
        );
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Get the first validation error message
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()  // Use findFirst to get only the first error message
                .orElse("Validation error");

        System.out.println("ValidationException occurred: Message = " + errorMessage + ", Status = " + HttpStatus.BAD_REQUEST.value());

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        System.out.println("GenericException occurred: Message = " + ex.getMessage() + ", Status = " + HttpStatus.INTERNAL_SERVER_ERROR.value());
        ErrorResponse errorResponse = new ErrorResponse(
                "Unable to process your request",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Other exception handlers can be added here
}
