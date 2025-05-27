package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Request.PutEmployee;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UpdateEmployeeController {

    @FXML private TextField nameField, nifField, phoneField, emailField, usernameField, benefitField, entryTimeField, exitTimeField, salaryField;
    @FXML private PasswordField passwordField;

    private EmployeeDTO employee;

    public void setEmployeeData(EmployeeDTO employee) {
        this.employee = employee;
        nameField.setText(employee.getNombreEmpleado());
        nifField.setText(employee.getNifEmpleado());
        phoneField.setText(String.valueOf(employee.getTelefonoEmpleado()));
        emailField.setText(employee.getEmail());
        usernameField.setText(employee.getUsername());
        passwordField.setText(employee.getPasswordEmpleado());
        benefitField.setText(String.valueOf(employee.getBeneficioEmpleado()));
        entryTimeField.setText(employee.getHoraEntrada().toString());
        exitTimeField.setText(employee.getHoraSalida().toString());
        salaryField.setText(employee.getSalario().toString());
    }

    @FXML
    private void updateEmployee() {
        employee.setNombreEmpleado(nameField.getText());
        employee.setNifEmpleado(nifField.getText());
        employee.setTelefonoEmpleado(Integer.parseInt(phoneField.getText()));
        employee.setEmail(emailField.getText());
        employee.setUsername(usernameField.getText());
        employee.setPasswordEmpleado(passwordField.getText());
        employee.setBeneficioEmpleado(Integer.parseInt(benefitField.getText()));
        employee.setHoraEntrada(LocalDateTime.parse(entryTimeField.getText()));
        employee.setHoraSalida(LocalDateTime.parse(exitTimeField.getText()));
        employee.setSalario(new BigDecimal(salaryField.getText()));

        new PutEmployee().sendPutEmployeeRequest(employee, employee.getIdEmpleado());

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}