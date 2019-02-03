/**
 * Tori Windrich
 * 3/18/2018
 * Project 3: Tie Fighter Patrols
 */
package TieFighter;

public class Node <E>
{
    //data being stored in the node (generic)
    private E var;
    //next and previous values
    protected Node next;
    protected Node prev;
    
    //creates a Node with passed in object as the data
    public Node(E o)
    {
        var = o;
    }
    //creates a completely empty Node (default constructor)
    public Node()
    { 
        var = null;
        next = null;
        prev = null;
    }
    //returns next value
    public Node getNext()
    {
        return next;
    }
    //returns prev value
    public Node getPrev()
    {
        return prev;
    }
    //returns the object stored in var
    public E getVar()
    {
        return var;
    }
    //changes var to the passed in object
    public void setVar(E o)
    {
        var = o;
    }
    //sets the next value
    public void setNext(Node nxt)
    {
        next = nxt;
    }
    //sets the prev value
    public void setPrev(Node prv)
    {
        prev = prv;
    }
}
