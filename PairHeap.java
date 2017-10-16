
public class PairHeap
{
	
    public PairNode root; 
    private PairNode [ ] treeArray = new PairNode[ 5 ];
    /* Constructor */
    public PairHeap( )
    {
        root = null;
      }
    
    /* Check if heap is empty */
    public boolean isEmpty() 
    {
        return root == null;
    }
    /* Make heap logically empty */ 
    public void makeEmpty( )
    {
        root = null;
    }
    /* Function to insert data */
    public PairNode insert(PairNode x)
    {
        
        if (root == null)
            root = x;
        else
            root = compareAndLink(root, x);
        return x;
    }
    /* Function compareAndLink */
    private PairNode compareAndLink(PairNode first, PairNode second)
    {
        if (second == null)
            return first;
 
        if (second.node.frequency < first.node.frequency)
        {
            /* Attach first as leftmost child of second */
            second.prev = first.prev;
            first.prev = second;
            first.nextSibling = second.leftChild;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.leftChild = first;
            return second;
        }
        else
        {
            /* Attach second as leftmost child of first */
            second.prev = first;
            first.nextSibling = second.nextSibling;
            if (first.nextSibling != null)
                first.nextSibling.prev = first;
            second.nextSibling = first.leftChild;
            if (second.nextSibling != null)
                second.nextSibling.prev = second;
            first.leftChild = second;
            return first;
        }
    }
    private PairNode combineSiblings(PairNode firstSibling)
    {
        if( firstSibling.nextSibling == null )
            return firstSibling;
        /* Store the subtrees in an array */
        int numSiblings = 0;
        for ( ; firstSibling != null; numSiblings++)
        {
            treeArray = doubleIfFull( treeArray, numSiblings );
            treeArray[ numSiblings ] = firstSibling;
            /* break links */
            firstSibling.prev.nextSibling = null;  
            firstSibling = firstSibling.nextSibling;
        }
        treeArray = doubleIfFull( treeArray, numSiblings );
        treeArray[ numSiblings ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numSiblings; i += 2)
            treeArray[ i ] = compareAndLink(treeArray[i], treeArray[i + 1]);
        int j = i - 2;
        /* j has the result of last compareAndLink */
        /* If an odd number of trees, get the last one */
        if (j == numSiblings - 3)
            treeArray[ j ] = compareAndLink( treeArray[ j ], treeArray[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j -= 2)
            treeArray[j - 2] = compareAndLink(treeArray[j-2], treeArray[j]);
        return treeArray[0];
    }
    private PairNode[] doubleIfFull(PairNode [ ] array, int index)
    {
        if (index == array.length)
        {
            PairNode [ ] oldArray = array;
            array = new PairNode[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
    /* Delete min element */
    public TreeNode deleteMin( )
    {
        if (isEmpty( ) )
            return new TreeNode(null,0);
        TreeNode x = root.node;
        if (root.leftChild == null)
            root = null;
        else
            root = combineSiblings( root.leftChild );
        return x;
    }
    /* inorder traversal */
    public void inorder()
    {
        inorder(root);
    }
    private void inorder(PairNode r)
    {
        if (r != null)
        {
            inorder(r.leftChild);
            System.out.print(r.node.frequency +": "+root.node.number);
            inorder(r.nextSibling);
        }
    }

    }
 
