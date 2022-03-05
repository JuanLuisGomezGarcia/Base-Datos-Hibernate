
package hibernatee;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TestORM {

    public static void main(String[] args) {
    //Evitamos que los logger de hibernate se muestren por pantalla    
    LogManager.getLogManager().reset();
    Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
    globalLogger.setLevel(java.util.logging.Level.OFF);
    
     //Creamos objetos de las respectivas clases para usar sus funciones.
    FuncionesHistorial funcionesHistorial = new FuncionesHistorial();
    FuncionesIncidencia funcionesIncidencia = new FuncionesIncidencia();
    FuncionesEmpleado funcionesEmpleado = new FuncionesEmpleado();
   
    //Variable que usaran los distintos menus en su switch y do while
    int numeroMenu = 0;
    int numeroMenuUsuario = 0;
    int numeroMenuIncidencia = 0;
    int numeroMenuHistorial = 0;
    //MENU DE LA ACTIVIDAD
    do{
        //MENU CENTRAL    
        System.out.println("1.GESTION USUARIOS\n" +
        "2.GESTION INCIDENCIAS\n" +
        "3.GESTION HISTORIAL\n" +
        "0 Salir.");
        numeroMenu = funcionesIncidencia.pedirInt();    
        switch(numeroMenu){
        case 1:
            //Menu empleado
            do{
            System.out.println("1.Insertar un empleado nuevo en la B.D.\n" +
            "2.Validar la entrada de un empleado (suministrando usuario y contraseña)\n" +
            "3.Modificar el perfil de un empleado existente.\n" +
            "4.Cambiar la contraseña de un empleado existente.\n" +
            "5.Eliminar un empleado.\n" +
            "0 Salir.");
            numeroMenuUsuario = funcionesIncidencia.pedirInt();
            switch(numeroMenuUsuario){
            case 1: funcionesEmpleado.insertarEmpleado();
                break;
            case 2: funcionesEmpleado.logginUsuario();
                break;
            case 3: funcionesEmpleado.modificarEmpleado();
                break;
            case 4: funcionesEmpleado.modificarPassword();
                break;
            case 5: funcionesEmpleado.eliminarEmpleado();
                break;
            case 0: System.out.println("MENU USUARIOS CERRADO");
                break; }
            }while(!(numeroMenuUsuario == 0));
        break;
        case 2:
            //Menu incidencias
            do{
            System.out.println("1.Obtener un objeto Incidencia a partir de su Id.\n" +
            "2.Obtener la lista de todas las incidencias.\n" +
            "3.Insertar una incidencia.\n" +
            "4.Obtener las incidencias para un empleado a partir de un objeto de clase Empleado.\n" +
            "5.Obtener las incidencias creadas por un empleado concreto.\n" + 
            "0.Salir");
            numeroMenuIncidencia = FuncionesIncidencia.pedirInt();
            switch(numeroMenuIncidencia){
            case 1: funcionesIncidencia.obtenerrIncidencia();
                break;
            case 2: funcionesIncidencia.listaIncidencias();
                break;
            case 3: funcionesIncidencia.insertarIncidencia();
                break;
            case 4: funcionesIncidencia.incidenciasDestinoEmpledo();
                break;
            case 5: funcionesIncidencia.incidenciasOrigenEmpledo();
                break;
            case 0: System.out.println("MENU INCIDENCIAS CERRADO");
                break; }
            }while(!(numeroMenuIncidencia == 0));
        break;
        case 3:
            //Menu historial
            do{
            System.out.println("1.Obtener la fecha-hora del último inicio de sesión para un empleado concreto.\n" +
            "2.Obtener el ranking de los empleados por cantidad de incidencias urgentes creadas (más incidencias primero).\n" +
            "3.Obtener la posición dentro del ranking para un empleado concreto.\n" +
            "0.Salir");
            numeroMenuHistorial = FuncionesIncidencia.pedirInt();
            switch(numeroMenuHistorial){
            case 1: funcionesHistorial.mostrarHistorialReciente();
                break;
            case 2: funcionesHistorial.ObtenerRanking();
                break;
            case 3: funcionesHistorial.posicionRanking();
                break;
            case 0: System.out.println("MENU HISTORIAL CERRADO");
                break; }
            }while(!(numeroMenuHistorial == 0));
        break;}}while(!(numeroMenu == 0)); 
System.out.println("FIN DE PROGRAMA");}}
