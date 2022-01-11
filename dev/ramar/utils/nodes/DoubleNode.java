package dev.ramar.utils.nodes;

import dev.ramar.utils.nodes.Node.NodeBuilder;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

public class DoubleNode<E> extends Node<E>
{
    private Node<E> one, two;

    public DoubleNode(E val)
    {
        super(val);
        one = two = null;
    }

    public DoubleNode(E val, Node<E> one, Node<E> two)
    {
        super(val);
        this.one = one;
        this.two = two;
    }

    public String toString()
    {
        String out = "";

        if( two != null )
            out += two.value.toString() + " <-- ";

        out += value.toString();

        if( one != null )
            out += " --> " + one.value.toString(); 

        return out;
    }



    public List<Node<E>> getLinks()
    {
        List<Node<E>> out = new ArrayList<>();
        out.add(one);
        out.add(two);

        return out;
    }

    public Node<E> getLink(int n)
    {
        Node<E> out = null;
        if( n == 0 )
            out = one;
        else if( n == 1 )
            out = two;
        else
            throw new IndexOutOfBoundsException(n + " must be in bounds (0:1)");
        return out;
    }

    public Node<E> setLink(int i, E val)
    {
        Node<E> out = null;
        if( i == 0 )
        {
            one = getNodeBuilder().build(val);
            out = one;
        }
        else if( i == 1 )
        {
            two = getNodeBuilder().build(val);
            out = two;
        }
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");

        return out;
    }

    public void setLink(int i, Node<E> val)
    {
        if( i == 0 )
            one = val;
        else if( i == 1 )
            two = val;
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");
    }


    public Node<E> removeLink(int i)
    {
        Node<E> exp = null;
        if( i == 0 )
        {
            exp = one;
            one = null;
        }
        else if( i == 1 )
        {
            exp = two;
            two = null;
        } 
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");
        return exp;
    }

    public void clear()
    {
        one = two = null;
    }


    public Node<E> add(E val)
    {
        boolean shouldDo = one == null || two == null;

        if(! shouldDo )
            throw new IllegalStateException("Cannot add more than 2 nodes to a DoubleNode");

        Node<E> n = getNodeBuilder().build(val);

        if( one == null )
            one = n;
        else
            two = n;

        return n;
    }

    protected NodeBuilder getNodeBuilder()
    {
        return new DoubleNodeBuilder();
    }


    protected static class DoubleNodeBuilder<V> extends NodeBuilder<V>
    {
        protected DoubleNodeBuilder()
        {

        }

        Node<V> build(V val, Node... links)
        {
            DoubleNode<V> dn = new DoubleNode(val);

            if( links.length > 3 )
                throw new IllegalArgumentException("Only two links can be established to a DoubleNode");

            if( links.length > 1 )
                dn.one = links[0];

            if( links.length > 2 )
                dn.two = links[1];

            return dn;
        }

    }


    /* Testing
    --===--------
    */


    public static void main(String[] args)
    {
        if( args.length >= 1 && args[0].equals("--test") )
            test();
    }


    private static void test()
    {
        Tests t = new Tests();
        t.start();
        t.outputResults();
    }

    private static class Tests
    {
        Map<Test, Boolean> testsPassed = new HashMap<>();

        private interface Test
        {
            public boolean start();

            public String getName();
        }

        private void addTest(Test t)
        {
            testsPassed.put(t, false);
        }

        public Tests()
        {

            /*
            Test: add
             - Tests the adding functionality of DoubleNode
             - basically is just boundary checking
            */
            addTest(new Test()
            {
                public String getName() { return "add"; }

                public boolean start()
                {
                    boolean pass = false;

                    // basically we only want an IllegalStateException
                    // when the third node is added, anything prior is a fail
                    DoubleNode dn = new DoubleNode("1");
                    try
                    {
                        dn.add("2");
                        dn.add("3");
                    }
                    catch(IllegalStateException e)
                    { /* (pass == false) already, just don't crash */ }

                    try
                    {
                        dn.add("4");
                    }
                    catch(IllegalStateException e)
                    {// we did it!
                        pass = true;
                    }
                    return pass;
                }
            });

            /*
            Test: get/set/remove link validation
             - Validates expected results of set / get / remove link methods
            */
            addTest(new Test()
            {
                public String getName() { return "get/set/remove link validation"; }

                public boolean start()
                {
                    DoubleNode n1 = new DoubleNode("1");

                    DoubleNode n2 = new DoubleNode("2");
                    n1.setLink(0, n2);

                    // check if n2 is actually the link 0
                    boolean check1 = n2 == n1.getLink(0);

                    // once we remove n2 (as it's link 0)
                    n1.removeLink(0);

                    // we want to validate that getLink shows null
                    boolean check2 = n1.getLink(0) == null;

                    return check1 && check2;
                }
            });

            /*
            Test: listeners
             - Tests the value listening functionality of the abstract Node
             - DoubleNode doesn't change how this works, but if there's anything
               wrong there it should be caught here, just in case
            */
            addTest(new Test()
            {
                private boolean listenerCheck = false;
                public String getName() { return "listeners"; }
                public boolean start()
                {
                    listenerCheck = false;
                    final String toChange = "2";
                    DoubleNode<String> n1 = new DoubleNode<>("1");
                    n1.listeners.add((String val) ->
                    {
                        listenerCheck = toChange.equals(val);
                    }); 
                    n1.setValue(toChange);

                    return listenerCheck;
                }
            });
        }   

        private void start()
        {
            Map<Test, Boolean> newMap = new HashMap<>();
            for( Test t : testsPassed.keySet() )
            {
                boolean passed = false;
                try
                {
                    passed = t.start();
                }
                finally
                {
                    newMap.put(t, passed);
                }
            }
            // just in case someone wants to do more with the results later
            // we want to commit our new map of results
            testsPassed = newMap;

        }

        private void outputResults()
        {
            System.out.println("\nTest results:");

            String[] messages = new String[testsPassed.size()];

            int ii = 0;

            for( Test t : testsPassed.keySet() )
            {
                int maxNameLen = 0;
                for( Test u : testsPassed.keySet() )
                {
                    if( maxNameLen < u.getName().length() )
                        maxNameLen = u.getName().length();
                }


                messages[ii] = "";
                int targetLen = maxNameLen - t.getName().length();
                for( int jj = 0; jj < targetLen; jj++ )
                    messages[ii] += " ";

                messages[ii] += t.getName() + " || " + (testsPassed.get(t) != null && testsPassed.get(t) ? "Passed" : "Failed"); 
                ii++;
            }


            for( int jj = 0; jj < messages.length; jj++ )
                System.out.println(messages[jj]);

            System.out.println();
        }


   
    }

}