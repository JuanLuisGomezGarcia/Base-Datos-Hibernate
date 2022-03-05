
package hibernatee;
import java.io.Serializable;
import javax.persistence.*;
//Creamos la relacion del objeto con la tabla.
@Entity
@Table(name="historial")
public class Historial implements Serializable {
    
    //Creamos los atributos de la tabla
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idevento")
    private int idevento;
    @Column(name="tipo")
    private String tipo;
    @Column(name="fechahora")
    private String fechahora;
    @Column (name="empleado")
    private String empleado;
    
    //Creamos el constructor vacio y otro con los atributos del objeto.
    public Historial() {}
    public Historial( String tipo, String fechahora, String empleado) {
    this.tipo = tipo;
    this.fechahora = fechahora;
    this.empleado = empleado;}

    // Getter y Setter de la clase
    public int getIdevento() {
    return idevento;}
    public String getTipo() {
    return tipo;}
    public void setTipo(String tipo) {
    this.tipo = tipo;}
    public String getFechahora() {
    return fechahora;}
    public void setFechahora(String fechahora) {
    this.fechahora = fechahora;}
    public String getEmpleado() {
    return empleado;}
    public void setEmpleado(String empleado) {
    this.empleado = empleado;}

    //Metodo toString
    @Override
    public String toString() {
    return  "id del evento: " + idevento + "\ntipo: " + tipo
    + "\nfechahora: " + fechahora + "\nempleado: " + empleado;}

 


    
}

