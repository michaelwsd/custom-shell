enum OutputType {
    err,
    out;

    static OutputType of(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}