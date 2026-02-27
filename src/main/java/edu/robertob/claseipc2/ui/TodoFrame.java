/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.claseipc2.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import edu.robertob.claseipc2.models.User;
import edu.robertob.claseipc2.models.Todo;
import edu.robertob.claseipc2.data.TodoDB;
/**
 *
 * @author robertob
 */
public class TodoFrame extends JFrame {
    private final User user;
    private final TodoDB todoDb = new TodoDB();
    
    
    // Tabla
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Título", "Hecho", "Creado"}, 0
    ) {
        // Hacemos la tabla NO editable para simplificar (editamos solo con botones)
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable table = new JTable(tableModel);
    
    // Controles
    private final JTextField txtNewTodo = new JTextField(25);
    private final JButton btnAdd = new JButton("Agregar");
    private final JButton btnToggle = new JButton("Marcar/Desmarcar");
    private final JButton btnDelete = new JButton("Eliminar");
    private final JButton btnRefresh = new JButton("Refrescar");
    private final JButton btnLogout = new JButton("Logout");
    
    
    
    
    public TodoFrame(User user) {
        super("To-Do de " + user.getUsername());
        this.user = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        wireEvents();

        // Cargar datos al iniciar
        refreshTable();
    }
    
    private void wireEvents() {
        btnAdd.addActionListener(e -> onAdd());
        btnToggle.addActionListener(e -> onToggle());
        btnDelete.addActionListener(e -> onDelete());
//        btnRefresh.addActionListener(e -> refreshTable());
//        btnLogout.addActionListener(e -> onLogout());

        // Enter en el input agrega
//        txtNewTodo.addActionListener(e -> onAdd());
    }
    
    private void onDelete() {
        Integer todoId = getSelectedTodoId();
       
        if (todoId == null) return;
        
        boolean ok = todoDb.delete(todoId);
        
        if (!ok) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la tarea");
        }
        refreshTable();
    }
    
    private void onToggle() {
        Integer todoId = getSelectedTodoId();
        
        if (todoId == null) return;
        
        boolean ok = todoDb.toggleDone(todoId);
        
        if (!ok) {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la tarea");
        }
        refreshTable();
    }
    
    private void onAdd() {
        String title = txtNewTodo.getText().trim();
        
        if (title.isEmpty()){
            JOptionPane.showMessageDialog(this, "Ingresa un nombre para la tarea");
        }
        
        boolean ok = todoDb.insert(this.user.getId(), title);
        
        txtNewTodo.setText("");
        refreshTable();
    }
    
    private JPanel buildTopPanel() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel lblUser = new JLabel("Usuario: " + user.getUsername() + " (ID=" + user.getId() + ")");
        top.add(lblUser, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.add(btnLogout);
        top.add(right, BorderLayout.EAST);

        return top;
    }

    private JScrollPane buildTablePanel() {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return scroll;
    }

    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.add(new JLabel("Nuevo To-Do:"));
        addPanel.add(txtNewTodo);
        addPanel.add(btnAdd);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.add(btnRefresh);
        actionsPanel.add(btnToggle);
        actionsPanel.add(btnDelete);

        bottom.add(addPanel, BorderLayout.WEST);
        bottom.add(actionsPanel, BorderLayout.EAST);

        return bottom;
    }
    
    private Integer getSelectedTodoId() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea primero.");
            return null;
        }

        Object value = tableModel.getValueAt(row, 0); // columna ID
        return (Integer) value;
    }

    
    
    private void refreshTable() {
        // Limpiar tabla
        tableModel.setRowCount(0);

        // Traer desde BD
        List<Todo> todos = todoDb.findByUserId(user.getId());

        // Insertar filas
        for (Todo t : todos) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getTitle(),
                    t.isDone() ? "Sí" : "No",
            });
        }
    }


}
