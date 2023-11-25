package Tema1;
public class DiedOfOldAgeException extends RuntimeException{

    public DiedOfOldAgeException(Object obj){
        super(obj + " died of old age");
    }
}
