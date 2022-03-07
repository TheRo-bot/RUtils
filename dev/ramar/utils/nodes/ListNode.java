package dev.ramar.utils.nodes;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

public class ListNode<E> extends Node<E>
{
    private ArrayList<Node<E>> nodes = new ArrayList<>();


    public ListNode(E val)
    {
        super(val);
    }    


    public String toString()
    {
        String out = value + " -> [";
        for( Node<E> node : nodes )
            out += node.value + ", ";

        return out.substring(0, out.length() - 2) + "]";
    }

    public boolean equals(Object in)
    {
        boolean isEqual = false;

        if( in instanceof ListNode )
        {
            ListNode<E> ln = (ListNode<E>)in;
            if( ( value == null && ln.value == null ) ||
                ( value.equals(ln.value)            ) )
            {
                if( nodes.equals(ln.nodes) )
                    isEqual = true;
            }
        }

        return isEqual;
    }



    /* Get Methods
    --===------------
    */


    public Node<E> getLink(int i)
    {
        if( i < 0 || i >= nodes.size())
            throw new IndexOutOfBoundsException(i + " must be in bounds (0" + nodes.size() + ")");
        return nodes.get(i);
    }

    public E get(int i)
    {
        Node<E> n = getLink(i);

        E out = null;
        if( n != null )
            out = n.getValue();

        return out;
    }


    /* Add Methods
    --===------------
    */

    public void addLink(Node<E> val)
    {
        nodes.add(val);
        onChange_link(null, val);
    }

    public Node<E> add(E val)
    {
        Node<E> n = getNodeBuilder().build(val);
        addLink(n);
        
        return n;
    }


    /* Remove Methods
    --===-----------------
    */

    public boolean removeLink(Node<E> n)
    {   
        return nodes.remove(n);
    }


    public Node<E> remove(int i)
    {
        return nodes.remove(i);
    }


    /* Set Methods
    --===-------------
    */

    public Node<E> setLink(int i, Node<E> val)
    {
        return nodes.set(i, val);
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
        return nodes.size();
    }


    public void clear()
    {
        nodes.clear();
    }


    public boolean isConnected(Node<E> to)
    {
        return nodes.contains(to);
    }


    public List<Node> getLinks()
    {
        ArrayList<Node> out = new ArrayList<>();
        
        for( Node<E> n : nodes )
            if( n != null )
                out.add(n);

        return out;
    }







    // public List<Node<E>> getLinks()
    // {
    //     return nodes;
    // }




    // public Node<E> setLink(int i, E val)
    // {
    //     nodes.set(i, getNodeBuilder().build(val));
    //     return nodes.get(i);
    // }


    // public Node<E> setLink(int i, Node<E> val)
    // {
    //     /*
    //     so basically, we want the ArrayList to always
    //     be able to take a setLink argument, since it can have
    //     n elements but is also explicitly ordered and gaps can exist.
    //     so basically, we always ensure <i> can be set within the
    //     confines of the list
    //     */
    //     while(nodes.size() <= i )
    //         nodes.add(null);

    //     Node<E> replaced = nodes.set(i, val);
    //     int ii = 0;
    //     while(replaced != null)
    //     {
    //         while(nodes.size() <= ii )
    //             nodes.add(null);
    //         replaced = nodes.set(ii, replaced);

    //         ii++;
    //     }

    //     onChange_link(val, true);
    // }


    // public Node<E> removeLink(int i)
    // {
    //     return nodes.remove(i);
    // }

    // public boolean isConnected(Node<E> n)
    // {
    //     return nodes.contains(n);
    // }

    // public boolean remove(Node<E> n)
    // {
    //     return nodes.remove(n);
    // }

    // public void clear()
    // {
    //     nodes.clear();
    // }

    // public Node<E> add(E val)
    // {
    //     Node<E> n = getNodeBuilder().build(val);
    //     add(n);
    //     return n;
    // }

    // public void add(Node<E> val)
    // {
    //     nodes.add(val);
    // }


    protected ListNodeBuilder<E> getNodeBuilder()
    {  return new ListNodeBuilder<E>();  }

    protected static class ListNodeBuilder<V extends Object>
    {
        protected Node<V> build(V val, Node... links)
        {
            ListNode<V> node = new ListNode<>(val);

            for( Node n : links )
                node.addLink(n);
            return node;
        }

    }

    public static void main(String[] args)
    {
        ListNodeTests lnt = new ListNodeTests();
        lnt.start();
        lnt.outputResults();
    }


    static class ListNodeTests
    {
        Map<Test, Boolean> tests = new HashMap<>();
        ListNodeTests()
        {
            /*
            Test: equality
             - Tests ListNode equality
            */
            addTest(new Test()
            {
                public String getName() { return "equality"; }
                public boolean start()
                {
                    ListNode<String> n1 = new ListNode<>("1");
                    ListNode<String> n2 = new ListNode<>("1");

                    boolean check1 = n1.equals(n2);


                    return check1;
                }
            });

            addTest(new Test()
            {
                public String getName() { return "node magic"; }
                public boolean start()
                {
                    ListNode<String> n1 = new ListNode<>("1");
                    ListNode<String> n100 = (ListNode<String>)n1.add("2").add("3");
                    n100.add("4");

                    n1.add("_2").add("_3");


                    boolean check1 = false;

                    if( n1.getLink(0).getValue().equals("2") )
                        if( n1.getLink(0).getLink(0).getValue().equals("3") )
                            if( n1.getLink(1).getValue().equals("_2") )
                                if( n1.getLink(1).getLink(0).getValue().equals("_3") )
                                    check1 = true;
                                else
                                    System.out.println("n1.1.0 != _3");
                            else
                                System.out.println("n1.1 != _2");
                        else
                            System.out.println("n1.0.0 != 3");
                    else
                        System.out.println("n1.0 != 2");


                    boolean check2 = n1.traverse("0,0") == n100;

                    return check1 && check2;

                }
            });

            addTest(new Test()
            {
                public String getName() { return "get/set/remove nodes"; }
                public boolean start()
                {

                    ListNode<String> n1 = new ListNode<>("1"),
                                     n2 = new ListNode<>("2"),
                                     n3 = new ListNode<>("3");

                    n1.setLink(0, n2);
                    n1.setLink(1, n3);

                    boolean check1 = n1.getLink(0).getValue().equals("2");

                    boolean check2 = n1.remove(0).getValue().equals("2");

                    return check1 && check2;
                }

            });

        }


        private void addTest(Test t)
        {
            tests.put(t, false);
        }

        public void start()
        {
            Map<Test, Boolean> newMap = new HashMap<>();
            for( Test t : tests.keySet() )
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
            tests = newMap;
        }

        interface Test
        {
            public String getName();
            public boolean start();
        }

        private void outputResults()
        {
            System.out.println("\nListNode Test results:");

            String[] messages = new String[tests.size()];

            int ii = 0;

            for( Test t : tests.keySet() )
            {
                int maxNameLen = 0;
                for( Test u : tests.keySet() )
                {
                    if( maxNameLen < u.getName().length() )
                        maxNameLen = u.getName().length();
                }


                messages[ii] = "";
                int targetLen = maxNameLen - t.getName().length();
                for( int jj = 0; jj < targetLen; jj++ )
                    messages[ii] += " ";

                messages[ii] += t.getName() + " || " + (tests.get(t) != null && tests.get(t) ? "Passed" : "Failed"); 
                ii++;
            }


            for( int jj = 0; jj < messages.length; jj++ )
                System.out.println(messages[jj]);

            System.out.println();
        }


    }


}