/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.InsumoCtrl;
import controlador.MovimientoCtrl;
import controlador.ProveedorCtrl;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.entidad.Insumo;
import modelo.entidad.MovimientoInventario;
import modelo.entidad.Proveedor;

/**
 *
 * @author Dayanna
 */
public class MenuAdministrador extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuAdministrador.class.getName());

    /**
     * Creates new form MenuAdministrador
     */
    public MenuAdministrador() {
        initComponents();
        diseñoTablaInsu(jTableInsumos);
        diseñoTablaProv(jTableProveedores);
        diseñoTablaInve(tblInventario);
        diseñoTablaRepo(tablaReporte);
        diseñoTablaPed(tblPedidos);
        diseñoTablaProd(tblProductos);
        tblInventario.setDefaultRenderer(Object.class, new InventarioRenderer());
        cargarTablaInventario(""); 
        cargarComboInsumosMovimiento();
        configurarPanelReportes();
        cargarTablaInsumos();
        cargarTablaProveedores("");
        cargarTablaInventario(""); 
        cargarComboInsumosMovimiento();
        cargarTablaPedidos();
        cargarTablaProductos();
        cargarComboProduccion();
        aplicarEstiloMenu(btnInsumos);
        aplicarEstiloMenu(btnProveedores);
        aplicarEstiloMenu(btnInventario);
        aplicarEstiloMenu(btnPedidos);
        aplicarEstiloMenu(btnProduccion);
        aplicarEstiloMenu(btnReportes);
        //Botones
        java.awt.Color temaInsumos = new java.awt.Color(255, 105, 180); // Rosado Fuerte

        diseñoBotonModulo(jButtonAgregarInsumo, temaInsumos);
        diseñoBotonModulo(btnActualizar, temaInsumos);
        diseñoBotonModulo(btnEditarInsumo, temaInsumos);
        diseñoBotonModulo(btnEliminarInsumo, temaInsumos);

        java.awt.Color temaProv = new java.awt.Color(147, 112, 219); // Lavanda

        diseñoBotonModulo(btnAgregarProveedor, temaProv);
        diseñoBotonModulo(btnActualizarProveedor, temaProv);
        diseñoBotonModulo(btnEditarProveedor, temaProv);
        diseñoBotonModulo(btnEliminarProveedor, temaProv);
    }

    public void cargarTablaInsumos() {
        InsumoCtrl controlador = new InsumoCtrl();
        List<Insumo> lista = controlador.listarInsumos();

        String[] columnas = {
            "ID", "Nombre", "Descripción", "Unidad",
            "Stock Actual", "Stock Mínimo", "Fecha Caducidad", "Estado"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        for (Insumo ins : lista) {
            Object[] fila = {
                ins.getId_insumo(),
                ins.getNombre(),
                ins.getDescripcion(),
                ins.getUnidad_medida(),
                ins.getStock_actual(),
                ins.getStock_minimo(),
                ins.getFecha_caducidad(),
                ins.getEstado()
            };
            modelo.addRow(fila);
        }

        jTableInsumos.setModel(modelo);
    }

    // Método mejorado con buscador para Proveedores
    void cargarTablaProveedores(String filtro) {
        controlador.ProveedorCtrl controlador = new controlador.ProveedorCtrl();
        java.util.List<modelo.entidad.Proveedor> lista = controlador.listarProveedores();

        String[] columnas = {
            "ID", "Nombre", "RUC", "Teléfono", "Dirección", "Correo", "Estado"
        };

        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (modelo.entidad.Proveedor p : lista) {
            // Lógica de Filtro: Busca por Nombre O por RUC
            if (filtro == null || filtro.isEmpty() || 
                p.getNombre().toLowerCase().contains(filtro.toLowerCase()) || 
                p.getRUC().contains(filtro)) {

                Object[] fila = {
                    p.getId_proveedor(),
                    p.getNombre(),
                    p.getRUC(),
                    p.getTelefono(),
                    p.getDireccion(),
                    p.getCorreo(),
                    p.getEstado()
                };
                modelo.addRow(fila);
            }
        }

        jTableProveedores.setModel(modelo);
        diseñoTablaProv(jTableProveedores);
    }
    
    
    public void cargarTablaPedidos() {
        controlador.PedidoCtrl ctrl = new controlador.PedidoCtrl();
        List<modelo.entidad.Pedido> lista = ctrl.listarPedidos();

        // Columnas
        String[] columnas = {"ID", "Fecha", "Proveedor", "Total", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Llenar filas
        for (modelo.entidad.Pedido p : lista) {
            modelo.addRow(new Object[]{
                p.getId_pedido(),
                p.getFecha_pedido(),
                (p.getProveedor() != null) ? p.getProveedor().getNombre() : "Sin Proveedor",
                "S/. " + p.getTotal(),
                p.getEstado_pedido()
            });
        }

        tblPedidos.setModel(modelo);

        // Opcional: Dale estilo si quieres
        // diseñoTablaInsu(tblPedidos); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        NavBar = new javax.swing.JPanel();
        btnInsumos = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnProduccion = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        Logo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        paneInsumos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInsumos = new javax.swing.JTable();
        jButtonAgregarInsumo = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEditarInsumo = new javax.swing.JButton();
        btnEliminarInsumo = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        panelProveedores = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProveedores = new javax.swing.JTable();
        btnAgregarProveedor = new javax.swing.JButton();
        btnActualizarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtBuscarProveedor = new javax.swing.JTextField();
        panelPedidos = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        btnNuevoPedido = new javax.swing.JButton();
        btnConfirmarRecepcion = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelReportes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaReporte = new javax.swing.JTable();
        btnImprimirReporte = new javax.swing.JButton();
        btnExportarReporte = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cmbTipoReporte = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fechaInicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        fechaFin = new com.toedter.calendar.JDateChooser();
        btnGenerarReporte = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        panelProduccion = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCantidadProduccion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbProductoProduccion = new javax.swing.JComboBox<>();
        btnConfirmarProduccion = new javax.swing.JButton();
        btnIrRecetas = new javax.swing.JButton();
        panelInventario = new javax.swing.JPanel();
        registro = new javax.swing.JPanel();
        cmbInsumosMovimiento = new javax.swing.JComboBox<>();
        lblStockDisponible = new javax.swing.JLabel();
        txtCantidadMovimiento = new javax.swing.JTextField();
        cmbTipoMovimiento = new javax.swing.JComboBox<>();
        btnRegistrarMovimiento = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBuscarInventario = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1029, 750));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        NavBar.setBackground(new java.awt.Color(255, 204, 204));

        btnInsumos.setBackground(new java.awt.Color(255, 204, 204));
        btnInsumos.setForeground(new java.awt.Color(60, 63, 65));
        btnInsumos.setText("Gestionar Insumos");
        btnInsumos.setBorder(null);
        btnInsumos.setBorderPainted(false);
        btnInsumos.setFocusPainted(false);
        btnInsumos.setOpaque(true);
        btnInsumos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsumosActionPerformed(evt);
            }
        });

        btnProveedores.setBackground(new java.awt.Color(255, 204, 204));
        btnProveedores.setForeground(new java.awt.Color(60, 63, 65));
        btnProveedores.setText("Gestionar Proveedores");
        btnProveedores.setToolTipText("");
        btnProveedores.setBorder(null);
        btnProveedores.setBorderPainted(false);
        btnProveedores.setMaximumSize(new java.awt.Dimension(127, 23));
        btnProveedores.setMinimumSize(new java.awt.Dimension(127, 23));
        btnProveedores.setOpaque(true);
        btnProveedores.setPreferredSize(new java.awt.Dimension(127, 23));
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnInventario.setBackground(new java.awt.Color(255, 204, 204));
        btnInventario.setForeground(new java.awt.Color(60, 63, 65));
        btnInventario.setText("Inventario");
        btnInventario.setBorder(null);
        btnInventario.setBorderPainted(false);
        btnInventario.setFocusPainted(false);
        btnInventario.setMaximumSize(new java.awt.Dimension(127, 23));
        btnInventario.setMinimumSize(new java.awt.Dimension(127, 23));
        btnInventario.setOpaque(true);
        btnInventario.setPreferredSize(new java.awt.Dimension(127, 23));
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnPedidos.setBackground(new java.awt.Color(255, 204, 204));
        btnPedidos.setForeground(new java.awt.Color(60, 63, 65));
        btnPedidos.setText("Gestionar Pedidos");
        btnPedidos.setBorder(null);
        btnPedidos.setBorderPainted(false);
        btnPedidos.setFocusPainted(false);
        btnPedidos.setMaximumSize(new java.awt.Dimension(127, 23));
        btnPedidos.setMinimumSize(new java.awt.Dimension(127, 23));
        btnPedidos.setOpaque(true);
        btnPedidos.setPreferredSize(new java.awt.Dimension(127, 23));
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });

        btnReportes.setBackground(new java.awt.Color(255, 204, 204));
        btnReportes.setForeground(new java.awt.Color(60, 63, 65));
        btnReportes.setText("Reportes");
        btnReportes.setBorder(null);
        btnReportes.setBorderPainted(false);
        btnReportes.setFocusPainted(false);
        btnReportes.setOpaque(true);
        btnReportes.setPreferredSize(new java.awt.Dimension(127, 23));
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnProduccion.setBackground(new java.awt.Color(255, 204, 204));
        btnProduccion.setForeground(new java.awt.Color(60, 63, 65));
        btnProduccion.setText("Registrar Produccion");
        btnProduccion.setBorder(null);
        btnProduccion.setBorderPainted(false);
        btnProduccion.setFocusPainted(false);
        btnProduccion.setOpaque(true);
        btnProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduccionActionPerformed(evt);
            }
        });

        btnCerrarSesion.setBackground(new java.awt.Color(255, 51, 51));
        btnCerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarSesion.setText("Cerrar Sesion");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logito.jpg"))); // NOI18N

        javax.swing.GroupLayout LogoLayout = new javax.swing.GroupLayout(Logo);
        Logo.setLayout(LogoLayout);
        LogoLayout.setHorizontalGroup(
            LogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        LogoLayout.setVerticalGroup(
            LogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout NavBarLayout = new javax.swing.GroupLayout(NavBar);
        NavBar.setLayout(NavBarLayout);
        NavBarLayout.setHorizontalGroup(
            NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProduccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnInsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(NavBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        NavBarLayout.setVerticalGroup(
            NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavBarLayout.createSequentialGroup()
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(NavBar, java.awt.BorderLayout.LINE_START);

        panelContenido.setBackground(new java.awt.Color(255, 255, 255));
        panelContenido.setLayout(new java.awt.CardLayout());

        paneInsumos.setBackground(new java.awt.Color(255, 255, 255));

        jTableInsumos.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableInsumos.setOpaque(false);
        jScrollPane1.setViewportView(jTableInsumos);

        jButtonAgregarInsumo.setText("Agregar Insumo");
        jButtonAgregarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarInsumoActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEditarInsumo.setText("Editar Insumo");
        btnEditarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarInsumoActionPerformed(evt);
            }
        });

        btnEliminarInsumo.setText("Eliminar Insumo");
        btnEliminarInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarInsumoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Gestionar Insumos");

        javax.swing.GroupLayout paneInsumosLayout = new javax.swing.GroupLayout(paneInsumos);
        paneInsumos.setLayout(paneInsumosLayout);
        paneInsumosLayout.setHorizontalGroup(
            paneInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInsumosLayout.createSequentialGroup()
                .addGroup(paneInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paneInsumosLayout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(jButtonAgregarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(63, 63, 63)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(116, 116, 116)
                            .addComponent(btnEditarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(83, 83, 83)
                            .addComponent(btnEliminarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(paneInsumosLayout.createSequentialGroup()
                            .addGap(241, 241, 241)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        paneInsumosLayout.setVerticalGroup(
            paneInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneInsumosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(paneInsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        panelContenido.add(paneInsumos, "cardInsumos");

        panelProveedores.setBackground(new java.awt.Color(255, 255, 255));

        jTableProveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableProveedores);

        btnAgregarProveedor.setText("Agregar Proveedor");
        btnAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProveedorActionPerformed(evt);
            }
        });

        btnActualizarProveedor.setText("Actualizar");
        btnActualizarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setText("Editar Proveedor");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setText("Eliminar Proveedor");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Gestionar Proveedores");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Buscar proveedor:");

        txtBuscarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscarProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelProveedoresLayout = new javax.swing.GroupLayout(panelProveedores);
        panelProveedores.setLayout(panelProveedoresLayout);
        panelProveedoresLayout.setHorizontalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProveedoresLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProveedoresLayout.createSequentialGroup()
                        .addComponent(btnAgregarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34)
                        .addComponent(btnActualizarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(btnEditarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProveedoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(252, 252, 252))
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProveedoresLayout.setVerticalGroup(
            panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedoresLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(panelProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        panelContenido.add(panelProveedores, "cardProveedores");

        panelPedidos.setBackground(new java.awt.Color(255, 255, 255));

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPedidosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblPedidos);

        btnNuevoPedido.setBackground(new java.awt.Color(153, 255, 255));
        btnNuevoPedido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNuevoPedido.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevoPedido.setText("Nuevo Pedido");
        btnNuevoPedido.setBorder(null);
        btnNuevoPedido.setOpaque(true);
        btnNuevoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPedidoActionPerformed(evt);
            }
        });

        btnConfirmarRecepcion.setBackground(new java.awt.Color(153, 255, 255));
        btnConfirmarRecepcion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirmarRecepcion.setForeground(new java.awt.Color(0, 0, 0));
        btnConfirmarRecepcion.setText("Confirmar Recepcion");
        btnConfirmarRecepcion.setBorder(null);
        btnConfirmarRecepcion.setOpaque(true);
        btnConfirmarRecepcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarRecepcionActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Gestionar Pedidos");

        jLabel14.setText("Doble click para ver detalles");

        javax.swing.GroupLayout panelPedidosLayout = new javax.swing.GroupLayout(panelPedidos);
        panelPedidos.setLayout(panelPedidosLayout);
        panelPedidosLayout.setHorizontalGroup(
            panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosLayout.createSequentialGroup()
                .addGroup(panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addGroup(panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelPedidosLayout.createSequentialGroup()
                            .addGap(236, 236, 236)
                            .addComponent(jLabel12))
                        .addGroup(panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPedidosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNuevoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(btnConfirmarRecepcion))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPedidosLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        panelPedidosLayout.setVerticalGroup(
            panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel12)
                .addGap(25, 25, 25)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(panelPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmarRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        panelContenido.add(panelPedidos, "cardPedidos");

        panelReportes.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaReporte);

        btnImprimirReporte.setBackground(new java.awt.Color(218, 165, 32));
        btnImprimirReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnImprimirReporte.setForeground(new java.awt.Color(0, 0, 0));
        btnImprimirReporte.setText("Imprimir");
        btnImprimirReporte.setBorderPainted(false);
        btnImprimirReporte.setOpaque(true);
        btnImprimirReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirReporteActionPerformed(evt);
            }
        });

        btnExportarReporte.setBackground(new java.awt.Color(218, 165, 32));
        btnExportarReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportarReporte.setForeground(new java.awt.Color(0, 0, 0));
        btnExportarReporte.setText("Exportar");
        btnExportarReporte.setBorderPainted(false);
        btnExportarReporte.setOpaque(true);
        btnExportarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarReporteActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N

        cmbTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Tipo de reporte:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Inicio:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Fin:");

        btnGenerarReporte.setBackground(new java.awt.Color(255, 140, 0));
        btnGenerarReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGenerarReporte.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerarReporte.setText("Generar Reporte");
        btnGenerarReporte.setBorderPainted(false);
        btnGenerarReporte.setOpaque(true);
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel2))
                                .addComponent(fechaInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Gestion de Reportes");

        javax.swing.GroupLayout panelReportesLayout = new javax.swing.GroupLayout(panelReportes);
        panelReportes.setLayout(panelReportesLayout);
        panelReportesLayout.setHorizontalGroup(
            panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelReportesLayout.createSequentialGroup()
                .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelReportesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnImprimirReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelReportesLayout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelReportesLayout.createSequentialGroup()
                            .addGap(251, 251, 251)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelReportesLayout.setVerticalGroup(
            panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReportesLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimirReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        panelContenido.add(panelReportes, "cardReportes");

        panelProduccion.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Registrar Produccion");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblProductos);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Cantidad a producir:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Producto Elaborado:");

        cmbProductoProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProductoProduccionActionPerformed(evt);
            }
        });

        btnConfirmarProduccion.setBackground(new java.awt.Color(160, 82, 45));
        btnConfirmarProduccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirmarProduccion.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmarProduccion.setText("Confirmar Produccion");
        btnConfirmarProduccion.setBorderPainted(false);
        btnConfirmarProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarProduccionActionPerformed(evt);
            }
        });

        btnIrRecetas.setBackground(new java.awt.Color(205, 133, 63));
        btnIrRecetas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIrRecetas.setForeground(new java.awt.Color(0, 0, 0));
        btnIrRecetas.setText("Configurar Recetas");
        btnIrRecetas.setBorderPainted(false);
        btnIrRecetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIrRecetasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCantidadProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cmbProductoProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIrRecetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfirmarProduccion, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbProductoProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCantidadProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnConfirmarProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnIrRecetas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelProduccionLayout = new javax.swing.GroupLayout(panelProduccion);
        panelProduccion.setLayout(panelProduccionLayout);
        panelProduccionLayout.setHorizontalGroup(
            panelProduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProduccionLayout.createSequentialGroup()
                .addGroup(panelProduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProduccionLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel16))
                    .addGroup(panelProduccionLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelProduccionLayout.setVerticalGroup(
            panelProduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProduccionLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel16)
                .addGap(78, 78, 78)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(360, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelContenido.add(panelProduccion, "cardProduccion");
        panelProduccion.getAccessibleContext().setAccessibleName("");

        panelInventario.setBackground(new java.awt.Color(255, 255, 255));

        registro.setBackground(new java.awt.Color(230, 239, 230));
        registro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Movimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(0, 0, 0))); // NOI18N
        registro.setPreferredSize(new java.awt.Dimension(300, 0));

        cmbInsumosMovimiento.setBackground(new java.awt.Color(255, 255, 255));
        cmbInsumosMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbInsumosMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbInsumosMovimientoActionPerformed(evt);
            }
        });

        lblStockDisponible.setForeground(new java.awt.Color(0, 0, 0));

        txtCantidadMovimiento.setBackground(new java.awt.Color(255, 255, 255));

        cmbTipoMovimiento.setBackground(new java.awt.Color(255, 255, 255));
        cmbTipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Consumo en Producción", "Venta", "Merma" }));

        btnRegistrarMovimiento.setBackground(new java.awt.Color(204, 255, 204));
        btnRegistrarMovimiento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistrarMovimiento.setForeground(new java.awt.Color(0, 0, 0));
        btnRegistrarMovimiento.setText("Registrar");
        btnRegistrarMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarMovimientoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Producto:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Cantidad:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Motivo:");

        javax.swing.GroupLayout registroLayout = new javax.swing.GroupLayout(registro);
        registro.setLayout(registroLayout);
        registroLayout.setHorizontalGroup(
            registroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registroLayout.createSequentialGroup()
                .addGroup(registroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(registroLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegistrarMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(registroLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(registroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStockDisponible, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(registroLayout.createSequentialGroup()
                                .addGroup(registroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbInsumosMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCantidadMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 21, Short.MAX_VALUE)))))
                .addGap(83, 83, 83))
        );
        registroLayout.setVerticalGroup(
            registroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registroLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbInsumosMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidadMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(btnRegistrarMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText(" Buscar:");

        txtBuscarInventario.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscarInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarInventarioActionPerformed(evt);
            }
        });
        txtBuscarInventario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarInventarioKeyReleased(evt);
            }
        });

        tblInventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblInventario);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Gestionar Inventario");

        javax.swing.GroupLayout panelInventarioLayout = new javax.swing.GroupLayout(panelInventario);
        panelInventario.setLayout(panelInventarioLayout);
        panelInventarioLayout.setHorizontalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInventarioLayout.createSequentialGroup()
                        .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInventarioLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtBuscarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInventarioLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addComponent(registro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelInventarioLayout.setVerticalGroup(
            panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(registro, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
            .addGroup(panelInventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(35, 35, 35)
                .addGroup(panelInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBuscarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        panelContenido.add(panelInventario, "cardInventario");

        jPanel1.add(panelContenido, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsumosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsumosActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardInsumos");
    }//GEN-LAST:event_btnInsumosActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardPedidos");
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void jButtonAgregarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarInsumoActionPerformed
        AgregarInsumoForm form = new AgregarInsumoForm();
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }//GEN-LAST:event_jButtonAgregarInsumoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarTablaInsumos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEditarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarInsumoActionPerformed
        int fila = jTableInsumos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un insumo para editar.");
            return;
        }

        // Obtener el ID del insumo desde la tabla
        int idInsumo = Integer.parseInt(jTableInsumos.getValueAt(fila, 0).toString());

        // Abrir el formulario de edición y pasarle el ID
        new EditarInsumoForm(idInsumo, this).setVisible(true);
    }//GEN-LAST:event_btnEditarInsumoActionPerformed

    private void btnEliminarInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarInsumoActionPerformed
        int fila = jTableInsumos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un insumo primero.");
            return;
        }

        int id = Integer.parseInt(jTableInsumos.getValueAt(fila, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este insumo?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            InsumoCtrl ctrl = new InsumoCtrl();

            if (ctrl.eliminarInsumo(id)) {
                JOptionPane.showMessageDialog(this, "Insumo eliminado correctamente");
                cargarTablaInsumos(); // refrescar tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar insumo");
            }
        }
    }//GEN-LAST:event_btnEliminarInsumoActionPerformed

    private void cargarTiposDeReporte() {
        cmbTipoReporte.removeAllItems();
        cmbTipoReporte.addItem("Stock Actual");
        cmbTipoReporte.addItem("Movimientos de Insumos");
        cmbTipoReporte.addItem("Insumos Próximos a Caducar");
        cmbTipoReporte.addItem("Compras a Proveedores");
        cmbTipoReporte.addItem("Ventas de Productos");
    }

    private void configurarPanelReportes() {
        // Solo actualizar los combos
        cmbTipoReporte.removeAllItems();
        cmbTipoReporte.addItem("Stock Actual");
        cmbTipoReporte.addItem("Insumos Próximos a Caducar");
        cmbTipoReporte.addItem("Movimientos de Insumos");
        cmbTipoReporte.addItem("Compras a Proveedores");
        cmbTipoReporte.addItem("Ventas de Productos");
        
        cmbTipoReporte.addActionListener((java.awt.event.ActionEvent evt) -> { visibilidadFechas(); });
        visibilidadFechas();

        // Repintar
        panelReportes.revalidate();
        panelReportes.repaint();
    }
    
    private void visibilidadFechas() {
        String tipo = (String) cmbTipoReporte.getSelectedItem();
        if (tipo == null) return;

        boolean necesitaFechas = tipo.equals("Movimientos de Insumos") || 
                                 tipo.equals("Compras a Proveedores") || 
                                 tipo.equals("Ventas de Productos");

        // Ocultar o mostrar
        jLabel2.setVisible(necesitaFechas); // Label Fecha Inicio
        fechaInicio.setVisible(necesitaFechas);
        jLabel3.setVisible(necesitaFechas); // Label Fecha Fin
        fechaFin.setVisible(necesitaFechas);
    }
    // Ejemplo básico de generarReporte

    private void generarReporte() {
        String tipo = cmbTipoReporte.getSelectedItem().toString();

        String fechaIni = "";
        String fechaFinStr = "";

        if (fechaInicio.getDate() != null) {
            fechaIni = new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaInicio.getDate());
        }
        if (fechaFin.getDate() != null) {
            fechaFinStr = new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaFin.getDate());
        }

        // Aquí debes llenar la tabla según tipo y fechas
        // Datos simulados
        String[] columnas = {"Columna 1", "Columna 2", "Columna 3"};
        Object[][] datos = {
            {"Dato 1", "Dato 2", "Dato 3"},
            {"Dato A", "Dato B", "Dato C"}
        };
        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }

    private void btnAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProveedorActionPerformed
        new AgregarProveedorForm(this).setVisible(true);
    }//GEN-LAST:event_btnAgregarProveedorActionPerformed

    private void btnActualizarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProveedorActionPerformed
        cargarTablaProveedores("");
    }//GEN-LAST:event_btnActualizarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        int fila = jTableProveedores.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor primero.");
            return;
        }

        int id = Integer.parseInt(jTableProveedores.getValueAt(fila, 0).toString());

        new EditarProveedorForm(id, this).setVisible(true);
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        int fila = jTableProveedores.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor primero.");
            return;
        }

        int id = Integer.parseInt(jTableProveedores.getValueAt(fila, 0).toString());

        int conf = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar este proveedor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (conf == JOptionPane.YES_OPTION) {
            ProveedorCtrl ctrl = new ProveedorCtrl();

            if (ctrl.eliminarProveedor(id)) {
                cargarTablaProveedores("");
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardProveedores");
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private java.util.List<modelo.entidad.Insumo> listaInsumosParaMovimiento;
    private modelo.entidad.Usuario usuarioLogueado;

    private void cargarComboInsumosMovimiento() {
        listaInsumosParaMovimiento = new java.util.ArrayList<>();
        controlador.InsumoCtrl ctrl = new controlador.InsumoCtrl();
        listaInsumosParaMovimiento = ctrl.listarInsumos();

        cmbInsumosMovimiento.removeAllItems();
        for (modelo.entidad.Insumo ins : listaInsumosParaMovimiento) {
            String texto = ins.getId_insumo() + " - " + ins.getNombre() + " (stock: " + ins.getStock_actual() + " " + ins.getUnidad_medida() + ")";
            cmbInsumosMovimiento.addItem(texto);
        }

        lblStockDisponible.setText("Stock: 0");
    }

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Cerrar Sesión",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose();
            LoginForm login = new LoginForm();
            login.setVisible(true);
            login.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void dsf(java.awt.event.ActionEvent evt) {
        int idx = cmbInsumosMovimiento.getSelectedIndex();
        if (idx >= 0 && idx < listaInsumosParaMovimiento.size()) {
            modelo.entidad.Insumo ins = listaInsumosParaMovimiento.get(idx);
            lblStockDisponible.setText(String.format("Stock: %.2f %s", ins.getStock_actual(), ins.getUnidad_medida()));
        } else {
            lblStockDisponible.setText("Stock: 0");
        }
    }

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardInventario");
        cargarComboInsumosMovimiento();
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnRegistrarMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarMovimientoActionPerformed
        int idx = cmbInsumosMovimiento.getSelectedIndex();
        if (idx == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un insumo.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelo.entidad.Insumo ins = listaInsumosParaMovimiento.get(idx);

        String cantidadStr = txtCantidadMovimiento.getText();
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese una cantidad.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        double cantidad;
        try {
            cantidad = Double.parseDouble(cantidadStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidad <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar stock suficiente
        if (ins.getStock_actual() < cantidad) {
            javax.swing.JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock actual: " + ins.getStock_actual(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = cmbTipoMovimiento.getSelectedItem() != null ? cmbTipoMovimiento.getSelectedItem().toString() : "Consumo en Producción";

        // Usuario: debes tener la referencia del usuario logueado en usuarioLogueado.
        // Si no la tienes, por ahora usa new Usuario() con id 1 (mejor setearlo al hacer login).
        modelo.entidad.Usuario usr = usuarioLogueado;
        if (usr == null) {
            // temporal: si no tienes login, usa id 1 (Admin)
            usr = new modelo.entidad.Usuario();
            usr.setId_usuario(1);
        }

        controlador.MovimientoCtrl movCtrl = new controlador.MovimientoCtrl();
        boolean ok = movCtrl.registrarSalida(ins, cantidad, tipo, usr);

        if (ok) {
            // refrescar combo y cualquier tabla de insumos
            cargarComboInsumosMovimiento();
            // opcional: actualizar tabla general de insumos si existe
            cargarTablaInsumos(); // si tienes este método ya creado
            txtCantidadMovimiento.setText("");
            cargarTablaInventario("");
        }
    }//GEN-LAST:event_btnRegistrarMovimientoActionPerformed

    private void cmbInsumosMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbInsumosMovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbInsumosMovimientoActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardReportes");
        configurarPanelReportes();
        cargarTiposDeReporte();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        String tipo = cmbTipoReporte.getSelectedItem().toString();
    
        // Limpiar tabla antes de buscar
        tablaReporte.setModel(new javax.swing.table.DefaultTableModel());

        // Validar fechas solo si el reporte lo requiere
        if (fechaInicio.isVisible()) {
            if (fechaInicio.getDate() == null || fechaFin.getDate() == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor seleccione ambas fechas.", "Faltan Datos", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (fechaInicio.getDate().after(fechaFin.getDate())) {
                javax.swing.JOptionPane.showMessageDialog(this, "La Fecha Inicio no puede ser mayor a la Fecha Fin.", "Error en Fechas", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Switch para elegir la lógica
        switch (tipo) {
            case "Stock Actual" -> generarReporteStockActual();
            case "Insumos Próximos a Caducar" -> generarReporteCaducidad();
            case "Movimientos de Insumos" -> generarReporteMovimientos();
            case "Compras a Proveedores" -> generarReporteCompras();
            case "Ventas de Productos" -> generarReporteVentas();
        }

        diseñoTablaRepo(tablaReporte); 

        // si la tabla quedó vacia....
        if (tablaReporte.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontraron datos para el reporte solicitado.", "Reporte Vacío", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    private void btnImprimirReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirReporteActionPerformed
        if (tablaReporte.getRowCount() == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "No hay datos para generar el documento.", "Tabla Vacía", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
        }

        try {
            java.io.File archivoPDF = java.io.File.createTempFile("Reporte_Delyjhos", ".html");

            try (java.io.PrintWriter pw = new java.io.PrintWriter(archivoPDF)) {
                pw.println("<html><head><title>Reporte Delyjhos</title>");
                pw.println("<meta charset='UTF-8'>"); // Importante para tildes
                pw.println("<style>");
                pw.println("body { font-family: 'Segoe UI', sans-serif; padding: 40px; color: #333; }");

                // Estilos del encabezado
                pw.println("h1 { color: #d35400; text-align: center; margin-bottom: 5px; }");
                pw.println("h3 { text-align: center; color: #555; margin-top: 0; }");
                pw.println(".fecha { text-align: right; font-size: 12px; color: #777; margin-bottom: 20px; border-bottom: 2px solid #d35400; padding-bottom: 10px; }");

                // Estilos de la tabla
                pw.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
                pw.println("th { background-color: #ffe0b2; color: #d35400; padding: 12px; border: 1px solid #ddd; text-align: left; }");
                pw.println("td { padding: 10px; border: 1px solid #ddd; }");
                pw.println("tr:nth-child(even) { background-color: #fff8e1; }"); // Filas alternas color crema

                // Boton flotante
                pw.println(".btn-flotante { position: fixed; bottom: 30px; right: 30px; background-color: #d35400; color: white; padding: 15px 25px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 8px rgba(0,0,0,0.3); cursor: pointer; border: none; font-size: 16px; transition: transform 0.2s; }");
                pw.println(".btn-flotante:hover { transform: scale(1.05); background-color: #e67e22; }");

                // Ocultar el boton al imprimir
                pw.println("@media print { .no-print { display: none !important; } }");

                pw.println("</style></head><body>");

                // Boton de descarga
                pw.println("<button onclick='window.print()' class='btn-flotante no-print'>🖨️ Guardar como PDF</button>");

                // Contenido del Reporte
                pw.println("<h1>🍰 Pastelería Delyjhos</h1>");
                pw.println("<h3>Reporte Oficial: " + cmbTipoReporte.getSelectedItem().toString() + "</h3>");

                // Fecha formateada
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy, HH:mm");
                pw.println("<div class='fecha'>Generado el: " + sdf.format(new java.util.Date()) + "</div>");

                // Tabla
                pw.println("<table>");
                pw.println("<thead><tr>");
                for (int i = 0; i < tablaReporte.getColumnCount(); i++) {
                    pw.println("<th>" + tablaReporte.getColumnName(i) + "</th>");
                }
                pw.println("</tr></thead>");

                pw.println("<tbody>");
                for (int i = 0; i < tablaReporte.getRowCount(); i++) {
                    pw.println("<tr>");
                    for (int j = 0; j < tablaReporte.getColumnCount(); j++) {
                        Object val = tablaReporte.getValueAt(i, j);
                        pw.println("<td>" + (val != null ? val.toString() : "") + "</td>");
                    }
                    pw.println("</tr>");
                }
                pw.println("</tbody></table>");

                pw.println("<div style='margin-top: 30px; text-align: center; font-size: 12px; color: #aaa;'>Documento interno - Delyjhos Sistema v1.0</div>");
                pw.println("</body></html>");
            }

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(archivoPDF.toURI());
            }

        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnImprimirReporteActionPerformed

    private void txtBuscarInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarInventarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarInventarioActionPerformed

    private void txtBuscarInventarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarInventarioKeyReleased
        cargarTablaInventario(txtBuscarInventario.getText());
    }//GEN-LAST:event_txtBuscarInventarioKeyReleased

    private void txtBuscarProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyReleased
        cargarTablaProveedores(txtBuscarProveedor.getText());
    }//GEN-LAST:event_txtBuscarProveedorKeyReleased

    private void btnExportarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarReporteActionPerformed
        if (tablaReporte.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay datos para exportar.", "Tabla Vacia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte");
        fileChooser.setSelectedFile(new java.io.File("Reporte_" + cmbTipoReporte.getSelectedItem().toString().replace(" ", "_") + ".csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave)) {

                // Para encabezados
                for (int i = 0; i < tablaReporte.getColumnCount(); i++) {
                    fw.write(tablaReporte.getColumnName(i) + ",");
                }
                fw.write("\n");

                // Para escribir datos
                for (int i = 0; i < tablaReporte.getRowCount(); i++) {
                    for (int j = 0; j < tablaReporte.getColumnCount(); j++) {
                        Object val = tablaReporte.getValueAt(i, j);
                        String valorStr = (val != null) ? val.toString() : "";
                        // Reemplazar comas por puntos para no romper el CSV
                        fw.write(valorStr.replace(",", ".") + ",");
                    }
                    fw.write("\n");
                }

                javax.swing.JOptionPane.showMessageDialog(this, "Reporte exportado exitosamente en:\n" + fileToSave.getAbsolutePath());

            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnExportarReporteActionPerformed

    private void btnNuevoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPedidoActionPerformed
        CrearPedidoForm form = new CrearPedidoForm(this);
        form.setVisible(true);
    }//GEN-LAST:event_btnNuevoPedidoActionPerformed

    private void btnConfirmarRecepcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarRecepcionActionPerformed
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un pedido de la lista.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = Integer.parseInt(tblPedidos.getValueAt(fila, 0).toString());
        String estadoActual = tblPedidos.getValueAt(fila, 4).toString();

        if ("Recibido".equalsIgnoreCase(estadoActual)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Este pedido ya fue procesado.", "Aviso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this, 
                "¿Confirmar llegada de mercadería?\nSe aumentará el stock de los insumos.", 
                "Confirmar Recepción", 
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            controlador.PedidoCtrl ctrl = new controlador.PedidoCtrl();

            int idUsuario = 1; 

            boolean ok = ctrl.confirmarRecepcion(idPedido, idUsuario);

            if (ok) {
                javax.swing.JOptionPane.showMessageDialog(this, "¡Recepción exitosa! Stock actualizado.");
                cargarTablaPedidos(); // Refrescar la lista para ver el cambio a "Recibido"
                cargarTablaInventario(""); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al procesar la recepción.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnConfirmarRecepcionActionPerformed

    private void tblPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPedidosMouseClicked
        if (evt.getClickCount() == 2) {
        int fila = tblPedidos.getSelectedRow();
        if (fila != -1) {
            int idPedido = Integer.parseInt(tblPedidos.getValueAt(fila, 0).toString());
            new VerPedidoForm(idPedido).setVisible(true);
        }
    }
    }//GEN-LAST:event_tblPedidosMouseClicked

    private void btnProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduccionActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) panelContenido.getLayout();
        layout.show(panelContenido, "cardProduccion");

        cargarTablaProductos();
    }//GEN-LAST:event_btnProduccionActionPerformed

    private void btnConfirmarProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarProduccionActionPerformed
        int fila = tblProductos.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.");
            return;
        }

        int idProducto = Integer.parseInt(tblProductos.getValueAt(fila, 0).toString());
        String nombreProd = tblProductos.getValueAt(fila, 1).toString();

        // 2. Obtener cantidad
        String cantStr = txtCantidadProduccion.getText();
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida mayor a 0.");
            return;
        }

        // 3. Ejecutar
        int resp = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Producir " + cantidad + " unidades de '" + nombreProd + "'?\nSe descontarán insumos.", 
            "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (resp == javax.swing.JOptionPane.YES_OPTION) {
            controlador.RecetaCtrl ctrl = new controlador.RecetaCtrl();
            // ID usuario temporal (1) o logueado
            if (ctrl.registrarProduccion(idProducto, cantidad, 1)) {
                javax.swing.JOptionPane.showMessageDialog(this, "¡Producción registrada!");
                txtCantidadProduccion.setText("");
                cargarTablaProductos(); // Refrescar para ver el aumento de stock
                cargarTablaInventario(""); // Refrescar insumos también
            }
        }
    }//GEN-LAST:event_btnConfirmarProduccionActionPerformed

    private void btnIrRecetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIrRecetasActionPerformed
        GestionarRecetas form = new GestionarRecetas();
        form.setVisible(true);
    }//GEN-LAST:event_btnIrRecetasActionPerformed

    private void cmbProductoProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProductoProduccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProductoProduccionActionPerformed

    private void generarReporteStockActual() {
        InsumoCtrl ctrl = new InsumoCtrl();
        List<Insumo> lista = ctrl.listarInsumos();

        String[] columnas = {"ID", "Insumo", "Unidad", "Stock Actual", "Stock Mí    nimo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Insumo i : lista) {
            modelo.addRow(new Object[]{i.getId_insumo(), i.getNombre(), i.getUnidad_medida(), i.getStock_actual(), i.getStock_minimo(), i.getEstado()});
        }
        tablaReporte.setModel(modelo);
    }

    private void generarReporteCaducidad() {
        InsumoCtrl ctrl = new InsumoCtrl();
        List<Insumo> lista = ctrl.listarInsumos(); 

        String[] columnas = {"Insumo", "Stock", "Fecha Caducidad", "Días Restantes"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        java.util.Date hoy = new java.util.Date();

        for (Insumo i : lista) {
            if (i.getFecha_caducidad() != null) {
                long diff = i.getFecha_caducidad().getTime() - hoy.getTime();
                long dias = java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);

                // Regla: Mostrar si faltan 15 días o menos (o ya vencio)
                if (dias <= 15) {
                    modelo.addRow(new Object[]{i.getNombre(), i.getStock_actual(), i.getFecha_caducidad(), dias + " días"});
                }
            }
        }
        tablaReporte.setModel(modelo);
    }

    private void generarReporteMovimientos() {
        MovimientoCtrl ctrl = new MovimientoCtrl();
        List<MovimientoInventario> lista = ctrl.listarMovimientosPorFecha(fechaInicio.getDate(), fechaFin.getDate());

        String[] columnas = {"Fecha", "Insumo", "Tipo", "Cantidad", "Usuario"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (MovimientoInventario m : lista) {
            modelo.addRow(new Object[]{
                m.getFecha_movimiento(),
                (m.getInsumo() != null ? m.getInsumo().getNombre() : "—"),
                m.getTipo_movimiento(),
                m.getCantidad(),
                (m.getUsuario() != null ? m.getUsuario().getUsuario() : "—")
            });
        }
        tablaReporte.setModel(modelo);
    }

    private void generarReporteCompras() {
        controlador.PedidoCtrl ctrl = new controlador.PedidoCtrl();
        List<modelo.entidad.Pedido> lista = ctrl.listarPedidos();

        String[] columnas = {"ID Compra", "Fecha", "Proveedor", "Total", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        Date ini = fechaInicio.getDate();
        Date fin = fechaFin.getDate();

        for (modelo.entidad.Pedido p : lista) {
            // Filtro manual
            Date fechaP = p.getFecha_pedido();
            if (fechaP != null && !fechaP.before(ini) && !fechaP.after(fin)) {
                modelo.addRow(new Object[]{
                    p.getId_pedido(),
                    p.getFecha_pedido(),
                    (p.getProveedor() != null ? p.getProveedor().getNombre() : "Sin Prov."),
                    "S/. " + p.getTotal(),
                    p.getEstado_pedido()
                });
            }
        }
        tablaReporte.setModel(modelo);
    }

    private void generarReporteVentas() {
        // Usamos Movimientos tipo "Venta" o "Salida"
        MovimientoCtrl ctrl = new MovimientoCtrl();
        List<MovimientoInventario> lista = ctrl.listarMovimientosPorFecha(fechaInicio.getDate(), fechaFin.getDate());

        String[] columnas = {"Fecha", "Producto/Insumo", "Cantidad Vendida", "Usuario"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (MovimientoInventario m : lista) {
            // Filtramos solo lo que sea VENTA
            if (m.getTipo_movimiento().toLowerCase().contains("venta")) {
                String item = (m.getProducto() != null) ? m.getProducto().getNombre() : 
                              (m.getInsumo() != null) ? m.getInsumo().getNombre() : "—";

                modelo.addRow(new Object[]{
                    m.getFecha_movimiento(),
                    item,
                    m.getCantidad(),
                    (m.getUsuario() != null ? m.getUsuario().getUsuario() : "—")
                });
            }
        }
        tablaReporte.setModel(modelo);
    }

    // Método para transformar botones normales en botones de menú estilo Dashboard
    private void aplicarEstiloMenu(javax.swing.JButton boton) {

        java.awt.Color colorFondoNormal = new java.awt.Color(255, 204, 204); // rosado
        java.awt.Color colorFondoHover  = new java.awt.Color(255, 153, 153); // Rosado oscuro 
        java.awt.Color colorTexto       = new java.awt.Color(60, 63, 65);    // Gris oscuro

        boton.setBackground(colorFondoNormal);
        boton.setForeground(colorTexto);
        boton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        boton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boton.setMargin(new java.awt.Insets(10, 20, 10, 0));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondoHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondoNormal);
            }
        });
    }

    public void diseñoTablaInsu(javax.swing.JTable tabla) {
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        header.setBackground(new java.awt.Color(255, 204, 204));
        header.setForeground(new java.awt.Color(60, 60, 60));
        header.setOpaque(false);

        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 153, 153)));

        // Filas
        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new java.awt.Color(255, 228, 225));
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));

        //Cuadriculas
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(230, 230, 230));

        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }

    public void diseñoTablaProv(javax.swing.JTable tabla) {
        // Header
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        header.setBackground(new java.awt.Color(204, 190, 235));
        header.setForeground(new java.awt.Color(60, 60, 60));
        header.setOpaque(false);

        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(180, 160, 210)));

        //Filas
        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new java.awt.Color(245, 240, 255));
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));

        // Cuadriculas
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(235, 235, 235));

        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }
    
    // Metodo para estilizar la tabla de Inventario
    public void diseñoTablaInve(javax.swing.JTable tabla) {
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        header.setBackground(new java.awt.Color(204, 255, 204)); // Celeste "Powder Blue"
        header.setForeground(new java.awt.Color(60, 60, 60));
        header.setOpaque(false);

        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(135, 206, 235)));

        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new java.awt.Color(204, 255, 204)); 
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));

        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(235, 235, 235));

        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }
    
    // Metodo para estilizar para la tabla de Reportes
    public void diseñoTablaRepo(javax.swing.JTable tabla) {
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        header.setBackground(new java.awt.Color(255, 228, 181)); 
        header.setForeground(new java.awt.Color(102, 51, 0));
        header.setOpaque(false);

        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(238, 200, 140)));

        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new java.awt.Color(255, 250, 240)); 
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));

        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(245, 245, 220));

        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }
    
    public void diseñoTablaPed(javax.swing.JTable tabla) {
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        header.setBackground(new java.awt.Color(153,255,255)); 
        header.setForeground(new java.awt.Color(60, 60, 60));
        header.setOpaque(false);


        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(70, 130, 180)));


        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);

        tabla.setSelectionBackground(new java.awt.Color(240, 248, 255)); 
        tabla.setSelectionForeground(new java.awt.Color(0, 0, 0));

        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(230, 240, 250));

        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }
    
    public void diseñoTablaProd(javax.swing.JTable tabla) {
        // 1. Encabezado (Header) - Tono Café con Leche
        javax.swing.table.JTableHeader header = tabla.getTableHeader();
        header.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));

        // Color de fondo: Marrón claro (Tan)
        header.setBackground(new java.awt.Color(210, 180, 140)); 
        header.setForeground(new java.awt.Color(60, 30, 10)); // Texto Café Oscuro
        header.setOpaque(false);

        // Borde inferior: Chocolate
        header.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(139, 69, 19))); 

        // 2. Cuerpo de la Tabla
        tabla.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        tabla.setRowHeight(30);

        // Color al seleccionar: Crema Suave (Bisque)
        tabla.setSelectionBackground(new java.awt.Color(255, 228, 196)); 
        tabla.setSelectionForeground(new java.awt.Color(50, 20, 0)); // Texto Marrón muy oscuro

        // Líneas de la cuadrícula: Color Trigo
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new java.awt.Color(245, 222, 179)); 

        // 3. Arreglar fondo blanco del scroll
        if (tabla.getParent() != null && tabla.getParent().getParent() instanceof javax.swing.JScrollPane) {
            javax.swing.JScrollPane scroll = (javax.swing.JScrollPane) tabla.getParent().getParent();
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        }
    }
    

    //Metodo para los botones
    private void diseñoBotonModulo(javax.swing.JButton btn, java.awt.Color colorTema) {
        // colores estandar
        java.awt.Color colorEliminar = new java.awt.Color(255, 102, 102); // Rojo suave
        java.awt.Color colorEditar = new java.awt.Color(255, 165, 0);   // Naranja

        // Decidir el color seguntexto
        java.awt.Color colorFinal;
        String texto = btn.getText().toLowerCase();

        if (texto.contains("eliminar")) {
            colorFinal = colorEliminar; // Siempre Rojo
        } else if (texto.contains("editar")) {
            colorFinal = colorEditar;   // Siempre Naranja
        } else {
            colorFinal = colorTema;
        }

        btn.setBackground(colorFinal);
        btn.setForeground(java.awt.Color.WHITE); // Texto blanco
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(colorFinal.darker()); // Oscurecer
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(colorFinal); // Restaurar
            }
        });
    }
    
    // Método para cargar la tabla con filtros
    private void cargarTablaInventario(String filtro) {
        controlador.InsumoCtrl controlador = new controlador.InsumoCtrl();
        java.util.List<modelo.entidad.Insumo> lista = controlador.listarInsumos();

        // Nombres de columnas según Caso de Uso
        String[] columnas = {
            "Nombre", "Descripción", "Stock Actual", "Unidad", "Stock Mínimo", "F. Caducidad"
        };

        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas) {
            @Override // Hacer que la tabla no sea editable (Requisito: Solo lectura)
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (modelo.entidad.Insumo ins : lista) {
            // Lógica de filtrado (Si filtro está vacío o el nombre coincide)
            if (filtro == null || filtro.isEmpty() || 
                ins.getNombre().toLowerCase().contains(filtro.toLowerCase())) {

                Object[] fila = {
                    ins.getNombre(),
                    ins.getDescripcion(),
                    ins.getStock_actual(),   // Posición 2 (Importante para el Renderer)
                    ins.getUnidad_medida(),
                    ins.getStock_minimo(),
                    ins.getFecha_caducidad()
                };
                modelo.addRow(fila);
            }
        }

        tblInventario.setModel(modelo);
        tblInventario.setDefaultRenderer(Object.class, new InventarioRenderer());
    }
    
    // Clase ara pintar las celdas
    class InventarioRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            try {
                // Obtenemos los valores de la fila (Asegúrate que el orden coincida con tu modelo abajo)
                // Asumimos: Col 2 = Stock Actual, Col 4 = Stock Mínimo, Col 5 = Fecha Caducidad
                double stockActual = Double.parseDouble(table.getValueAt(row, 2).toString());
                double stockMinimo = Double.parseDouble(table.getValueAt(row, 4).toString());

                // REGLA ROJA: Stock Crítico
                if (stockActual <= stockMinimo) {
                    c.setBackground(new java.awt.Color(255, 102, 102)); // Rojo suave
                    c.setForeground(java.awt.Color.WHITE);
                } else {
                    // REGLA AMARILLA: Próximo a Caducar (7 dias)
                    Object fechaObj = table.getValueAt(row, 5);
                    if (fechaObj != null) {
                        String fechaStr = fechaObj.toString();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fechaCad = sdf.parse(fechaStr);

                        java.util.Date hoy = new java.util.Date();
                        long diff = fechaCad.getTime() - hoy.getTime();
                        long dias = java.util.concurrent.TimeUnit.DAYS.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);

                        if (dias <= 7 && dias >= 0) {
                            c.setBackground(new java.awt.Color(255, 255, 153)); // Amarillo
                            c.setForeground(java.awt.Color.BLACK);
                        } else {
                            // Normal
                            c.setBackground(isSelected ? table.getSelectionBackground() : java.awt.Color.WHITE);
                            c.setForeground(isSelected ? table.getSelectionForeground() : java.awt.Color.BLACK);
                        }
                    } else {
                        // Si no hay fecha, pintar normal
                        c.setBackground(isSelected ? table.getSelectionBackground() : java.awt.Color.WHITE);
                        c.setForeground(isSelected ? table.getSelectionForeground() : java.awt.Color.BLACK);
                    }
                }
            } catch (NumberFormatException | ParseException e) {
                // Si falla algo, pintar blanco por defecto
                c.setBackground(isSelected ? table.getSelectionBackground() : java.awt.Color.WHITE);
                c.setForeground(isSelected ? table.getSelectionForeground() : java.awt.Color.BLACK);
            }
            return c;
        }
    }
    
    public void cargarTablaProductos() {
        controlador.ProductoCtrl ctrl = new controlador.ProductoCtrl();
        java.util.List<modelo.entidad.Producto> lista = ctrl.listarProductos();

        String[] columnas = {"ID", "Producto", "Precio", "Stock Listo", "Estado"};
        
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (modelo.entidad.Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getId_producto(),
                p.getNombre(),
                "S/. " + p.getPrecio(),
                p.getStock_actual(), // ¡Aquí ves el stock real!
                p.getEstado()
            });
        }

        tblProductos.setModel(modelo);
        diseñoTablaProd(tblProductos); // Aplicar el estilo Chocolate/Verde
    }
    
    public void cargarComboProduccion() {
        controlador.ProductoCtrl ctrl = new controlador.ProductoCtrl();
        java.util.List<modelo.entidad.Producto> lista = ctrl.listarProductos();
        
        cmbProductoProduccion.removeAllItems();
        for (modelo.entidad.Producto p : lista) {
            cmbProductoProduccion.addItem(p); 
        }
    }

    // 2. Método para registrar la producción (Se conecta al botón Confirmar)
    private void registrarProduccion() {
        // Validaciones
        modelo.entidad.Producto prod = (modelo.entidad.Producto) cmbProductoProduccion.getSelectedItem();
        String cantStr = txtCantidadProduccion.getText();
        
        if (prod == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida mayor a 0.");
            return;
        }

        // Confirmación
        int resp = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Confirmar producción de " + cantidad + " " + prod.getNombre() + "?\n" +
            "Se descontarán los insumos del stock.", 
            "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

        if (resp == javax.swing.JOptionPane.YES_OPTION) {
            controlador.RecetaCtrl ctrl = new controlador.RecetaCtrl();
            
            // Usamos ID 1 si no tienes usuario logueado, o la variable usuarioLogueado
            int idUsuario = (usuarioLogueado != null) ? usuarioLogueado.getId_usuario() : 1;
            
            boolean exito = ctrl.registrarProduccion(prod.getId_producto(), cantidad, idUsuario);
            
            if (exito) {
                javax.swing.JOptionPane.showMessageDialog(this, "¡Producción registrada! Stock actualizado.");
                txtCantidadProduccion.setText(""); // Limpiar campo
                
                // Actualizar otras tablas para ver el cambio reflejado al instante
                cargarTablaInventario(""); 
                cargarComboInsumosMovimiento(); // Si tienes el panel de inventario
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MenuAdministrador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Logo;
    private javax.swing.JPanel NavBar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarProveedor;
    private javax.swing.JButton btnAgregarProveedor;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnConfirmarProduccion;
    private javax.swing.JButton btnConfirmarRecepcion;
    private javax.swing.JButton btnEditarInsumo;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarInsumo;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnExportarReporte;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnImprimirReporte;
    private javax.swing.JButton btnInsumos;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnIrRecetas;
    private javax.swing.JButton btnNuevoPedido;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnProduccion;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnRegistrarMovimiento;
    private javax.swing.JButton btnReportes;
    private javax.swing.JComboBox<String> cmbInsumosMovimiento;
    private javax.swing.JComboBox<Object> cmbProductoProduccion;
    private javax.swing.JComboBox<String> cmbTipoMovimiento;
    private javax.swing.JComboBox<String> cmbTipoReporte;
    private com.toedter.calendar.JDateChooser fechaFin;
    private com.toedter.calendar.JDateChooser fechaInicio;
    private javax.swing.JButton jButtonAgregarInsumo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTableInsumos;
    private javax.swing.JTable jTableProveedores;
    private javax.swing.JLabel lblStockDisponible;
    private javax.swing.JPanel paneInsumos;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelInventario;
    private javax.swing.JPanel panelPedidos;
    private javax.swing.JPanel panelProduccion;
    private javax.swing.JPanel panelProveedores;
    private javax.swing.JPanel panelReportes;
    private javax.swing.JPanel registro;
    private javax.swing.JTable tablaReporte;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscarInventario;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtCantidadMovimiento;
    private javax.swing.JTextField txtCantidadProduccion;
    // End of variables declaration//GEN-END:variables
}
