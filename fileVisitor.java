package machine1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class fileVisitor extends SimpleFileVisitor<Path>{

	String keyword;
	public String hits = "";
	
	public fileVisitor(String keyword){
		this.keyword = keyword;
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		System.out.println("Just visited: " + dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("About to visit: " + dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		String  everything="", fileName =file.getFileName().toString();
		if(fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".txt")){
			System.out.println(file);
			everything = "";
			File asd = new File(file.toAbsolutePath().toString());
			BufferedReader br = new BufferedReader(new FileReader(asd));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = "";
			    line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    everything = sb.toString();
			} finally {
				if(everything.contains(keyword)){
					System.out.println("Has hit " + file);
				hits = hits  + file + "##"; 
				}
			    br.close();
			}
		}
		return FileVisitResult.CONTINUE;
		
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println(exc.getMessage());
		return FileVisitResult.CONTINUE;
	}

}

