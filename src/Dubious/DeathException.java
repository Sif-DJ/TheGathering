package Dubious;
public class DeathException extends RuntimeException{

    /**
     * Exception for when things die. Used to end certain functions,
     * that would break, because the entity stopped existing.
     * @param obj The object that died.
     */
    public DeathException(Object obj){
        super(obj+" died");
    }
}