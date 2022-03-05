
package hibernatee;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Creamos la relacion del objeto con la tabla.
@Entity
@Table(name="empleado")
public class Empleado implements java.io.Serializable {

    //Creamos los atributos de la tabla
    @Id
    @Column(name="nombreusuario")
    private String nombreusuario;
    @Column(name="password")
    private String password;
    @Column(name="nombrecompleto")
    private String nombrecompleto;
    @Column(name="telefono")
    private String telefono;
    
    //Creamos la relacion con otras tablas
    @OneToMany(mappedBy="origen",
    cascade={CascadeType.PERSIST,CascadeType.MERGE, 
    CascadeType.DETACH,CascadeType.REFRESH })
    private List <Incidencias> incidenciasOrigen;
    @OneToMany(mappedBy="destino",
    cascade={CascadeType.PERSIST,CascadeType.MERGE, 
    CascadeType.DETACH,CascadeType.REFRESH })
    private List <Incidencias> incidenciasDestino;
   @OneToMany(mappedBy="empleado", cascade= {CascadeType.PERSIST, 
    CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Historial> listaHistorial;

   //oCreamos el constructor vacio y otro con los atributos del objeto.
    public Empleado() {}
    public Empleado(String nombreusuario, String password, String nombrecompleto, String telefono) {
    this.nombreusuario = nombreusuario;
    this.password = password;
    this.nombrecompleto = nombrecompleto;
    this.telefono = telefono;}
    
    // Getter y Setter de la clase
    public String getNombreusuario() {
    return nombreusuario;}
    public void setNombreusuario(String nombreusuario) {
    this.nombreusuario = nombreusuario;}
    public String getPassword() {
    return password;}
    public void setPassword(String password) {
    this.password = password;}
    public String getNombrecompleto() {
    return nombrecompleto;}
    public void setNombrecompleto(String nombrecompleto) {
    this.nombrecompleto = nombrecompleto;}
    public String getTelefono() {
    return telefono;}
    public void setTelefono(String telefono) {
    this.telefono = telefono;}
    public List<Incidencias> getIncidenciasOrigen() {
    return incidenciasOrigen;}
    public void setIncidenciasOrigen(List<Incidencias> incidenciasOrigen) {
    this.incidenciasOrigen = incidenciasOrigen;}
    public void setIncidenciaOrigen(Incidencias incidenciaOrigen){
    this.incidenciasOrigen.add(incidenciaOrigen);}
    public List<Incidencias> getIncidenciasDestino() {
    return incidenciasDestino;}
    public void setIncidenciasDestino(List<Incidencias> incidenciasDestino) {
    this.incidenciasDestino = incidenciasDestino;}
    public void setIncidenciaDestino(Incidencias incidenciaDestino){
    this.incidenciasDestino.add(incidenciaDestino);}
    public List<Historial> getListaHistorial() {
    return listaHistorial;}
    public void setListaHistorial(List<Historial> listaHistorial) {
    this.listaHistorial = listaHistorial;}
    public void addEventoToHistorial(Historial miHistorial){
    this.listaHistorial.add(miHistorial);}
    
    //Metodo toString
    @Override
    public String toString() {
    return "Nombre del usuario: " + nombreusuario + "\npassword=" + password + 
    "\nnombre completo: " + nombrecompleto + "\ntelefono: " + telefono ;}}

