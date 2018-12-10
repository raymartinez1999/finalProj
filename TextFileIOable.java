package finalProj;

public interface TextFileIOable {
	public void createNewFile(String fileName);
	public void writeToNewFile(String fileName, String text);
	public void appendToFile(String fileName, String text);
	public boolean deleteFile(String fileName);
	public String readFile(String fileName);
	public boolean copyFile(String fileNameOrig);
	public boolean copyFile(String fileNameOrig, String fileNameCopy);
	public String readDelimitedFile(String fileName, String delimiter);
	public boolean findAndReplaceFileContent(String fileName, String textOrig, String textCopy);

}
