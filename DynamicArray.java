import java.util.Iterator;

//
// Complete this class: 15 points
//
// Note:
// Do NOT copy from textbook, or any other sources
// Do NOT copy from code that we did in class (as it contains errors)
//
// Do READ CODE from textbook and in-class code and write it out as your own code
//

public class DynamicArray<T> implements Iterable<T> 
{

    public static void main(String [] args)
    {
        //Optional: test DynamicArray here
        DynamicArray<Double> A = new DynamicArray<Double>();
        for(int i=0;i<20;i++) A.insert(Math.random()*100);
        for(Double v : A)
        {
          System.out.println(v);
        }
				System.out.println("Round 2");
				for(Double v : A)
        {
          System.out.println(v);
        }
    }

    // Note: You can add any private data and methods here

		private T data[]; //stores data
		private int capacity; //array size
		private int size; //data size
		private int modCount; //number of modifications
		
    @SuppressWarnings("unchecked")
    public DynamicArray() //constructor
    {
			capacity = 10;
			data = (T[])new Object[capacity];
			size = 0;
			modCount = 0;
    }

		/**
		*		Inserts the data at the end of the array and increase capacity if necessary
		*
		*		@param val  the value to add
		**/
    @SuppressWarnings("unchecked")
    public void insert(T val)
    {
			if(size >= capacity) increaseCapacity();
			data[size++] = val;
			modCount++;
    }
		
		/**
		*		Increases the capacity of the array
		**/
		@SuppressWarnings("unchecked")
		private void increaseCapacity()
		{
			capacity *= 2;
			T temp[] = (T[])new Object[capacity];
			
			for(int i = 0; i < size; i++)
			{
				temp[i] = data[i];
			}
			
			data = temp;
		}
		/**
		*		Obtains the value at the index
		*
		*		@param index  the index of the data
		*		@return the value at the index
		**/
    public T get(int index)
    {
      return data[index];
    }
		/**
		*		Obtains the data size in the array
		*
		*		@return the amount of data in the array
		**/
    public int size()
    {
      return size;
    }
		/**
		*		Deletes the last value in the array
		**/
    void delete() 
  	{
			delete(size - 1);
  	}
		/**
		*		Removes data at a given index
		*
		*		@param loc  the index to remove
		**/
  	void delete(int loc)
  	{
			if(is_empty() || loc >= size)//not valid index
			{
				System.out.println("Invalid index");
			}
			
			size--;
			modCount++;
			for(int i = loc; i < size; i++)
			{
				data[i] = data[i + 1];
			}
  	}
		/**
		*		Checks if the array is empty
		*
		*		@return boolean indicating whether the array is empty
		**/
    boolean is_empty(){ return size == 0;} 
		/**
		*		Returns an iterator object for the array
		*
		*		@return an Iterator for this DynamicArray
		**/
    public Iterator<T> iterator(){
      return new arrayIterator();
    }

    //Note: You will need to implement an iterator class using java.util.Iterator
    //      interface

    //TO DO: implement iterator here
		
		private class arrayIterator implements java.util.Iterator<T>
		{
			private int current = 0;
			private int previousData = -1;
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
				previousData = current;
				return data[current++];
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
				return current < size;
			}
		/**
		*		Removes the value last crossed/returned by next()
		**/			
			public void remove()
			{
				iterModCount++;
				delete(previousData);
				if(previousData < current) current--;
				previousData = -1;
			}
		}
}
