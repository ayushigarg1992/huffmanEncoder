import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class BinaryHeap    
{
    /** The number of children each node has **/
    private static final int d = 2;
    private int heapSize;
    public TreeNode[] heap;
 
    /** Constructor **/    
    public BinaryHeap(int capacity)
    {
        heapSize = 0;
        heap = new TreeNode[capacity + 1];
		for(int i=0;i<capacity;i++){
	        	heap[i] = new TreeNode(null,0);
	        }
	}
 
    /** Function to check if heap is empty **/
    public boolean isEmpty( )
    {
        return heapSize == 0;
    }
 
    /** Check if heap is full **/
    public boolean isFull( )
    {
        return heapSize == heap.length;
    }
 
    /** Clear heap */
    public void makeEmpty( )
    {
        heapSize = 0;
    }
 
    /** Function to  get index parent of i **/
    private int parent(int i) 
    {
        return (i - 1)/d;
    }
 
    /** Function to get index of k th child of i **/
    private int kthChild(int i, int k) 
    {
        return d * i + k;
    }
 
    /** Function to insert element */
    public void insert(TreeNode x)
    {
        if (isFull( ) )
            throw new NoSuchElementException("Overflow Exception");
        /** Percolate up **/
        heap[heapSize++] = x;
        heapifyUp(heapSize - 1);
    }
 
    /** Function to find least element **/
    public TreeNode findMin( )
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");           
        return heap[0];
    }
 
    /** Function to delete min element **/
    public TreeNode deleteMin()
    {
        TreeNode keyItem = heap[0];
        delete(0);
        return keyItem;
    }
 
    /** Function to delete element at an index **/
    public TreeNode delete(int ind)
    {
        if (isEmpty() )
            throw new NoSuchElementException("Underflow Exception");
        TreeNode keyItem = heap[ind];
        heap[ind] = heap[heapSize - 1];
        heapSize--;
        heapifyDown(ind);        
        return keyItem;
    }
 
    /** Function heapifyUp  **/
    private void heapifyUp(int childInd)
    {
        TreeNode tmp = heap[childInd];    
        while (childInd > 0 && tmp.frequency < heap[parent(childInd)].frequency)
        {
            heap[childInd] = heap[ parent(childInd) ];
            childInd = parent(childInd);
        }                   
        heap[childInd] = tmp;
    }
 
    /** Function heapifyDown **/
    private void heapifyDown(int ind)
    {
        int child;
        TreeNode tmp = heap[ ind ];
        while (kthChild(ind, 1) < heapSize)
        {
            child = minChild(ind);
            if (heap[child].frequency < tmp.frequency)
                heap[ind] = heap[child];
            else
                break;
            ind = child;
        }
        heap[ind] = tmp;
    }
 
    /** Function to get smallest child **/
    private int minChild(int ind) 
    {
        int bestChild = kthChild(ind, 1);
        int k = 2;
        int pos = kthChild(ind, k);
        while ((k <= d) && (pos < heapSize)) 
        {
            if (heap[pos].frequency < heap[bestChild].frequency) 
                bestChild = pos;
            pos = kthChild(ind, k++);
        }    
        return bestChild;
    }
 
    /** Function to print heap **/
    public void printHeap()
    {
        System.out.print("\nHeap = ");
        for (int i = 0; i < heapSize; i++)
            System.out.print(heap[i] +" ");
        System.out.println();
    }     
}
 
	 
	