package resource;

public class Results {

    public void pass(String value) {
        System.out.println(value);
    }

    public void fail(String value) {
        System.err.println(value);
    }
}
