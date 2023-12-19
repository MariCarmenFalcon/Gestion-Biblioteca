package biblioteca;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
/**
 *
 * @author mcfal
 */
public class VentPrincipal extends javax.swing.JFrame {
          
    int x = 0, y = 0;    
    DefaultTableModel l; 
    DefaultTableModel p;
     
    public VentPrincipal() {
        initComponents();
        setSize(950, 700);
        setLocationRelativeTo(null);        
        mostrarFechaActual();
        configurarJSpinnerFecha();
        ajustarAnchoColumnas();
        realizarAutenticacion();
        
        PrepararTablaLibros();        
        agregarLibrosATabla();
        ajustarAnchoColumnas();
        PrepararTablaPrestamos();
        
        
    }
    
    private void realizarAutenticacion() {
        String usuario = txtLoginUsuario.getText();
        String contraseña = txtPassword.getText();

        if ((usuario.equals("Alumno") && contraseña.equals("Alumno"))
                || (usuario.equals("Admin") && contraseña.equals("Admin"))) {
            habilitarAcceso(usuario.equals("Admin"));
        } else {
            denegarAcceso();
        }
    }

    private void habilitarAcceso(boolean esAdmin) {
        jPanelPrestamo.setEnabled(true);
        jPanelDevol.setEnabled(true);
        jPanelUsuarios.setEnabled(true);
        jPanelLibros.setEnabled(true);

        jButton1AñadirLibro.setEnabled(esAdmin);
        jButtonEditar.setEnabled(esAdmin);
        jButton4Eliminar.setEnabled(esAdmin);
    }

    private void denegarAcceso() {
        jPanelPrestamo.setEnabled(false);
        jPanelDevol.setEnabled(false);
        jPanelUsuarios.setEnabled(false);
        jPanelLibros.setEnabled(false);

        jButton1AñadirLibro.setEnabled(false);
        jButtonEditar.setEnabled(false);
        jButton4Eliminar.setEnabled(false);

       // JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecta", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
    }
    
