package makersharks.user.config;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetail> handleGlobalException(Exception exception, WebRequest webRequest) {
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<ExceptionDetail> handleUserExistException(UserExistException exception, WebRequest webRequest){
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<ExceptionDetail> handleInternalServerException(InternalServerException exception, WebRequest webRequest){
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetail,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionDetail> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest){
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionDetail> handleUnauthorizedException(UnauthorizedException exception, WebRequest webRequest){
		ExceptionDetail errorDetail = new ExceptionDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetail,HttpStatus.UNAUTHORIZED);
	}
	
	
	
	


}
