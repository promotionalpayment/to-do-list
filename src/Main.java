import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
    public static int id=0;
    public static String filepath="taskmanager.json";
    public static void add(int id,HashMap<String,ArrayList<String>> tasks,String task,String description){

        String default_status="Not done";
        String key=String.valueOf(id);
        ArrayList<String> tasks_details=new ArrayList<>();
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String date_time=Datetime();
        try(BufferedReader read=new BufferedReader(new FileReader(filepath))) {
            String read_first_line=read.readLine();
            //first time file writing
            if(read_first_line==null){
                tasks_details.add(task);
                tasks_details.add(description);
                tasks_details.add(default_status);
                tasks_details.add(date_time);
                tasks_details.add(date_time);
                tasks.put(key,tasks_details);
                System.out.println(Colors.GREEN+"Task added successfully (ID:"+id+")"+Colors.RESET);
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                    ArrayList<String> taskentry=new ArrayList<>();
                    taskentry.add(json_task_name);
                    taskentry.add(json_task_description);
                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key,taskentry);



                }
                ArrayList<String> newtask=new ArrayList<>();
                newtask.add(task);
                newtask.add(description);
                newtask.add(default_status);
                newtask.add(date_time);
                newtask.add(date_time);
                String localid=String.valueOf(Integer.parseInt(json_key)+1);

                tasks.put(localid,newtask);
                System.out.println(Colors.GREEN+"Task added successfully (ID:"+localid+")"+Colors.RESET);
                filewriter(tasks);

            }
        }
        catch(IOException e){
            System.out.println(e);
        }

    }
    public static void list(){
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(filepath))){
            String line=reader.readLine();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n","ID", "Task Name", "Task Description", "Status", "Created At", "Updated At");

            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                    if(json_task_status.equals("Not done")){
                        System.out.printf(Colors.RED+"| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n"+Colors.RESET, json_key, json_task_name,json_task_description, json_task_status,json_task_CreatedAt,json_task_updatedAt);
                    }
                    else if(json_task_status.equals("In progress")) {
                        System.out.printf(Colors.YELLOW+"| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n"+Colors.RESET, json_key, json_task_name,json_task_description, json_task_status,json_task_CreatedAt,json_task_updatedAt);
                    }
                    else if(json_task_status.equals("Done")){
                        System.out.printf(Colors.GREEN+"| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n"+Colors.RESET, json_key, json_task_name,json_task_description, json_task_status,json_task_CreatedAt,json_task_updatedAt);
                    }
                    else{
                        System.out.printf("| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n", json_key, json_task_name,json_task_description, json_task_status,json_task_CreatedAt,json_task_updatedAt);
                    }
                }



            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        }
        catch(IOException e){
            System.out.println(Colors.RED+e+Colors.RESET);
        }
    }
    public static void list(String search_status){
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        try(BufferedReader reader=new BufferedReader(new FileReader(filepath))){
            String line=reader.readLine();
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n","ID", "Task Name", "Task Description", "Status", "Created At", "Updated At");

            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

            boolean isempty=true;
            if(line==null||line.trim().equals("{}"))
            {
                System.out.println("""
                                    There are no tasks to be displayed.
                                    Use 'add \"task\"' to add some.
                                    """);
            }
            switch (search_status){
                case "Done":
                    while((line=reader.readLine())!=null) {
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
                        if (line.startsWith("\"")) {

                            isempty = false;
                            first_occurrence_key = task_content.indexOf("\"");
                            last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                            json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                            first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                            last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                            json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                            first_occurrence_task_description = task_content.indexOf("\"", last_occurrence_task_name + 1);
                            last_occurrence_task_description = task_content.indexOf("\"", first_occurrence_task_description + 1);
                            json_task_description = task_content.substring(first_occurrence_task_description + 1, last_occurrence_task_description);

                            first_occurrence_task_status = task_content.indexOf("\"", last_occurrence_task_description + 1);
                            last_occurrence_task_status = task_content.indexOf("\"", first_occurrence_task_status + 1);
                            json_task_status = task_content.substring(first_occurrence_task_status + 1, last_occurrence_task_status);

                            first_occurrence_task_creationtime = task_content.indexOf("\"", last_occurrence_task_status + 1);
                            last_occurrence_task_creationtime = task_content.indexOf("\"", first_occurrence_task_creationtime + 1);
                            json_task_CreatedAt = task_content.substring(first_occurrence_task_creationtime + 1, last_occurrence_task_creationtime);

                            first_occurrence_task_updatetime = task_content.indexOf("\"", last_occurrence_task_creationtime + 1);
                            last_occurrence_task_updatetime = task_content.indexOf("\"", first_occurrence_task_updatetime + 1);
                            json_task_updatedAt = task_content.substring(first_occurrence_task_updatetime + 1, last_occurrence_task_updatetime);

                             if (json_task_status.equals("Done")) {
                                System.out.printf(Colors.GREEN + "| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n" + Colors.RESET, json_key, json_task_name, json_task_description, json_task_status, json_task_CreatedAt, json_task_updatedAt);
                            }
                        }
                    }
                    break;
                case "Not done":
                    while((line=reader.readLine())!=null) {
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
                        if (line.startsWith("\"")) {

                            isempty = false;
                            first_occurrence_key = task_content.indexOf("\"");
                            last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                            json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                            first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                            last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                            json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                            first_occurrence_task_description = task_content.indexOf("\"", last_occurrence_task_name + 1);
                            last_occurrence_task_description = task_content.indexOf("\"", first_occurrence_task_description + 1);
                            json_task_description = task_content.substring(first_occurrence_task_description + 1, last_occurrence_task_description);

                            first_occurrence_task_status = task_content.indexOf("\"", last_occurrence_task_description + 1);
                            last_occurrence_task_status = task_content.indexOf("\"", first_occurrence_task_status + 1);
                            json_task_status = task_content.substring(first_occurrence_task_status + 1, last_occurrence_task_status);

                            first_occurrence_task_creationtime = task_content.indexOf("\"", last_occurrence_task_status + 1);
                            last_occurrence_task_creationtime = task_content.indexOf("\"", first_occurrence_task_creationtime + 1);
                            json_task_CreatedAt = task_content.substring(first_occurrence_task_creationtime + 1, last_occurrence_task_creationtime);

                            first_occurrence_task_updatetime = task_content.indexOf("\"", last_occurrence_task_creationtime + 1);
                            last_occurrence_task_updatetime = task_content.indexOf("\"", first_occurrence_task_updatetime + 1);
                            json_task_updatedAt = task_content.substring(first_occurrence_task_updatetime + 1, last_occurrence_task_updatetime);

                            if (json_task_status.equals("Not done")) {
                                System.out.printf(Colors.RED + "| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n" + Colors.RESET, json_key, json_task_name, json_task_description, json_task_status, json_task_CreatedAt, json_task_updatedAt);
                            }
                        }
                    }
                    break;

                case "In progress":
                    while((line=reader.readLine())!=null) {
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
                        if (line.startsWith("\"")) {

                            isempty = false;
                            first_occurrence_key = task_content.indexOf("\"");
                            last_occurrence_key = task_content.indexOf("\"", first_occurrence_key + 1);
                            json_key = task_content.substring(first_occurrence_key + 1, last_occurrence_key);

                            first_occurrence_task_name = task_content.indexOf("[", last_occurrence_key);
                            last_occurrence_task_name = task_content.indexOf(",", first_occurrence_task_name);
                            json_task_name = task_content.substring(first_occurrence_task_name + 2, last_occurrence_task_name - 1);

                            first_occurrence_task_description = task_content.indexOf("\"", last_occurrence_task_name + 1);
                            last_occurrence_task_description = task_content.indexOf("\"", first_occurrence_task_description + 1);
                            json_task_description = task_content.substring(first_occurrence_task_description + 1, last_occurrence_task_description);

                            first_occurrence_task_status = task_content.indexOf("\"", last_occurrence_task_description + 1);
                            last_occurrence_task_status = task_content.indexOf("\"", first_occurrence_task_status + 1);
                            json_task_status = task_content.substring(first_occurrence_task_status + 1, last_occurrence_task_status);

                            first_occurrence_task_creationtime = task_content.indexOf("\"", last_occurrence_task_status + 1);
                            last_occurrence_task_creationtime = task_content.indexOf("\"", first_occurrence_task_creationtime + 1);
                            json_task_CreatedAt = task_content.substring(first_occurrence_task_creationtime + 1, last_occurrence_task_creationtime);

                            first_occurrence_task_updatetime = task_content.indexOf("\"", last_occurrence_task_creationtime + 1);
                            last_occurrence_task_updatetime = task_content.indexOf("\"", first_occurrence_task_updatetime + 1);
                            json_task_updatedAt = task_content.substring(first_occurrence_task_updatetime + 1, last_occurrence_task_updatetime);

                             if (json_task_status.equals("In progress")) {
                                System.out.printf(Colors.YELLOW + "| %-5s | %-20s | %-25s | %-12s | %-19s | %-19s |\n" + Colors.RESET, json_key, json_task_name, json_task_description, json_task_status, json_task_CreatedAt, json_task_updatedAt);
                            }
                        }
                    }
                    break;
                default:
                    System.out.println(Colors.RED+"No such task status like that."+Colors.RESET);

            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        }
        catch(IOException e){
            System.out.println(Colors.RED+e+Colors.RESET);
        }
    }
    public static void delete(String id,HashMap<String,ArrayList<String>> tasks){
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
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

                first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                ArrayList<String> taskentry=new ArrayList<>();
                taskentry.add(json_task_name);
                taskentry.add(json_task_description);
                taskentry.add(json_task_status);
                taskentry.add(json_task_CreatedAt);
                taskentry.add(json_task_updatedAt);
                tasks.put(json_key,taskentry);


            }
            if(tasks.containsKey(id))
            {
                System.out.println(Colors.GREEN+"Task #"+id+" has been deleted."+Colors.RESET);
                tasks.remove(id);

            }else{
                System.out.println(Colors.RED+"Invalid id. Please enter a valid id."+Colors.RESET);
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
    public static void updatetask(HashMap<String,ArrayList<String>> tasks ,String id,String newtask) {

        boolean key_found=false;
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String new_date_time=Datetime();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line=reader.readLine();

            if(line==null){
                System.out.println(Colors.RED+"There's nothing in the task file to update. Please add a task before updating."+Colors.RESET);
                return;
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                }





                if (json_key.equals(id)) {

                    taskentry.add(newtask);
                    taskentry.add(json_task_description);
                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(new_date_time);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {
                    taskentry.add(json_task_name);
                    taskentry.add(json_task_description);
                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key, taskentry);
                }

            }
                if(key_found!=true){
                    System.out.println(Colors.RED+"Invalid ID specified. Please provide a valid ID."+Colors.RESET);
                }
                else{
                    System.out.println(Colors.GREEN+"Updated Task status of #"+id+" to "+newtask+Colors.RESET);
                }

                filewriter(tasks);




        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void updatetask(HashMap<String,ArrayList<String>> tasks ,String id,String newtask,String newtaskdescription) {

        boolean key_found=false;
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String new_date_time=Datetime();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line=reader.readLine();

            if(line==null){
                System.out.println(Colors.RED+"There's nothing in the task file to update. Please add a task before updating."+Colors.RESET);
                return;
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                }





                if (json_key.equals(id)) {

                    taskentry.add(newtask);
                    taskentry.add(newtaskdescription);
                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(new_date_time);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {
                    taskentry.add(json_task_name);
                    taskentry.add(json_task_description);
                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key, taskentry);
                }

            }
            if(key_found!=true){
                System.out.println(Colors.RED+"Invalid ID specified. Please provide a valid ID."+Colors.RESET);
            }
            else{
                System.out.println(Colors.GREEN+"Updated Task status of #"+id+" to "+newtask+Colors.RESET);
            }

            filewriter(tasks);




        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void In_progress(HashMap<String,ArrayList<String>> tasks ,String id) {
        String newtaskstatus="In progress";
        boolean key_found=false;
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String new_date_time=Datetime();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line=reader.readLine();

            if(line==null){
                System.out.println(Colors.RED+"There's nothing in the task file to update. Please add a task before updating."+Colors.RESET);
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                }


                taskentry.add(json_task_name);
                taskentry.add(json_task_description);

                if (json_key.equals(id)) {

                    taskentry.add(newtaskstatus);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(new_date_time);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {

                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key, taskentry);
                }

            }
            if(key_found!=true){
                System.out.println(Colors.RED+"Invalid ID specified. Please provide a valid ID."+Colors.RESET);
            }
            else{
                System.out.println(Colors.GREEN+"Updated Task status of #"+id+" to "+newtaskstatus+Colors.RESET);
            }

            filewriter(tasks);




        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void done(HashMap<String,ArrayList<String>> tasks ,String id) {
        String newtaskstatus="Done";
        boolean key_found=false;
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String new_date_time=Datetime();
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                }


                taskentry.add(json_task_name);
                taskentry.add(json_task_description);

                if (json_key.equals(id)) {

                    taskentry.add(newtaskstatus);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(new_date_time);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {

                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key, taskentry);
                }

            }
            if(key_found!=true){
                System.out.println(Colors.RED+"Invalid ID specified. Please provide a valid ID."+Colors.RESET);
            }
            else{
                System.out.println(Colors.GREEN+"Updated Task status of #"+id+" to "+newtaskstatus+Colors.RESET);
            }

            filewriter(tasks);




        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void not_done(HashMap<String,ArrayList<String>> tasks ,String id) {
        String newtaskstatus="Not done";
        boolean key_found=false;
        String json_key="",json_task_name="",json_task_status="",json_task_description="",json_task_CreatedAt="",json_task_updatedAt="";
        int first_occurrence_key=-1,last_occurrence_key=0;
        int first_occurrence_task_name=-1,last_occurrence_task_name=0;
        int first_occurrence_task_status=-1,last_occurrence_task_status=0;
        int first_occurrence_task_description=-1,last_occurrence_task_description=0;
        int first_occurrence_task_creationtime=-1,last_occurrence_task_creationtime=0;
        int first_occurrence_task_updatetime=-1,last_occurrence_task_updatetime=0;
        String new_date_time=Datetime();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line=reader.readLine();

            if(line==null){
                System.out.println(Colors.RED+"There's nothing in the task file to update. Please add a task before updating."+Colors.RESET);
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

                    first_occurrence_task_description=task_content.indexOf("\"",last_occurrence_task_name+1);
                    last_occurrence_task_description=task_content.indexOf("\"",first_occurrence_task_description+1);
                    json_task_description=task_content.substring(first_occurrence_task_description+1,last_occurrence_task_description);

                    first_occurrence_task_status=task_content.indexOf("\"",last_occurrence_task_description+1);
                    last_occurrence_task_status=task_content.indexOf("\"",first_occurrence_task_status+1);
                    json_task_status=task_content.substring(first_occurrence_task_status+1,last_occurrence_task_status);

                    first_occurrence_task_creationtime=task_content.indexOf("\"",last_occurrence_task_status+1);
                    last_occurrence_task_creationtime=task_content.indexOf("\"",first_occurrence_task_creationtime+1);
                    json_task_CreatedAt=task_content.substring(first_occurrence_task_creationtime+1,last_occurrence_task_creationtime);

                    first_occurrence_task_updatetime=task_content.indexOf("\"",last_occurrence_task_creationtime+1);
                    last_occurrence_task_updatetime=task_content.indexOf("\"",first_occurrence_task_updatetime+1);
                    json_task_updatedAt=task_content.substring(first_occurrence_task_updatetime+1,last_occurrence_task_updatetime);

                }


                taskentry.add(json_task_name);
                taskentry.add(json_task_description);

                if (json_key.equals(id)) {

                    taskentry.add(newtaskstatus);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(new_date_time);
                    key_found = true;
                    tasks.put(json_key, taskentry);

                } else {

                    taskentry.add(json_task_status);
                    taskentry.add(json_task_CreatedAt);
                    taskentry.add(json_task_updatedAt);
                    tasks.put(json_key, taskentry);
                }

            }
            if(key_found!=true){
                System.out.println(Colors.RED+"Invalid ID specified. Please provide a valid ID."+Colors.RESET);
            }
            else{
                System.out.println(Colors.GREEN+"Updated Task status of #"+id+" to "+newtaskstatus+Colors.RESET);
            }

            filewriter(tasks);




        } catch (IOException e) {
            System.out.println(Colors.RED+e+Colors.RESET);
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
                System.out.println(Colors.RED+e+Colors.RESET);
            }
        }
        if (check.equals("add")) {
            if(args.length!=3){
            System.out.println(Colors.RED+"Please provide a valid command and task name. Example: 'add \"task name\" \"task description\"'" +Colors.RESET);

                return;
            }

        }
        else if(check.equals("delete")){
            if(args.length!=2) {
                System.out.println(Colors.RED + """ 
                          Please provide a valid command and task name. Example: 'delete \"id\"'
                          """ + Colors.RESET);
                return;
            }

        }
        else if(check.equals("update")){
            if((args.length==3||args.length==4)!=true){
                System.out.println(Colors.RED+""" 
                                  Please provide a valid command and task name. Example: 'update \"id\" \"new task\"' 
                                                                                         'update \"id\" \"new task\" \"new task description\"'
                                """+Colors.RESET);
                return;
            }

        }
        else if(check.equals("mark-done")||check.equals("mark-in-progress")||check.equals("mark-not-done"))
        {
            if(args.length!=2){
                System.out.println(Colors.RED+""" 
                                  Please provide a valid command and task name. Example: 'mark-done \"id\"'  
                                """+Colors.RESET);
                return;
            }


        }
        else if(check.equals("list")){
            if((args.length==1||args.length==2)!=true){
                System.out.println(Colors.RED+""" 
                                  Please provide a valid command and task name. Example: 'list'
                                                                                          'list \"status\"'  
                                """+Colors.RESET);
                return;
            }
        }
        String command = args[0].toLowerCase();

        switch (command) {
            case "add":
                add(id, tasks, args[1],args[2]);
                id++;
                break;
            case "list":
                if(args.length==1){
                list();
                }else if(args.length==2){
                    list(args[1]);
                }
                break;
            case "delete":
                delete(args[1],tasks);
                break;
            case "update":
                if(args.length==3) {
                    updatetask(tasks, args[1], args[2]);//passing id and new task name
                } else if (args.length==4) {
                    updatetask(tasks,args[1],args[2],args[3]);//passing id , new task name and new task description
                }
                break;
            case "mark-in-progress":
                In_progress(tasks,args[1]);
                break;
            case "mark-done":
                done(tasks,args[1]);
                break;
            case "mark-not-done":
                not_done(tasks,args[1]);
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
                System.out.println(Colors.RED+e+Colors.RESET);
            }
        }
    public class Colors {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
    }//ANSI escape codes for terminal text coloring
    public static String Datetime(){
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String newdatetime=formatter.format(dateTime);
        return newdatetime;
    }//Pulls local time and date

}
