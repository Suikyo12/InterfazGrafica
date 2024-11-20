import javax.swing.*; // IMportar clases Swing
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Definir la clase, extender a JFrame para heredar propiedades.
public class Interface extends JFrame {

    private DefaultListModel<String> tareasModel;//guardar tareas y modificar la lista.
    private JList<String> tareasList; //Mostrar tareas en interfaz con el modelo como parametro.
    private JTextField tareaListado; //Listado de tareas que se van listando.
    private JTextField campoBuscar; //campo de busqueda de tareas.

    //Constructor de la clase Interface, donde crearemos y organizaremos componentes de la interfaz.
    public Interface() {
        super("Gestión de Tareas"); //titulo.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cierre de ventana.
        setSize(600,500);
        setLocationRelativeTo(null);//centrar la ventana en pantalla.
        setLayout(new BorderLayout());//diseno principal

        //Inicializa modelo de la lista y la lista visual, vinculandolas.
        tareasModel= new DefaultListModel<>();
        tareasList = new JList<>(tareasModel);
        tareasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Para seleccionar solo una tarea a la vez.

        //Panel para buscar y agregar las tareas.
        JPanel panelSuperior= new  JPanel();
        panelSuperior.setLayout(new FlowLayout()); //Organiza los componentes en fila

        //Agregamos componentes y campos de texto para agregar y buscar tareas, añadiendolos al panel superior.
        tareaListado= new JTextField(15);
        JButton agregarBoton= new JButton("Agregar Tarea");
        agregarBoton.addActionListener(new AgregarTareaListener());

        campoBuscar= new JTextField(10);
        JButton buscarBoton= new JButton("Buscar");
        buscarBoton.addActionListener(new BuscarTareaListener());

        panelSuperior.add(new JLabel("Tarea:"));
        panelSuperior.add(tareaListado);
        panelSuperior.add(agregarBoton);
        panelSuperior.add(new JLabel("Buscar:"));
        panelSuperior.add(campoBuscar);
        panelSuperior.add(buscarBoton);

        //panel para marcar y eliminar las tareas.
        JPanel panelInferior= new JPanel();
        panelInferior.setLayout(new FlowLayout());

        JButton marcarFinalizadaBoton= new JButton("Marcar como Finalizada");
        marcarFinalizadaBoton.addActionListener(new MarcarFinalizadaListener());

        JButton eliminarBoton= new JButton("Eliminar Tarea");
        eliminarBoton.addActionListener(new EliminarTareaListener());

        panelInferior.add(marcarFinalizadaBoton);
        panelInferior.add(eliminarBoton);

        //Agregar componentes al marco.
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tareasList), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true); //Hace visible la ventana.
    }

    //Listener para agregar tarea con validación de entrada.
    private class AgregarTareaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nuevaTarea = tareaListado.getText().trim(); //Obtiene la tarea ingresada.
            if (nuevaTarea.isEmpty()) { //Manejo de error.
                JOptionPane.showMessageDialog(Interface.this, "Ingrese un nombre valido para agregar");
            } else {
                tareasModel.addElement(nuevaTarea); //Agregamos la tarea al modelo
                tareaListado.setText(""); //Limpiamos campo texto.
            }
        }
    }
    //Listener para buscar tareas en la lista.
    private class  BuscarTareaListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = campoBuscar.getText().trim().toLowerCase(); //Obtenemos la busqueda.
            if (!query.isEmpty()) { //Manejo en caso de campo vacio
                for (int i= 0; i < tareasModel.size(); i++){ //Iteracion
                    if (tareasModel.getElementAt(i).toLowerCase().contains(query)) { //Caso de coincidencia:
                      tareasList.setSelectedIndex(i); //Selecciona la tarea encontrada.
                      tareasList.ensureIndexIsVisible(i);// Verificamos su visibilidad
                      return; //Termina el metodo al encontrar la tarea.
                    }
                }//Manejo en caoso de no encontrar coincidencias
                JOptionPane.showMessageDialog(Interface.this, "No se encontraron tareas que coincidan con la busqueda");
            }
        }
    }
    //Listener para marcar tarea como completada.
    private class MarcarFinalizadaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = tareasList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(Interface.this, "Seleccione una tarea primero.");
            } else {
                String tareaCompletada = tareasModel.getElementAt(selectedIndex) + " (Completada) ";
                tareasModel.set(selectedIndex, tareaCompletada);
            }
        }
    }
    //Listener para eliminar la tarea seleccionada.
    private class EliminarTareaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = tareasList.getSelectedIndex();
            if (selectedIndex != -1) {
                tareasModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(Interface.this, "Seleccione una tarea para eliminar.");
            }
        }
    }
    //Metodo principal que ejecuta la aplicacion.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interface()); //Se ejecuta el constructor para dar pie al hilo de eventos.
    }
}