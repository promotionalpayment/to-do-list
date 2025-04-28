import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
    public static int id=0;
    public static String filepath="taskmanager.json";
    public static void add(int id,HashMap<String,ArrayList<String>> tasks,String task){

        String default_status="To-be-done";
        String key=String.valueOf(id);
        ArrayList<String> tasks_details=new ArrayList<>();
        String json_key="",json_task_name="",json_task_status="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_details=-1,last_occurrence_task_details=0;
        try(BufferedReader read=new BufferedReader(new FileReader(filepath))) {
            String read_first_line=read.readLine();
            //first time file writing
            if(read_first_line==null){
                tasks_details.add(task);
                tasks_details.add(default_status);
                tasks.put(key,tasks_details);
                System.out.println("Task added successfully (ID:"+id+")");
                filewriter(tasks);

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
                    first_occurrence_key=task_content.indexOf("\"");
                    last_occurrence_key=task_content.indexOf("\"",first_occurrence_key+1);
                    json_key=task_content.substring(first_occurrence_key+1,last_occurrence_key);

                    first_occurrence_task_name=task_content.indexOf("[",last_occurrence_key);
                    last_occurrence_task_name=task_content.indexOf(",",first_occurrence_task_name);
                    json_task_name=task_content.substring(first_occurrence_task_name+2,last_occurrence_task_name-1);

                    first_occurrence_task_details=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_details=task_content.indexOf("\"",first_occurrence_task_details+1);
                    json_task_status=task_content.substring(first_occurrence_task_details+1,last_occurrence_task_details);

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
                System.out.println("Task added successfully (ID:"+localid+")");
                filewriter(tasks);

            }
        }
        catch(IOException e){
            System.out.println(e);
        }

    }
    public static void list(){
        String json_key="",json_task_name="",json_task_status="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_details=-1,last_occurrence_task_details=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(filepath))){
            String line=reader.readLine();
            System.out.println("---------------------------------------------------------------------");
            System.out.printf("| %-20s | %-20s | %-20s |\n", "Task ID", "Task Name", "Task Status");
            System.out.println("---------------------------------------------------------------------");
            boolean isempty=true;
            if(line==null||line.trim().equals("{}"))
            {
                System.out.println("""
                                    There are no tasks to be displayed.
                                    Use 'add \"task\"' to add some.
                                    """);
            }
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
                if(line.startsWith("\"")) {
                    isempty=false;
                    first_occurrence_key = task_content.indexOf("\"");
                    last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                    json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                    first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                    last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                    json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                    first_occurrence_task_details = task_content.indexOf("\"", last_occurrence_task_name + 1);
                    last_occurrence_task_details = task_content.indexOf("\"", first_occurrence_task_details + 1);
                    json_task_status = task_content.substring(first_occurrence_task_details + 1, last_occurrence_task_details);
                    System.out.printf("| %-20s | %-20s | %-20s |\n", json_key, json_task_name, json_task_status);
                }



            }
            System.out.println("---------------------------------------------------------------------");

        }
        catch(IOException e){
            System.out.println("File is empty.");
        }
    }
    public static void delete(String id,HashMap<String,ArrayList<String>> tasks){
        String json_key="",json_task_name="",json_task_status="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_details=-1,last_occurrence_task_details=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(filepath))){
            String line;

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


                    first_occurrence_key = task_content.indexOf("\"");
                    last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                    json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                    first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                    last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                    json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                    first_occurrence_task_details = task_content.indexOf("\"", last_occurrence_task_name + 1);
                    last_occurrence_task_details = task_content.indexOf("\"", first_occurrence_task_details + 1);
                    json_task_status = task_content.substring(first_occurrence_task_details + 1, last_occurrence_task_details);
                    ArrayList<String> taskentry=new ArrayList<>();
                    taskentry.add(json_task_name);
                    taskentry.add(json_task_status);
                    tasks.put(json_key,taskentry);


            }
            if(tasks.containsKey(id))
            {
                System.out.println("Task #"+id+" has been deleted.");
                tasks.remove(id);

            }else{
                System.out.println("Invalid id. Please enter a valid id.");
            }
            if(tasks.isEmpty()){
                FileWriter file=new FileWriter(filepath);
                file.write("null");

            }
            else {
                filewriter(tasks);
            }

        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public static void updatetask(HashMap<String,ArrayList<String>> tasks ,String id,String newtaskstatus) {

        boolean key_found=false;
        String json_key = "", json_task_name = "", json_task_status = "";
        int first_occurrence_key = -1, last_occurrence_key = 0;
        int first_occurrence_task_name = -1, last_occurrence_task_name = 0;
        int first_occurrence_task_details = -1, last_occurrence_task_details = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line=reader.readLine();

            if(line==null){
                System.out.println("There's nothing in the task file to update. Please add a task before updating.");
            }
            while ((line = reader.readLine()) != null) {
                ArrayList<String> taskentry = new ArrayList<>();
                if (line.equals("{") || line.equals("}")) {
                    //do nothing
                    continue;
                }
                String task_content = line;//storing the task details into a string
                // Removing spaces and trailing commas
                task_content = task_content.trim();
                if (task_content.endsWith(",") == true) {
                    task_content = task_content.substring(0, task_content.length() - 1);
                }

                if (task_content.startsWith("\"")) {
                    first_occurrence_key = task_content.indexOf("\"");
                    last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                    json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                    first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                    last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                    json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                    first_occurrence_task_details = task_content.indexOf("\"", last_occurrence_task_name + 1);
                    last_occurrence_task_details = task_content.indexOf("\"", first_occurrence_task_details + 1);
                    json_task_status = task_content.substring(first_occurrence_task_details + 1, last_occurrence_task_details);

                }


                taskentry.add(json_task_name);

                if (json_key.equals(id)) {

                    taskentry.add(newtaskstatus);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {

                    taskentry.add(json_task_status);
                    tasks.put(json_key, taskentry);
                }

            }
                if(key_found!=true){
                    System.out.println("Invalid ID specified. Please provide a valid ID.");
                }
                else{
                    System.out.println("Updated Task status of #"+id+" to "+newtaskstatus);
                }

                filewriter(tasks);




        } catch (IOException e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> tasks = new HashMap<>();
        String check = args[0].toLowerCase();
        //force creating taskmanager.json file if it doesn't exist
        File file=new File(filepath);
        if(file.exists()!=true){
            try(FileWriter newfile=new FileWriter(filepath)){
                newfile.write("");
            }
            catch (IOException e){
                System.out.println(e);
            }
        }
        if (check.equals("add")||check.equals("delete")) {
            if(args.length<2){
            System.out.println(""" 
                                  Please provide a valid command and task name. Example: 'add \"task name\"'
                                                                                         'delete \"id\"'
                                """);
            return;
            }
        }
        else if(check.equals("update")){
            if(args.length<3){
                System.out.println(""" 
                                  Please provide a valid command and task name. Example: 'update \"id\" \"new status\" 
                                """);
            }
        }

        String command = args[0].toLowerCase();
        switch (command) {
            case "add":
                add(id, tasks, args[1]);
                id++;
                break;
            case "list":
                list();
                break;
            case "delete":
                delete(args[1],tasks);
                break;
            case "update":
                updatetask(tasks,args[1],args[2]);//passing id and new status
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
