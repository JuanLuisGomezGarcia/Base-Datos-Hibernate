
package hibernatee;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FuncionesHistorial {
    
    //Creamos el session factory que luego introduciremos en el objeto session
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
    .addAnnotatedClass(Incidencias.class).addAnnotatedClass(Empleado.class)
    .addAnnotatedClass(Historial.class).buildSessionFactory();
    
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION OBTIENE EL RANKING EN FORMA DE OBJETOS
        public List<Object []> dameRanking(){         
    // Creamos la query con parámetros.
    Session sesion = sessionFactory.openSession(); 
    Query consulta = sesion.createQuery("select a.empleado, count(*) as contador "
    + " from Historial a "+ "where tipo = 'U' "+ "group by empleado "+ "order by contador desc");
    //Metemos la consulta en una lista de objetos
    List<Object[]> lisHistorial = consulta.getResultList();
    //Que luego retornamos
    return lisHistorial;}
    //ESTA FUNCION ORDENA LOS OBJEETOS Y TERMINA DE FORMAR EL RANKING
    public  void ObtenerRanking(){ 
    //recivimos el ranking y creamos la variable que generara la posicion
    List<Object[]> lisHistorial = dameRanking();
    int posicion=1;   
    //Recorreos la lista de objetos
    for (Object[] lista : lisHistorial){
    System.out.println("   RANKING");   
    //Y mostramos e contenido del objeto junto la posicion
    System.out.println("Posicion: " + posicion + " " + Arrays.toString((Object[])lista));  
    //Aumentamos la posicion cada vez que se repita el bucle
    posicion++;}}

    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------) 
    
    //ESTA FUNCION OBTIENE LA POSICION DE UN USUARIO EN UN RANKING
    public  void posicionRanking(){         
    //Creamos los objetos necesarios para usar funciones de otras clases
    FuncionesEmpleado funcionesEmpleado = new FuncionesEmpleado();
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();   
    //Llamamos a la lista de objeto como en la funcion anterior y creamos otra vez la variable posicion    
    List<Object[]> lisHistorial = dameRanking();
    int posicion=1;   
    //Pedimos un usuario al usuario de l aplicacion
    funcionesIncidencia.mostrarEmpleados();
    String mensage = "Introduce el nombre del empleado que quieres saber su posicion en el ranking";
    Empleado empleadoSeleccionado = funcionesEmpleado.verificarEmpleado(mensage);   
    //Recorremos la lista
    for (Object[] lista : lisHistorial){       
    //Transformamos el objeto en un tring quitandole la informacion del numero de historiales
    String string = Arrays.toString((Object[])lista);
    String[] parts = string.split(",");
    String part1 = parts[0]; 
    StringBuilder MyString = new StringBuilder(part1);
    MyString = MyString.deleteCharAt(0);    
    //Metemos el nombre que ha introducido el usuario junto con el que proporciona el objeto de la lista
    //Y si cuinciden entramos dentro del if donde daremos su nombre y la posicion que va aumentando 
    //cada vez que se repite el for dando la situacion exacta dentro del array
    if(empleadoSeleccionado.getNombreusuario().equals(MyString.toString())){
    System.out.println("La posicion del usuario " + MyString + " es: " + posicion);}       
    posicion++;}}
     
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION MUESTRA EL HISTORIAL DE INICIO SESION MAS RECIENTE
    public void mostrarHistorialReciente(){       
    //Iniciamos sesion  y creamos los objetos necesarios para usar otras funciones de otras clases       
    Session session = sessionFactory.openSession(); 
    FuncionesEmpleado funcionesEmpleado = new FuncionesEmpleado();
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();
    Historial historial = null;   
    //Pedimos al usuario que introdusca el nombre del ususario 
    funcionesIncidencia.mostrarEmpleados(); 
    String mensage = "Introduce el nombre del empleado del cual quiere conocer la fecha y hora de su ultimo historial de inicio de sesion";
    Empleado empleadoSeleccionado = funcionesEmpleado.verificarEmpleado(mensage);
    String nombreEmpleado = empleadoSeleccionado.getNombreusuario();   
    //Introducimos la sentenia query que nos proporcionara objetos historiales del usuario introducido del tipo I
    String ordenSQL = "SELECT p FROM Historial p WHERE empleado='"+nombreEmpleado+"' AND tipo = 'I' ORDER BY id ASC";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();   
    //Recorremos el iterator Y lo introducimos en un objeto histoial quedandonos con el ultimo que es el que tiene el id
    //mayor y por lo tanto mas reciente
    while(iterator.hasNext()){
    historial =(Historial)iterator.next();}  
    //Mostamos lafecha y hora de ese historial por pantalla
    System.out.println(historial.getFechahora());   
    //Cerramos sesion
    session.close();  }  }
