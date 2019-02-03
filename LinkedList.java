/**
 * Tori Windrich
 * 3/18/2018
 * Project 3: Tie Fighter Patrols
 */
package TieFighter;
//(Main.java) MERGE SORT CALLS FOUND: LINES 132,136,147,151
//(LinkedList.java) MERGE SORT IMPLEMENTATION: LINES 160 TO 350... RECURSIVE TOSTRING IMPLEMENTATION: LINES 355 TO 367


public class LinkedList 
{
    private Node head = null; //front of list
    private Node tracker = head; //for use in recursive toString method
    private Node tail = null; //end of list
    
    //default constructor defaults head and tail to null (empty list)
    public LinkedList()
    {
        head = null;
        tail = null;
    }
    
    //overloaded constructor takes in a head node and assigns it to head, then properly assigns tail
    public LinkedList(Node h)
    {
        head = h;
        Node trav = head;
        while(trav.next != null)
        {
            trav = trav.next;
        }
        tail = trav;
    }
    
    //method getLength traverses the list while the traversal node isn't null,
    //increments the length integer each time and returns at the end
    public int getLength()
    {
        Node trav = head;
        int len = 0;
        while(trav != null)
        {
            trav = trav.next;
            len++;
        }
        return len;
    }
    
    //returns head node
    public Node getHead()
    {
        return head;
    }
    
    //returns tail node
    public Node getTail()
    {
        return tail;
    }
    
    //sets the head node to the passed in node
    public void setHead(Node h)
    {
        head = h;
    }
    
    //returns the node at the specified index
    public Node getNode(int index)
    {
        int i = 0;
        //if the index is too bigi or too small, just return null
        if (index >= getLength() || index < 0)
            return null;
        //traverse the list while integer i is still less than index (stops once it equals index)
        Node trav = head;
        while(i < index)
        {
            trav = trav.next;
            i++;
        }
        //return the node at the index
        return trav;
    }
    
    //adds a node to the end of a list
    public void append(Node add)
    {
        //if head is null, makes head and tail equal to the new node
        if(head == null)
        {
            head = add;
            add.prev = null;
            tail = head;
        }
        //else if there's only one node in the list, add the new node
        //after head, connect the previous properly, and point tail to the new node
        else if (head.next == null)
        {
            head.next = add;
            add.prev = head;
            tail = add;
        }
        //otherweise, make tail's next value be the new node, connect the new node's
        //previous value to the old tail, and then move tail
        else
        {
            tail.next = add;
            add.prev = tail;
            tail = add;
        }
        //set tracker to head again in case head changed
        tracker = head;
    }
    
    //searches for the specified name in a node's payload (returns the index of the list in which it was found)
    public int searchName(String nm)
    {
        int pos = 0;
        Node trav = head;
        
        //while the traversal node isn't null and the payload's pilot name is not equal to the passed in name, move trav forward and increment position
        while(trav != null && ((Payload)(trav.getVar())).getPilot().compareTo(nm) != 0)
        {
            trav = trav.next;
            pos++;
        }
        //if trav is null, name wasn't found, return -1
        if (trav == null)
            return -1;
        //otherwise return the position at which the name was found
        return pos;
    }
    
    //searches for the specified area in a node's payload (returns the index of the list in which it was found)
    public int searchArea(double ar)
    {
        int pos = 0;
        Node trav = head;
        
        //while the traversal node isn't null and the payload's area is not equal to the passed in area, move trav forward and increment position
        while(trav != null && ((Payload)(trav.getVar())).getArea() != ar)
        {
            trav = trav.next;
            pos++;
        }
        //if trav is null, area wasn't found, return -1
        if (trav == null)
            return -1;
        //otherwise retur the position at which the name was found
        return pos;
    }
    
    //MergeSort calls the merge sort function, which returns the new head value
    //the MergeSort overloaded correctly links all of the next values, so then
    //this helper function will go through the list, connecting the prev values
    //correctly, followed by pointing tail at the correct location
    public void MergeSort(String direction, String type)
    {
        head = MergeSort(head,direction,type);
        head.prev = null;
        tracker = head;
        Node trav = head;
        while(trav.next != null)
        {
            trav.next.prev = trav;
            trav = trav.next;
        }
        tail = trav;
    }
    
    //MergeSort is the recursive function that splits the passed in list in half,
    //calling mergeAsc or mergeDsc accordingly. (called from its helper function as seen above)
    public Node MergeSort(Node headOriginal, String direction, String type) 
    {
        //if the list is empty or just one element, return it as the sorted list
        if (headOriginal == null || headOriginal.next == null) {
            return headOriginal;
        }
        //otherwise, use two traversal Nodes to find the halfway point of the list
        else {
            Node a = headOriginal;
            Node b = headOriginal.next;
            while ((b != null) && (b.next != null)) 
            {
                headOriginal = headOriginal.next;
                b = (b.next).next;
            }
            //set b back to the middle of the list, break the connection between the two halves
            b = headOriginal.next;
            headOriginal.next = null;
            //if this is an ascending order sort, call mergeAsc, passing in MergeSort of each half
            //otherwise, do the same with mergeDsc
            if(direction.compareTo("asc") == 0)
                return mergeAsc(MergeSort(a, direction, type), MergeSort(b, direction, type), type);
            else
                return mergeDsc(MergeSort(a, direction, type), MergeSort(b, direction, type), type);
        }
        
    }
    
