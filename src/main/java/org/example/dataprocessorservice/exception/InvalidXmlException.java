package org.example.dataprocessorservice.exception;

public class InvalidXmlException extends XmlConversionException {
    public InvalidXmlException() {
        super(ExceptionName.INVALID_XML);
    }
}
