package org.example.builder;

import org.example.validators.Basic.NumberValidator.RangeValidator;
import org.example.validators.Basic.StringValidator.LengthValidator;
import org.example.validators.Basic.StringValidator.MaxLengthValidator;
import org.example.validators.Basic.StringValidator.MinLengthValidator;
import org.example.validators.Basic.StringValidator.UpperCaseValidator;
import org.example.validators.ComplexValidor.EmailValidator;

public class StringValidatorBuilder extends AbstractValidatorBuilder<String, StringValidatorBuilder>{
    private StringValidatorBuilder() {
        super();
    }


    @Override
    protected StringValidatorBuilder self() {
        return this;
    }

    public static StringValidatorBuilder builder(){
        return new StringValidatorBuilder();
    }

    public StringValidatorBuilder email(){
        manager.addValidator(new EmailValidator());
        return self();
    }
    public StringValidatorBuilder minLength(int minLength){
        manager.addValidator(new MinLengthValidator(minLength));
        return self();
    }
    public StringValidatorBuilder maxLength(int maxLength){
        manager.addValidator(new MaxLengthValidator(maxLength));
        return self();
    }
    public StringValidatorBuilder range(int min, int max){
        manager.addValidator(new LengthValidator(min, max));
        return self();
    }
    public StringValidatorBuilder upperCase() {
        manager.addValidator(new UpperCaseValidator());
        return self();
    }

}
