import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class encoder {
	String smallInput;
	String largeInput; 
	public encoder(){
		smallInput = "C:/Users/ayushi/workspace/HuffmanEncoderDecoder/src/sample_input_small.txt";
		largeInput =  "C:/Users/ayushi/workspace/HuffmanEncoderDecoder/src/sample_input_extralarge.txt";
	}
	public static void main(String[] args) throws IOException{
		
		/*if(args.length!=1)
		{
			System.out.println("Invalid input file name");
			System.exit(1);
		}
		else{*/
			encoder h = new encoder();
			//String input = args[0];
			File inputF = new File(h.largeInput);
			long start = System.nanoTime();
			WriteToEncodedFile(inputF);
			long end = System.nanoTime();
			double diff = (end- start)/1e9;
			System.out.println("It took "+ diff +" seconds to encode the file");
		/*}*/
	}
	/*function to test huffman tree creation for different */
	private static void testAllHeapTreeTimes(encoder h) throws IOException {
		long start_time;
		long end_time = System.nanoTime();
		double sum=0;
		double avg=0;
		for (int i = 0; i < 10; i++) {
			start_time = System.nanoTime();
			MakeHuffManTreeBin(MakeFrequencyHeapBinary(new File(h.largeInput)));
			end_time = System.nanoTime();
			double diff = (double)(end_time-start_time)/1e9;
			sum+=diff;
		}
		avg=sum/10;
		System.out.println("Average time binary: "+ avg);
		sum=0;
		avg=0;
		for (int i = 0; i <10; i++) {
			start_time = System.nanoTime();
			MakeHuffManTreeFourWay(MakeFrequencyHeapFourWay(new File(h.largeInput)));
			end_time = System.nanoTime();
			double diff= (double) (end_time - start_time) / 1e9;
			sum+=diff;
		}
		avg=sum/10;
		System.out.println("Average time four way: "+ avg);
	}
	public static void WriteToEncodedFile(File input) throws IOException{
		File encoding = EncodingFile(input);
		FileReader readerF = new FileReader(encoding);
		BufferedReader readerB = new BufferedReader(readerF);
		int z=0;
		char[] buf = new char[8];
		long start = System.nanoTime();
		FileOutputStream output = new FileOutputStream("encoded.bin");
		
		try {
			int countk = 0;
			while ((z = readerB.read()) != -1) {
				buf[countk++] = (char) z;
				if(countk == 8) {
					byte ans = getByteByString(String.valueOf(buf));
					
					output.write(ans);
					countk = 0;
				}
				
			} 
			long end = System.nanoTime();
			double tot = (end- start)/1e9;
		} finally {
			output.close();
			readerB.close();
			readerF.close();
		}
		
		
	}
 	public static byte getByteByString(String binaryString) {
	    int splitSize = 8;

	        int index = 0;
	        int position = 0;

	        byte resultByteArray=0;
	        StringBuilder text = new StringBuilder(binaryString);

	        while (index < text.length()) {
	            String binaryStringChunk = text.substring(index, Math.min(index + splitSize, text.length()));
	            Integer byteAsInt = Integer.parseInt(binaryStringChunk, 2);
	            resultByteArray = byteAsInt.byteValue();
	            index += splitSize;
	            position ++;
	        }
	        return resultByteArray;
	    
	    
	}
	public static File EncodingFile(File input) throws IOException{
		//to convert input file data to binary string
		encoder h = new encoder();
		//TreeNode root = MakeHuffManTreeBin(MakeFrequencyHeapBinary(input));
		//TreeNode root = MakeHuffManTreeFourWay(MakeFrequencyHeapFourWay(input));
		TreeNode root = MakeHuffManTreeFourWay(MakeFrequencyHeapFourWay(input));
		long start = System.nanoTime();
		Map<String,String> codeTable = GenerateCodeTable(root,"",new HashMap<String,String>());
		FileWriter f = new FileWriter(new File("codetable.txt"));
		BufferedWriter r = new BufferedWriter(f);
		for(Map.Entry<String, String> entry : codeTable.entrySet()){
			r.write(entry.getKey()+"==>"+entry.getValue()+"\n");
		}
		long end = System.nanoTime();
		double diff = (end-start)/1e9;
		//write the encoding to the file
		StringBuilder encodedString = new StringBuilder();
		
		File fileArg=input;
		FileReader fr;
		long start_encoding_to_txt = System.nanoTime();
		File outputTxt=new File("encoded.txt");
		try {
			fr = new FileReader(fileArg);
			BufferedReader br=new BufferedReader(fr);
			String line = null;
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputTxt));
			try {
				while((line=br.readLine())!=null){
					writer.write(codeTable.get(line));
				}
				//System.out.println(encodedString.toString());
			} finally{
				r.close();
				writer.close();
				fr.close();
				br.close();
			}
			long end_encoding_to_txt = System.nanoTime();
			double diff_encod = (end_encoding_to_txt-start_encoding_to_txt)/1e9;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return outputTxt;
		}
	
	private static Map<String,String> GenerateCodeTable(TreeNode root,String code,Map<String,String> codeTable){
		 //= new HashMap<>();
		if(root==null) return new HashMap<String,String>();
		if(root.number!=null) 
			codeTable.put(root.number, code);
		GenerateCodeTable(root.left,code+"0",codeTable);
		GenerateCodeTable(root.right,code+"1",codeTable);
		return codeTable;
	}
	private static TreeNode MakeHuffManTreeBin(BinaryHeap heap){
		TreeNode left, right, root;
		root = new TreeNode(null,0);
		left = new TreeNode(null,0);
		long start_time = System.nanoTime();
		while(heap.heap.length>1){
				
				left = heap.deleteMin();
				//heap.deleteMin();
				if(!heap.isEmpty()){
					right = heap.deleteMin();
				}
				else{
					//System.out.println("Chal gya break!");
					break;
				} 
				root = new TreeNode(null,left.frequency+right.frequency);
				root.left = left;
				root.right = right;
				heap.insert(root);
		}
		heap.insert(left);
		long end_time = System.nanoTime();
		//System.out.println("end at: "+end_time);
		double difference = (end_time - start_time)/1e6;
	
		return root;
	}
	private static TreeNode MakeHuffManTreeFourWay(FourWayHeap heap){
		TreeNode left, right, root;
		root = new TreeNode(null,0);
		left = new TreeNode(null,0);
		while(heap.heapSize>3){
				
				left = heap.deleteMin();
				//heap.deleteMin();
				if(!heap.isEmpty()){
					right = heap.deleteMin();
				}
				else break;
				root = new TreeNode(null,left.frequency+right.frequency);
				root.left = left;
				root.right = right;
				heap.insert(root);
		}
		heap.insert(left);
		//root.inorder(root);
		return root;
	}
	private static TreeNode MakeHuffManTreePairs(PairHeap heap){
		TreeNode left, right, root;
		root = new TreeNode(null,0);
		left = new TreeNode(null,0);
		long start = System.nanoTime();
		//heap.inorder();
		while(heap.root.leftChild!=null){
				
				left = heap.deleteMin();
				//heap.deleteMin();
				right = heap.deleteMin();
				root = new TreeNode(null,left.frequency+right.frequency);
				root.left = left;
				root.right = right;
				heap.insert(new PairNode(root));
		}
		long end = System.nanoTime();
		root = heap.deleteMin();
		double tot = (end-start)/1e9;

		//root.inorder(root);
		return root;
	}
	public static FourWayHeap MakeFrequencyHeapFourWay(File input) throws IOException{

		int[] frequencyArr = new int[1000000];
		encoder h = new encoder();
		HashMap<String,Integer> frequencyTable = new HashMap<>();
		FileReader fr;
		FourWayHeap heap = new FourWayHeap(10000000);
		//BinaryHeap heap = new BinaryHeap(10000000);
		//PairHeap heap = new PairHeap();
		fr = new FileReader(input);
		BufferedReader br=new BufferedReader(fr);
		try {
			
			
			String line = null;
			try {
				while((line=br.readLine())!=null){
					int number = Integer.parseInt(line);
					int freq = frequencyArr[number]++;
					frequencyTable.put(line,++freq);
					//heap.insert(freq);
				}
				
				
				for(Map.Entry<String, Integer> entry : frequencyTable.entrySet()){
					TreeNode n = new TreeNode(entry.getKey(),entry.getValue());
					heap.insert(n);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			} 
		finally{
			fr.close();
			br.close();
		}
		
	return heap;
	
	}
	public static BinaryHeap MakeFrequencyHeapBinary(File input) throws IOException{
		int[] frequencyArr = new int[1000000];
		encoder h = new encoder();
		HashMap<String,Integer> frequencyTable = new HashMap<>();
		FileReader fr;
		BinaryHeap heap = new BinaryHeap(10000000);
		fr = new FileReader(input);
		BufferedReader br=new BufferedReader(fr);
		try {
			
			
			String line = null;
			try {
				long start = System.nanoTime();
				while((line=br.readLine())!=null){
					int number = Integer.parseInt(line);
					int freq = frequencyArr[number]++;
					frequencyTable.put(line,++freq);
					//heap.insert(freq);
				}
				long end = System.nanoTime();
				double diff = (end - start)/1e9;
				long heap_start = System.nanoTime();
				for(Map.Entry<String, Integer> entry : frequencyTable.entrySet()){
					TreeNode node =new TreeNode(entry.getKey(),entry.getValue(),null,null);
					heap.insert(node);
				}
				long heap_end = System.nanoTime();
				double diff_heap = (heap_end - heap_start)/1e9;

			} catch (IOException e) {
				e.printStackTrace();
			}
			//heap.printHeap();
		} 
		finally{
			fr.close();
			br.close();
		}
		
	return heap;
	}
	public static PairHeap MakeFrequencyHeapPair(File input) throws IOException{

		int[] frequencyArr = new int[1000000];
		encoder h = new encoder();
		HashMap<String,Integer> frequencyTable = new HashMap<>();
		FileReader fr;
		PairHeap heap = new PairHeap();
		fr = new FileReader(input);
		BufferedReader br=new BufferedReader(fr);
		try {
			
			long start = System.nanoTime();
			String line = null;
			try {
				while((line=br.readLine())!=null){
					int number = Integer.parseInt(line);
					int freq = frequencyArr[number]++;
					frequencyTable.put(line,++freq);
				}
				
				
				for(Map.Entry<String, Integer> entry : frequencyTable.entrySet()){
					TreeNode t = new TreeNode(entry.getKey(),entry.getValue());
					PairNode node = new PairNode(t);
					heap.insert(node);
				}
				long end = System.nanoTime();
				double tot = (end-start)/1e9;
				System.out.println("Heap created in"+tot +" seconds");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		finally{
			fr.close();
			br.close();
		}
		//heap.inorder();
	return heap;
	
	}
}
