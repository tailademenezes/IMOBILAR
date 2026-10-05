import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author taina
 */
public class TelaRelatorios extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaRelatorios.class.getName());

    /**
     * Creates new form TelaRelatorios
     */
    public TelaRelatorios() {
        initComponents();
    }
private void gerarRelatorio() {
    String tipo = jComboBox1.getSelectedItem().toString();

    JOptionPane.showMessageDialog(this, "[" + tipo + "]");

    String sql = "";
    switch (tipo) {
        case "Imoveis":
            sql = "SELECT id_imovel AS ID, endereco AS Endereco, "
                + "tipo_imovel AS Tipo, valor_aluguel AS Valor, "
                + "status AS Status "
                + "FROM imovel WHERE id_proprietario = ?";
            break;

        case "Inquilinos":
            sql = "SELECT i.id_inquilino AS ID, i.nome AS Nome, "
                + "i.cpf AS CPF, i.telefone AS Telefone, "
                + "i.email AS Email "
                + "FROM inquilino i "
                + "INNER JOIN imovel im ON i.id_imovel = im.id_imovel "
                + "WHERE im.id_proprietario = ?";
            break;

        case "Contratos":
            sql = "SELECT c.id_contrato AS ID, i.nome AS Inquilino, "
                + "im.endereco AS Imovel, c.valor_aluguel AS Valor, "
                + "c.data_inicio AS Inicio, c.data_fim AS Fim, "
                + "c.status AS Status "
                + "FROM contrato c "
                + "INNER JOIN imovel im ON c.id_imovel = im.id_imovel "
                + "INNER JOIN inquilino i ON c.id_inquilino = i.id_inquilino "
                + "WHERE im.id_proprietario = ?";
            break;

        case "Alugueis e pagamentos":
            sql = "SELECT a.id_aluguel AS ID, i.nome AS Inquilino, "
                + "a.valor AS Valor, a.data_vencimento AS Vencimento, "
                + "a.status AS Status "
                + "FROM aluguel a "
                + "INNER JOIN contrato c ON a.id_contrato = c.id_contrato "
                + "INNER JOIN imovel im ON c.id_imovel = im.id_imovel "
                + "INNER JOIN inquilino i ON c.id_inquilino = i.id_inquilino "
                + "WHERE im.id_proprietario = ?";
            break;

        case "Manutencoes":
            sql = "SELECT m.id_manutencao AS ID, "
                + "im.endereco AS Imovel, i.nome AS Inquilino, "
                + "m.descricao AS Descricao, "
                + "m.data_solicitacao AS Data, m.status AS Status "
                + "FROM manutencao m "
                + "INNER JOIN imovel im ON m.id_imovel = im.id_imovel "
                + "INNER JOIN inquilino i ON m.id_inquilino = i.id_inquilino "
                + "WHERE im.id_proprietario = ?";
            break;
    }

    try (Connection conexao = Conexao.conectar();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setInt(1, Sessao.idProprietario);

        try (ResultSet resultado = stmt.executeQuery()) {
            ResultSetMetaData dados = resultado.getMetaData();
            int quantidadeColunas = dados.getColumnCount();

            DefaultTableModel modelo = new DefaultTableModel();

            for (int i = 1; i <= quantidadeColunas; i++) {
                modelo.addColumn(dados.getColumnLabel(i));
            }

            while (resultado.next()) {
                Object[] linha = new Object[quantidadeColunas];

                for (int i = 1; i <= quantidadeColunas; i++) {
                    linha[i - 1] = resultado.getObject(i);
                }

                modelo.addRow(linha);
            }

            jTable1.setModel(modelo);

            String registro =
                "INSERT INTO relatorio (id_proprietario, tipo, data_geracao) "
                + "VALUES (?, ?, NOW())";

            try (PreparedStatement salvar =
                     conexao.prepareStatement(registro)) {
                salvar.setInt(1, Sessao.idProprietario);
                salvar.setString(2, tipo);
                salvar.executeUpdate();
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                    this, "Nenhum registro encontrado para esse relatorio."
                );
            } else {
                JOptionPane.showMessageDialog(
                    this, "Relatorio gerado com sucesso!"
                );
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this, "Erro ao gerar relatorio: " + e.getMessage()
        );
        e.printStackTrace();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("RELATÓRIOS");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("TIPO DE RELATÓRIO:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("RESULTADO DOS RELATÓRIOS:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Imoveis", "Inquilinos", "Contratos", "Alugueis e pagamentos", "Manutencoes" }));

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText("GERAR RELATÓRIO");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "ENDEREÇO", "TIPO", "VALOR"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setText("LIMPAR");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setText("VOLTAR");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
gerarRelatorio();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
modelo.setRowCount(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    tela3prop menu = new tela3prop();
    menu.setVisible(true);
    this.dispose();

    }//GEN-LAST:event_jButton3ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TelaRelatorios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
