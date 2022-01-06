package com.configparse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConfigParser {
    private String filename;
    private String environment = "src/main/resources/Config.txt";
    private final static Map<String, String> configuration  = new HashMap<>();

    public ConfigParser() {
    }

    public ConfigParser(String filename) {
        this.filename = filename != null && filename.length()>0 ? filename: environment;

        readFile(this.filename);

    }

    private static void readFile(String fileName) {

        File file = new File(fileName);

        if(!file.exists()) {
            System.out.println("Configuration file not found");
        }

        int count = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))){

            String line;
            String prefix = "";

            while ((line = br.readLine()) != null){

                if(line.isEmpty()){
                    prefix = "";
                    continue;
                }

                if(line.startsWith("[") && line.endsWith("]")){

                    count++;

                    prefix = line.substring(1, line.length()-1)+count+".";
                    continue;
                }

                if(count == 1){
                    configuration.put(prefix+splitString(line)[0], splitString(line)[1]);
                }

                else if(count == 2){

                    configuration.put(prefix+splitString(line)[0], splitString(line)[1]);
                }
                else{
                    configuration.put(splitString(line)[0], splitString(line)[1]);
                }
            }
        }
        catch (IOException e){
            System.out.println("Error - cannot read from file "+ fileName);
        }

    }

    private static String[] splitString(final String line){

        return line.split("=");

    }

    public void readConfig(){
        System.out.println("YOU ARE IN A "+ configuration.get("mode").toUpperCase() + " ENVIRONMENT.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to run the program (Y/N) : ");
        String opt = scanner.nextLine();
        if (opt.equalsIgnoreCase("N") || !opt.equalsIgnoreCase("Y")){
            return;
        } else if(opt.equalsIgnoreCase("Y")){
            boolean terminate = false;
            while (!terminate) {
                System.out.print("Enter KEY : ");
                System.out.println("VALUE : " + configuration.get(scanner.nextLine().toLowerCase()));

                System.out.print("Do you want to continue (Y/N) : ");

                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("N")) {
                    terminate = true;
                }
                else if (!answer.equalsIgnoreCase("Y")){
                    System.out.println("Error, Invalid KEY!");
                    terminate = true;
                }
            }
        }
    }



    public Map<String, String> getConfiguration(){
        return this.configuration;
    }

}

