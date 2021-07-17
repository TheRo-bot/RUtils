package dev.ramar.utils.exceptions;

public class IncorrectArgsException extends Exception 
{
    public IncorrectArgsException(String message)
    {
        super(message);
    }

    public IncorrectArgsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}