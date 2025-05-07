// Helper interface for DocumentListener
@FunctionalInterface
interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
    void update();
    default void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
    default void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
    default void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
}
