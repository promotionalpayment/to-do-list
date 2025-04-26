import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Main {
    public static int id=0;
    public static void add(int id,HashMap<String,ArrayList<String>> tasks,String task){
        String default_status="To-be-done";
        String key=String.valueOf(id);
        ArrayList<String> tasks_details=new ArrayList<>();
        tasks_details.add(task);
        tasks_details.add(default_status);
        tasks.put(key,tasks_details);
        System.out.println(tasks);
        filewriter(tasks);
    }
    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> tasks = new HashMap<>();
        if (args.length < 2) {
            System.out.println("Please provide a valid command and task name. Example: 'add \"task name\"'");
            return;
        }
        String command = args[0].toLowerCase();
        switch (command) {
            case "add":
                add(++id, tasks, args[1]);

        }
    }
        public static void filewriter(HashMap<String,ArrayList<String>> task){
            try(FileWriter file = new FileWriter("taskmanager.json")) {

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
