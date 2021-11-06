import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

public class Main {

	// Global variables
	static List<String> totalnumUsuarios = new ArrayList<String>();
	static Map<String, Integer> totalguardiasUsuarios = new HashMap<String, Integer>();
	static List<String> usuariosDisponiblesHoy = new ArrayList<String>();
	static List<String> usuariosSeleccionadosHoy = new ArrayList<String>();
	static List<String> usuariosSeleccionadosAyer = new ArrayList<String>();
	static Map<String, Integer> totalnumViernes = new HashMap<String, Integer>();
	static Map<String, Integer> totalnumSabados = new HashMap<String, Integer>();
	static Map<String, Integer> totalnumDomingos = new HashMap<String, Integer>();
	static Map<String, Integer> totalnumEntreSemana = new HashMap<String, Integer>();

	public static void main(String[] args) {
		try {
			// Variables
			String dia = "";
			Calendar systemCalendar = Calendar.getInstance();

			// Creación num total usuarios
			for (int i = 1; i < 21; i++) {
				totalnumUsuarios.add("Usuario" + i);
			}

			// Inicia Colecciones
			for (int i = 0; i < totalnumUsuarios.size(); i++) {
				totalnumViernes.put(totalnumUsuarios.get(i), 0);
				totalnumSabados.put(totalnumUsuarios.get(i), 0);
				totalnumDomingos.put(totalnumUsuarios.get(i), 0);
				totalnumEntreSemana.put(totalnumUsuarios.get(i), 0);
				totalguardiasUsuarios.put(totalnumUsuarios.get(i), 0);
			}

			// Imprime dia actual
			printToday(systemCalendar, "Día de incio");
			for (int j = 0; j < 3*365; j++) {
				// Imprime dia siguiente
				dia = printDayLater(systemCalendar, "Calendario del sistema");

				// Calcula usuarios disponibles
				switch (dia) {
				case "vie":
					disponiblesGuardiaViernes();
					break;
				case "sáb":
					disponiblesGuardiaSabados();
					break;
				case "dom":
					disponiblesGuardiaDomingos();
					break;
				default:
					disponiblesGuardiaEntreSemana();
					break;
				}

				// Aleatoriamente asigna usuarios
				usuariosSeleccionadosHoy.clear();
				usuariosSeleccionadosAyer.clear();
				for (int i = 0; i < 2; i++) {
					seleccionAleatoriaGuardiaHoy();
				}
				// Recuento dias
				switch (dia) {
				case "vie":
					recuentoGuardiaViernes();
					recuentoGuardiaTotal();
					break;
				case "sáb":
					recuentoGuardiaSabados();
					recuentoGuardiaTotal();
					break;
				case "dom":
					recuentoGuardiaDomingo();
					recuentoGuardiaTotal();
					break;
				default:
					recuentoGuardiaEntreSemana();
					recuentoGuardiaTotal();
					break;
				}

				// Imprime usuarios día
				for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
					System.out.println("Usuario seleccionado guardia: " + usuariosSeleccionadosHoy.get(i));
					usuariosSeleccionadosAyer.add(usuariosSeleccionadosHoy.get(i));
				}
				System.out.println();

			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}

		// Recuento días cada usuario
		recuentoTotal();
	}

	private static void recuentoTotal() {
		for (int i = 1; i < 21; i++) {
			System.out.print("Usuario" + i + ": ");
			System.out.print(totalguardiasUsuarios.get("Usuario" + i) + "\n");
		}

	}

