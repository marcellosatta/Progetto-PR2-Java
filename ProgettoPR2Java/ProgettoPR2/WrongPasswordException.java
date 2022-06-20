

// Eccezione CHECKED che viene lanciata quando un la password inserita non corrisponde alla password del
// proprietario della bacheca.
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String s) {
        super(s);
    }
}
