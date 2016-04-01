/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spiderv2;
import java.io.*;     // for File
import java.util.*;   // for Scanner
import static spiderv2.Spiderv2.addTree;
/**
 *
 * @author zulfiqar.bscs13seecs
 */
public class Spiderv2 {

    /**
     * @param args the command line arguments
     */
    public static Scanner consoles = new Scanner(System.in);
    public static String search = "";
   public static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                addTree(child, all);
            }
        }else{
            all.add(file);
        }
    }

    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Map<String, String> allEntries = new HashMap<String, String>();
        Collection<File> all = new ArrayList<File>();
        //result file stream
        FileWriter fstream = null;
        fstream = new FileWriter("result.txt");
        //result file writer
        BufferedWriter out = new BufferedWriter(fstream);

       // System.out.println("Thread"+threadName+"SAVING STARTED");

        //loop - find size and extension (lowercase)
        Iterator itr = all.iterator();
        while(itr.hasNext()){
            //get file
            File tested = (File) itr.next();
            //get ext
            String name = tested.getName();
            String path = tested.getPath();
            String[] splitted = tested.getName().split("\\.");
            String ext = splitted[splitted.length-1];
            //get size
            long size = tested.length();
            //put into file
            //if(size!=0){
                out.write(name + " " +path);
                allEntries.put(name, path);
                out.newLine();
            //}
        } 
        out.close();
        
        int size = allEntries.size()/3;
        
        System.out.println("Search keyword: ");
        search = consoles.nextLine();
        RunnableDemo R1 = new RunnableDemo("Thread-1",search,allEntries,all);
      R1.start();
      //System.out.println("Search keyword: ");
       // search = consoles.nextLine();
      RunnableDemo R2 = new RunnableDemo("Thread-2",search,allEntries,all);
      R2.start();
     //System.out.println("Search keyword: ");
        //search = consoles.nextLine();
      RunnableDemo R3 = new RunnableDemo("Thread-3",search,allEntries,all);
      R3.start();
        
        
    }

    
}

class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   private String search1;
   private Map<String, String> allEntries = new HashMap<String, String>();
   private Collection<File> all = new ArrayList<File>();
   RunnableDemo( String name, String search,Map<String, String> allEntries,Collection<File> all){
       threadName = name;
       System.out.println("Creating " +  threadName );
       search1 = search;
   }
   public void run() {
       
        
        
        
    //build file list
    System.out.println("Thread"+threadName+"COLLECTING");
    //put your own path here
    addTree(new File("C:\\Users\\awais\\Documents\\NetBeansProjects\\spiderv2"), all);
    //result file
    try {
        
    
        
        //iterating over keys only
        for (String key : allEntries.keySet()) {
            
            if(key.contains(search1)){
            System.out.println( key +" : "+ allEntries.get(key));
        }
            String line = null;
            String[] splitted = key.split("\\.");
            String ext = splitted[splitted.length-1];
            if(ext.equals("txt")){
                
               
                //File fjkg = new File(allEntries.get(key));
                FileReader fileReader = 
                new FileReader(key);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if(line.contains(search1)){
                    System.out.println(key +":"+ allEntries.get(key));
                    break;
                }
            }   

            // Always close files.
            bufferedReader.close();             
               
            }
     }
       
        

        //close file / save
        
    }catch(IOException ex){}

   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}

