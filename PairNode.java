
public class PairNode {
	
	    TreeNode node;
	    PairNode leftChild;
	    PairNode nextSibling;
	    PairNode prev;
	 
	    /* Constructor */
	    public PairNode(TreeNode node)
	    {
	        this.node = node; 
	        leftChild = null;
	        nextSibling = null;
	        prev = null;
	    }
	}


