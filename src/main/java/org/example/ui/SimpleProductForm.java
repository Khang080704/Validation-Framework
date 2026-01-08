package org.example.ui;

import org.example.common.ValidationObserver;
import org.example.common.ValidationViolation;
import org.example.entities.Product;
import org.example.providers.AnnotationClassValidatorProvider;
import org.example.validators.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimpleProductForm extends JFrame {
    private Validator validator;
    private Product product = new Product();
    
    private JTextField nameField;
    private JTextField quantityField;
    private JTextArea statusArea;
    
    public SimpleProductForm() {
        this.validator = new Validator(List.of(
            new AnnotationClassValidatorProvider()
        ));
        
        this.validator.addObserver(new ConsoleValidationObserver());
        
        this.validator.addObserver(new UIValidationObserver());
        
        setupUI();
    }
    
    private void setupUI() {
        setTitle("Product Form - Simple Validation Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        formPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);
        
        JButton validateBtn = new JButton("Validate");
        validateBtn.addActionListener(e -> validateProduct());
        formPanel.add(new JLabel());
        formPanel.add(validateBtn);
        
        add(formPanel, BorderLayout.CENTER);
        
        statusArea = new JTextArea(8, 40);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statusArea.setBorder(BorderFactory.createTitledBorder("Validation Status"));
        add(new JScrollPane(statusArea), BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void validateProduct() {
        product.setName(nameField.getText());
        
        try {
            product.setQuantity(Integer.parseInt(quantityField.getText()));
        } catch (NumberFormatException e) {
            product.setQuantity(0);
        }
        
        validator.validate(product);
    }
    
    private class ConsoleValidationObserver implements ValidationObserver {
        @Override
        public void onValidationSuccess() {
            System.out.println("✓ Validation SUCCESS");
        }
        
        @Override
        public void onValidationFailure(List<ValidationViolation> violations) {
            System.err.println("✗ Validation FAILED:");
            for (ValidationViolation v : violations) {
                System.err.println("  - " + v.getPath() + ": " + v.getMessages());
            }
        }
    }
    
    private class UIValidationObserver implements ValidationObserver {
        @Override
        public void onValidationSuccess() {
            statusArea.setForeground(new Color(0, 128, 0));
            statusArea.setText("✓ VALIDATION SUCCESSFUL\n\n" +
                             "Product Details:\n" +
                             "  Name: " + product.getName() + "\n" +
                             "  Quantity: " + product.getQuantity() + "\n\n" +
                             "All validation rules passed!");
        }
        
        @Override
        public void onValidationFailure(List<ValidationViolation> violations) {
            statusArea.setForeground(Color.RED);
            StringBuilder sb = new StringBuilder();
            sb.append("✗ VALIDATION FAILED\n\n");
            sb.append("Found ").append(violations.size()).append(" error(s):\n\n");
            
            for (ValidationViolation v : violations) {
                sb.append("Field: ").append(v.getPath()).append("\n");
                for (String msg : v.getMessages()) {
                    sb.append("  • ").append(msg).append("\n");
                }
                sb.append("\n");
            }
            
            statusArea.setText(sb.toString());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleProductForm form = new SimpleProductForm();
            form.setVisible(true);
        });
    }
}