	private static void recuentoGuardiaViernes() {
		for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
			totalnumViernes.put(usuariosSeleccionadosHoy.get(i),
					totalnumViernes.get(usuariosSeleccionadosHoy.get(i)) + 1);
		}
	}

	private static void recuentoGuardiaSabados() {
		for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
			totalnumSabados.put(usuariosSeleccionadosHoy.get(i),
					totalnumSabados.get(usuariosSeleccionadosHoy.get(i)) + 1);
		}
	}

	private static void recuentoGuardiaDomingo() {
		for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
			totalnumDomingos.put(usuariosSeleccionadosHoy.get(i),
					totalnumDomingos.get(usuariosSeleccionadosHoy.get(i)) + 1);
		}
	}

	private static void recuentoGuardiaEntreSemana() {
		for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
			totalnumEntreSemana.put(usuariosSeleccionadosHoy.get(i),
					totalnumEntreSemana.get(usuariosSeleccionadosHoy.get(i)) + 1);
		}
	}

	private static void recuentoGuardiaTotal() {
		for (int i = 0; i < usuariosSeleccionadosHoy.size(); i++) {
			totalguardiasUsuarios.put(usuariosSeleccionadosHoy.get(i),
					totalguardiasUsuarios.get(usuariosSeleccionadosHoy.get(i)) + 1);
		}
	}

	private static void seleccionAleatoriaGuardiaHoy() {
		Random random = new Random();
		if (!usuariosDisponiblesHoy.isEmpty()) {
			int randomNumber = random.nextInt(usuariosDisponiblesHoy.size());
			usuariosSeleccionadosHoy.add(usuariosDisponiblesHoy.get(randomNumber));
			usuariosDisponiblesHoy.remove(randomNumber);
		} else {
			System.out.println("NO HAY USUARIOS DISPONIBLES!!!");
		}
	}

