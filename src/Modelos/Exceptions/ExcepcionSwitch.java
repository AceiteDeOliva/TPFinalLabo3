package Modelos.Exceptions;

public class ExcepcionSwitch extends Exception{
    private String inputInvalido;
    public ExcepcionSwitch(String message,String inputInvalido) {
        super(message + ":" +  inputInvalido);
    }

    public String getInputInvalido(){
        return inputInvalido;
    }

}
