package dev.ramar.utils;

import dev.ramar.utils.exceptions.*;


import java.util.List;

public abstract class Action
{
    public Action nextAction;

    public Action(Action a)
    {
        nextAction = a;
    }

    public Action()
    {
        nextAction = null;
    }

    final public void doAction(Void... args) throws IncorrectArgsException
    {
        Void[] nextArgs = execute(args);
        if( nextAction != null )
            nextAction.doAction(nextArgs);
    }

    final public void addAction(Action a)
    {
        if( nextAction == null )
            nextAction = a;
        else if( a != this )
            nextAction.addAction(a);
    }


    protected abstract Void[] execute(Void... args) throws IncorrectArgsException;

    public abstract List<?> getArgsList();




}