import java.io.Serializable;

public class Task implements Serializable {
    private String text, status;
    int id;

    public Task(String text, int id) {
        this.text = text;
        this.id = id;
        this.status = "In lucru";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.text + " - " + this.status;
    }
}
