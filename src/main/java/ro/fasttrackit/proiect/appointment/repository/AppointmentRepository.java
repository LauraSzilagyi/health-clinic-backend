package ro.fasttrackit.proiect.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.proiect.appointment.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
