package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class preprocessing {
	public static void main(String args[]){
		File amazon = new File("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/amazon/com-amazon.ungraph.txt");
		File dblp = new File("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/dblp/com-dblp.ungraph.txt");
		File youtube = new File("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/youtube/com-youtube.ungraph.csv");
		File test = new File("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/test/test.txt");
		
		int min = 1;
		int max = 548458;
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream(amazon), "GBK");
            BufferedReader reader = new BufferedReader(read);
            
            PrintWriter writer = new PrintWriter("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/amazon/amazon-update.txt", "UTF-8");
            while(min <= max){
            	writer.print(min+",");
            	min++;
            }
            writer.println();
            String line;
            int fromnode = 1;
            int point = 1;
            while ((line = reader.readLine()) != null) {
            	String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer .parseInt(a[0]), Integer.parseInt(a[1])};
            	if(b[0] != fromnode){
            		System.out.println(b[0]);
            		writer.println("");
            		point = 1;
            		fromnode = b[0];
            		ArrayList<Integer> al = getPrevious(b[0],amazon);
            		for(int i = 0; i < al.size(); i++){
            			if(i == point-1){
            				writer.print("true,");
                    		point++;
            			}else{
            				writer.print("false,");
                    		point++;
            			}
            		}
            	}
            	while(point < b[0]){
            		writer.print("false,");
            		point++;
            	}
            	if(point == b[0]){
            		writer.print("true,");
            		point++;
            	}
            	while(point < b[1]){
            		writer.print("false,");
            		point++;
            	}
            	if(point == max){
            		writer.print("true");
            	}else{
            		writer.print("true,");
            	}
            	point++;
            }
            reader.close();
            read.close();
            writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> getPrevious(int n, File file){
		ArrayList<Integer> al = new ArrayList<Integer>();
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
            	String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer.parseInt(a[0]), Integer.parseInt(a[1])};
            	if(b[1] == n){
            		al.add(b[0]);
            	}
            	if(b[0] >= n){
            		break;
            	}
			}
			reader.close();
            read.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return al;
	}
}
