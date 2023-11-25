package Tema1;
public class DeathException extends RuntimeException{

    /**
     * Exception for when things die.
     * @param obj
     */
    public DeathException(Object obj){
        super(obj+" died");
    }
}
