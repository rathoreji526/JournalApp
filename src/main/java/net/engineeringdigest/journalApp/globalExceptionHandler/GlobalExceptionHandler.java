package net.engineeringdigest.journalApp.globalExceptionHandler;

import net.engineeringdigest.journalApp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(CustomAccessDeniedException ex){
        return new ResponseEntity<>("You are not allowed to perform this action!" , HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException(InvalidEmailException ex){
        return new ResponseEntity<>("The email you entered is not correct!" , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JournalEntryWithSameTitleException.class)
    public ResponseEntity<String> handleJournalEntryWithSameTitleException(JournalEntryWithSameTitleException ex){
        return new ResponseEntity<>("Journal entry with same title is not allowed." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoChangeException.class)
    public ResponseEntity<String> handleNoChangeException(NoChangeException ex){
        return new ResponseEntity<>("Please make any kind of change." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex){
        return new ResponseEntity<>("Not found!" , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NullContentException.class)
    public ResponseEntity<String> handleNullContentException(NullContentException ex){
        return new ResponseEntity<>("Can not perform this action with empty content." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OTPExpiredException.class)
    public ResponseEntity<String> handleOTPExpiredException(OTPExpiredException ex){
        return new ResponseEntity<>("OTP expired." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity<String> handlePasswordLengthException(PasswordLengthException ex){
        return new ResponseEntity<>("Minimum password length should be 6." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>("Resource not found." , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<String> handleSamePasswordException(SamePasswordException ex){
        return new ResponseEntity<>("New password can not be same as previous password." , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TooFastOTPRequestException.class)
    public ResponseEntity<String> handleTooFastOTPRequestException(TooFastOTPRequestException ex){
        return new ResponseEntity<>("You can request new OTP after 2 minutes." , HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return new ResponseEntity<>("This username is already exists!" , HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<String> handleUserNotExistsException(UserNotExistsException ex){
        return new ResponseEntity<>("User not found with this username!" , HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(WrongOTPException.class)
    public ResponseEntity<String> handleWrongOTPException(WrongOTPException ex){
        return new ResponseEntity<>("Incorrect OTP" , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException ex){
        return new ResponseEntity<>("Wrong password!" , HttpStatus.UNAUTHORIZED);
    }
}
