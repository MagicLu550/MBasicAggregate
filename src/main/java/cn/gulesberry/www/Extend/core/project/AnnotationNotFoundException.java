package cn.gulesberry.www.Extend.core.project;

public class AnnotationNotFoundException extends Exception{
    public AnnotationNotFoundException() {
        super();
    }


    public AnnotationNotFoundException(String message) {
        super(message);
    }


    public AnnotationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    protected AnnotationNotFoundException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
