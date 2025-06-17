package xyz.cursedman.gym_api.controllers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.cursedman.gym_api.domain.dtos.ErrorDto;
import xyz.cursedman.gym_api.exceptions.NotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleException(Exception ex) {
		log.error("Caught exception", ex);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("An unknown error occurred");
		return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("Caught ConstraintViolationException", ex);
		ErrorDto errorDto = new ErrorDto();
		String message = ex.getConstraintViolations().stream()
			.findFirst()
			.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
			.orElse("A ConstraintViolationException error occurred");
		errorDto.setError(message);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDto> handleException(NotFoundException ex) {
		log.error("Caught NotFoundException", ex.getMessage());
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError(ex.getMessage());
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {
		log.error("Caught MethodArgumentNotValidException", ex);
		ErrorDto errorDto = new ErrorDto();

		String validationErrors = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.collect(Collectors.joining(", "));

		errorDto.setError(validationErrors);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		log.error("Caught HttpMessageNotReadableException", ex);
		ErrorDto errorDto = new ErrorDto();
		errorDto.setError("Required request body is missing");
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}


}
