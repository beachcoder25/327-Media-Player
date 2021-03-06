package Phase3_Other;


import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;
import static java.lang.Long.parseLong;




public class DFSCommand
{
    DFS dfs;
        
    public DFSCommand(int p, int portToJoin) throws Exception {
        dfs = new DFS(p);
        
        if (portToJoin > 0)
        {
            System.out.println("Joining "+ portToJoin);
            dfs.join("127.0.0.1", portToJoin);            
        }
        
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        String line = buffer.readLine();  
        while (!line.equals("quit"))
        {
            String[] result = line.split("\\s");
            if (result[0].equals("join")  && result.length > 1)
            {
                dfs.join("127.0.0.1", Integer.parseInt(result[1]));     
            }
            if (result[0].equals("print"))
            {
                dfs.print();     
            }
            if (result[0].equals("ls"))
            {
                System.out.println("***Start List***");
                System.out.println(dfs.lists());    
                System.out.println("***End List***");
            }
            
            if (result[0].equals("delete")) {
                System.out.println("Enter a file to be deleted");
                String fileName = buffer.readLine();
                dfs.delete(fileName);
            }
            if (result[0].equals("move")) {
                System.out.println("Enter a file whose name you want to change");
                String oldFileName = buffer.readLine();
                System.out.println("Enter a new name for this file");
                String newFileName = buffer.readLine();
                dfs.move(oldFileName, newFileName);
            }
            
            
            if (result[0].equals("touch"))
            {
                // WORK ON THIS!!!
                // Verify that parameters are greater than 1, for all of the functions
                // Validate argument is provided
                
                System.out.println("Enter name of file to be added");
                String fileName = buffer.readLine();
                
                System.out.println("Enter size of file to be created");
                long fileSize = parseLong(buffer.readLine());
                dfs.create(fileName, fileSize); 
  
            }
            
            if (result[0].equals("append"))
            {
                
                //application.args = "2001" "2000"
                
                
                System.out.println("Provide full filepath of music.json and append directory of page"); // logical file
                String filePath = buffer.readLine();
                RemoteInputFileStream data = new RemoteInputFileStream(filePath, false);
                System.out.println("Enter name for this file");
                String fileName = buffer.readLine();
                
                dfs.append(fileName, data);
                // WORK ON THIS!!!
                // Verify that parameters are greater than 1, for all of the functions
                // Validate argument is provided
  
            }
            
            if (result[0].equals("leave"))
            {
                dfs.leave();     
            }
            line=buffer.readLine();  
            
            
        }
            // User interface:
            // join, ls, touch, delete, read, tail, head, append, move
    }
    
    static public void main(String args[]) throws Exception
    {
        //application.args="5555" "5556"
        
        Gson gson = new Gson();
//        RemoteInputFileStream in = new RemoteInputFileStream("music.json", false); // Ask the user for path of music.JSOn file, page
//        in.connect();
//        Reader targetReader = new InputStreamReader(in);
//        JsonReader jreader = new  JsonReader(targetReader);
//        //Music[] music = gson.fromJson(jreader, Music[].class);
        
        if (args.length < 1 ) {
            throw new IllegalArgumentException("Parameter: <port> <portToJoin>");
        }
        if (args.length > 1 ) {
            DFSCommand dfsCommand=new DFSCommand(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        else
        {
            DFSCommand dfsCommand=new DFSCommand( Integer.parseInt(args[0]), 0);
        }
     } 
}
