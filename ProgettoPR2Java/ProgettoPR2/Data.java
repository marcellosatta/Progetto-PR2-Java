
public interface Data {

    // OVERVIEW: Tipo di dato astratto modificabile che intende rappresentare un dato appartenente ad una bacheca,
    // composto da un campo autore e da un campo testo.

    // TYPICAL ELEMENT: <author, text>

    // REQUIRES: true;
    // EFFECTS: Stampa a schermo i campi this.author e this.text.
    void display();

    // REQUIRES: true;
    // EFFECTS: Restituisce una stringa composta dai campi this.author e this.text.
    String toString();

    // REQUIRES: data != null;
    // THROWS: NullPointerException se data == null;
    // EFFECTS: restituisce true se data e this rappresentano lo stesso oggetto.
    boolean equals(Object data) throws NullPointerException;

    // REQUIRES: true;
    // EFFECTS: Restituisce this.author.
    String getAuthor();

    // REQUIRES: true;
    // EFFECTS: Restituisce this.text.
    String getText();
}
