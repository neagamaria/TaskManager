import java.io.Serializable;

public class Task implements Serializable {
    private String text, status;

    public Task(String text) {
        this.text = text;
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
