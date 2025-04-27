import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
    public static int id=0;
    public static String filepath="taskmanager.json";
    public static void add(int id,HashMap<String,ArrayList<String>> tasks,String task){
        boolean FileNotFound=true;
        String default_status="To-be-done";
        String key=String.valueOf(id);
        ArrayList<String> tasks_details=new ArrayList<>();
        String json_key="",json_task_name="",json_task_status="";
        int first_occurance_key=-1,last_occurance_key=0;
        int first_occurance_task_name=-1,last_occurance_task_name=0;
        int first_occurance_task_details=-1,last_occurance_task_details=0;
        try(BufferedReader read=new BufferedReader(new FileReader(filepath))) {
            String read_first_line=read.readLine();
            //first time file writing
            if(read_first_line==null){
                tasks_details.add(task);
                tasks_details.add(default_status);
                tasks.put(key,tasks_details);

                filewriter(tasks);
                FileNotFound=false;
            }
            else{
                String line;
                while((line=read.readLine())!=null){
                    if(line.equals("{")||line.equals("}")){
                        //do nothing
                        continue;
                    }
                    String task_content=line;//storing the task details into a string
                    // Removing spaces and trailing commas
                    task_content=task_content.trim();
                    if(task_content.endsWith(",")==true)
                    {
                        task_content=task_content.substring(0,task_content.length()-1);
                    }
                    first_occurance_key=task_content.indexOf("\"");
                    last_occurance_key=task_content.indexOf("\"",first_occurance_key+1);
                    json_key=task_content.substring(first_occurance_key+1,last_occurance_key);

                    first_occurance_task_name=task_content.indexOf("[",last_occurance_key);
                    last_occurance_task_name=task_content.indexOf(",",first_occurance_task_name);
                    json_task_name=task_content.substring(first_occurance_task_name+2,last_occurance_task_name-1);

                    first_occurance_task_details=task_content.indexOf("\"",last_occurance_task_name+1);
                    last_occurance_task_details=task_content.indexOf("\"",first_occurance_task_details+1);
                    json_task_status=task_content.substring(first_occurance_task_details+1,last_occurance_task_details);

                    ArrayList<String> taskentry=new ArrayList<>();
                    taskentry.add(json_task_name);
                    taskentry.add(json_task_status);
                    tasks.put(json_key,taskentry);



                }
                ArrayList<String> newtask=new ArrayList<>();
                newtask.add(task);
                newtask.add(default_status);
                String localid=String.valueOf(Integer.parseInt(json_key)+1);

                tasks.put(localid,newtask);
                filewriter(tasks);

            }
        }
        catch(IOException e){
            System.out.println(e);
        }

    }
    public static void list(){
        String json_key="",json_task_name="",json_task_status="";
        int first_occurance_key=-1,last_occurance_key=0;
        int first_occurance_task_name=-1,last_occurance_task_name=0;
        int first_occurance_task_details=-1,last_occurance_task_details=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(filepath))){
            String line;
            System.out.println("---------------------------------------------------------------------");
            System.out.printf("| %-20s | %-20s | %-20s |\n", "Task ID", "Task Name", "Task Status");
            System.out.println("---------------------------------------------------------------------");
            while((line=reader.readLine())!=null){

                if(line.equals("{")||line.equals("}")){
                    //do nothing
                    continue;
                }
                String task_content=line;//storing the task details into a string
                // Removing spaces and trailing commas
                task_content=task_content.trim();
                if(task_content.endsWith(",")==true)
                {
                    task_content=task_content.substring(0,task_content.length()-1);
                }
                first_occurance_key=task_content.indexOf("\"");
                last_occurance_key=task_content.indexOf("\"",first_occurance_key+1);
                json_key=task_content.substring(first_occurance_key+1,last_occurance_key);

                first_occurance_task_name=task_content.indexOf("[",last_occurance_key);
                last_occurance_task_name=task_content.indexOf(",",first_occurance_task_name);
                json_task_name=task_content.substring(first_occurance_task_name+2,last_occurance_task_name-1);

                first_occurance_task_details=task_content.indexOf("\"",last_occurance_task_name+1);
                last_occurance_task_details=task_content.indexOf("\"",first_occurance_task_details+1);
                json_task_status=task_content.substring(first_occurance_task_details+1,last_occurance_task_details);
                System.out.printf("| %-20s | %-20s | %-20s |\n", json_key, json_task_name, json_task_status);

            }
            System.out.println("---------------------------------------------------------------------");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> tasks = new HashMap<>();
        String check = args[0].toLowerCase();
        //force creating taskmanager.json file if it doesnt exist
        File file=new File(filepath);
        if(file.exists()!=true){
            try(FileWriter newfile=new FileWriter(filepath)){
                newfile.write("");
            }
            catch (IOException e){
                System.out.println(e);
            }
        }
        if (check.equals("add")) {
            if(args.length<2){
            System.out.println("Please provide a valid command and task name. Example: 'add \"task name\"'");
            return;
            }
        }

        String command = args[0].toLowerCase();
        switch (command) {
            case "add":
                add(++id, tasks, args[1]);
                break;
            case "list":
                list();
                break;

        }
    }
        public static void filewriter(HashMap<String,ArrayList<String>> task){

        try(FileWriter file = new FileWriter(filepath)) {

                file.write("{\n");
                boolean first=true;
                for(String keys:task.keySet()) {
                    ArrayList<String> task_details = task.get(keys);
                    if(!first)
                    {
                        file.write(",\n");
                    }
                    first=false;


                    file.write("\"" + keys + "\":[");

                    for (int i = 0; i < task_details.size(); i++)
                    {
                        file.write("\""+task_details.get(i)+"\"");
                        if(i<task_details.size()-1){
                            file.write(",");
                        }

                    }
                    file.write("]");
                }
                file.write("\n}");


            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
