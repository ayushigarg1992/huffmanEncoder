
public class TreeNode {
	String number;
	int frequency;
	public TreeNode left;
	public TreeNode right;
	public TreeNode(String num, int freq, TreeNode left,TreeNode right){
		this.number = num;
		this.frequency = freq;
		this.left = left;
		this.right = right;
	}
	public TreeNode(String num, int freq){
		number = num;
		frequency = freq;
	}
	public  void inorder(TreeNode root){
		if(root==null) return;
		inorder(root.left);
		System.out.print(root.number+" :"+root.frequency+",");
		inorder(root.right);
	}
}
