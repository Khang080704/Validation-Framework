package org.example.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ValidatedTextField extends JTextField {
    private static final Color ERROR_COLOR = new Color(255, 220, 220);
    private static final Color SUCCESS_COLOR = new Color(220, 255, 220);
    private static final Color NORMAL_COLOR = Color.WHITE;
    private static final Color ERROR_BORDER = new Color(244, 67, 54);
    private static final Color SUCCESS_BORDER = new Color(76, 175, 80);
    
    private JLabel errorLabel;
    private ValidationState state = ValidationState.NORMAL;
    
    public enum ValidationState {
        NORMAL, SUCCESS, ERROR
    }
    
    public ValidatedTextField() {
        super();
        initialize();
    }
    
    public ValidatedTextField(int columns) {
        super(columns);
        initialize();
    }
    
    private void initialize() {
        setBackground(NORMAL_COLOR);
    }
    
    public void setValidationState(ValidationState state, String message) {
        this.state = state;
        
        switch (state) {
            case SUCCESS:
                setBackground(SUCCESS_COLOR);
                setBorder(new LineBorder(SUCCESS_BORDER, 2));
                if (errorLabel != null) {
                    errorLabel.setText("✓");
                    errorLabel.setForeground(SUCCESS_BORDER);
                }
                break;
                
            case ERROR:
                setBackground(ERROR_COLOR);
                setBorder(new LineBorder(ERROR_BORDER, 2));
                if (errorLabel != null && message != null) {
                    errorLabel.setText(message);
                    errorLabel.setForeground(ERROR_BORDER);
                }
                break;
                
            case NORMAL:
            default:
                clearValidationState();
                break;
        }
    }
    
    public void clearValidationState() {
        this.state = ValidationState.NORMAL;
        setBackground(NORMAL_COLOR);
        setBorder(UIManager.getBorder("TextField.border"));
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }
    
    public void setErrorLabel(JLabel errorLabel) {
        this.errorLabel = errorLabel;
    }
    
    public ValidationState getValidationState() {
        return state;
    }
    
    public void setValid() {
        setValidationState(ValidationState.SUCCESS, null);
    }
    
    public void setInvalid(String message) {
        setValidationState(ValidationState.ERROR, message);
    }
}
