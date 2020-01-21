
//
//TreeDictionary implements the (self-balance) binary search tree as a Dictionary
//

public class TreeDictionary<T extends Comparable<T>>
{
    public static void main(String [] args)
    {
				TreeDictionary<Integer> t = new TreeDictionary<Integer>();
				for(int i = 0; i < 10; i++)
				{
					Record<Integer> r = new Record<Integer>();
					r.Name = (int)Math.random() * 100;
					t.insert(r);
				}
				
				
    }
		/**
		*		Inserts the record into the tree at each corresponding keyword
		*		Creates a new keyword Node if not found in the tree
		*
		*		@param record  the record to insert into the tree
		**/
    public void insert(Record<T> record)
    {
        //insert this records into the tree based on its keywords
				
        //1. for each keyword in this record, find the node that contains this keyword
				
        //2. if no such node exists, create a new node and assign the keyword
				
				//3. insert the record into the node

        //4. repeat until all keywords in the record are processed
				
				//(bonus: implement AVL insertion that balances the tree)
				for(T key : record.Keywords)
				{
					Node<T> keyNode = find(key);
					
					if(keyNode == null)
					{
						//create new node, assign keyword
						//check for AVL equality when adding new node
						Node<T> newNode = new Node<T>(key);
						newNode.height = 0;
						newNode.records.insert(record);
						
						insert(newNode);
					}
					else
					{
						keyNode.records.insert(record);
					}
				}
    }
		
		/**
		*		Inserts the Node into the tree in sorted order
		*		Rebalances tree if necessary(AVL)
		*
		*		@param n  the node to add
		**/
		private void insert(Node<T> n)
		{
			//Assuming that there must be a spot to insert
			//Iterate through tree, comparing keywords until a spot is found
			//Insert node and update references
			//Update height and check for AVL equality
			Node<T> curr = root;
			
			if(root == null)
			{
				root = n;
				root.height = 0;
				return;
			}
			while(true)
			{
				int compare = curr.keyword.compareTo(n.keyword);

				if(compare > 0) 
				{
					if(curr.left == null)
					{
						//found empty spot for sorted insert
						curr.left = n;
						n.parent = curr;
						break;
					}
					else
						curr = curr.left;
				}
				else if(compare < 0)
				{					
					if(curr.right == null)
					{
						//found empty spot for sorted insert
						curr.right = n;
						n.parent = curr;
						break;
					}
					else
						curr = curr.right;
				}
			}

			propagateHeight(n);
			AVLCheck(n);
		}
		
		/**
		*		Adjusts tree to meet AVL requirements if necessary by checking this node and its parents
		*
		*		@param child  the node and the parents that will be checked
		**/
		private void AVLCheck(Node<T> n)
		{
			//Go through all nodes above this node
			//Check for AVL height equality
			//Shift nodes around as necessary
			Node<T> p = n;
			while(p != null)
			{
				//check for existing left and right nodes
				int left = (p.left == null) ? -1 : p.left.height;
				int right = (p.right == null) ? -1 : p.right.height;
				Node<T> gp = p.parent;
				Node<T> rotated = null;
				
				if(left - right > 1)//left is too high
				{
					int leftleft = (p.left.left == null) ? -1 : p.left.left.height;
					int leftright = (p.left.right == null) ? -1 : p.left.right.height;
					//left left is too high
					if(leftleft > leftright)
						rotated = rotateLeftChild(p); 
					//left right is too high
					else
						rotated = doubleRotateLeftChild(p); 
				}
				else if(right - left > 1)//right is too high
				{
					int rightright = (p.right.right == null) ? -1 : p.right.right.height;
					int rightleft = (p.right.left == null) ? -1 : p.right.left.height;
					//right right is too high
					if(rightright > rightleft)
						rotated = rotateRightChild(p);
					//right left is too high
					else
						rotated = doubleRotateRightChild(p);
				}
				
				if(rotated != null)//reattach rotated node to tree again, update heights and references
				{
					if(gp == null)//root
					{
						root = rotated;
						root.parent = null;
					}
					else if(gp.left == p)
					{
						gp.left = rotated;
						rotated.parent = gp;
					}
					else
					{
						gp.right = rotated;
						rotated.parent = gp;
					}
					
					//one must be != null because that was the original parent
					if(rotated.left != null)//update heights
						propagateHeight(rotated.left);
					if(rotated.right != null)
						propagateHeight(rotated.right);
					
				}
				
				p = p.parent;
			}
		}

