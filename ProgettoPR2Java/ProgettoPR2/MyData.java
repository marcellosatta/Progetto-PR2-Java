

public class MyData implements Data {

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare un dato appartenente ad una bacheca,
    // composto da un campo autore e da un campo testo.

    // TYPICAL ELEMENT: <author, text>

    private String author;
    private String text;

    // FA: a(C) = <this.author, this. text>
    // IR: this.author != null && this.text != null

    // REQUIRES: author != null && text != null;
    // THROWS: NullPointerException se author == null || text == null;
    // MODIFIES: this;
    // EFFECTS: Istanzia un nuovo oggetto di tipo MyData.
    public MyData(String author, String text) throws NullPointerException {
        // COSTRUTTORE:
        if(author == null || text == null) throw new NullPointerException("One or more parameter are se to null!\n");
        this.author = author;
        this.text = text;
    }

    // REQUIRES: true;
    // EFFECTS: Stampa a schermo i campi this.author e this.text.
    public void display() {
        System.out.println(toString());
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce una stringa composta dai campi this.author e this.text.
    public String toString() {
        return "Creator: " + this.author + "\nText: " + this.text + "\n";
    }

    @Override
    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: restituisce true se data e this rappresentano lo stesso oggetto.
    public boolean equals(Object data) throws NullPointerException {
        if(this.author.equals(((MyData) data).author) && this.text.equals(((MyData)data).text)) return true;
        return false;
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce this.author.
    public String getAuthor() {
        return this.author;
    }

    // REQUIRES: true;
    // EFFECTS: Restituisce this.text.
    public String getText() {
        return this.text;
    }
}
