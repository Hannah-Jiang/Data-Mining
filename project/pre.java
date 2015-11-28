package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class pre {
	public static void main(String args[]){
		String filename = "youtube";
		File file = new File("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/com-" + filename + ".ungraph.csv");
		int length = 1000;
	
		ArrayList<Integer> al = new ArrayList<Integer>();
		int maxNum = 0;
		HashSet<Integer> set = new HashSet<Integer>();
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader reader = new BufferedReader(read);

            String line;
            int cur = 2;
            int prev = 0;
            int num = 0;
            while ( (line = reader.readLine()) != null) {
            	num++;
            	if(num <= 35){
            		continue;
            	}
            	if(num > length + 35){
            		break;
            	}
            	//System.out.println(num);
            	String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer .parseInt(a[0]), Integer.parseInt(a[1])};
            	if(prev == 0){
            		prev = b[0];
            	}else if(b[0] != prev){
            		prev = b[0];
            		if(b[0] != cur){
            			while(cur < b[0]){
            				al.add(cur);
            				cur++;
            			}
            			cur++;
            		}else{
            			cur++;
            		}
            	}
            	set.add(b[0]);
            	maxNum = maxNum > b[0] ? maxNum : b[0];
            }
            reader.close();
            read.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		int r[] = changeFile(set,file,filename, al,length);
		updateFile(file,filename,length);
		int col = r[1];
		int row = r[0];
		System.out.println(row + ", " + col);
		//compose(row,col,filename);
		// update file
		int min = 1;
		int max = 0;
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + ".txt"), "GBK");
            System.out.println("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + ".txt");
			BufferedReader reader = new BufferedReader(read);
            
            PrintWriter writer = new PrintWriter("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-matrix" + length + ".csv", "UTF-8");

            while(min <= col){
            	writer.print(min+",");
            	min++;
            }
            writer.println();
            String line;
            int fromnode = 1;
            int point = 1;
            int count = 0;
            boolean flg = false;
            ArrayList<Integer> l = new ArrayList<Integer>();
            
            while ((line = reader.readLine()) != null) {
            	String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer .parseInt(a[0]), Integer.parseInt(a[1])};
            	
            	if(b[0] != fromnode){
            		flg = false;
            		int pointer = 1;
            		l.sort(null);
            		while(l.size() > 0){
            			if(pointer == l.get(0)){
            				if(pointer == col){
            					writer.print("true");
            				}else{
            					writer.print("true,");
            				}
            				l.remove(0);
            			}else{
            				writer.print("false,");
            			}
            			pointer++;
            			max++;
            			count++;
            		}
            		while(max < col){
            			if(count == col-1){
            				writer.println("false");
            			}else{
            				writer.print("false,");
            			}
            			max++;
            			count++;
            		}
            		if(count != col){
            			System.out.println("from node = " + fromnode + ", count = " + count);
            		}
            		//System.out.println("from node = " + fromnode + ", count = " + count);
            		max = 0;
            		count = 0;
            		point = 1;
            		fromnode = b[0];
            		l = getPrevious(b[0],file, filename, length);
            		l.add(b[1]);
            	}else{
            		l.add(b[1]);
            		if(!flg){
            			l.add(b[0]);
            			flg = true;
            		}
            	}
            	/*
            	int b0 = Math.min(b[0], b[1]);
            	int b1 = Math.max(b[0], b[1]);
            	while(point < b0){
            		writer.print("false,");
            		point++;
            		count++;
            	}
            	if(point == b0){
            		writer.print("true,");
            		point++;
            		count++;
            	}
            	while(point < b1){
            		writer.print("false,");
            		point++;
            		count++;
            	}
            	if(b1 == b[0] && !flg){
            		writer.print("true,");
            		flg = true;
            	}
            
            	max = b1;
            	point++;
            	count++;
            	*/
            }
            l.sort(null);
            int pointer = 1;
    		while(l.size() > 0){
    			if(pointer == l.get(0)){
    				writer.print("true,");
    				l.remove(0);
    			}else{
    				writer.print("false,");
    			}
    			pointer++;
    			max++;
    			count++;
    		}
    		while(max < col){
    			if(count == col-1){
    				writer.println("false");
    			}else{
    				writer.print("false,");
    			}
    			max++;
    			count++;
    		}
    		if(count != col){
    			System.out.println("from node = " + fromnode + ", count = " + count);
    		}
            reader.close();
            read.close();
            writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static int[] changeFile(HashSet<Integer> set, File file, String filename, ArrayList<Integer> al, int length){
		int col = 0;
		int row = 1;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
			BufferedReader reader = new BufferedReader(read);
			
			PrintWriter writer = new PrintWriter("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + "-org.txt", "UTF-8");
			
			String line;
			int r = 0;
			int prev = 0;
			int num = 0;
			while (num < length + 35 && (line = reader.readLine()) != null) {
				num++;
				if(num <= 35){
            		continue;
            	}
				String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer.parseInt(a[0]), Integer.parseInt(a[1])};
            	if(r == 0){
            		prev = b[0];
            		r++;
            	}
            	if(prev != b[0]){
            		row++;
            		prev = b[0];
            	}
            	writer.print(b[0] + "	");
            	System.out.print(b[0] + "	");
            	if(set.contains(b[1])){
            		writer.print(b[1]);
            	}else{
            		if(map.containsKey(b[1])){
            			writer.print(map.get(b[1]));
            			System.out.println(map.get(b[1]));
            		}else{
            		if(al.size() > 0){
            			map.put(b[1], al.get(0));
            			writer.print(al.get(0));
            			System.out.println(al.get(0));
            			if(al.size() == 1){
            				int c = al.get(0)+1;
            				while(set.contains(c)){
            					c++;
            				}
            				col = c;
            			}
            			al.remove(0);
            		}else{
            			map.put(b[1], col);
            			writer.print(col);
            			System.out.println(col);
            			col++;
            		}
            		}
            	}
            	writer.println();
			}
			reader.close();
            read.close();
            writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		int[] r = {row,col-1};
		return r;
	} 
	
	public static void compose(int row, int col, String filename, int length){
		try{
			PrintWriter writer = new PrintWriter("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + ".txt", "UTF-8");
			int r = 0;
			while(r < row){
				int c = 0;
				while(c < col){
					writer.print("false");
					if(c < col-1){
						writer.print(",");
					}else{
						writer.println();
					}
					c++;
				}
				r++;
			}
			writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> getPrevious(int n, File file,String filename, int length){
		ArrayList<Integer> al = new ArrayList<Integer>();
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + ".txt"), "GBK");
			BufferedReader reader = new BufferedReader(read);
			String line;
			int num = 0;
			
			while ((line = reader.readLine()) != null) {
            	String s = line;
            	num++;
            	if(num < 35){
            		continue;
            	}
            	if(num > length + 35){
            		
            		break;
            	}
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
	
	public static void updateFile(File file, String filename, int length){
		try{
			InputStreamReader read = new InputStreamReader(new FileInputStream("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + "-org.txt"), "GBK");
			BufferedReader reader = new BufferedReader(read);
		
			PrintWriter writer = new PrintWriter("C:/Users/benb/Dropbox/WashU/2015spring/Data Mining/data set/" + filename + "/" + filename + "-" + length + ".txt", "UTF-8");
			String line;
			ArrayList<Integer> list = new ArrayList<Integer>();
			int prev = 0;
			while ((line = reader.readLine()) != null) {
            	String s = line;
            	String[] a = s.split("	");
            	int[] b = {Integer.parseInt(a[0]), Integer.parseInt(a[1])};
            	if(prev == 0){
            		prev = b[0];
            	}
            	if(prev == b[0]){
            		list.add(b[1]);
            	}else{
            		
            		list.sort(null);
            		for(int i = 0; i < list.size(); i++){
            			writer.print(prev+"	");
            			writer.println(list.get(i));
            		}
            		list = new ArrayList<Integer>();
            		list.add(b[1]);
            		prev = b[0];
            	}
			}
			list.sort(null);
    		for(int i = 0; i < list.size(); i++){
    			writer.print(prev+"	");
    			writer.println(list.get(i));
    		}
			reader.close();
            read.close();
            writer.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
