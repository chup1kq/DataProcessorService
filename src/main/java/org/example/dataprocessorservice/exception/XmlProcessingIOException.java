package org.example.dataprocessorservice.exception;

public class XmlProcessingIOException extends XmlConversionException {
    public XmlProcessingIOException() {
        super(ExceptionName.XML_PROCESSING_IO_ERROR);
    }
}
