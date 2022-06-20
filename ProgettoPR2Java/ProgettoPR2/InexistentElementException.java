

// Eccezione CHECKED che viene lanciata quando un elemento non è presente all'interno della bacheca.
public class InexistentElementException extends Exception {
    public InexistentElementException(String s) {
        super(s);
    }
}