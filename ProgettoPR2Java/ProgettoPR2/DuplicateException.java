
// Eccezione CHECKED che viene lanciata quando un elemento è già presente all'interno della bacheca.
public class DuplicateException extends Exception {
    public DuplicateException(String s) {
        super(s);
    }
}

