import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>
{
    public static void main(String [] args)
    {
        Double A[]={0.1,0.34,0.7,23.1,-0.75};
        LinkedList<Double> M = new LinkedList<Double>(A);
        System.out.println("Linked List: "+M);
    }

		private int modCount = 0; //counter of modifications
		/**
		*		Creates a linked list from a given array
		*
		*		@param A  array to convert
		**/
    public LinkedList(T [] A) //create a linked list from an array
    {
			head=new Node<T>();
      tail=new Node<T>();
      head.next=tail;
      tail.prev=head;
			for(int i = 0; i < A.length; i++)
			{
				insert(A[i]);
			}
    }
		
		/**
		*		Inserts the data at the end of the list
		*
		*		@param data  the data to add
		**/
  	public void insert(T data) 
  	{
			modCount++;
			Node<T> newNode = new Node<T>(data);
			insert(newNode, tail.prev);
  	}
		/**
		*		Inserts the Node "n" after Node "after"
		**/	
		private void insert(Node<T> n, Node<T> after)
		{
			Node<T> before = after.next;
			after.next = n;
			n.prev = after;
			n.next = before;
			before.prev = n;
		}
		/**
		*		Removes data at a given index
		*
		*		@param n  the node to remove
		**/		
    private void delete(Node<T> n)
    {
			modCount++;
			Node<T> prev = n.prev;
			Node<T> next = n.next;
			prev.next = next;
			next.prev = prev;
    }
		/**
		*		Checks if the array is empty
		*
		*		@return boolean indicating whether the array is empty
		**/
    public boolean is_empty()
    {
      return head.next == tail;
    }

		/**
		*		Returns an iterator object for the array
		*
		*		@return an Iterator for this DynamicArray
		**/
    public Iterator<T> iterator(){
      return new listIterator();
    }
		
    //Note: You will need to implement an iterator class using java.util.Iterator
    //      interface

    //TO DO: implement iterator here

		private class listIterator implements Iterator<T> 
		{
			Node<T> curr = head.next;
			Node<T> prev = null;
			private int iterModCount = modCount;
		/**
		*		Returns the next value from the iterator
		*
		*		@return the next value in the iterator
		**/					
			public T next()
			{
				if(iterModCount!=modCount)
				{
					System.out.println("No concurrent modification"); 
					return null;
				}
				if(!hasNext())
				{
					System.out.println("No next value"); 
					return null;
				}
				prev = curr;
				curr = curr.next;
				return prev.data;
			}
		/**
		*		Checks if the iterator has a next value
		*
		*		@return boolean indicating whether there is another value
		**/					
			public boolean hasNext()
			{
				if(iterModCount!=modCount)
				{
					System.out.println("No concurrent modification"); 
					return false;
				}
				return curr!= tail;
			}
		/**
		*		Removes the value last crossed/returned by next()
		**/	
			public void remove()
			{
				if(prev == null) 
				{
					System.out.println("Nothing to remove");
					return;
				}
				delete(prev);
				prev = null;
				iterModCount++;
			}
		}

    // ----------------------------------------------------------------------
    //
    // !!! READ but Do NOT Change anything after this line
    //
    // ----------------------------------------------------------------------

    private class Node<T>
    {
      Node(){}
      Node(T data){ this.data=data; }
      public T data;
      public Node<T> next;
      public Node<T> prev; //for doubly linked list
    }

    Node<T> head; //pointing to the location BEFORER the first element
    Node<T> tail; //for doubly linked list
                  //pointing to the location AFTER the last element

    public LinkedList() //constructor
    {
      head=new Node<T>();
      tail=new Node<T>();
      head.next=tail;
      tail.prev=head;
    }

    public T last()
    {
      //nothing to return
      if(head.next==tail) return null;
      return tail.prev.data;
    }

    public String toString()
    {
      String S="(";
      for(T t : this) S=S+t+", ";
      if(is_empty()==false) S=S.substring(0,S.length()-2);
      S=S+")";
      return S;
    }
}
