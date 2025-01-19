public class Task {
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

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {

        return this.text + " - " + this.status;
    }
}
