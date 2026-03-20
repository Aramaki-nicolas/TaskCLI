
public class TaskCli {

    
    public static void main(String[] args) {
        
        if(args.length==0){
            printUsage();
            return;
        }
        TaskList taskList = new  TaskList();

        switch (args[0]) {
            case "add" -> {
                requireArgs(args, 2, "task-cli add \"<description>\"");
                taskList.add(args[1]);
            }
            case "update" -> {
                requireArgs(args, 3, "task-cli update <id> \"<new description>\"");
                taskList.update(parseId(args[1]), args[2]);
            }
            case "delete" -> {
                requireArgs(args, 2, "task-cli delete <id>");
                taskList.delete(parseId(args[1]));
            }
            case "mark-in-progress" -> {
                requireArgs(args, 2, "task-cli mark-in-progress <id>");
                taskList.markStatus(parseId(args[1]), "in-progress");
            }
            case "mark-done" -> {
                requireArgs(args, 2, "task-cli mark-done <id>");
                taskList.markStatus(parseId(args[1]), "done");
            }
            case "list" -> {
                String filter = (args.length > 1) ? args[1] : null;
                taskList.list(filter);
            }
            default -> {
                System.out.println("Unknown command: " + args[0]);
                printUsage();
            }
        }
    }
    //Helpers
        //parse id
    private static int parseId(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e){
            System.out.println("Invalid ID/"+ s + "/ - must be a number.");
            System.exit(1);
            return -1;
        }
    }
        // requiriment of arguments
    private static void requireArgs(String[] args,int min,String usage){
        if(args.length<min){
            System.out.println("Usage: " + usage);
            System.exit(1);
        }
    }
        //print Usage
    private static void printUsage(){
        System.out.println("""
            Task CLI commands:
              java TaskCli add "<description>"
              java TaskCli update <id> "<new description>"
              java TaskCli delete <id>
              java TaskCli mark-in-progress <id>
              java TaskCli mark-done <id>
              java TaskCli list
              java TaskCli list todo
              java TaskCli list in-progress
              java TaskCli list done
            """);
    }

}
