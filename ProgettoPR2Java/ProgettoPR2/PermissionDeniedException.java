

// Eccezione CHECKED che viene lanciata quando un amico non ha i permessi per mettere like ad un data.
public class PermissionDeniedException extends Exception {
    public PermissionDeniedException(String s) {
        super(s);
    }
}