		/**
		*		Rotates the parent Node and its left child to the right
		*
		*		@param parent  the parent node to rotate
		**/
		private Node<T> rotateLeftChild(Node<T> parent)
		{
			Node<T> child = parent.left;
			parent.left = child.right;
			if(parent.left != null)
				parent.left.parent = parent;
			child.right = parent;
			parent.parent = child;
			return child;
		}
		/**
		*		Rotates the parent Node and its right child to the left
		*
		*		@param parent  the parent node to rotate
		**/
		private Node<T> rotateRightChild(Node<T> parent)
		{
			Node<T> child = parent.right;
			parent.right = child.left;
			if(parent.right != null)
				parent.right.parent = parent;
			child.left = parent;
			parent.parent = child;
			return child;
		}
		/**
		*		Rotates the parent Node and its left inner grandchild to the right
		*
		*		@param parent  the parent node to rotate
		**/
		private Node<T> doubleRotateLeftChild(Node<T> parent)
		{
			parent.left = rotateRightChild(parent.left);
			return rotateLeftChild(parent);
		}
		/**
		*		Rotates the parent Node and its right inner grandchild to the left
		*
		*		@param parent  the parent node to rotate
		**/	
		private Node<T> doubleRotateRightChild(Node<T> parent)
		{
			parent.right = rotateLeftChild(parent.right);
			return rotateRightChild(parent);
		}
		/**
		*		Returns a list of the tree in sorted order
		*
		*		@return the LinkedList of sorted Nodes
		**/
    public LinkedList<Node<T>> InOrderTraversal() //10 points
    {
        //TODO : store in-order traversal of tree nodes in a linked list
				LinkedList<Node<T>> list = new LinkedList<Node<T>>();
				inOrderTraversal(root, list);
        return list;
    }
		/**
		*		Traverses through the tree in sorted order, adding Nodes to the LinkedList
		*
		*		@param n  the current node to add to the list
		*		@param l  the LinkedList to add nodes to
		**/
		private void inOrderTraversal(Node<T> n, LinkedList<Node<T>> l)
		{
			if(n.left != null) inOrderTraversal(n.left, l);
			l.insert(n);
			if(n.right != null)inOrderTraversal(n.right, l);
		}

		/**
		*		Removes the node with the given keyword if in tree and adjusts for AVL compatability
		*
		*		@param keyword  the keyword of the node to search and remove
		**/
    private void remove(T keyword) //10 points + 10 bonux (AVL remove)
    {
        //
        // TODO: use a keyword to remove a node that contains this word
        //

        //(bonus: implement AVL remove that balances the tree)
				
				//Check for simple one child/no child cases first
				Node<T> n = find(keyword);

				if(n.left == null && n.right == null)
				{
					Node<T> p = n.parent;
					if(p.left == n)
						p.left = null;
					else
						p.right = null;
					n = p;
				}
				else if(n.left == null)
				{
					Node<T> p = n.parent;
					n = n.right;
					n.parent = p;
				}
				else if(n.right == null)
				{
					Node<T> p = n.parent;
					n = n.left;
					n.parent = p;
				}
				else//two children
				{
					//switch with smallest right node
					//remove n at new location
					//assign n to parent for AVL check
					
					Node<T> swapNode = smallest(n.right);
					T key = n.keyword;
					DynamicArray<Record<T>> arr = n.records;
					
					n.keyword = swapNode.keyword;
					n.records = swapNode.records;
					swapNode.keyword = key;
					swapNode.records = arr;
					
					n = swapNode;
				}

				propagateHeight(n);
				AVLCheck(n);
    }
		
