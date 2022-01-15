package dev.ramar.utils.nodes;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;


/*
Node: DoubleNode
 - a **NON DIRECTED** node with two links
 - none of this front/back or next/prev shit. 
   it's one/two baby.
*/
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


    public boolean equals(Object in)
    {   
        boolean isEqual = false;
        if( in instanceof DoubleNode )
        {
            DoubleNode<E> n = (DoubleNode<E>)in;

            // if value == n.value == null is essentially
            // the main case that matters here, but if they're
            // identically similar, then yeah keep on going
            if( ( value == n.value                       ) || 
                ( value != null && value.equals(n.value) ) )
            {
                if( ( one == n.one      ) || 
                    ( one.equals(n.one) ) )
                {
                    if( ( two == n.two      ) ||
                        ( two.equals(n.two) ) )
                    {
                        isEqual = true;
                    }

                }
            }
        }

        return isEqual;
    }



    /* Get Methods
    --===------------
    */

    public Node<E> getLink(int i)
    {
        Node<E> out = null;
        if( i == 0 )
            out = one;
        else if( i == 1 )
            out = two;
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");
        return out;
    }


    public E get(int i)
    {
        Node<E> n = getLink(i);
        return n != null ? n.getValue() : null;
    }




    /* Add Methods
    --===------------
    */

    public void addLink(Node<E> node)
    {
        boolean shouldDo = one == null || two == null;

        Node<E> old = null, out = null;

        if( one == null )
        {
            old = one;
            out = node;
            one = out;
        }
        else if( two == null )
        {
            old = two;
            out = node;
            two = out;
        }
        else
            throw new IllegalStateException("Cannot add more than 2 nodes to a DoubleNode");

        onChange_link(old, out);
    }


    /*
    Modifier: add
     - We can't wrap add() in addLink() here since
       we don't know where addLink() adds (assuming it does)
       so basically just re-use the code  
    */
    public Node<E> add(E val)
    {
        boolean shouldDo = one == null || two == null;

        Node<E> old = null, out = null;

        if( one == null )
        {
            old = one;
            out = getNodeBuilder().build(val);
            one = out;
        }
        else if( two == null )
        {
            old = two;
            out = getNodeBuilder().build(val);
            two = out;
        }
        else
            throw new IllegalStateException("Cannot add more than 2 nodes to a DoubleNode");

        onChange_link(old, out);

        return out;
    }


    /* Remove Methods
    --===-----------------
    */

    public boolean removeLink(Node<E> n)
    {
        boolean removed = false;
        if( one != null && one.equals(n) )
        {
            one = null;
            onChange_link(n, null);
            removed = true;
        }
        else if( two != null && two.equals(n) )
        {
            two = null;
            onChange_link(n, null);
            removed = true;
        }

        return removed;
    }


    public Node<E> remove(int i)
    {
        Node<E> old;
        if( i == 0 )
        {
            old = one;
            one = null;
        }
        else if( i == 1 )
        {
            old = two;
            two = null;
        }
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");

        onChange_link(old, null);
        return old;
    }




    /* Set Methods
    --===-------------
     - the returned Node<E> is the node already at <i>
    */

    public Node<E> setLink(int i, Node<E> val)
    {
        Node<E> replaced;
        if( i == 0 )
        {
            replaced = one;
            one = val;
        }
        else if( i == 1 )
        {
            replaced = two;
            two = val;
        }
        else
            throw new IndexOutOfBoundsException(i + " must be in bounds (0:1)");

        onChange_link(replaced, val);

        return replaced;
    }

    public Node<E> set(int i, E val)
    {
        return setLink(i, getNodeBuilder().build(val));
    }


    /* Utility Methods
    --===-----------------
    */

    public int size()
    {
        return 0 + (one != null ? 1 : 0) + (two != null ? 1 : 0);
    }


    public void clear()
    {
        Node<E> oOne = one,
                oTwo = two;

        onChange_link(oOne, null);
        onChange_link(oTwo, null);
    }


    public boolean isConnected(Node<E> to)
    {
        return (one != null && one.equals(to)) || 
               (two != null && two.equals(to)) ; 
    }



    public List<Node> getLinks()
    {
        ArrayList<Node> out = new ArrayList<>();
        if( one != null )
            out.add(one);
        if( two != null )
            out.add(two);
        
        return out;
    }




    protected DoubleNodeBuilder<E> getNodeBuilder()
    {   return new DoubleNodeBuilder<E>();  }

    /*
    NodeBuilder: DoubleNodeBuilder
     - Builds DoubleNodes
    */
    protected static class DoubleNodeBuilder<V>
    {
        protected Node<V> build(V val, Node... links)
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
                    n1.remove(0);

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
                    n1.listeners.value.add((String from, String to) ->
                    {
                        listenerCheck = toChange.equals(to);
                    }); 
                    n1.setValue(toChange);

                    return listenerCheck;
                }
            });

            /*
            Test: equality
             - tests that two nodes are equal/inequal in all cases
            */
            addTest(new Test()
            {
                public String getName() { return "equality"; }

                public boolean start()
                {
                    DoubleNode<String> n1 = new DoubleNode<>("1");
                    DoubleNode<String> n2 = new DoubleNode<>("1");
                    // equal
                    boolean check1 = n1.equals(n2);

                    DoubleNode<String> n3 = new DoubleNode<>("2");
                    // inequal
                    boolean check2 = ! n1.equals(n3);

                    n1.setLink(0, n2);
                    n2.setLink(0, n2);
                    // equal
                    boolean check3 = n1.equals(n2);

                    n1.setValue(null);
                    boolean check4 = !n1.equals(n2);

                    n2.setValue(null);
                    n1.setValue("1");
                    boolean check5 = !n1.equals(n2);

                    n1.setLink(1, n1);
                    // inequal
                    boolean check6 = !n1.equals(n2);


                    return check1 && check2 && check3 && check4 && check5;
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
                catch(Exception e) 
                {
                    System.out.println("Fatal exception occurred mid test!");
                    e.printStackTrace();
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
            System.out.println("\nDoubleNode Test results:");

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