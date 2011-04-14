package test;
import test.Calculator;
import java.applet.Applet;

public class MathApplet extends Applet{

    public String userName = null;
         
    public String getGreeting(String  name) {
        return "Hello " + userName + "" + name;
    }
    
    public Calculator getCalculator() {
        return new Calculator();
    } 
}
