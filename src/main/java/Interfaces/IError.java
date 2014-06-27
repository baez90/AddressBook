package Interfaces;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

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
     * Getter für Error-Console
     *
     * @return ErrorClass als StringProperty
     */
    public StringProperty getErrorClassProperty();

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
     * Getter für Error-Console
     *
     * @return ErrorMethod als StringProperty
     */
    public StringProperty getErrorMethodProperty();

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
     * Getter für Error-Console
     *
     * @return ErrorContext als StringProperty
     */
    public StringProperty getErrorContextProperty();

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
     * Getter für ErrorConsole
     *
     * @return ErrorException als StringProperty
     */
    public StringProperty getErrorExceptionProperty();

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
     * Getter für die Error-Console
     *
     * @return ErrorTime als ObjectProperty
     */
    public ObjectProperty<LocalDateTime> getErrorTimeProperty();

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
