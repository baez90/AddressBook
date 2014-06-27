package Model;

import Interfaces.IError;

import java.time.LocalDateTime;

/**
 * Speichert Fehler inkl. aller benötigten Infos
 *
 * @author baez
 */
public class Error implements IError {

    /**
     * Speichert in welcher Klasse der Fehler aufgetreten ist
     */
    private String ErrorClass;

    /**
     * Speichert in welcher Methode der Fehler aufgetreten ist
     */
    private String ErrorMethod;

    /**
     * Speichert bei Bedarf den Kontext
     */
    private String ErrorContext;

    /**
     * Speichert die genaue Exception
     */
    private String ErrorException;

    /**
     * Speichert den Zeitpunkt der Exception zur Nachverfolgung
     */
    private LocalDateTime ErrorTime;

    /**
     * Speichert die gesamte StackTrace für die Analyse
     */
    private StackTraceElement[] ExceptionStacktrace;

    /**
     * Default-Konstruktor
     */
    public Error() {
        ErrorClass = "";
        ErrorMethod = "";
        ErrorContext = "";
        ErrorException = "";
        ErrorTime = LocalDateTime.now();
        ExceptionStacktrace = new StackTraceElement[0];
    }

    /**
     * Kompletter Konstruktor
     *
     * @param errorClass          Klasse in welcher der Fehler aufgereten ist als String
     * @param errorMethod         Methode in welcher der Fehler aufgetreten ist als String
     * @param errorContext        Context des Fehlers als String
     * @param errorException      Exception als String
     * @param exceptionStacktrace gesamte Stacktrace zur genaueren Analyse
     */
    public Error(String errorClass, String errorMethod, String errorContext, String errorException, StackTraceElement[] exceptionStacktrace) {
        ErrorClass = errorClass;
        ErrorMethod = errorMethod;
        ErrorContext = errorContext;
        ErrorException = errorException;
        ErrorTime = LocalDateTime.now();
        ExceptionStacktrace = exceptionStacktrace;
    }

    /**
     * Standard-Getter
     *
     * @return Klasse des Errors als String
     */
    @Override
    public String getErrorClass() {
        return ErrorClass;
    }

    /**
     * Standard-Setter
     *
     * @param errorClass Klasse in welcher der Fehler aufgereten ist als String
     */
    @Override
    public void setErrorClass(String errorClass) {
        ErrorClass = errorClass;
    }

    /**
     * Standard-Getter
     *
     * @return Methode in welcher der Fehler aufgetreten ist als String
     */
    @Override
    public String getErrorMethod() {
        return ErrorMethod;
    }

    /**
     * Standard-Setter
     *
     * @param errorMethod Methode in welcher der Fehler aufgetreten ist als String
     */
    @Override
    public void setErrorMethod(String errorMethod) {
        ErrorMethod = errorMethod;
    }

    /**
     * Standard-Getter
     *
     * @return Context des Fehler als String
     */
    @Override
    public String getErrorContext() {
        return ErrorContext;
    }

    /**
     * Standard-Setter
     *
     * @param errorContext Kontext des Fehlers
     */
    @Override
    public void setErrorContext(String errorContext) {
        ErrorContext = errorContext;
    }

    /**
     * Standard-Getter
     *
     * @return Exception als String
     */
    @Override
    public String getErrorException() {
        return ErrorException;
    }

    /**
     * Standard-Setter
     *
     * @param errorException Exception als String
     */
    @Override
    public void setErrorException(String errorException) {
        ErrorException = errorException;
    }

    /**
     * Standard-Getter
     *
     * @return Zeitpunkt zu welchem der Error gemeldet wurde
     */
    @Override
    public LocalDateTime getErrorTime() {
        return ErrorTime;
    }

    /**
     * Standard-Setter
     *
     * @param errorTime Zeitpunkt zu welchem der Fehler aufgetreten ist
     */
    @Override
    public void setErrorTime(LocalDateTime errorTime) {
        ErrorTime = errorTime;
    }

    /**
     * Standard-Getter
     *
     * @return StackTrace der Exception
     */
    @Override
    public StackTraceElement[] getExceptionStracktrace() {
        return ExceptionStacktrace;
    }

    /**
     * Standard-Setter
     *
     * @param exceptionStacktrace Stacktrace der aufgetretenen Exception
     */
    @Override
    public void setExceptionStacktrace(StackTraceElement[] exceptionStacktrace) {
        ExceptionStacktrace = exceptionStacktrace;
    }
}
