import java.util.*;
import java.io.*;

public class ToDoApp {
    private static final String FILE_NAME = "tasks.txt";
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasksFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- TO-DO LIST ---");
            System.out.println("1. Görevleri Listele");
            System.out.println("2. Yeni Görev Ekle");
            System.out.println("3. Görev Sil");
            System.out.println("4. Görevi Tamamla");
            System.out.println("5. Kaydet");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: listTasks(); break;
                case 2: addTask(scanner); break;
                case 3: deleteTask(scanner); break;
                case 4: completeTask(scanner); break;
                case 5: saveTasksToFile(); break;
                default: System.out.println("Geçersiz seçim!");
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Hiç görev yok.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Görev açıklaması: ");
        String desc = scanner.nextLine();
        tasks.add(new Task(desc));
        System.out.println("Görev eklendi.");
    }

    private static void deleteTask(Scanner scanner) {
        listTasks();
        System.out.print("Silmek istediğiniz görev numarası: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Görev silindi.");
        } else {
            System.out.println("Geçersiz numara.");
        }
    }

    private static void completeTask(Scanner scanner) {
        listTasks();
        System.out.print("Tamamlamak istediğiniz görev numarası: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
            System.out.println("Görev tamamlandı.");
        } else {
            System.out.println("Geçersiz numara.");
        }
    }

    private static void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toFileString());
            }
            System.out.println("Görevler kaydedildi.");
        } catch (IOException e) {
            System.out.println("Kaydedilirken hata oluştu.");
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Dosya yüklenirken hata oluştu.");
        }
    }
}