//	static void printCalendar(Calendar calendar, String name) {
//		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");
//        TimeZone timeZone = calendar.getTimeZone();
//        sdf.setTimeZone(timeZone);
// 
//        System.out.printf("***** %s *****\n", name);
//        System.out.printf("Time zone        : %s\n", timeZone.getID());
//        System.out.printf("default time zone: %s\n", TimeZone.getDefault().getID());
//        System.out.printf("UTC              : %s\n", sdf.format(calendar.getTime()));
//        System.out.printf("Default          : %s\n", calendar.getTime());
//        System.out.printf("First Day of Week: %s\n", calendar.getFirstDayOfWeek());
//        System.out.println();
//	}

	private static void disponiblesGuardiaViernes() {
		int max = 0;
		int min = 0;
		int dias_trabajados = 0;
		boolean flag = false; // Detecta que todos tienen mismo num dias trabajados
		// Calcula el que ha trabajado más días
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumViernes.get(totalnumUsuarios.get(i));
			if (dias_trabajados > max) {
				max = dias_trabajados;
			} else if (dias_trabajados > 0 && dias_trabajados < max) {
				min = dias_trabajados;
				flag = true;
			} else if (dias_trabajados == 0) {
				min = 0;
				flag = true;
			}
		}
		// Detecta que no ha actualizado variable min xq todos han trabajado mismo num
		// de dias
		if (!flag) {
			min = max;
		}
		// Borra usuarios disponibles
		usuariosDisponiblesHoy.clear();
		// Añade los que menos dias han trabajado y no trabajaron ayer
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumViernes.get(totalnumUsuarios.get(i));
			if (dias_trabajados == min && !usuariosSeleccionadosAyer.contains(totalnumUsuarios.get(i))) {
				usuariosDisponiblesHoy.add(totalnumUsuarios.get(i));
			}
		}
		if (usuariosDisponiblesHoy.isEmpty()) {
			System.out.println("DISPONIBLES VACÍO");
		}
	}

	private static void disponiblesGuardiaSabados() {
		int max = 0;
		int min = 0;
		int dias_trabajados = 0;
		boolean flag = false; // Detecta que todos tienen mismo num dias trabajados
		// Calcula el que ha trabajado más días
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumSabados.get(totalnumUsuarios.get(i));
			if (dias_trabajados > max) {
				max = dias_trabajados;
			} else if (dias_trabajados > 0 && dias_trabajados < max) {
				min = dias_trabajados;
				flag = true;
			} else if (dias_trabajados == 0) {
				min = 0;
				flag = true;
			}
		}
		// Detecta que no ha actualizado variable min xq todos han trabajado mismo num
		// de dias
		if (!flag) {
			min = max;
		}
		// Borra usuarios disponibles
		usuariosDisponiblesHoy.clear();
		// Añade los que menos dias han trabajado y no trabajaron ayer
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumSabados.get(totalnumUsuarios.get(i));
			if (dias_trabajados == min && !usuariosSeleccionadosAyer.contains(totalnumUsuarios.get(i))) {
				usuariosDisponiblesHoy.add(totalnumUsuarios.get(i));
			}
		}
		if (usuariosDisponiblesHoy.isEmpty()) {
			System.out.println("DISPONIBLES VACÍO");
		}
	}

	private static void disponiblesGuardiaDomingos() {
		int max = 0;
		int min = 0;
		int dias_trabajados = 0;
		boolean flag = false; // Detecta que todos tienen mismo num dias trabajados
		// Calcula el que ha trabajado más días
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumDomingos.get(totalnumUsuarios.get(i));
			if (dias_trabajados > max) {
				max = dias_trabajados;
			} else if (dias_trabajados > 0 && dias_trabajados < max) {
				min = dias_trabajados;
				flag = true;
			} else if (dias_trabajados == 0) {
				min = 0;
				flag = true;
			}
		}
		// Detecta que no ha actualizado variable min xq todos han trabajado mismo num
		// de dias
		if (!flag) {
			min = max;
		}
		// Borra usuarios disponibles
		usuariosDisponiblesHoy.clear();
		// Añade los que menos dias han trabajado y no trabajaron ayer
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumDomingos.get(totalnumUsuarios.get(i));
			if (dias_trabajados == min && !usuariosSeleccionadosAyer.contains(totalnumUsuarios.get(i))) {
				usuariosDisponiblesHoy.add(totalnumUsuarios.get(i));
			}
		}
		if (usuariosDisponiblesHoy.isEmpty()) {
			System.out.println("DISPONIBLES VACÍO");
		}
	}

	private static void disponiblesGuardiaEntreSemana() {
		int max = 0;
		int min = 0;
		int dias_trabajados = 0;
		boolean flag = false; // Detecta que todos tienen mismo num dias trabajados
		// Calcula el que ha trabajado más días
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumEntreSemana.get(totalnumUsuarios.get(i));
			if (dias_trabajados > max) {
				max = dias_trabajados;
			} else if (dias_trabajados > 0 && dias_trabajados < max) {
				min = dias_trabajados;
				flag = true;
			} else if (dias_trabajados == 0) {
				min = 0;
				flag = true;
			}
		}
		// Detecta que no ha actualizado variable min xq todos han trabajado mismo num
		// de dias
		if (!flag) {
			min = max;
		}
		// Borra usuarios disponibles
		usuariosDisponiblesHoy.clear();
		// Añade los que menos dias han trabajado y no trabajaron ayer
		for (int i = 0; i < totalnumUsuarios.size(); i++) {
			dias_trabajados = totalnumEntreSemana.get(totalnumUsuarios.get(i));
			if (dias_trabajados == min && !usuariosSeleccionadosAyer.contains(totalnumUsuarios.get(i))) {
				usuariosDisponiblesHoy.add(totalnumUsuarios.get(i));
			}
		}
		if (usuariosDisponiblesHoy.isEmpty()) {
			System.out.println("DISPONIBLES VACÍO");
		}
	}

	private static void printToday(Calendar calendar, String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");
		TimeZone timeZone = calendar.getTimeZone();
		sdf.setTimeZone(timeZone);
		System.out.printf("***** %s *****\n", name);
		System.out.printf("Time zone        : %s\n", timeZone.getID());
		System.out.printf("UTC              : %s\n", sdf.format(calendar.getTime()));
		System.out.println();
	}

	private static String printDayLater(Calendar calendar, String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");
		SimpleDateFormat day = new SimpleDateFormat("EE");
		TimeZone timeZone = calendar.getTimeZone();
		sdf.setTimeZone(timeZone);
		System.out.printf("***** %s *****\n", name);
		calendar.add(Calendar.DATE, 1);
		System.out.printf("Next day			: %s\n", sdf.format(calendar.getTime()));
		System.out.println();
		return day.format(calendar.getTime()).toString();
	}
}