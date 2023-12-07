package Dubious;
public class DeathException extends RuntimeException{

    /**
     * Exception for when things die.
     * @param obj the object that dies
     */
    public DeathException(Object obj){
        super(obj+" died");
    }
}
