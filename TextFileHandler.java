package finalProj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class TextFileHandler implements TextFileIOable{

	@Override
	public void createNewFile(String fileName) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(fileName);
		}catch(FileNotFoundException fnfe){
			System.out.println("Cannot create the file " + fileName);
			fnfe.getMessage();
		}
		finally {
			if(outStream != null) {
				outStream.close();
			}
		}
		
	}

	@Override
	public void writeToNewFile(String fileName, String text) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(fileName);
			outStream.println(text);
		}catch(FileNotFoundException fnfe){
			System.out.println("Cannot create the file " + fileName);
			fnfe.getMessage();
		}
		finally {
			if(outStream != null) {
				outStream.close();
			}
		}
		
	}

	@Override
	public void appendToFile(String fileName, String text) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(new FileOutputStream(fileName, true));
			outStream.println(text);
			outStream.flush();
		}catch(FileNotFoundException fnfe){
			System.err.println("Cannot create the file " + fileName);
			fnfe.getMessage();
		}
		finally {
			if(outStream != null) {
				outStream.close();
			}
		}
		
	}

	@Override
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if(file.exists()) {
			if(file.delete()) {
				return true;
			}
		}
		else{
			System.out.println("File " + fileName + " was NOT deleted or does not exist.");
		}
		return false;
	}

	@Override
	public String readFile(String fileName) {
		Scanner inStream = null;
		String fileContents = "";
		try {
			inStream = new Scanner(new File(fileName));
			
			while(inStream.hasNextLine()){
			fileContents += inStream.nextLine() + "\n";
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Sorry, I can't find the file " + fileName);
			e.printStackTrace();
		}
		finally {
			if(inStream != null) {
				inStream.close();
			}
		}
		
		
		
		
		return fileContents;
	}

	@Override
	public boolean copyFile(String fileNameOrig) {
		return false;
	}

	@Override
	public boolean copyFile(String fileNameOrig, String fileNameCopy) {
		Scanner inStream = null;
		PrintWriter outStream = null;
		try {
			inStream = new Scanner(new File(fileNameOrig));
			outStream = new PrintWriter(new File(fileNameCopy));
			while(inStream.hasNextLine()) {
				String lineIn = inStream.nextLine();
				outStream.println(lineIn);
//				char c1 = (lineIn.charAt(0));
//				String lineOut = Character.toUpperCase(c1) + lineIn.substring(1);
//				outStream.println(lineOut);
			}
			return true;
		} catch (FileNotFoundException e){
			System.err.println("UH OH! Cannot copy file");
		}
		finally {
			if(inStream != null) {
				inStream.close();
			}
			if(outStream != null) {
				outStream.close();
			}
		}
		return false;
	}

	@Override
	public String readDelimitedFile(String fileName, String delimiter) {
		Scanner inStream = null;
		String token = "";
		String fileContent = "";
		try {
			File theFile = new File(fileName);
			if(theFile.exists() && theFile.canRead()){
				inStream = new Scanner(theFile);
				inStream.useDelimiter(delimiter);
//				while(inStream.hasNext()) {
				while(inStream.hasNextLine()) {
//					token = inStream.next();
//					fileContent += token + "\n";
					String lineIn = inStream.nextLine();
					String[] tokens = lineIn.split(delimiter);
					for(int i = 0; i < tokens.length; i++) {
						fileContent += tokens[i] + "\n";
					}
				}
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(inStream != null) {
				inStream.close();
			}
		}
		return null;
	}

	@Override
	public boolean findAndReplaceFileContent(String fileName, String textOrig, String textCopy) {
		// TODO Auto-generated method stub
		return false;
	}
	public String readFileFromFileChooser() {
		JFileChooser jfc = new JFileChooser();
		int yesNo = jfc.showDialog(null, null);
		if(yesNo == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if(file.isFile()) {
				return readFile(file.getAbsolutePath());
			}
		}
		return "";
	}
	
	public static String[] sort(String[] arr) {
		Arrays.sort(arr, Comparator.comparingInt(String::length));
		return arr;
	}

}
