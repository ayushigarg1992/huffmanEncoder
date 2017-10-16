import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileInput {
	

		public static String path = "C:/users/ayushi/workspace/HuffmanEncoderDecoder/src/sample_input_large.txt";
		public static String largepath = "C:/users/ayushi/workspace/HuffmanEncoderDecoder/src/sample_input_extralarge.txt";
		
		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			/*FileReader input_file = new FileReader(new File(path));
			BufferedReader input_buff = new BufferedReader(input_file);*/
			
			String newline = "";
			StringBuilder s = new StringBuilder();
			FileReader input_file = new FileReader(new File(path));
			BufferedReader input_buff = new BufferedReader(input_file);
			
			while((newline = input_buff.readLine()) != null && !(newline.equals(""))){
					s.append(newline + "\n");
				}
			
			FileWriter file_out = new FileWriter(new File(largepath));
			BufferedWriter buff_out = new BufferedWriter(file_out);
			
	  			
	  			for(int i=0;i<10;i++){
	  				buff_out.write(s.toString());  				
	  			}
	  			
	  			buff_out.close();
	  			input_buff.close();
	  		
		}
	
}
