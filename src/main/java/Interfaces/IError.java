package Interfaces;

import java.time.LocalDateTime;

/**
 * @author baez
 */
public interface IError {
    /**
     * Standard-Getter
     *
     * @return Klasse des Errors als String
     */
    public String getErrorClass();

    /**
     * Standard-Setter
     *
     * @param errorClass Klasse in welcher der Fehler aufgereten ist als String
     */
    public void setErrorClass(String errorClass);

    /**
     * Standard-Getter
     *
     * @return Methode in welcher der Fehler aufgetreten ist als String
     */
    public String getErrorMethod();

    /**
     * Standard-Setter
     *
     * @param errorMethod Methode in welcher der Fehler aufgetreten ist als String
     */
    public void setErrorMethod(String errorMethod);

    /**
     * Standard-Getter
     *
     * @return Context des Fehler als String
     */
    public String getErrorContext();

    /**
     * Standard-Setter
     *
     * @param errorContext Kontext des Fehlers
     */
    public void setErrorContext(String errorContext);

    /**
     * Standard-Getter
     *
     * @return Exception als String
     */
    public String getErrorException();

    /**
     * Standard-Setter
     *
     * @param errorException Exception als String
     */
    public void setErrorException(String errorException);

    /**
     * Standard-Getter
     *
     * @return Zeitpunkt zu welchem der Error gemeldet wurde
     */
    public LocalDateTime getErrorTime();

    /**
     * Standard-Setter
     *
     * @param errorTime Zeitpunkt zu welchem der Fehler aufgetreten ist
     */
    public void setErrorTime(LocalDateTime errorTime);

    /**
     * Standard-Getter
     *
     * @return StackTrace der Exception
     */
    public StackTraceElement[] getExceptionStracktrace();

    /**
     * Standard-Setter
     *
     * @param exceptionStacktrace Stacktrace der aufgetretenen Exception
     */
    public void setExceptionStacktrace(StackTraceElement[] exceptionStacktrace);
}
