package test.comm;
public class Dervied extends Base {

	private String name = "Dervied";
    private static String name1 = "Dervied1";
    private static final String name2 = "Dervied2";
    private final String name3=  "Dervied3";
    public String name4 = "Dervied4";
    protected String name5 = "Dervied5";
    protected static String name6 = "Dervied6";
    String name7 = "Dervied7";

    public Dervied() {
        tellName();
    }
    
    public void tellName() {
    	System.out.println(name);
    	System.out.println(name1);
    	System.out.println(name2);
    	System.out.println(name3);
    	System.out.println(name4);
    	System.out.println(name5);
    	System.out.println(name6);
    	System.out.println(name7);
    }

    public static void main(String[] args){
        
        new Dervied();    
    }
}

class Base {
    
    private String name = "base";
    private static String name1 = "1";
    private static final String name2 = "2";
    private final String name3=  "3";
    public String name4 = "4";
    protected String name5 = "5";
    protected static String name6 = "6";
    String name7 = "7";

    public Base() {
        tellName();
    }
    
    public void tellName() {
    	System.out.println(name);
    	System.out.println(name1);
    	System.out.println(name2);
    	System.out.println(name3);
    	System.out.println(name4);
    	System.out.println(name5);
    	System.out.println(name6);
    	System.out.println(name7);
    }

}