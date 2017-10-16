import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class decoder {
	File codetable ;
	File encoded ;
	public static void main(String[] args) throws IOException{
		if(args.length!=2){
			System.out.println("Invalid input file name");
			System.exit(1);
		}
		else{
			String encoded = args[0];
			File encoding = new File(encoded);
			String table = args[1];
			File ctable = new File(table);
			long start = System.nanoTime();
			decoder(readBinFile(),makeCodeMap(ctable));
			long end = System.nanoTime();
			double diff = (end - start)/1e9;
			System.out.println("It took "+ diff+" seconds to decode the file");
			
		}
	}
	public static void insertToTree(String number, String code, TreeNode root){
		TreeNode temp = root;
		for(int i =0;i<code.length();i++){
			switch(code.charAt(i)){
			case '0':
				if(root.left==null)
					root.left = new TreeNode(null,0);
				root=root.left;
				break;
			case '1':
				if(root.right==null)
					root.right = new TreeNode(null,0);
				root=root.right;
				break;
			default:
				System.err.println("Invalid code found");
				System.exit(1);
				
			}
			
		}
		root.number = number; 
		root = temp;
	}
	public static void decoder(String binary, TreeNode root) throws IOException{
		File decode = new File("decode.txt");
		FileWriter f = new FileWriter(decode);
		BufferedWriter b = new BufferedWriter(f);
		TreeNode temp = null;
		try {
			int i=0;
			while(i<binary.length()) {
				temp = root;
				while (temp.left != null || temp.right != null) {
					char x = binary.charAt(i);
					if (x == '0')
						temp = temp.left;
					else
						temp = temp.right;
					i++;
				}
				b.write(temp.number+"\n");
			} 
		} finally {
			b.close();
			f.close();
		}
	}
	public static String readBinFile() throws IOException{
		decoder hd = new decoder();
		hd.encoded = new File("encoded.bin");
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(hd.encoded));
		int byteRead = 0;
		StringBuilder encoding = new StringBuilder();
		while((byteRead=br.read())!=-1){
			encoding.append(String.format("%8s", Integer.toBinaryString(byteRead)).replace(" ", "0"));
		}
		return encoding.toString();
	}
	private static TreeNode makeCodeMap(File codetable) throws IOException{
		String line=null;
		FileReader f = new FileReader(codetable);
		BufferedReader b = new BufferedReader(f);
		Map<String,String> codeMap = new HashMap<String,String>();
		TreeNode root = new TreeNode(null,0);
		while((line=b.readLine())!=null){
			String[] arr = line.split("==>");
			codeMap.put(arr[0], arr[1]);
			insertToTree(arr[0],arr[1],root);
		}
		
		return root;
	}
	private static void printTree(TreeNode root){
		if(root.number!=null) {
			System.out.println(root.number);
			return;
		}
		printTree(root.left);
		printTree(root.right);
	} 
	private static String getStringFromByte(Byte code){

	    int splitSize = 8;

	        int index = 0;
	        int position = 0;

	        
	        StringBuilder text = new StringBuilder();
	        Integer r = code.intValue();
	        String ans = Integer.toBinaryString(r);
	        return ans;
	    
	}
}
