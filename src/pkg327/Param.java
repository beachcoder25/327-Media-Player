
package pkg327;

/**
 *
 * @author Jonah
 */
public class Param {
    
    String name;
    String type;
    
    public Param(String name, String type){
        name = name;
        type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    @Override
    public String toString() { 
        return String.format(this.getName() + ":" + this.getType() + " "); 
    } 
    
}
