/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.siakad.zkoss.validator;

import java.util.Map;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

/**
 *
 * @author yhawin
 */
public class GenericFormValidator extends AbstractValidator{

    @Override
    public void validate(ValidationContext vc) {
        //all the bean properties
        Map<String,Property> beanProps = vc.getProperties(vc.getProperty().getBase());
         
        //first let's check the passwords match
        validatePasswords(vc, (String)beanProps.get("password").getValue(), (String)vc.getValidatorArg("retypedPassword"));
        validateBulatPositif(vc, (Integer)beanProps.get("idKurikulum").getValue());
        validateEmail(vc, (String)beanProps.get("email").getValue());

        validateCaptcha(vc, (String)vc.getValidatorArg("captcha"), (String)vc.getValidatorArg("captchaInput"));
        
    }
     
    private void validatePasswords(ValidationContext ctx, String password, String retype) {
        if(password == null || retype == null || (!password.equals(retype))) {
            this.addInvalidMessage(ctx, "password", "Your passwords do not match!");
        }
    }
     
    private void validateBulatPositif(ValidationContext ctx, int age) {
        if(age <= 0) {
            this.addInvalidMessage(ctx, "age", "Your age should be > 0!");
        }
    }
     
     
    private void validateEmail(ValidationContext ctx, String email) {
        if(email == null || !email.matches(".+@.+\\.[a-z]+")) {
            this.addInvalidMessage(ctx, "email", "Please enter a valid email!");           
        }
    }
     
    private void validateCaptcha(ValidationContext ctx, String captcha, String captchaInput) {
        if(captchaInput == null || !captcha.equals(captchaInput)) {
            this.addInvalidMessage(ctx, "captcha", "The captcha doesn't match!");
        }
    }
    
}
