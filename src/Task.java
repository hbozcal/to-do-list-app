public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    public String toFileString() {
        return isDone + ";" + description;
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split(";");
        Task task = new Task(parts[1]);
        if (Boolean.parseBoolean(parts[0])) {
            task.markAsDone();
        }
        return task;
    }
}