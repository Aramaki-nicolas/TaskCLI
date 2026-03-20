
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task {
    static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    int id;
    String description;
    String status;
    String createdAt;
    String updatedAt;
    
    //Create a brand-new Task
    public Task(int id,String description){
        this.id=id;
        this.description=description;
        this.status="todo";
        String now=LocalDate.now().format(FMT);
        this.createdAt=now;
        this.updatedAt=now;
    }
    //Loading Task from JSON;
    public Task(int id,String description,String status,String createdAt,String updatedAt){
        this.id=id;
        this.description=description;
        this.status=status;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    public  void updateDescription(String newDescription){
        this.description=newDescription;
        this.updatedAt=LocalDateTime.now().format(FMT);
    }

    public void updateStatus(String newStatus){
        this.status=newStatus;
        this.updatedAt=LocalDateTime.now().format(FMT);
    }
    //Adapt to JSON
    public String toJson() {
    return "  {\n" +
           "    \"id\": " + id + ",\n" +
           "    \"description\": \"" + escapeJson(description) + "\",\n" +
           "    \"status\": \"" + status + "\",\n" +
           "    \"createdAt\": \"" + createdAt + "\",\n" +
           "    \"updatedAt\": \"" + updatedAt + "\"\n" +
           "  }";
    }
    @Override
    public String toString() {
        return String.format("%-5d %-12s %-40s %-19s",
                id,
                status,
                truncate(description, 39),
                updatedAt.replace("T", " "));
    }
    //Helpers
    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
 
    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
