package Phase3_Other;


import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;




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
            if (result[0].equals("create")) {
                System.out.println("Enter a file name:");
                String fileName = buffer.readLine();
                dfs.create(fileName, "3000");
            }
            if (result[0].equals("delete")) {
                System.out.println("Enter a file to be deleted");
                String fileName = buffer.readLine();
                dfs.delete(fileName);
            }
            if (result[0].equals("touch"))
            {
                // WORK ON THIS!!!
                // Verify that parameters are greater than 1, for all of the functions
                // Validate argument is provided
                
                //Call DFS to create file
                //dfs.create();  // Create the file and give the parameter, which is the name of the file     
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
        Gson gson = new Gson();
        RemoteInputFileStream in = new RemoteInputFileStream("music.json", false);
        in.connect();
        Reader targetReader = new InputStreamReader(in);
        JsonReader jreader = new  JsonReader(targetReader);
        //Music[] music = gson.fromJson(jreader, Music[].class);
        
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
