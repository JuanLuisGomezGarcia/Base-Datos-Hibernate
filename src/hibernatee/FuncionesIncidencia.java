
package hibernatee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FuncionesIncidencia {
    //Creamos el session factory que luego introduciremos en el objeto session
    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
    .addAnnotatedClass(Incidencias.class).addAnnotatedClass(Empleado.class)
    .addAnnotatedClass(Historial.class).buildSessionFactory();
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTE METODO GENERA UNA LISTA CON TODAS LAS INCIDENCIAS DESTINADAS A EL EMPLEADO SELECCONADO
    public void incidenciasDestinoEmpledo(){
    //inicializamos la session
    Session session = sessionFactory.openSession();
    //Mostramos los empleados disponibles y el String con el mensage que queremos que salga por pantalla
    //para indicar el motivo de la seleccion de un usuario y activamos la funion especifica para seleccionar usuario
    mostrarEmpleados();
    String mensage = "Introduce el nombre del empleado que quieras realizar la consulta";
    FuncionesEmpleado funconesEmpleado = new FuncionesEmpleado();
    Empleado origen = funconesEmpleado.verificarEmpleado(mensage);
    //Pasamos la lista de incidencias DESTINO a una list que despues leemos con un for
    List listaIncidencia = origen.getIncidenciasDestino();
    for (int i = 0; i<listaIncidencia.size();i++){System.out.println(listaIncidencia.get(i));   }
    //Cerramos la sesion
    session.close();    
    //Una vez que se han mostrado las incidencias que tienen como destio el usuario se crea una 
    //variable que almacena la fecha y hora y se convierte a string
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String format = dtf.format(LocalDateTime.now());
    //Este string junto el nombre del usuario y el tipo en este caso I se usan par crear un objeto historial
    Historial nuevoHistorial = new Historial("C",format,origen.getNombreusuario());
    //Se abre sessio y se realiza la transsaccion 
    session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(nuevoHistorial);
    session.getTransaction().commit();
    session.close();}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTE METODO GENERA UNA LISTA CON TODAS LAS INCIDENCIAS GENERADAS POR EL EMPLEADO SELECCONADO
    public void incidenciasOrigenEmpledo(){
    //inicializamos la session
    Session session = sessionFactory.openSession();
    //Mostramos los empleados disponibles y el String con el mensage que queremos que salga por pantalla
    //para indicar el motivo de la seleccion de un usuario y activamos la funion especifica para seleccionar usuario
    mostrarEmpleados();
    String mensage = "Introduce el nombre del empleado que quieras realizar la consulta";
    FuncionesEmpleado funconesEmpleado = new FuncionesEmpleado();
    Empleado origen = funconesEmpleado.verificarEmpleado(mensage);
    //Pasamos la lista de incidencias ORIGEN a una list que despues leemos con un for
    List listaIncidencia = origen.getIncidenciasOrigen();
    for (int i = 0; i<listaIncidencia.size();i++){System.out.println(listaIncidencia.get(i));   }
    //Cerramos la sesion
    session.close();}

    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTE METODO MUESTRA POR PANTALLA TODAS LAS INCIDENCIAS EN LA BASE DE DATOS
    public void listaIncidencias(){
    //inicializamos la session
    Session session = sessionFactory.openSession();
    //Creamos la sentencia SQL y la introducimos en un objeto query del cual luego sacaremos una lista
    String ordenSQL = "SELECT p FROM Incidencias p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();
    //Recorremos el iterator con un while y lo mostramos por pantalla
    while(iterator.hasNext()){
    Incidencias incidencias =(Incidencias)iterator.next();
    System.out.println(incidencias.toString());}
    //Cerramos sesion
    session.close();  }    

    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION OBTIENE LA INCIDENCIA SSELECCIONADA A TRAVES DE SU ID
    public void obtenerrIncidencia(){
    //inicializamos la session
    Session session = sessionFactory.openSession();
    //creamos una clave y un while para que controle la excepcion en caso de que el id no exista
    int clave = 0;
    while(clave==0){
    try{
    System.out.println("Introduce el id de la incidencia");
    int idSeleccionado = pedirInt();
    //Generamos un objeto incidencia que recogera la incidencia funcionesEmpleado traves del metodo get
    // que recoge la clase del objeto y el id previamente recogedo funcionesEmpleado traves del usuario
    Incidencias incidenciaSeleccionada = session.get(Incidencias.class,idSeleccionado);
    System.out.println(incidenciaSeleccionada.toString());
    clave = -1;
    }catch(Exception e){System.out.println("Numero identificador no encontrado");}}
    session.close();}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION NOS PERMITE PEDIR EL TIPO DE LA INCIDENCIA
    public String introducirTipo(){
    //Es una funcion sensilla pedimos un string con la funcion pedir string y pasamos su tamaño por el while
    //para controlar los requisitos de cantidad de varchar de la base de datos que no han proporcionado
    String tipoSeleccionado = "";
    do{
    System.out.println("Introduce el tipo recuerda que su tamaño maximo sera de 8 caracteres");
    tipoSeleccionado = pedirString();
    }while(!(tipoSeleccionado.length()<9));
    return tipoSeleccionado;}        
     
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION NOS PERMITE PEDIR LA FECHA Y HORA DE LA INCIDENCIA
    public String introducirFechaHora(){
    //Es una funcion sensilla pedimos un string con la funcion pedir string y pasamos su tamaño por el while
    //para controlar los requisitos de cantidad de varchar de la base de datos que no han proporcionado
    String fechaHoraSeleccionada = "";
    do{
    System.out.println("Introduce la fecha y hora recuerda que su tamaño maximo sera de 50 caracteres");
    fechaHoraSeleccionada = pedirString();
    }while(!(fechaHoraSeleccionada.length()<51));
    return fechaHoraSeleccionada;}
              
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
           
    //ESTA FUNCION NOS PERMITE PEDIR EL NUMERO DE IDENTIFICACION DE LA INCIDENCIA
    public int introducirNumeroIncidencia(){
    int claveIdIncidencia = 0;
    int idSeleccionado =0;
    //inicializamos la session
    Session session = sessionFactory.openSession();  
    //Sentencia query para seleccionar todas las incidencias y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    String ordenSQL = "SELECT p FROM Incidencias p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    // pedimos el numero funcionesEmpleado trabes de la funcion pedirINT creada previamente y lo comparamos con todos los
    //id para evitar errores de repeticion todo esto controlado dentro de un while que se repetira 
    //hasta que el numero seleccionado sea diferente funcionesEmpleado los ya existentes
    while(!(claveIdIncidencia==-1)){
    System.out.println("Introdusca el nuevo numero de identificacion");
    claveIdIncidencia = -1;
    idSeleccionado = pedirInt();
    Iterator iterator = results.iterator();
    while(iterator.hasNext()){   
    Incidencias a =(Incidencias)iterator.next();
    if(idSeleccionado==a.getIdincidencia()){
    System.out.println("Ese numero de identificacion ya existe");claveIdIncidencia = 0;}}}
    //Cerramos la sesin y retornamos el valor valido
    session.close();
    return idSeleccionado;}

    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
    //ESTA FUNCION NOS PERMITE PEDIR TODOS LOS DATOS DE LA INCIDENCIA
    public void insertarIncidencia(){
    //inicializamos la session   
    Session miSession = sessionFactory.openSession();      
    //Pedimos todos los valores necesarios para crear la incidencias funcionesEmpleado traves de las funciones
    //creadas especificamente en esta clase para este motivo
    int idIncidencia = introducirNumeroIncidencia();
    mostrarEmpleados();
    String mensage = "Introdusca el nombre del empleado de ORIGEN:";
    FuncionesEmpleado funcionesEmpleado = new FuncionesEmpleado();
    Empleado origen = funcionesEmpleado.verificarEmpleado(mensage);
    String empleadoOrigen = origen.getNombreusuario();
    mensage = "Introdusca el nombre del empleado de DESTINO:";
    Empleado destino = funcionesEmpleado.verificarEmpleado(mensage); 
    String empleadoDestino = destino.getNombreusuario();
    String fechaHora = introducirFechaHora();   
    String tipo = introducirTipo();
    System.out.println("Introduce los detalles");
    String detalle = pedirString();
    //Introducimos todos los valores en un nuevo objeto de incidencias
    Incidencias incidencia = new Incidencias(idIncidencia,empleadoOrigen,empleadoDestino,fechaHora,detalle,tipo);
    //Y usamos las funciones de la clase session para introducir dicho objeto en la base de datos  
    miSession.beginTransaction();
    miSession.save(incidencia);
    miSession.getTransaction().commit();
    miSession.close();
    //Creamos un if que filtra las incidencia y genera un historial en caso de que sea urgente
    if(tipo.equals("Urgente")||tipo.equals("urgente")){    
    //Una vez creada la incidencia se crea una variable que almacena la fecha y hora y se convierte a string
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String format = dtf.format(LocalDateTime.now());
    //Este string junto el nombre del usuario y el tipo en este caso I se usan par crear un objeto historial
    Historial nuevoHistorial = new Historial("C",format,origen.getNombreusuario());
    //Se abre sessio y se realiza la transsaccion 
    miSession = sessionFactory.openSession();
    miSession.beginTransaction();
    miSession.save(nuevoHistorial);
    miSession.getTransaction().commit();
    miSession.close();}}
    
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)
    
        //Funcion para pedirr String
        public static String pedirString(){
        String cadenaRetorno="";
        double clave_pedirDouble;
        do{ clave_pedirDouble=0;
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        cadenaRetorno= br. readLine();
        clave_pedirDouble=0;
        }catch(Exception e){ clave_pedirDouble=-1; System.out.println("Intentelo de nuevo");}
        }while(! (clave_pedirDouble==0));
        return cadenaRetorno;}    

        //Fucion parapedir int
        public static int pedirInt(){
        int numeroRetornar=0;
        int clave_pedirInt;
        do{ clave_pedirInt=0;
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String brm = br.readLine();
        numeroRetornar = Integer.parseInt(brm);
        if(numeroRetornar<0){
            System.out.println("Introdusca un valor positivo por favor");
            clave_pedirInt=-1;
        }
        }catch(Exception e){ clave_pedirInt=-1;System.out.println("Introdusca un numero por favor");}
        }while(!(clave_pedirInt==0));
        return numeroRetornar;}      
        
    //(--------------S-E-P-A-R-A-D-O-R----------0-------------F-U-N-C-I-O-N-E-S----------------------)       
    
//FUNCION QUE MUESTRA LOS EMPLEADOS        
    public void mostrarEmpleados(){
    //Iniciamos sesion        
    Session session = sessionFactory.openSession(); 
    //Sentencia query para seleccionar todas las incidencias y poder comparar el nuevo
    //y metemos la consulta realizada en una lista y de hay al iterator
    System.out.println("Empleados disponibles");
    String ordenSQL = "SELECT p FROM Empleado p";
    Query query = session.createQuery(ordenSQL);
    List results = query.getResultList(); 
    Iterator iterator = results.iterator();
    //Recorremos el iterator con un while y lo mostramos por pantalla
    while(iterator.hasNext()){
    Empleado empleado =(Empleado)iterator.next();
    System.out.println(empleado.getNombreusuario());}
    //Cerramos sesion
    session.close();  } }
    
    