		/**
		*		Finds and returns the smallest node of a given tree
		*
		*		@param root  the root of the tree to search
		*		@return the node with the smallest value
		**/
		private Node<T> smallest(Node<T> root)
		{
			return (root.left != null) ? smallest(root.left) : root;
		}
		
		/**
		*		Adjusts the height of the node and its parents
		*
		*		@param n  the node to begin adjusting heights with
		**/
		private void propagateHeight(Node<T> n)
		{
			while(n != null)
			{
				n.height = updateHeight(n);
				n = n.parent;
			}
		}
		/**
		*		Adjusts the height of the given node based on its children's heights
		*
		*		@param m  the node whose height will be adjusted
		**/
		private int updateHeight(Node<T> n)
		{
			if(n.left == null && n.right == null) return 0;
			else if(n.left == null) return n.right.height + 1;
			else if(n.right == null) return n.left.height + 1;
			else return Math.max(n.right.height, n.left.height) + 1;
		}
		/**
		*		Finds the node with the given keyword
		*
		*		@param keyword the keyword to look for
		*		@return the Node containing the keyword, if found; otherwise null
		**/
    private Node<T> find(T keyword) //10 points
    {
        //
        // TODO: find a node that contains this keyword
        //
				Node<T> n = root;
				while(n != null)
				{
					int compare = n.keyword.compareTo(keyword);
					if(compare < 0) n = n.right;//n is smaller than keyword, look at right side
					else if(compare > 0) n = n.left;
					else return n;
				}
				
        return null;
    }
		/**
		*		Finds and returns all records containing the given keywords
		*
		*		@param keywords  the list of keywords to search for
		*		@return an array of records that contain the given keywords
		**/
    public DynamicArray<Record<T>> find( LinkedList<T> keywords  ) //10 points
    {
      //
      //TODO: find an array of records that contain the given keywords
      //

      //hint: use find_then_build
			DynamicArray<Record<T>> arr = new DynamicArray<Record<T>>();
			TreeDictionary<T> t = this;
			
			T last = keywords.last();
			for(T key : keywords)
			{
				if(key == last) break;
				if(t == null) break;
				t = t.find_then_build(key);
			}
			
			
			if(t == null)
				return null;
			
			Node<T> n = t.find(last);
			
			if(n == null)
				return null; //return null when no records are found
			
			for(Record<T> r : n.records)
			{
				arr.insert(r);
			}
			
			if(arr.size() > 0)
				return arr;
			
      return null; //return null when no records are found
    }

    private class Node<T>
    {
        Node(){ records=new DynamicArray<Record<T>>();}
        Node(T k){ keyword=k; records=new DynamicArray<Record<T>>();}

				private int height;
        T keyword; //nodes are ordered by Keywords
        Node<T> parent;
        Node<T> left, right; //children
        DynamicArray<Record<T>> records;
        public String toString(){ return "["+keyword+" ("+records.size()+")]"; }
    }

    private Node<T> root; //root of the tree, can be null

    //build this tree by inserting the records
    public void build( DynamicArray<Record<T>> records )
    {
        for(Record<T> r : records)
        {
          insert(r);
        }
    }

    //find a node that contains the given keyword and then
    //build a tree using the records stored in the found node
    //finally return the tree
    private TreeDictionary<T> find_then_build(T keyword)
    {
        //
        //use keyword to find the node
        Node<T> node = find(keyword);
        if(node==null) return null;

        //
        //build the tree from this node's record
        TreeDictionary<T> newT=new TreeDictionary<T>();
        newT.build(node.records);

        //
        //remove the keyword from the Tree
        newT.remove(keyword);

        //done
        return newT;
    }

    public String toString()
    {
      //list all the keyworkds and number of records for each keyword
      //visit all nodes in In-Order traversal.
      LinkedList<Node<T>> nodes = InOrderTraversal();
      String S="Tree Dictionary: {";
      for(Node<T> node : nodes) S+=node.toString()+", ";
      if(!nodes.is_empty()) S=S.substring(0,S.length()-2);
      S+="}";
      return S;
    }
}
