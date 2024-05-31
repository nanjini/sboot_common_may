package com.sboot.backend.common.core.api;

import jakarta.servlet.http.HttpServletRequest;
import com.sboot.backend.common.core.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) throws Exception{

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        HttpHeaders headers = new HttpHeaders();

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .resultUserMessage("Internal Server Error Exception")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest webRequest){

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        HttpHeaders headers = new HttpHeaders();

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.NOT_FOUND.value())
                .resultUserMessage("There is a problem with your account information")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.NOT_FOUND);
    }

    /**
     * @Valid Annotation 을 사용하여 유효성 검사를 수행할 때 발생하는 MethodArgumentNotValidException 예외를 처리하는 역할을 한다
     * @param ex the exception to handle
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param webRequest the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        //CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        //HttpHeaders headers = new HttpHeaders();
//        if (csrfToken != null) {
//            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
//        }


        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> fieldErrorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false),
                fieldErrorMessages);

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultUserMessage("An issue occurred with the method argument")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * ServletRequestBindingException은 Client의 요청을 서버의 메서드 인자에 바인딩하는 과정에서 문제가 발생했을 때 발생하는 예외이다.
     * 예를 들어, Client가 요청을 보낼 때 필요한 Header를 포함시키지 않았거나, 필요한 요청 파라미터를 제공하지 않았을 때 이런 예외가 발생할 수 있다.
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param webRequest the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        //CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        //HttpHeaders headers = new HttpHeaders();
//        if (csrfToken != null) {
//            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
//        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultUserMessage("Please check if the client has included the necessary headers or provided the required request parameters when sending the request")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.BAD_REQUEST);
    }


    /**
     * Client가 보낸 HTTP Message를 Server가 읽을 수 없을 때 발생하는 예외이다.
     * 예를 들어, Client가 JSON 형식의 데이터를 보냈는데, 이 데이터가 잘못된 형식이거나 파싱할 수 없는 경우 이 예외가 발생할 수 있다.
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param webRequest the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        //CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        //HttpHeaders headers = new HttpHeaders();
//        if (csrfToken != null) {
//            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
//        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultUserMessage("Unable to read HTTP message")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Client가 Server로 보낸 HTTP 요청의 메서드 (예: GET, POST, PUT, DELETE 등)가 서버에서 지원되지 않을 때 발생하는 예외이다
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param webRequest the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        //CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        //HttpHeaders headers = new HttpHeaders();
//        if (csrfToken != null) {
//            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
//        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.METHOD_NOT_ALLOWED.value()) // 405
                .resultUserMessage("The HTTP method of the request sent by the client is not supported by the server")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.METHOD_NOT_ALLOWED);

    }

    /**
     * Client가 요청한 URL에 해당하는 Controller나 요청 처리 Method가 없는 경우에 이 예외가 발생한다.
     * @param ex the exception to handle
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param webRequest the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) webRequest;
        HttpServletRequest request = attributes.getRequest();
        //CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        //HttpHeaders headers = new HttpHeaders();
//        if (csrfToken != null) {
//            headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
//        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        RestApiResponse<Object> response = RestApiResponse.builder()
                .result(errorDetails)
                .resultCode(HttpStatus.NOT_FOUND.value()) // 404
                .resultUserMessage("The resource requested by the client does not exist on the server")
                .build();

        return new ResponseEntity<Object>(response, headers, HttpStatus.NOT_FOUND);
    }
}
