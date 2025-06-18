package org.example.dataprocessorservice.exception;

public class XmlParserConfigurationException extends XmlConversionException {
  public XmlParserConfigurationException() {
    super(ExceptionName.XML_PARSER_ERROR);
  }
}
