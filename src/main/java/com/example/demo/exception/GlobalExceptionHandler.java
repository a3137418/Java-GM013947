package com.example.demo.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ApiResponse<?> handleBusinessException(BusinessException e){
		return ApiResponse.error(400, e.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ApiResponse<?> handleResourceNotFoundException(ResourceNotFoundException e){
		return ApiResponse.error(404, e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage(); // 提示：e.getBindingResult().getFieldErrors() 可以拿到所有欄位驗證錯誤
        return ApiResponse.error(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return ApiResponse.error(500, "系統錯誤");
    }
	
}