    //mergeAsc will compare the passed in lists and if they aren't in order, orders
    //them in ascending order via swapping (only using next values, prev values are fixed
    //in the mergeSort helper function), based on which type the sort needs to happen by
    public Node mergeAsc(Node a, Node b, String type) 
    {
        Node temp = new Node();
        Node hold = temp;
        Node swap = hold;
        Payload pay1;
        Payload pay2;
        //if the type is name
        if(type.compareTo("name") == 0)
        {
            //going through the list until the end
            while ((a != null) && (b != null)) 
            {
                //sets comparePilot to true, uses payload's compare to,
                //swaps if necessary
                pay1 = (Payload)a.getVar();
                pay2 = (Payload)b.getVar();
                pay1.setComparePilot(true);
                if (pay1.compareTo(pay2) <= 0) 
                {
                    swap.next = a;
                    swap = a;
                    a = a.next;
                }
                else 
                {
                    swap.next = b;
                    swap = b;
                    b = b.next;
                }
            }
            //properly reconnects based on which was null
            if(a == null)
                swap.next = b;
            else
                swap.next = a;
            return hold.next;
        }
        //if type is area
        else
        {
            //going through the list till the end
            while ((a != null) && (b != null)) 
            {
                //sets comparePilot to false, uses payload's compareTo
                //swaps if necessary
                pay1 = (Payload)a.getVar();
                pay2 = (Payload)b.getVar();
                pay1.setComparePilot(false);
                if (pay1.compareTo(pay2) <= 0) 
                {
                    swap.next = a;
                    swap = a;
                    a = a.next;
                }
                else 
                {
                    swap.next = b;
                    swap = b;
                    b = b.next;
                }
            }
            //properly reconnects based on which was null
            if(a == null)
                swap.next = b;
            else
                swap.next = a;
            return hold.next;
        }
    }
    
    //mergeDsc will compare the passed in lists and if they aren't in order, orders
    //them in descending order via swapping (only using next values, prev values are fixed
    //in the mergeSort helper function), based on which type the sort needs to happen by
    public Node mergeDsc(Node a, Node b, String type) 
    {
        Node temp = new Node(null);
        Node hold = temp;
        Node swap = hold;
        Payload pay1;
        Payload pay2;
        //if type is name
        if(type.compareTo("name") == 0)
        {
            //going through the list until the end
            while ((a != null) && (b != null)) 
            {
                //sets comparePilot to true, uses payload's compareTo
                //swaps if necessary
                pay1 = (Payload)a.getVar();
                pay2 = (Payload)b.getVar();
                pay1.setComparePilot(true);
                if (pay1.compareTo(pay2) >= 0) 
                {
                    swap.next = a;
                    swap = a;
                    a = a.next;
                }
                else 
                {
                    swap.next = b;
                    swap = b;
                    b = b.next;
                }
            }
            //properly reconnects based on which was null
            if(a == null)
                swap.next = b;
            else
                swap.next = a;
            return hold.next;
        }
        //if type is area
        else
        {
            //going through the list until the end
            while ((a != null) && (b != null)) 
            {
                //sets comparePilot to false, uses payload's compareTo
                //swaps if necessary
                pay1 = (Payload)a.getVar();
                pay2 = (Payload)b.getVar();
                pay1.setComparePilot(false);
                if (pay1.compareTo(pay2) >= 0) 
                {
                    swap.next = a;
                    swap = a;
                    a = a.next;
                }
                else 
                {
                    swap.next = b;
                    swap = b;
                    b = b.next;
                }
            }
            //properly reconnects based on which was null
            if(a == null)
                swap.next = b;
            else
                swap.next = a;
            return hold.next;
        }
    }
    
    //recursive toString uses a tracker Node that starts at head and traverses through the list
    //if it's on the last one, it prints out the current payload and doesn't call the toString again,
    //otherwise it does call the toStirng again.
    @Override
    public String toString()
    {
        Payload obj = (Payload)tracker.getVar();
        tracker = tracker.next;
        if(tracker == null)
        {
            tracker = head;
            return String.format("%-30s", obj.getPilot()) + String.format("%.2f",obj.getArea()) + "\r\n";
        }
        return (String.format("%-30s", obj.getPilot()) + String.format("%.2f",obj.getArea()) + "\r\n" + toString());
    }
}