    //TABLA RESERVAS PRESTAMOS USUARIOS(PANEL USUARIOS)
    public void PrepararTablaPrestamos(){
        
        String titulos[] = {"DNI Usuario", "ID Usuario", "Nombre Libro", "ID Libro", "Fecha Prestamo", "Fecha Devolucion"};
        p = new DefaultTableModel(null, titulos);
        tablaPrestamos.setModel(p);
        tablaPrestamos.setDefaultEditor(Object.class, null); 
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            System.out.println("Conectado");
            
            String query = "SELECT * FROM prestamos";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            p.setRowCount(0);
            
            while (rs.next()) {                
                String dni = rs.getString("Dni");
                String idUsuario = rs.getString("IDUsuario");                
                String nombreLibro = rs.getString("NombreLibro");
                String idLibro = rs.getString("IDLibro");                
                String fechaPrest = rs.getString("FechaPrestamo");  
                String fechaDevol = rs.getString("FechaDevolucion");
                
                p.addRow(new Object[]{dni, idUsuario, nombreLibro, idLibro, fechaPrest, fechaDevol});
            }
            rs.close();
            stmt.close();
            con.close();
            
        } catch (Exception ex) {
            System.out.println("No conectado. Error");
            ex.printStackTrace();
        }   
        ajustarAnchoColumnas();
    }    
    
    //TABLA LIBROS (PANEL LIBROS)
    public void PrepararTablaLibros(){
        String titulos[] = {"Nombre Libro", "ID Libro", "Autor", "Genero", "Año Publicacion", "Prestado", "ID Usuario"};
        l = new DefaultTableModel(null, titulos);        
        tablaLibros.setModel(l);
        tablaLibros.setDefaultEditor(Object.class, null);  
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            System.out.println("Conectado");
            
            String query = "SELECT * FROM libros";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            l.setRowCount(0);
            
            while (rs.next()) {                
                String nombre = rs.getString("Nombre");
                String idLibro = rs.getString("IDLibro");                
                String autor = rs.getString("Autor");
                String genero = rs.getString("Genero");                
                String anyoPubl = rs.getString("AñoPublicacion");
                String prestado = rs.getString("Prestado");
                String idUsuario = rs.getString("IDUsuario");
                
                l.addRow(new Object[]{nombre, idLibro, autor, genero, anyoPubl,prestado, idUsuario});
            }
            rs.close();
            stmt.close();
            con.close();
            
          
            
        } catch (Exception ex) {
            System.out.println("No conectado. Error");
            ex.printStackTrace();
        }
        ajustarAnchoColumnas();
        tablaLibros.setModel(l);
    }
    
   //AGREGAR LIBROS TABLA
   public void agregarLibrosATabla() {
        
       l.setRowCount(0);
       
       String[][] libros = {
           {"Cien años de soledad", "1", "Gabriel García Márquez", "Realismo", "1967", "Disponible", "0"},
           {"Don Quijote de la Mancha", "2", "Miguel de Cervantes", "Novela", "1605", "Disponible", "0"},
           {"La sombra del viento", "3", "Carlos Ruiz Zafón", "Misterio", "2001", "Disponible", "0"},
           {"Rayuela", "4", "Julio Cortázar", "Ficción", "1963", "Disponible", "0"},
           {"El amor en los tiempos del cólera", "5", "Gabriel García Márquez", "Romance", "1985", "Disponible", "0"},
           {"La Casa de los Espíritus", "6", "Isabel Allende", "Realismo", "1982", "Disponible", "0"},
           {"El laberinto de los espíritus", "7", "Carlos Ruiz Zafón", "Misterio", "2016", "Disponible", "0"},
           {"La ciudad y los perros", "8", "Mario Vargas Llosa", "Ficción", "1962", "Disponible", "0"},
           {"Los detectives salvajes", "9", "Roberto Bolaño", "Ficción", "1998", "Disponible", "0"},
           {"Pedro Páramo", "10", "Juan Rulfo", "Realismo", "1955", "Disponible", "0"},
           {"Crónica de una muerte anunciada", "11", "Gabriel García Márquez", "Realismo", "1981", "Disponible", "0"},
           {"El Aleph", "12", "Jorge Luis Borges", "Ficción", "1949", "Disponible", "0"},
           {"Ficciones", "13", "Jorge Luis Borges", "Ficción", "1944", "Disponible", "0"},
           {"Los ejércitos", "14", "Evelio Rosero", "Ficción", "2006", "Disponible", "0"},
           {"La hojarasca", "15", "Gabriel García Márquez", "Realismo", "1955", "Disponible", "0"},
           {"La metamorfosis", "16", "Franz Kafka", "Ficción", "1915", "Disponible", "0"},
           {"Rayuela", "17", "Julio Cortázar", "Ficción", "1963", "Disponible", "0"},
           {"La tregua", "18", "Mario Benedetti", "Ficción", "1960", "Disponible", "0"},
           {"La montaña mágica", "19", "Thomas Mann", "Ficción", "1924", "Disponible", "0"},
           {"Crimen y castigo", "20", "Fiodor Dostoievski", "Ficción", "1866", "Disponible", "0"}
       };

        for (String[] libro : libros) {
            l.addRow(libro);
        }

        insertarLibrosBaseDatos(libros);
    }
   
    public void insertarLibrosBaseDatos(String[][] libros) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            System.out.println("Conectado");

            // Elimina todos los registros de la tabla libros
            String deleteQuery = "DELETE FROM libros";
            Statement deleteStmt = con.createStatement();
            deleteStmt.executeUpdate(deleteQuery);

            // Inserta los nuevos libros en la base de datos
            String insertQuery = "INSERT INTO libros (Nombre, IDLibro, Autor, Genero, AñoPublicacion, Prestado, IDUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (String[] libro : libros) {
                preparedStatement.setString(1, libro[0]); // Nombre
                preparedStatement.setString(2, libro[1]); // ID Libro
                preparedStatement.setString(3, libro[2]); // Autor
                preparedStatement.setString(4, libro[3]); // Género
                preparedStatement.setString(5, libro[4]); // Año de Publicación
                preparedStatement.setString(6, "Disponible"); // Valor para Prestado
                preparedStatement.setString(7, "0"); // Valor para IDUsuario

                preparedStatement.executeUpdate();
            }

            deleteStmt.close();
            preparedStatement.close();
            con.close();
            
        } catch (Exception ex) {
            System.out.println("No conectado. Error");
            ex.printStackTrace();
        }
    }

    //AJUSTAR ANCHO COLUMNAS
    public void ajustarAnchoColumnas() {
        // REcorre sobre las columnas y ajustar el ancho basado en el contenido
        for (int i = 0; i < tablaLibros.getColumnCount(); i++) {
            TableColumn column = tablaLibros.getColumnModel().getColumn(i);
            int width = 50; // Ancho mínimo inicial

            // REcorre sobre las filas para encontrar el ancho necesario
            for (int j = 0; j < tablaLibros.getRowCount(); j++) {
                TableCellRenderer renderer = tablaLibros.getCellRenderer(j, i);
                Component comp = tablaLibros.prepareRenderer(renderer, j, i);
                width = Math.max(comp.getPreferredSize().width + tablaLibros.getIntercellSpacing().width, width);
            }

            // Establece el ancho calculado para la columna "Año Publicacion"
            if (tablaLibros.getColumnName(i).equals("Año Publicacion")) {
                // Ancho más grande para esta columna específica
                column.setPreferredWidth(width + 30); 
            } else {
                column.setPreferredWidth(width);
            }
        }
    }

    //METODO FECHA PRESTAMO Y DEVOLUCION
    private void configurarJSpinnerFecha() {
        // Crea modelo para manejar fechas
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);

        // Crea JSpinner con el modelo de fecha
        JSpinner jSpinnerFecha = new JSpinner(model);

        // Crea un formateador para mostrar la fecha en el formato deseado
        JSpinner.DateEditor editor = new JSpinner.DateEditor(jSpinnerFecha, "dd/MM/yyyy");
        jSpinnerFecha.setEditor(editor);

        // Añade el JSpinner al jPanelPrestamo
        jPanelPrestamo.add(jSpinnerFecha);

        // Actualiza el jPanelPrestamo después de agregar el JSpinner
        jPanelPrestamo.revalidate();
        jPanelPrestamo.repaint();
    }       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1Ppal = new javax.swing.JPanel();
        jPanelBiblioteca = new javax.swing.JPanel();
        jLabelBiblioteca = new javax.swing.JLabel();
        jLabelDelaTorre = new javax.swing.JLabel();
        jLabelLogoBiblio = new javax.swing.JLabel();
        jPanelMaxMin = new javax.swing.JPanel();
        jPanelCerrar = new javax.swing.JPanel();
        jLabeCerrar = new javax.swing.JLabel();
        jPanelMin = new javax.swing.JPanel();
        jLabelMin = new javax.swing.JLabel();
        jPanel3Hierba = new javax.swing.JPanel();
        jLabel2Hierba = new javax.swing.JLabel();
        jTabbedPrincipal = new javax.swing.JTabbedPane();
        jPanelInicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtLoginUsuario = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        javax.swing.JButton BotonRegistroUsuario = new javax.swing.JButton();
        txtPassword = new javax.swing.JTextField();
        jButton1AceptarInicio = new javax.swing.JButton();
        jPanelPrestamo = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        textoIdLibroPrest = new javax.swing.JTextField();
        textoDniPrest = new javax.swing.JTextField();
        imagenPrestLibro1 = new javax.swing.JLabel();
        jButton3Aceptar = new javax.swing.JButton();
        jButton4Cancelar = new javax.swing.JButton();
        jLabelFechaPrest = new javax.swing.JLabel();
        jSpinnerFechaPrest = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jPanelDevol = new javax.swing.JPanel();
        jLabelIdUsuario = new javax.swing.JLabel();
        textoIdUsuarioDevol = new javax.swing.JTextField();
        jLabelIdLibro = new javax.swing.JLabel();
        textoIdLibroDev = new javax.swing.JTextField();
        jLabelFoto_dev1libro = new javax.swing.JLabel();
        jButton3Aceptar1 = new javax.swing.JButton();
        jButton4Cancelar1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jPanelUsuarios = new javax.swing.JPanel();
        jLabelResg_Usuar = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButtonAceptar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPrestamos = new javax.swing.JTable();
        jPanelLibros = new javax.swing.JPanel();
        jLabelNombreLibro = new javax.swing.JLabel();
        jTextFieldNombreLibro = new javax.swing.JTextField();
        jLabelAutor = new javax.swing.JLabel();
        jTextFieldAutor = new javax.swing.JTextField();
        jLabelGenero = new javax.swing.JLabel();
        jTextFieldGenero = new javax.swing.JTextField();
        jLabelAño = new javax.swing.JLabel();
        jTextFieldAño = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLibros = new javax.swing.JTable();
        jButton1AñadirLibro = new javax.swing.JButton();
        jButton4Eliminar = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jLabelIDLibro = new javax.swing.JLabel();
        jTextFieldIdLibro = new javax.swing.JTextField();
        jButton1CancelarLibros = new javax.swing.JButton();
        jButton2AceptarLibros = new javax.swing.JButton();
        jButton4LimpiarLibros = new javax.swing.JButton();
        jPanel6Etiq_Icon = new javax.swing.JPanel();
        jPanelIcon_Inicio = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelIcon_Prest = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanelIcon_Devol = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanelIcon_Usuar = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel13EtiqIcon_Libros = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanelCampoFecha = new javax.swing.JPanel();
        etiqFecha = new javax.swing.JLabel();
        jPanelFecha2 = new javax.swing.JPanel();
        etiqFecha2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(194, 224, 194));
        setUndecorated(true);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1Ppal.setBackground(new java.awt.Color(194, 224, 194));
        jPanel1Ppal.setPreferredSize(new java.awt.Dimension(600, 600));
        jPanel1Ppal.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1PpalMouseDragged(evt);
            }
        });
        jPanel1Ppal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1PpalMouseClicked(evt);
            }
        });
        jPanel1Ppal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelBiblioteca.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBiblioteca.setLayout(null);

        jLabelBiblioteca.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabelBiblioteca.setForeground(new java.awt.Color(153, 0, 153));
        jLabelBiblioteca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBiblioteca.setText("Biblioteca");
        jPanelBiblioteca.add(jLabelBiblioteca);
        jLabelBiblioteca.setBounds(154, 0, 180, 31);

        jLabelDelaTorre.setBackground(new java.awt.Color(194, 224, 194));
        jLabelDelaTorre.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabelDelaTorre.setForeground(new java.awt.Color(174, 222, 174));
        jLabelDelaTorre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelDelaTorre.setText("De La Torre");
        jLabelDelaTorre.setToolTipText("");
        jPanelBiblioteca.add(jLabelDelaTorre);
        jLabelDelaTorre.setBounds(310, 0, 170, 31);

        jLabelLogoBiblio.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\logotipoBiblio.png")); // NOI18N
        jLabelLogoBiblio.setText("jLabel13");
        jPanelBiblioteca.add(jLabelLogoBiblio);
        jLabelLogoBiblio.setBounds(0, 0, 70, 40);

        jPanel1Ppal.add(jPanelBiblioteca, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 490, 40));

        jPanelMaxMin.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMaxMin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelCerrar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelCerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelCerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelCerrarMouseExited(evt);
            }
        });

        jLabeCerrar.setBackground(new java.awt.Color(255, 255, 255));
        jLabeCerrar.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabeCerrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabeCerrar.setText("X");
        jLabeCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelCerrarLayout = new javax.swing.GroupLayout(jPanelCerrar);
        jPanelCerrar.setLayout(jPanelCerrarLayout);
        jPanelCerrarLayout.setHorizontalGroup(
            jPanelCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabeCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        jPanelCerrarLayout.setVerticalGroup(
            jPanelCerrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabeCerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanelMaxMin.add(jPanelCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 0, 50, 40));

        jPanelMin.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelMinMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelMinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelMinMouseExited(evt);
            }
        });

        jLabelMin.setBackground(new java.awt.Color(255, 255, 255));
        jLabelMin.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabelMin.setForeground(new java.awt.Color(0, 0, 255));
        jLabelMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMin.setText("_");

        javax.swing.GroupLayout jPanelMinLayout = new javax.swing.GroupLayout(jPanelMin);
        jPanelMin.setLayout(jPanelMinLayout);
        jPanelMinLayout.setHorizontalGroup(
            jPanelMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMin, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        jPanelMinLayout.setVerticalGroup(
            jPanelMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanelMaxMin.add(jPanelMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 4, 60, 40));

        jPanel1Ppal.add(jPanelMaxMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 460, 40));

        jPanel3Hierba.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3Hierba.setOpaque(false);
        jPanel3Hierba.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2Hierba.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\fondo_hierba.png")); // NOI18N
        jPanel3Hierba.add(jLabel2Hierba, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 220));

        jPanel1Ppal.add(jPanel3Hierba, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 950, 160));

        jPanelInicio.setBackground(new java.awt.Color(194, 224, 194));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bienvenido");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\fila-libros.png")); // NOI18N
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 0, 102));
        jLabel27.setText("Login:");
        jLabel27.setToolTipText("");

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 0, 102));
        jLabel28.setText("Contraseña:");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 0, 102));
        jLabel29.setText("Si no está registrado, pulse aquí");

        BotonRegistroUsuario.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        BotonRegistroUsuario.setText("Registro");
        BotonRegistroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegistroUsuarioActionPerformed(evt);
            }
        });

        jButton1AceptarInicio.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton1AceptarInicio.setText("Aceptar");
        jButton1AceptarInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1AceptarInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelInicioLayout = new javax.swing.GroupLayout(jPanelInicio);
        jPanelInicio.setLayout(jPanelInicioLayout);
        jPanelInicioLayout.setHorizontalGroup(
            jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInicioLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInicioLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addComponent(BotonRegistroUsuario))
                    .addGroup(jPanelInicioLayout.createSequentialGroup()
                        .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLoginUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(138, 168, Short.MAX_VALUE))
            .addGroup(jPanelInicioLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInicioLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanelInicioLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jButton1AceptarInicio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelInicioLayout.setVerticalGroup(
            jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 140, Short.MAX_VALUE)
                .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInicioLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(txtLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelInicioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(BotonRegistroUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1AceptarInicio)
                .addGap(38, 38, 38))
        );

        jTabbedPrincipal.addTab("tab3", jPanelInicio);

        jPanelPrestamo.setBackground(new java.awt.Color(194, 224, 194));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 0, 102));
        jLabel19.setText("Dni Usuario");
        jLabel19.setToolTipText("");

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 0, 102));
        jLabel24.setText("Id Libro");
        jLabel24.setToolTipText("");

        textoIdLibroPrest.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        textoDniPrest.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        imagenPrestLibro1.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\prestamo-libro.jpeg")); // NOI18N
        imagenPrestLibro1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButton3Aceptar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton3Aceptar.setText("Aceptar");
        jButton3Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3AceptarActionPerformed(evt);
            }
        });

        jButton4Cancelar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4Cancelar.setText("Cancelar");
        jButton4Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4CancelarActionPerformed(evt);
            }
        });

        jLabelFechaPrest.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelFechaPrest.setForeground(new java.awt.Color(102, 0, 102));
        jLabelFechaPrest.setText("Fecha préstamo");
        jLabelFechaPrest.setToolTipText("");

        jSpinnerFechaPrest.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), null, java.util.Calendar.DAY_OF_MONTH));
        jSpinnerFechaPrest.setEditor(new javax.swing.JSpinner.DateEditor(jSpinnerFechaPrest, "dd/MM/yyyy"));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 102));
        jLabel11.setText("Préstamos");

        javax.swing.GroupLayout jPanelPrestamoLayout = new javax.swing.GroupLayout(jPanelPrestamo);
        jPanelPrestamo.setLayout(jPanelPrestamoLayout);
        jPanelPrestamoLayout.setHorizontalGroup(
            jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                                        .addComponent(jLabelFechaPrest)
                                        .addGap(29, 29, 29)
                                        .addComponent(jSpinnerFechaPrest, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel24))
                                        .addGap(103, 103, 103)
                                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textoDniPrest)
                                            .addComponent(textoIdLibroPrest, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrestamoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3Aceptar)
                        .addGap(91, 91, 91)
                        .addComponent(jButton4Cancelar)
                        .addGap(69, 69, 69)))
                .addComponent(imagenPrestLibro1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanelPrestamoLayout.setVerticalGroup(
            jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(imagenPrestLibro1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPrestamoLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(textoDniPrest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoIdLibroPrest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(36, 36, 36)
                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFechaPrest, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerFechaPrest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3Aceptar)
                            .addComponent(jButton4Cancelar))))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        jTabbedPrincipal.addTab("tab2", jPanelPrestamo);

        jPanelDevol.setBackground(new java.awt.Color(194, 224, 194));

        jLabelIdUsuario.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelIdUsuario.setForeground(new java.awt.Color(102, 0, 102));
        jLabelIdUsuario.setText("Dni Usuario");
        jLabelIdUsuario.setToolTipText("");

        textoIdUsuarioDevol.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabelIdLibro.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelIdLibro.setForeground(new java.awt.Color(102, 0, 102));
        jLabelIdLibro.setText("Id Libro");
        jLabelIdLibro.setToolTipText("");

        textoIdLibroDev.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabelFoto_dev1libro.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\devolucion_libro.jpeg")); // NOI18N

        jButton3Aceptar1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton3Aceptar1.setText("Aceptar");
        jButton3Aceptar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3Aceptar1ActionPerformed(evt);
            }
        });

        jButton4Cancelar1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4Cancelar1.setText("Cancelar");
        jButton4Cancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4Cancelar1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 0, 102));
        jLabel15.setText("Devolución");
        jLabel15.setToolTipText("");

        javax.swing.GroupLayout jPanelDevolLayout = new javax.swing.GroupLayout(jPanelDevol);
        jPanelDevol.setLayout(jPanelDevolLayout);
        jPanelDevolLayout.setHorizontalGroup(
            jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevolLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelDevolLayout.createSequentialGroup()
                .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDevolLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelIdLibro, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addComponent(jLabelIdUsuario))
                        .addGap(84, 84, 84)
                        .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textoIdLibroDev, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textoIdUsuarioDevol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(jPanelDevolLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jButton3Aceptar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4Cancelar1)
                        .addGap(55, 55, 55)))
                .addComponent(jLabelFoto_dev1libro, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanelDevolLayout.setVerticalGroup(
            jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDevolLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDevolLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelIdUsuario)
                            .addComponent(textoIdUsuarioDevol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textoIdLibroDev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelIdLibro))
                        .addGap(55, 55, 55)
                        .addGroup(jPanelDevolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3Aceptar1)
                            .addComponent(jButton4Cancelar1))
                        .addGap(92, 92, 92))
                    .addGroup(jPanelDevolLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabelFoto_dev1libro, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(101, Short.MAX_VALUE))))
        );

        jTabbedPrincipal.addTab("tab1", jPanelDevol);

        jPanelUsuarios.setBackground(new java.awt.Color(194, 224, 194));

        jLabelResg_Usuar.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\reservaLibro.png")); // NOI18N

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 0, 102));
        jLabel26.setText("Reservas Usuarios");
        jLabel26.setToolTipText("");

        jButtonAceptar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        tablaPrestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaPrestamos);

        javax.swing.GroupLayout jPanelUsuariosLayout = new javax.swing.GroupLayout(jPanelUsuarios);
        jPanelUsuarios.setLayout(jPanelUsuariosLayout);
        jPanelUsuariosLayout.setHorizontalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGroup(jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelResg_Usuar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(162, 162, 162)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelUsuariosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonAceptar)
                .addGap(318, 318, 318))
        );
        jPanelUsuariosLayout.setVerticalGroup(
            jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuariosLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelResg_Usuar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jButtonAceptar)
                .addGap(36, 36, 36))
        );

        jTabbedPrincipal.addTab("tab5", jPanelUsuarios);

        jPanelLibros.setBackground(new java.awt.Color(194, 224, 194));

        jLabelNombreLibro.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelNombreLibro.setForeground(new java.awt.Color(102, 0, 102));
        jLabelNombreLibro.setText("Nombre");

        jLabelAutor.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelAutor.setForeground(new java.awt.Color(102, 0, 102));
        jLabelAutor.setText("Autor");

        jTextFieldAutor.setToolTipText("");

        jLabelGenero.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelGenero.setForeground(new java.awt.Color(102, 0, 102));
        jLabelGenero.setText("Genero");

        jTextFieldGenero.setToolTipText("");

        jLabelAño.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelAño.setForeground(new java.awt.Color(102, 0, 102));
        jLabelAño.setText("Año Publicación");
        jLabelAño.setToolTipText("");

        jTextFieldAño.setToolTipText("");

        tablaLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaLibros);

        jButton1AñadirLibro.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton1AñadirLibro.setText("Añadir");
        jButton1AñadirLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1AñadirLibroActionPerformed(evt);
            }
        });

        jButton4Eliminar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4Eliminar.setText("Eliminar");
        jButton4Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4EliminarActionPerformed(evt);
            }
        });

        jButtonEditar.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButtonEditar.setText("Editar");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jLabelIDLibro.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabelIDLibro.setForeground(new java.awt.Color(102, 0, 102));
        jLabelIDLibro.setText("ID Libro");
        jLabelIDLibro.setToolTipText("");

        jTextFieldIdLibro.setToolTipText("");

        jButton1CancelarLibros.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton1CancelarLibros.setText("Cancelar");
        jButton1CancelarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1CancelarLibrosActionPerformed(evt);
            }
        });

        jButton2AceptarLibros.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton2AceptarLibros.setText("Aceptar");
        jButton2AceptarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2AceptarLibrosActionPerformed(evt);
            }
        });

        jButton4LimpiarLibros.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4LimpiarLibros.setText("Limpiar Formulario");
        jButton4LimpiarLibros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4LimpiarLibrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLibrosLayout = new javax.swing.GroupLayout(jPanelLibros);
        jPanelLibros.setLayout(jPanelLibrosLayout);
        jPanelLibrosLayout.setHorizontalGroup(
            jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLibrosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2AceptarLibros)
                .addGap(89, 89, 89)
                .addComponent(jButton1CancelarLibros)
                .addGap(238, 238, 238))
            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLibrosLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                                .addComponent(jLabelAño, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldAño, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                                .addComponent(jLabelAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                                .addComponent(jLabelNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLibrosLayout.createSequentialGroup()
                                .addComponent(jLabelGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLibrosLayout.createSequentialGroup()
                                .addComponent(jLabelIDLibro, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldGenero, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(jTextFieldIdLibro)))
                    .addGroup(jPanelLibrosLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jButton1AñadirLibro)
                        .addGap(88, 88, 88)
                        .addComponent(jButtonEditar)
                        .addGap(90, 90, 90)
                        .addComponent(jButton4Eliminar)
                        .addGap(59, 59, 59)
                        .addComponent(jButton4LimpiarLibros)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelLibrosLayout.setVerticalGroup(
            jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLibrosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombreLibro)
                    .addComponent(jTextFieldNombreLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIDLibro)
                    .addComponent(jTextFieldIdLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAutor)
                    .addComponent(jTextFieldAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelGenero)
                    .addComponent(jTextFieldGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAño))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1AñadirLibro)
                    .addComponent(jButton4Eliminar)
                    .addComponent(jButtonEditar)
                    .addComponent(jButton4LimpiarLibros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLibrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1CancelarLibros)
                    .addComponent(jButton2AceptarLibros))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPrincipal.addTab("tab3", jPanelLibros);

        jPanel1Ppal.add(jTabbedPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 170, 730, 410));

        jPanel6Etiq_Icon.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6Etiq_Icon.setLayout(new java.awt.GridLayout(8, 1));

        jPanelIcon_Inicio.setBackground(new java.awt.Color(255, 255, 255));
        jPanelIcon_Inicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelIcon_InicioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelIcon_InicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelIcon_InicioMouseExited(evt);
            }
        });
        jPanelIcon_Inicio.setLayout(new java.awt.GridLayout(1, 0));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\006-pgina-de-inicio.png")); // NOI18N
        jLabel14.setFocusable(false);
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelIcon_Inicio.add(jLabel14);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inicio");
        jLabel1.setFocusable(false);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Inicio.add(jLabel1);

        jPanel6Etiq_Icon.add(jPanelIcon_Inicio);

        jPanelIcon_Prest.setBackground(new java.awt.Color(255, 255, 255));
        jPanelIcon_Prest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelIcon_PrestMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelIcon_PrestMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelIcon_PrestMouseExited(evt);
            }
        });
        jPanelIcon_Prest.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\001-agregar-contacto.png")); // NOI18N
        jLabel4.setFocusable(false);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Prest.add(jLabel4);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Préstamo");
        jLabel3.setFocusable(false);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Prest.add(jLabel3);

        jPanel6Etiq_Icon.add(jPanelIcon_Prest);

        jPanelIcon_Devol.setBackground(new java.awt.Color(255, 255, 255));
        jPanelIcon_Devol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelIcon_DevolMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelIcon_DevolMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelIcon_DevolMouseExited(evt);
            }
        });
        jPanelIcon_Devol.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\004-devolucin-de-producto.png")); // NOI18N
        jLabel5.setFocusable(false);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Devol.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Devolución");
        jLabel6.setFocusable(false);
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Devol.add(jLabel6);

        jPanel6Etiq_Icon.add(jPanelIcon_Devol);

        jPanelIcon_Usuar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelIcon_Usuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelIcon_UsuarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelIcon_UsuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelIcon_UsuarMouseExited(evt);
            }
        });
        jPanelIcon_Usuar.setLayout(new java.awt.GridLayout(1, 0));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\003-agregar-contacto.png")); // NOI18N
        jLabel7.setFocusable(false);
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Usuar.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Usuarios");
        jLabel8.setFocusable(false);
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelIcon_Usuar.add(jLabel8);

        jPanel6Etiq_Icon.add(jPanelIcon_Usuar);

        jPanel13EtiqIcon_Libros.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13EtiqIcon_Libros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13EtiqIcon_LibrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13EtiqIcon_LibrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13EtiqIcon_LibrosMouseExited(evt);
            }
        });
        jPanel13EtiqIcon_Libros.setLayout(new java.awt.GridLayout(1, 0));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\mcfal\\OneDrive\\Escritorio\\DAM\\2º\\Desarrollo de Interfaces\\Practica\\Biblioteca\\imagenes\\png\\007-libro-1.png")); // NOI18N
        jLabel9.setFocusable(false);
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel13EtiqIcon_Libros.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Libros");
        jLabel10.setFocusable(false);
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel13EtiqIcon_Libros.add(jLabel10);

        jPanel6Etiq_Icon.add(jPanel13EtiqIcon_Libros);

        jPanelCampoFecha.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCampoFecha.setLayout(new java.awt.GridLayout(2, 0));

        etiqFecha.setBackground(new java.awt.Color(194, 224, 194));
        etiqFecha.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        etiqFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqFecha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelCampoFecha.add(etiqFecha);

        jPanel6Etiq_Icon.add(jPanelCampoFecha);

        jPanelFecha2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFecha2.setLayout(new java.awt.GridLayout(1, 0));

        etiqFecha2.setBackground(new java.awt.Color(194, 224, 194));
        etiqFecha2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        etiqFecha2.setForeground(new java.awt.Color(102, 0, 102));
        etiqFecha2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etiqFecha2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanelFecha2.add(etiqFecha2);

        jPanel6Etiq_Icon.add(jPanelFecha2);

        jPanel1Ppal.add(jPanel6Etiq_Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 207, 220, 370));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1Ppal, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1Ppal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //PANELES E ICONOS
    private void jPanel13EtiqIcon_LibrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13EtiqIcon_LibrosMouseExited
        jPanel13EtiqIcon_Libros.setOpaque(false);
        jPanel13EtiqIcon_Libros.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanel13EtiqIcon_LibrosMouseExited

    private void jPanel13EtiqIcon_LibrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13EtiqIcon_LibrosMouseEntered
        jPanel13EtiqIcon_Libros.setOpaque(true);
        jPanel13EtiqIcon_Libros.setBackground(new Color(169,235,169));
    }//GEN-LAST:event_jPanel13EtiqIcon_LibrosMouseEntered

    private void jPanel13EtiqIcon_LibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13EtiqIcon_LibrosMouseClicked
        jTabbedPrincipal.setSelectedIndex(4);
    }//GEN-LAST:event_jPanel13EtiqIcon_LibrosMouseClicked

    private void jPanelIcon_UsuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_UsuarMouseExited
        jPanelIcon_Usuar.setOpaque(false);
        jPanelIcon_Usuar.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelIcon_UsuarMouseExited

    private void jPanelIcon_UsuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_UsuarMouseEntered
        jPanelIcon_Usuar.setOpaque(true);
        jPanelIcon_Usuar.setBackground(new Color(169,235,169));
    }//GEN-LAST:event_jPanelIcon_UsuarMouseEntered

    private void jPanelIcon_UsuarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_UsuarMouseClicked
        jTabbedPrincipal.setSelectedIndex(3);
    }//GEN-LAST:event_jPanelIcon_UsuarMouseClicked

    private void jPanelIcon_DevolMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_DevolMouseExited
        jPanelIcon_Devol.setOpaque(false);
        jPanelIcon_Devol.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelIcon_DevolMouseExited

    private void jPanelIcon_DevolMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_DevolMouseEntered
        jPanelIcon_Devol.setOpaque(true);
        jPanelIcon_Devol.setBackground(new Color(169,235,169));
    }//GEN-LAST:event_jPanelIcon_DevolMouseEntered

    private void jPanelIcon_DevolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_DevolMouseClicked
        jTabbedPrincipal.setSelectedIndex(2);
    }//GEN-LAST:event_jPanelIcon_DevolMouseClicked

    private void jPanelIcon_PrestMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_PrestMouseExited
        jPanelIcon_Prest.setOpaque(false);
        jPanelIcon_Prest.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelIcon_PrestMouseExited

    private void jPanelIcon_PrestMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_PrestMouseEntered
        jPanelIcon_Prest.setOpaque(true);
        jPanelIcon_Prest.setBackground(new Color(169,235,169));
    }//GEN-LAST:event_jPanelIcon_PrestMouseEntered

    private void jPanelIcon_PrestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_PrestMouseClicked
        jTabbedPrincipal.setSelectedIndex(1);
    }//GEN-LAST:event_jPanelIcon_PrestMouseClicked

    private void jPanelIcon_InicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_InicioMouseExited
        jPanelIcon_Inicio.setOpaque(false);
        jPanelIcon_Inicio.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_jPanelIcon_InicioMouseExited

    private void jPanelIcon_InicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_InicioMouseEntered
        jPanelIcon_Inicio.setOpaque(true);
        jPanelIcon_Inicio.setBackground(new Color(169,235,169));
    }//GEN-LAST:event_jPanelIcon_InicioMouseEntered

    private void jPanelIcon_InicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelIcon_InicioMouseClicked
        
        jTabbedPrincipal.setSelectedIndex(0);
    }//GEN-LAST:event_jPanelIcon_InicioMouseClicked

    private void jPanelCerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCerrarMouseEntered
        jPanelCerrar.setOpaque(true);
        jPanelCerrar.setBackground(new Color(237, 211, 205));
    }//GEN-LAST:event_jPanelCerrarMouseEntered
    
    private void jPanelCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCerrarMouseClicked
        System.exit(0);
    }//GEN-LAST:event_jPanelCerrarMouseClicked

    //BOTON MINIMIZAR VENTANA
    private void jPanelMin(java.awt.event.MouseEvent evt) {                                          
        this.setExtendedState(javax.swing.JFrame.ICONIFIED);        
    } 
    private void jPanel1PpalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1PpalMouseClicked
        //Me da la posicion de x e y del mouse
        x = evt.getX();
        y = evt.getY();
        System.out.println(x + " " + y + " ");
    }//GEN-LAST:event_jPanel1PpalMouseClicked

    private void jPanel1PpalMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1PpalMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_jPanel1PpalMouseDragged

    private void BotonRegistroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegistroUsuarioActionPerformed
        RegistroUsuario Rg = new RegistroUsuario(this,true);        
        Rg.setVisible(true);
    }//GEN-LAST:event_BotonRegistroUsuarioActionPerformed

    //BOTON CANCELAR PRESTAMO, VUELVE A LA PANTALLA INICIO
    private void jButton4CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4CancelarActionPerformed
        
        if (jPanelInicio != null) {
            jPanelInicio.setVisible(true); 
            //jButton4Cancelar.setVisible(false);
        }    
    }//GEN-LAST:event_jButton4CancelarActionPerformed
        
    //BOTON MINIMIZAR
    private void jPanelMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMinMouseClicked
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jPanelMinMouseClicked

    private void jPanelMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMinMouseEntered
        jPanelMin.setOpaque(true);
        jPanelMin.setBackground(new Color(186, 240, 155 ));
    }//GEN-LAST:event_jPanelMinMouseEntered

    private void jPanelMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelMinMouseExited
        jPanelMin.setOpaque(false);
        jPanelMin.setBackground(Color.WHITE);
    }//GEN-LAST:event_jPanelMinMouseExited

    //BOTON ACEPTAR PRESTAMO 
    private void jButton3AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3AceptarActionPerformed

        String dni = textoDniPrest.getText();
        String idLibro = textoIdLibroPrest.getText();
        String idUsuario = obtenerIdUsuario(dni);

        boolean libroEncontrado = false;

        boolean dniValido = validarDNI(dni);

        if (dniValido) {
            for (int i = 0; i < l.getRowCount(); i++) {
                if (idLibro.equals(l.getValueAt(i, 1).toString())) {
                    libroEncontrado = true;
                    if ("Disponible".equalsIgnoreCase(l.getValueAt(i, 5).toString())) {
                        try {
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");

                            // Imprimo el valor actual de Prestado en la consola para comprobación mia
                            System.out.println("Valor actual de Prestado: " + l.getValueAt(i, 5));

                            String updateQuery = "UPDATE libros SET Prestado = ?, IDUsuario = ? WHERE IDLibro = ?";
                            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
                            preparedStatement.setString(1, "No disponible");
                            preparedStatement.setString(2, dni);
                            preparedStatement.setString(3, idLibro);

                            preparedStatement.executeUpdate();

                            DefaultTableModel p = (DefaultTableModel) tablaPrestamos.getModel();
                            String nombreLibro = l.getValueAt(i, 0).toString();
                            Date fechaActual = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String fechaPrestamo = sdf.format(fechaActual);

                            p.addRow(new Object[]{dni, idUsuario, nombreLibro, idLibro, fechaPrestamo});

                            // Inserción en la base de datos
                            String insertQuery = "INSERT INTO prestamos (DNI, IDUsuario, NombreLibro, IDLibro, FechaPrestamo) VALUES (?, ?, ?, ?, ?)";
                            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                            insertStatement.setString(1, dni);
                            insertStatement.setString(2, idUsuario);
                            insertStatement.setString(3, nombreLibro);
                            insertStatement.setString(4, idLibro);
                            insertStatement.setString(5, fechaPrestamo);

                            insertStatement.executeUpdate();

                            JOptionPane.showMessageDialog(this, "¡Libro reservado con éxito!");

                            // Cierro la conexión
                            preparedStatement.close();
                            insertStatement.close();
                            con.close();

                            // Actualizo la tabla libros
                            tablaLibros.setModel(l);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "El libro no está disponible para reservar.");
                    }
                    break;
                }
            }

            if (!libroEncontrado) {
                JOptionPane.showMessageDialog(this, "El ID de libro ingresado no se encuentra en la base de datos.");
            }

            int option = JOptionPane.showConfirmDialog(this, "¿Desea realizar otro préstamo?", "Realizar otro préstamo", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                textoDniPrest.setText("");
                textoIdLibroPrest.setText("");
            } else {
                jPanelInicio.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "DNI no válido o no existe en la base de datos.");
        }

    }//GEN-LAST:event_jButton3AceptarActionPerformed

    //OBTENER EL IDUSUARIO
    private String obtenerIdUsuario(String dni) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            String query = "SELECT IDUsuario FROM usuario WHERE DNI = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("IDUsuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    //VALIDAR EL DNI
    private boolean validarDNI(String dni) {
        boolean dniValido = false;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            String query = "SELECT * FROM usuario WHERE DNI = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, dni);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                dniValido = true;
            }

            rs.close();
            preparedStatement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dniValido;
    }
    
    //BOTON ACEPTAR DEVOLUCION
    private void jButton3Aceptar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3Aceptar1ActionPerformed
  
        String dni = textoIdUsuarioDevol.getText();
        String idLibro = textoIdLibroDev.getText();

        // Valido DNI y ID de Libro
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");

            // Actualizo la tabla de prestamos
            String updatePrestamosQuery = "UPDATE prestamos SET FechaDevolucion = CURRENT_DATE() WHERE DNI = ? AND IDLibro = ? AND FechaDevolucion IS NULL";
            try (PreparedStatement updatePrestamosStatement = con.prepareStatement(updatePrestamosQuery)) {
                updatePrestamosStatement.setString(1, dni);
                updatePrestamosStatement.setString(2, idLibro);

                int rowsUpdated = updatePrestamosStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    
                    Date fechaDevolucion = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaDevolucionStr = sdf.format(fechaDevolucion);

                    // Muestro un mensaje con la fecha de devolución
                    JOptionPane.showMessageDialog(this, "Libro devuelto con éxito.\nFecha de devolución: " + fechaDevolucionStr);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un préstamo activo para el libro y usuario proporcionados.");
                }
            }

            // Actualizo la tabla de libros
            String updateLibrosQuery = "UPDATE libros SET Prestado = NULL WHERE IDLibro = ? AND Prestado IS NOT NULL";
            try (PreparedStatement updateLibrosStatement = con.prepareStatement(updateLibrosQuery)) {
                updateLibrosStatement.setString(1, idLibro);

                int rowsUpdated = updateLibrosStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    // Muestro un mensaje de que el libro está disponible
                    JOptionPane.showMessageDialog(this, "Libro marcado como disponible.");
                } else {
                    JOptionPane.showMessageDialog(this, "El libro no está marcado como prestado o no existe.");
                }
            }

            // cierro la conexión
            con.close();

            // Actualizo las tablas en la interfaz gráfica
            PrepararTablaPrestamos();
            PrepararTablaLibros();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al procesar la devolución del libro.");
        }
        
    }//GEN-LAST:event_jButton3Aceptar1ActionPerformed
    //BOTON CANCELAR DEVOLUCIÓN, VUELVE A INICIO
    private void jButton4Cancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4Cancelar1ActionPerformed
        if (jPanelInicio != null) {
            jPanelInicio.setVisible(true); 
            jButton4Cancelar1.setVisible(false);
        }  
    }//GEN-LAST:event_jButton4Cancelar1ActionPerformed

    private void jPanelCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelCerrarMouseExited
        jPanelCerrar.setOpaque(true);
        jPanelCerrar.setBackground(Color.WHITE);
    }//GEN-LAST:event_jPanelCerrarMouseExited

    //BOTON AÑADIR LIBRO
    private void jButton1AñadirLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1AñadirLibroActionPerformed
        String nombreLibro = jTextFieldNombreLibro.getText();
        String idLibro = jTextFieldIdLibro.getText();
        String autor = jTextFieldAutor.getText();
        String genero = jTextFieldGenero.getText();
        String anyoPubl = jTextFieldAño.getText(); 
        String prestado = "";
        String idUsuario = "";
        
        l = (DefaultTableModel) tablaLibros.getModel();
        String filaNombreLibros[] = {nombreLibro, idLibro, autor, genero, anyoPubl, prestado, idUsuario};
        l.addRow(filaNombreLibros);
        
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            System.out.println("Conectado");
            
            //Creo la sentencia SQL de insertar
            String insertQuery = "INSERT INTO libros (Nombre, IDLibro, Autor, Genero, AñoPublicacion, Prestado, IDUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            //Preparo la sentencia
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, nombreLibro);
            preparedStatement.setString(2, idLibro);            
            preparedStatement.setString(3, autor);
            preparedStatement.setString(4, genero);            
            preparedStatement.setString(5, anyoPubl);
            preparedStatement.setString(6, prestado);
            preparedStatement.setString(7, idUsuario);
                        
            //Ejecuto la insercion
            int rowCount = preparedStatement.executeUpdate();
            
            //Cierro la conexion
            preparedStatement.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println("No conectado. Error");
            e.printStackTrace();            
        }
         
        l.fireTableDataChanged();
    }//GEN-LAST:event_jButton1AñadirLibroActionPerformed

    //BOTON ELIMINAR LIBRO
    private void jButton4EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4EliminarActionPerformed
        int fila = tablaLibros.getSelectedRow();
        String idLibro = null;

        if (fila >= 0) {
            idLibro = (String) l.getValueAt(fila, 1); // Obtener el IDLibro de la fila seleccionada
            l.removeRow(fila);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
                System.out.println("Conectado");

                String deleteQuery = "DELETE FROM libros WHERE IDLibro = ?";

                PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);
                preparedStatement.setString(1, idLibro);

                // Ejecuto la eliminación
                int rowCount = preparedStatement.executeUpdate();

                // Cierra la conexion
                preparedStatement.close();
                con.close();

            } catch (Exception e) {
                System.out.println("No conectado. Error");
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }

        l.fireTableDataChanged();
    }//GEN-LAST:event_jButton4EliminarActionPerformed

    //LIMPIAR FORMULARIO
    private void jButton4LimpiarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4LimpiarLibrosActionPerformed
        jTextFieldNombreLibro.setText("");
        jTextFieldIdLibro.setText("");
        jTextFieldAutor.setText("");
        jTextFieldGenero.setText("");
        jTextFieldAño.setText("");
    }//GEN-LAST:event_jButton4LimpiarLibrosActionPerformed

    //BOTON EDITAR LIBRO
    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed

        EditarLibro dialog = new EditarLibro(this, true);
        dialog.setVisible(true);

        //Cojo el valor de la fila seleccionada
        String nombreLibro = jTextFieldNombreLibro.getText();
        String idLibro = jTextFieldIdLibro.getText();
        String autor = jTextFieldAutor.getText();
        String genero = jTextFieldGenero.getText();
        String anyoPubl = jTextFieldAño.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tabla", "root", "");
            System.out.println("Conectado");

            String updateQuery = "UPDATE libros SET Nombre = ?, Autor = ?, Genero = ? WHERE IDLibro = ?";

            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, nombreLibro);
            preparedStatement.setString(2, autor);
            preparedStatement.setString(3, genero);
            preparedStatement.setString(4, idLibro);

            int rowCount = preparedStatement.executeUpdate();

            preparedStatement.close();
            con.close();

           // this.setVisible(false);

        } catch (Exception e) {
            System.out.println("No se pudo actualizar en la base de datos. Error");
            e.printStackTrace();
        }

        // Actualizo la tabla con los datos recién editados
        l.fireTableDataChanged();
    
    }//GEN-LAST:event_jButtonEditarActionPerformed
    //BOTON CANCELAR LIBROS
    private void jButton1CancelarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1CancelarLibrosActionPerformed
        if (jPanelInicio != null) {
            jPanelLibros.setVisible(false);            
            jButton1CancelarLibros.setVisible(false);            
            jPanelInicio.setVisible(true);
        }  
    }//GEN-LAST:event_jButton1CancelarLibrosActionPerformed

    //BOTON ACEPTAR LIBROS, VUELVE A INICIO
    private void jButton2AceptarLibrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2AceptarLibrosActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null, "¿Desea realizar otra acción?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            //Limpio los campos si hay algo escrito
            jTextFieldNombreLibro.setText("");
            jTextFieldIdLibro.setText("");
            jTextFieldAutor.setText("");
            jTextFieldGenero.setText("");
            jTextFieldAño.setText("");
        } else {
            jPanelLibros.setVisible(false);             
            jButton2AceptarLibros.setVisible(false);
            jPanelInicio.setVisible(true);

        }
    }//GEN-LAST:event_jButton2AceptarLibrosActionPerformed

    //BOTON ACEPTAR INICIO
    private void jButton1AceptarInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1AceptarInicioActionPerformed
        
        String usuario = txtLoginUsuario.getText();
        String contraseña = txtPassword.getText();
        
         if ((usuario.equals("Admin") && contraseña.equals("Admin"))) {
           jButton1AñadirLibro.setEnabled(true);
           jButtonEditar.setEnabled(true);
           jButton4Eliminar.setEnabled(true);
        }
    }//GEN-LAST:event_jButton1AceptarInicioActionPerformed
    
    //BOTON ACEPTAR USUARIO
    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        if (jPanelInicio != null) {
            jPanelInicio.setVisible(true);
            jButtonAceptar.setVisible(false);
        }  
    }//GEN-LAST:event_jButtonAceptarActionPerformed
    
    //BOTON CANCELAR REGISTRO USUARIO NUEVO, VUELVE A PANEL
    //USUARIO
    public void mostrarPanelUsuarios() {
        if (jPanelUsuarios != null) {
            jPanelUsuarios.setVisible(true);              
        }
    }
    
    //BOTON FECHA
    private void mostrarFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        etiqFecha2.setText("  Hoy es " + dateFormat.format(date));
        
    }   
    
    /**
     * @param args the command line arguments
     */    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel etiqFecha;
    private javax.swing.JLabel etiqFecha2;
    public javax.swing.JLabel imagenPrestLibro1;
    private javax.swing.JButton jButton1AceptarInicio;
    private javax.swing.JButton jButton1AñadirLibro;
    private javax.swing.JButton jButton1CancelarLibros;
    private javax.swing.JButton jButton2AceptarLibros;
    private javax.swing.JButton jButton3Aceptar;
    private javax.swing.JButton jButton3Aceptar1;
    private javax.swing.JButton jButton4Cancelar;
    private javax.swing.JButton jButton4Cancelar1;
    private javax.swing.JButton jButton4Eliminar;
    private javax.swing.JButton jButton4LimpiarLibros;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JLabel jLabeCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    public javax.swing.JLabel jLabel2Hierba;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAutor;
    private javax.swing.JLabel jLabelAño;
    private javax.swing.JLabel jLabelBiblioteca;
    private javax.swing.JLabel jLabelDelaTorre;
    private javax.swing.JLabel jLabelFechaPrest;
    public javax.swing.JLabel jLabelFoto_dev1libro;
    private javax.swing.JLabel jLabelGenero;
    private javax.swing.JLabel jLabelIDLibro;
    private javax.swing.JLabel jLabelIdLibro;
    private javax.swing.JLabel jLabelIdUsuario;
    public javax.swing.JLabel jLabelLogoBiblio;
    private javax.swing.JLabel jLabelMin;
    private javax.swing.JLabel jLabelNombreLibro;
    public javax.swing.JLabel jLabelResg_Usuar;
    private javax.swing.JPanel jPanel13EtiqIcon_Libros;
    private javax.swing.JPanel jPanel1Ppal;
    private javax.swing.JPanel jPanel3Hierba;
    private javax.swing.JPanel jPanel6Etiq_Icon;
    private javax.swing.JPanel jPanelBiblioteca;
    private javax.swing.JPanel jPanelCampoFecha;
    private javax.swing.JPanel jPanelCerrar;
    public javax.swing.JPanel jPanelDevol;
    private javax.swing.JPanel jPanelFecha2;
    private javax.swing.JPanel jPanelIcon_Devol;
    private javax.swing.JPanel jPanelIcon_Inicio;
    private javax.swing.JPanel jPanelIcon_Prest;
    private javax.swing.JPanel jPanelIcon_Usuar;
    public javax.swing.JPanel jPanelInicio;
    private javax.swing.JPanel jPanelLibros;
    private javax.swing.JPanel jPanelMaxMin;
    public javax.swing.JPanel jPanelMin;
    private javax.swing.JPanel jPanelPrestamo;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerFechaPrest;
    public javax.swing.JTabbedPane jTabbedPrincipal;
    private javax.swing.JTextField jTextFieldAutor;
    private javax.swing.JTextField jTextFieldAño;
    private javax.swing.JTextField jTextFieldGenero;
    private javax.swing.JTextField jTextFieldIdLibro;
    private javax.swing.JTextField jTextFieldNombreLibro;
    private javax.swing.JTable tablaLibros;
    private javax.swing.JTable tablaPrestamos;
    private javax.swing.JTextField textoDniPrest;
    private javax.swing.JTextField textoIdLibroDev;
    private javax.swing.JTextField textoIdLibroPrest;
    private javax.swing.JTextField textoIdUsuarioDevol;
    public javax.swing.JTextField txtLoginUsuario;
    public javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}