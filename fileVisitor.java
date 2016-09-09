/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class fileVisitor extends SimpleFileVisitor<Path> {

    public hits[] searchHit, temp;
    String keyword;
    public String hit = "", visited = "";
    public long tsc = 0, st, end;

    public fileVisitor(String keyword) {

        this.keyword = keyword;

    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        st = System.currentTimeMillis();
        System.out.println("Just visited: " + dir);
        visited = visited + "Visited:" + dir + "\n";
        end = System.currentTimeMillis();
        tsc = tsc + (end - st);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        st = System.currentTimeMillis();
        System.out.println("About to visit: " + dir);
        visited = visited + "Going inside:" + dir + "\n";
        end = System.currentTimeMillis();
        tsc = tsc + (end - st);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        st = System.currentTimeMillis();
        String everything = "", fileName = file.getFileName().toString();
        visited = visited + "Testing:" + file + "\n";
        if (fileName.substring(fileName.length() - 4, fileName.length()).equalsIgnoreCase(".txt")) {
            visited = visited + "Read:" + file + "\n";
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
                if (everything.contains(keyword)) {
                    hits newHit = new hits(file.getFileName(), file);
                    int x;
                    if (searchHit == null) {
                        searchHit = new hits[1];
                        searchHit[0] = newHit;
                    } else {
                        temp = searchHit;
                        searchHit = new hits[temp.length + 1];
                        for (x = 0; x < temp.length; x++) {
                            searchHit[x] = temp[x];
                        }
                        searchHit[x] = newHit;
                    }
                    System.out.println("Has hit " + file.getFileName());

                    hit = hit + file + "\n";
                }
                br.close();
            }
        } else {
            visited = visited + "Visited:" + file.getFileName() + " is not a text file\n";
        }
        end = System.currentTimeMillis();
        tsc = tsc + (end - st);
        return FileVisitResult.CONTINUE;

    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        st = System.currentTimeMillis();
        System.err.println(exc.getMessage());
        visited = visited + "Visit on " + file.getFileName() + " failed\n";
        end = System.currentTimeMillis();
        tsc = tsc + (end - st);
        return FileVisitResult.CONTINUE;
    }

}
