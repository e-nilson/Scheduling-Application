TimeZone EST = TimeZone.getTimeZone("America/New_York");
        Long offsetToEST = Long.valueOf(EST.getOffset(new Date().getTime()) /1000 /60);
        LocalDateTime startTime = LocalDateTime.parse(startTextField.getText(), format).minus(Duration.ofSeconds(offsetToUTC));
        startTime = startTime.plus(Duration.ofMinutes(offsetToEST));
        LocalDateTime endTime = LocalDateTime.parse(endTextField.getText(), format).minus(Duration.ofSeconds(offsetToUTC));
        endTime = endTime.plus(Duration.ofMinutes(offsetToEST));
        LocalTime businessHoursStart = LocalTime.of(8, 00);
        LocalTime businessHoursEnd = LocalTime.of(22, 00);

        try {
            int appointment_ID = 0;
            for (Appointment appointment : ListProvider.getAllAppointments()) {
                if (appointment.getAppointment_ID() > appointment_ID)
                    appointment_ID = (appointment.getAppointment_ID());
                appointment_ID = ++appointment_ID;
            }
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            Timestamp start = Timestamp.valueOf(startTextField.getText());
            Timestamp end = Timestamp.valueOf(endTextField.getText());
            int user_ID = valueOf(userIDTextField.getText());
            int contact_ID = valueOf(contactIDTextField.getText());
            int customer_ID = valueOf(customerIDTextField.getText());

            Appointment overlapAppt = AppointmentDB.appointmentOverlap(start, end, customer_ID);

            // checks for missing values
            if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty()
                    || startTextField.getText().isEmpty() || endTextField.getText().isEmpty() || customerIDTextField.getText().isEmpty()
                    || userIDTextField.getText().isEmpty() || contactIDTextField.getText().isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setTitle("Missing values");
                errorAlert.setContentText("Please enter missing values.");
                errorAlert.showAndWait();
            }

            // checks if appointment is during business hours
            if (startTime.toLocalTime().isBefore(businessHoursStart) || endTime.toLocalTime().isAfter(businessHoursEnd)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Please enter a time between 08:00 EST and 10:00 EST.");
                errorAlert.showAndWait();
            }

            // checks for overlapping appointments
            if (overlapAppt != null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                DialogPane dialogPane1 = errorAlert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                errorAlert.setContentText("Appointment time already taken, please enter different start and end times.");
                errorAlert.showAndWait();

            } else {
                appointmentToUpdate.setTitle(title);
                appointmentToUpdate.setDescription(description);
                appointmentToUpdate.setLocation(location);
                appointmentToUpdate.setType(type);
                appointmentToUpdate.setStart(start);
                appointmentToUpdate.setEnd(end);
                appointmentToUpdate.setUser_ID(user_ID);
                appointmentToUpdate.setContact_ID(contact_ID);
                appointmentToUpdate.setCustomer_ID(customer_ID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainAppointment.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();

                AppointmentDB.updateAppointment(
                        Integer.valueOf(appointmentIDTextField.getText()),
                        titleTextField.getText(),
                        descriptionTextField.getText(),
                        locationTextField.getText(),
                        typeTextField.getText(),
                        Timestamp.valueOf(startTextField.getText()),
                        Timestamp.valueOf(endTextField.getText()),
                        Integer.valueOf(userIDTextField.getText()),
                        Integer.valueOf(contactIDTextField.getText()),
                        Integer.valueOf(customerIDTextField.getText()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
