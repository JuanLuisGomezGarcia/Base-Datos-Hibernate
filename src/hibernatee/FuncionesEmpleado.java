
package hibernatee;


import static hibernatee.FuncionesIncidencia.pedirString;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FuncionesEmpleado {
    //Creamos el session factory que luego introduciremos en el objeto session
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
    .addAnnotatedClass(Incidencias.class).addAnnotatedClass(Empleado.class)
    .addAnnotatedClass(Historial.class).buildSessionFactory();
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
         
    //FUNCION QUE MUESTRA SI UN EMPLEADO EXISTE O NO
    public Empleado verificarEmpleado(String mensage){
    int claveIdIncidencia = 0;
    String nombreUsuarioSeleccinado ="";
    Empleado empleado2 = null;
    //inicializamos la session
    Session session = sessionFactory.openSession();  
    //Sentencia query para seleccionar todas las incidencias y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    // pedimos el nombre a traves de la funcion pedirString creada previamente y lo comparamos con todos los
    //nombres de ususario para evitar errores de repeticion todo esto controlado dentro de un while que se repetira 
    //hasta que el numero seleccionado sea diferente a los ya existentes
    while(!(claveIdIncidencia==-1)){
    System.out.println(mensage);
    claveIdIncidencia = 0;
    nombreUsuarioSeleccinado = pedirString();
    Iterator iterator = results.iterator();
    while(iterator.hasNext()){   
    Empleado empleado =(Empleado)iterator.next();  
    if(nombreUsuarioSeleccinado.equals(empleado.getNombreusuario())){
     empleado2 =empleado;    
    System.out.println("Usuario correcto");claveIdIncidencia = -1;}}}
    //Cerramos la sesin y retornamos el valor valido
    // session.close();
    return empleado2;}

    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION MODIFICA LOS DISTINTOS VALORES DEL UN USUARIO
    public void modificarEmpleado(){
    //inicializamos la session
    Session session = sessionFactory.openSession();     
    //Creamos un objeto Empleado para poder modificarlo y le metemos el nombre de usuario
    //recivido por la funcion verificar Empleado
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();
    funcionesIncidencia.mostrarEmpleados();
    String nombreEmpleado = verificarEmpleado();
    //Sentencia query para seleccionar el empleado elegido y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p WHERE nombreusuario='"+nombreEmpleado+"'";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();
    Empleado empleado =(Empleado)iterator.next();
    //Pedimos los nuevos valores
    String nuevoPassword = insertarPassword();
    String nuevoNombreCompleto = insertarNombreCompleto();
    String nuevoTelefono = insertarTelefono();  
    //Y los introducimos en el objeto
    empleado.setTelefono(nuevoTelefono);
    empleado.setNombrecompleto(nuevoNombreCompleto);
    empleado.setPassword(nuevoPassword);
    session.beginTransaction();
    session.update(empleado);
    session.getTransaction().commit();
    //Cerramos la sesion
    session.close();}
    
   //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------) 
     
    //ESTA FUNCION ELIMINA EL EMPLEADO
     public void eliminarEmpleado(){
    //inicializamos la session
    Session session = sessionFactory.openSession();     
    //Creamos un objeto Empleado para poder eliminarlo y le metemos el nombre de usuario
    //recivido por la funcion verificar Empleado
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();
    funcionesIncidencia.mostrarEmpleados();
    String nombreEmpleado = verificarEmpleado();
    //Sentencia query para seleccionar el empleado que queremos eleiminar 
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p WHERE nombreusuario='"+nombreEmpleado+"'";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();
    Empleado empleado =(Empleado)iterator.next(); 
    //Iniciamos la transaccion y eliminamos el objeto seleccionado
    session.beginTransaction();
    session.delete(empleado);
    session.getTransaction().commit();
    session.close();}
    
     //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
     
    //ESTA FUNCION MODIFICA EL PASSWORD DE UN USUARIO
    public void modificarPassword(){
    //inicializamos la session
    Session session = sessionFactory.openSession();     
    //Creamos un objeto Empleado para poder modificarlo su password 
    //recivido por la funcion verificar Empleado
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();
    funcionesIncidencia.mostrarEmpleados();
    String nombreEmpleado = verificarEmpleado();
    //Sentencia query para seleccionar todos los empleados y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p WHERE nombreusuario='"+nombreEmpleado+"'";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();
    Empleado empleado =(Empleado)iterator.next(); 
    String nuevaContraseña = insertarPassword();
    empleado.setPassword(nuevaContraseña);
    session.beginTransaction();
    session.update(empleado);
    session.getTransaction().commit();
    session.close();}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA LOGGEAR UN USUARIO    
    public void logginUsuario(){
    //Llamamos a las funciones verificar empleado y contraseña para verificar ambos parametros
    String empleadoSeleccionado = verificarEmpleado();
    verificarContraseña(empleadoSeleccionado);
    //Una vez loggeado el usuario se crea una variable que almacena la fecha y hora y se convierte a string
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String format = dtf.format(LocalDateTime.now());
    //Este string junto el nombre del usuario y el tipo en este caso I se usan par crear un objeto historial
    Historial nuevoHistorial = new Historial("I",format,empleadoSeleccionado);
    //Se abre sessio y se realiza la transsaccion 
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(nuevoHistorial);
    session.getTransaction().commit();
    session.close();}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION QUE MUESTRA SI LA CONTRASEÑA DEL USUARIO ES CORRECTA O NO
    public String verificarContraseña(String empleadoSeleccionado){
    int claveIdIncidencia = 0;
    String contraseñaIntroducida ="";
    //inicializamos la session
    Session session = sessionFactory.openSession(); 
    //Sentencia query para seleccionar la contraseña
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p WHERE nombreusuario='"+empleadoSeleccionado+"'";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    // pedimos el nombre a traves de la funcion pedirString creada previamente y lo comparamos con todos los
    //nombres de ususario para evitar errores de repeticion todo esto controlado dentro de un while que se repetira 
    //hasta que el numero seleccionado sea diferente a los ya existentes
    while(!(claveIdIncidencia==-1)){
    System.out.println("Introdusca el password");
    claveIdIncidencia = 0;
    contraseñaIntroducida = pedirString();
    Iterator iterator = results.iterator();
    while(iterator.hasNext()){   
    Empleado empleado =(Empleado)iterator.next();  
    if(contraseñaIntroducida.equals(empleado.getPassword())){
    System.out.println("Usuario correcto");claveIdIncidencia = -1;}}}
    //Cerramos la sesin y retornamos el valor valido
    session.close();
    return contraseñaIntroducida;}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION QUE MUESTRA SI UN EMPLEADO EXISTE O NO
    public String verificarEmpleado(){
    int claveIdIncidencia = 0;
    String nombreUsuarioSeleccinado ="";
    //inicializamos la session
    Session session = sessionFactory.openSession(); 
    String ordenSQL = "SELECT p FROM Empleado p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    // pedimos el nombre a traves de la funcion pedirString creada previamente y lo comparamos con todos los
    //nombres de ususario para evitar errores de repeticion todo esto controlado dentro de un while que se repetira 
    //hasta que el numero seleccionado sea diferente a los ya existentes
    while(!(claveIdIncidencia==-1)){
    System.out.println("Introdusca el nombre de usuario");
    claveIdIncidencia = 0;
    nombreUsuarioSeleccinado = pedirString();
    Iterator iterator = results.iterator();
    while(iterator.hasNext()){   
    Empleado empleado =(Empleado)iterator.next();  
    if(nombreUsuarioSeleccinado.equals(empleado.getNombreusuario())){
    System.out.println("Usuario correcto");claveIdIncidencia = -1;}}}
    //Cerramos la sesin y retornamos el valor valido
    session.close();
    return nombreUsuarioSeleccinado;}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION QUE VALIDA LA ENTRADA DE UN USUARIO
    public void validacionUsuario(){
    //Llamamos a las dos funciones creadas para verificar tanto usuario como password
    String ususario = verificarEmpleado();
    verificarContraseña(ususario);}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA PEDIR EL TELEFONO
    public String insertarTelefono(){
    String telefonoSeleccionado = "";
    //Es una funcion sensilla pedimos un string con la funcion pedir string y pasamos su tamaño por el while
    //para controlar los requisitos de cantidad de varchar de la base de datos que no han proporcionado
    do{
    System.out.println("Introduce el telefono recuerda que su tamaño maximo sera de 9 caracteres");
    telefonoSeleccionado = pedirString();
    }while(!(telefonoSeleccionado.length()<10));
    return telefonoSeleccionado;
    }
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA PEDIR EL NOMBRE COMPLETO
    public String insertarNombreCompleto(){
    String nombreSeleccionado = "";
    //Es una funcion sensilla pedimos un string con la funcion pedir string y pasamos su tamaño por el while
    //para controlar los requisitos de cantidad de varchar de la base de datos que no han proporcionado
    do{
    System.out.println("Introduce el nombre completo recuerda que su tamaño maximo sera de 50 caracteres");
    nombreSeleccionado = pedirString();
    }while(!(nombreSeleccionado.length()<51));
    return nombreSeleccionado;}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA PEDIR EL PASSWORD
    public String insertarPassword(){
    String passwordSeleccionado = "";
    //Es una funcion sensilla pedimos un string con la funcion pedir string y pasamos su tamaño por el while
    //para controlar los requisitos de cantidad de varchar de la base de datos que no han proporcionado
    do{
    System.out.println("Introduce el password recuerda que su tamaño maximo sera de 10 caracteres");
    passwordSeleccionado = pedirString();
    }while(!(passwordSeleccionado.length()<11));
    return passwordSeleccionado;}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA PEDIR EL NOMBRE DE USUARIO
    public String insertarNombreUsuario(){
    int claveIdIncidencia = 0;
    String nombreUsuarioSeleccinado ="";
    //inicializamos la session
    Session session = sessionFactory.openSession(); 
    //Sentencia query para seleccionar todas las incidencias y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Empleado p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    // pedimos el nombre a traves de la funcion pedirString creada previamente y lo comparamos con todos los
    //nombres de ususario para evitar errores de repeticion todo esto controlado dentro de un while que se repetira 
    //hasta que el numero seleccionado sea diferente a los ya existentes
    while(!(claveIdIncidencia==-1)){
    System.out.println("Introdusca el nuevo nombre de usuario");
    claveIdIncidencia = -1;
    nombreUsuarioSeleccinado = pedirString();
    Iterator iterator = results.iterator();
    while(iterator.hasNext()){   
    Empleado empleado =(Empleado)iterator.next();
    if(nombreUsuarioSeleccinado.equals(empleado.getNombreusuario())){
    System.out.println("Ese nombre de usuario ya existe");claveIdIncidencia = 0;}}}
    //Cerramos la sesin y retornamos el valor valido
    session.close();
    return nombreUsuarioSeleccinado;}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //FUNCION PARA INSERTAR EMPLEADO
    public void insertarEmpleado(){
    //inicializamos la session   
    Session miSession = sessionFactory.openSession();      
    //Pedimos todos los valores necesarios para crear la incidencias a traves de las funciones
    //creadas especificamente en esta clase para este motivo
    String nombreUsuario = insertarNombreUsuario();
    String password = insertarPassword();
    String nombreCompleto = insertarNombreCompleto();
    String telefono = insertarTelefono();                      
    //Introducimos todos los valores en un nuevo objeto de incidencias
    Empleado empleado = new Empleado(nombreUsuario,password,nombreCompleto,telefono);
    //Y usamos las funciones de la clase session para introducir dicho objeto en la base de datos    
    miSession.beginTransaction();
    miSession.save(empleado);
    miSession.getTransaction().commit();
    miSession.close();}}
    
    
    
    

