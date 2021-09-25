package int222.project.backend.exceptions;

import java.io.IOException;

public class ImageHandlerException extends IOException {

    ExceptionResponse.ERROR_CODE errorCode;
    public ImageHandlerException(String message, ExceptionResponse.ERROR_CODE error_code){
        super(message);
        this.errorCode = error_code;
    }

    public ExceptionResponse.ERROR_CODE getErrorCode(){
        return this.errorCode;
    }
}

