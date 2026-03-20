
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class TaskList {
    private static final String FILE = "tasks.json";
    private final List<Task> tasks;

    public TaskList(){
        this.tasks=load();
    }
    //public commands
        //add
    public void add(String description){
        int newId = tasks.stream().mapToInt(t->t.id).max().orElse(0)+1;
        Task task = new Task(newId,description);
        tasks.add(task);
        save();
        System.out.println("Task added successfully (ID: " + newId + ")");
    }
        //update
    public void update(int id,String newDescription){
        Task task= findById(id);
        if(task==null){
            System.out.println("Task not found: " + id); 
            return;
        }
        task.updateDescription(newDescription);
        save();
        System.out.println("Task " + id + " updated.");
    }
        //delete
    public void delete(int id){
        boolean removed = tasks.removeIf(t->t.id==id);
        if(!removed){
            System.out.println("Task not found: " + id);
            return;
        }
        save();
        System.out.println("Task "+id+"deleted");

    }
    
        //Status
    public void markStatus(int id,String status){
        Task task = findById(id);
        if(task==null){
            System.out.println("Task not found: " + id);
            return;
        }
        task.updateStatus(status);
        save();
        System.out.println("Task "+id+" marked as "+ status +".");
    } 
        //List by filter
    public void list(String filter) {
        if (filter != null && !filter.equals("todo")
                        && !filter.equals("done")
                        && !filter.equals("in-progress")) {
            System.out.println("Unknown status: " + filter);
            System.out.println("Valid options: todo, done, in-progress");
            return;
        }
        
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (filter == null || t.status.equals(filter)) {
                results.add(t);
            }
        }

        if (results.isEmpty()) {
            System.out.println(filter == null ? "No tasks found." : "No tasks with status: " + filter);
            return;
        }

        printHeader();
        for (Task t : results) {
            System.out.println(t);
        }
    }
    //Private Helpers
        //find task by id
    private Task findById(int id){
        return tasks.stream().filter(t->t.id==id).findFirst().orElse(null);

    }
        //print the header for main
    private void printHeader(){
        System.out.printf("%-5s %-12s %-40s %-19s%n", "ID", "Status", "Description","Updated At");
        System.out.println("-".repeat(80));
    }
    //JSON persistence
        //Save
    private void save() {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) sb.append(",\n");
            else sb.append("\n");
        }
        sb.append("]");
        try {
            Files.writeString(Path.of(FILE), sb.toString());
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
    private List<Task> load() {
        Path path = Path.of(FILE);
        if (!Files.exists(path)) return new ArrayList<>();
    
        try {
            String json = Files.readString(path).trim();
            if (json.isEmpty() || json.equals("[]")) return new ArrayList<>();
            return parseJsonArray(json);
        } catch (IOException e) {
            System.err.println("Error reading tasks file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //Json parsing
        //parseArray
    private  List<Task> parseJsonArray(String json){
        List<Task> list=new ArrayList<>();
        int depth = 0,objStart=-1;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if      (c == '{') { if (depth++ == 0) objStart = i; }
            else if (c == '}') { if (--depth == 0) list.add(parseJsonObject(json.substring(objStart, i + 1))); }
        }
        //System.out.println("Parsed " + list.size() + " tasks"); // debug
        return list;
    }
        //parse object
    private Task parseJsonObject(String obj) {
        String id          = extractValue(obj, "id");
        String description = extractValue(obj, "description");
        String status      = extractValue(obj, "status");
        String createdAt   = extractValue(obj, "createdAt");
        String updatedAt   = extractValue(obj, "updatedAt");
 
        return new Task(Integer.parseInt(id), description, status, createdAt, updatedAt);
    }
    private String extractValue(String obj, String key) {
        String search = "\"" + key + "\"";
        int keyIndex  = obj.indexOf(search);
        if (keyIndex == -1) return "";
    
        int colon = obj.indexOf(':', keyIndex + search.length());
        int valueStart = colon + 1;
    
        // Skip whitespace
        while (valueStart < obj.length() && Character.isWhitespace(obj.charAt(valueStart)))
            valueStart++;
    
        if (obj.charAt(valueStart) == '"') {
            // Quoted string value
            int vEnd = valueStart + 1;
            while (vEnd < obj.length()) {
                if (obj.charAt(vEnd) == '"' && obj.charAt(vEnd - 1) != '\\') break;
                vEnd++;
            }
            return obj.substring(valueStart + 1, vEnd)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
        } else {
            // Numeric value (id)
            int vEnd = valueStart;
            while (vEnd < obj.length() && (Character.isDigit(obj.charAt(vEnd)))) vEnd++;
            return obj.substring(valueStart, vEnd);
        }

    }
}    
    


