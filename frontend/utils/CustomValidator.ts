//? AbstractControl: A base class for the concrete form control classes FormControl, FormGroup, and FormArray. It provides their common behaviors and properties.
//? ValidationErrors: A type representing the structure of validation errors. It's an object with key-value pairs, where the key is the error name and the value can be any.

import { AbstractControl, ValidationErrors } from '@angular/forms';

export class UsernameValidators {
  /**
   * This static method validates a username based on certain criteria.
   * It returns a function that takes an AbstractControl as an argument.
   * The function checks if the username is at least 3 characters long and contains only alphanumeric characters.
   */
  static validUsername() {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;
      if (!value) {
        return null;
      }

      const isLengthValid = value.length >= 3;
      const isContentValid = /^[a-zA-Z0-9]+$/.test(value);

      const usernameValid = isLengthValid && isContentValid;

      // If the username meets these criteria, the function returns null, indicating no validation errors.
      // If the username does not meet these criteria, the function returns an object with a 'validUsername' property set to true, indicating a validation error.
      return !usernameValid ? { validUsername: true } : null;
    };
  }
}

export class CustomValidators {
  /*
  //* Static method called passwordStrength tha returns a function that takes a control as an argument,
  //* and returns a ValidationErrors object if the validation fails, otherwise null.

  */
  static passwordStrength() {
    return (control: AbstractControl): ValidationErrors | null => {
      //* This line retrieves the current value of the form control that the validator is applied to.
      const value = control.value;
      if (!value) {
        return null;
      }

      //* Validation checks criteria
      const hasUpperCase = /[A-Z]+/.test(value);
      const hasLowerCase = /[a-z]+/.test(value);
      const hasNumeric = /[0-9]+/.test(value);
      const hasSpecial = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(value);
      const passwordValid =
        hasUpperCase &&
        hasLowerCase &&
        hasNumeric &&
        hasSpecial &&
        value.length >= 8;

      //*The password is considered valid if it meets all the criteria.
      return !passwordValid ? { passwordStrength: true } : null;
    };
  }
}
