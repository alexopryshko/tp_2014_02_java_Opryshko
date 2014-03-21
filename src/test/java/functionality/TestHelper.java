package functionality;

public class TestHelper {
    public static String generateString(int length){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append((char) (65 + Math.random() * 25));
        return result.toString();
    }
}
