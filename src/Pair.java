public class Pair
{
    private Integer key;
    private String value;

    public Pair(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public  void swap(Pair p1, Pair p2){
        String p1String = p1.value;

        p1.value = p2.value;
        p2.value = p1String;
    }
    public Integer key()   { return key; }
    public String value() { return value; }
}
