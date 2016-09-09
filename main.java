/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {
        String folderPath, keyword;
        Long st, end;
        Scanner read = new Scanner(System.in);
        String again;
        do {
            do {
                UI ui = new UI();
                ui.show();
                System.out.print("Enter folder path (Ex: C:\\Users\\folder: ");
                folderPath = read.next();
            } while (Files.notExists(Paths.get(folderPath)));
            Path fileDir = Paths.get(folderPath);
            System.out.print("Enter keyword to be searched: ");
            keyword = read.next();
            st = System.currentTimeMillis();
            fileVisitor visitor = new fileVisitor(keyword);
            Files.walkFileTree(fileDir, visitor);
            System.out.println("\n\n_________________________________\nFiles has hits:\n" + visitor.hit);
            end = System.currentTimeMillis();
            System.out.println("\nSearch time: " + ((end - st)) + " millis");
            System.out.print("Search again?: Y/N");
            again = read.next();
        } while (again.equalsIgnoreCase("Y"));
    }
}
